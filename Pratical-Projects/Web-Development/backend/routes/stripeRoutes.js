const express = require("express");
const router = express.Router();
const stripe = require("stripe")(process.env.STRIPE_SECRET_KEY);
const Cliente = require("../models/Accounts/Cliente");
const Encomenda = require("../models/Core/Encomenda");
const autenticadoJWT = require("../middlewares/auth/autenticadoJWT"); // para o token
const { autenticadoCliente } = require("../middlewares/auth/roles");

router.post("/pagamento", autenticadoJWT, autenticadoCliente, async (req, res) =>  {
  try {
    const session = await stripe.checkout.sessions.create({
      payment_method_types: ["card"],
      line_items: [
        {
          price_data: {
            currency: "eur",
            product_data: {
              name: "Encomenda ServePoint",
            },
            unit_amount: req.body.total * 100,
          },
          quantity: 1,
        },
      ],
      mode: "payment",
      success_url: "http://localhost:4200/sucesso?session_id={CHECKOUT_SESSION_ID}",
      cancel_url: "http://localhost:4200/cancelado",
      metadata: {
        userId: req.user.id,
        carrinho: JSON.stringify(req.body.carrinho),
        restaurante: req.body.restaurante,
        morada: req.body.moradaEntrega,
        total: req.body.total.toString()
      },
    });

    res.json({ sessionId: session.id });
  } catch (err) {
    console.error("Erro ao criar sessão Stripe:", err);
    res.status(500).json({ error: "Erro ao criar sessão de pagamento." });
  }
});

router.post("/pagamento/webhook", (req, res) => {
	const sig = req.headers["stripe-signature"];
	let event;

	try {
		event = stripe.webhooks.constructEvent(req.rawBody, sig, process.env.STRIPE_WEBHOOK_SECRET);
	} catch (err) {
		console.error("Erro no webhook:", err.message);
		return res.status(400).send(`Webhook Error: ${err.message}`);
	}

	if (event.type === "checkout.session.completed") {
		const session = event.data.object;
		const userId = session.metadata.userId;
		const carrinho = JSON.parse(session.metadata.carrinho);
	}

	res.status(200).end();
});

console.log("🛣️ A preparar rota POST /confirmar");
router.post("/confirmar", async (req, res) => {
  try {
    const { sessionId } = req.body;

    const session = await stripe.checkout.sessions.retrieve(sessionId);

    if (session.payment_status !== "paid") {
      console.warn("⚠️ Pagamento não concluído");
      return res.status(400).json({ erro: "Pagamento ainda não concluído." });
    }

    const metadata = session.metadata;
    if (!metadata) {
      return res.status(400).json({ erro: "Metadata Stripe em falta." });
    }

    const clienteInfo = await Cliente.findOne({ userId: metadata.userId });
    if (!clienteInfo) {
      return res.status(404).json({ erro: "Cliente não encontrado." });
    }

    const novaEncomenda = new Encomenda({
      cliente: clienteInfo._id,
      restaurante: metadata.restaurante,
      pratos: metadata.carrinho ? JSON.parse(metadata.carrinho) : [],
      total: parseFloat(session.amount_total / 100),
      metodoPagamento: "Cartão de Crédito/Débito",
      moradaEntrega: metadata.morada || "Desconhecida",
      dadosPagamento: {
        stripeSessionId: session.id,
        stripePaymentIntent: session.payment_intent,
      },
      status: "pendente",
    });

    await novaEncomenda.save();

    const io = req.app.get("io");
    io.to(`restaurante_${metadata.restaurante}`).emit("nova-encomenda", {
      clienteId: clienteInfo.userId,
      nomeCliente: clienteInfo.nome,
      encomendaId: novaEncomenda._id,
      total: novaEncomenda.total,
    });

    console.log("✅ Encomenda criada após pagamento:", novaEncomenda._id);
    res.json({ sucesso: true });

  } catch (err) {
    console.error("❌ Erro ao confirmar Stripe:", err);
    res.status(500).json({ erro: "Erro ao confirmar pagamento." });
  }
});

module.exports = router;
