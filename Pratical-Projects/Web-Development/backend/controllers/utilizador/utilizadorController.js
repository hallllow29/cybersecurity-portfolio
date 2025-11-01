const Prato = require("../../models/Core/Prato");
const Encomenda = require("../../models/Core/Encomenda");
const Menu = require("../../models/Core/Menu");
const Restaurante = require("../../models/Accounts/Restaurante");
const Cliente = require("../../models/Accounts/Cliente");

exports.verMenu = async (req, res) => {
  const restauranteId = req.params.id;

  try {
    const menus = await Menu.find({ restaurante: restauranteId }).populate(
      "pratos"
    );
    const restaurante = await Restaurante.findById(restauranteId);
    const metodosPagamento = restaurante?.metodosPagamento || [];

    res.status(200).json({ menus, metodosPagamento });
  } catch (err) {
    console.error("Erro ao buscar menu:", err);
    res.status(500).json({ message: "Erro ao buscar menu" });
  }
};

exports.adicionarAoCarrinho = (req, res) => {
  const { pratoId } = req.body;
  if (!req.session.carrinho) req.session.carrinho = [];
  req.session.carrinho.push(pratoId);
  res.redirect("back");
};

exports.verCarrinho = async (req, res) => {
  try {
    const ids = req.session.carrinho || [];
    const pratos = await Prato.find({ _id: { $in: ids } });

    res.render("utilizador/carrinho", { pratos });
  } catch (err) {
    console.error("Erro ao carregar carrinho:", err);
    res.status(500).json({ message: "Erro ao carregar carrinho." });
  }
};

exports.criarEncomenda = async (req, res) => {
  try {
    const utilizadorId = req.user.id;
    const clienteInfo = await Cliente.findOne({ userId: utilizadorId });

    if (!clienteInfo) {
      return res.status(404).json({ message: "Cliente não encontrado." });
    }

    const {
      restaurante,
      pratos,
      total,
      metodoPagamento,
      moradaEntrega,
      dadosPagamento,
    } = req.body;

    if (!pratos?.length || !metodoPagamento || !restaurante || !moradaEntrega) {
      return res.status(400).json({ message: "Dados incompletos." });
    }

    const novaEncomenda = new Encomenda({
      cliente: clienteInfo._id,
      restaurante,
      pratos,
      total,
      metodoPagamento,
      moradaEntrega,
      dadosPagamento,
      status: "pendente",
    });

    await novaEncomenda.save();

    let referenciaGerada = null;
    if (metodoPagamento === "Referência Multibanco") {
      referenciaGerada = {
        entidade: "12345",
        referencia: Math.floor(
          100000000 + Math.random() * 900000000
        ).toString(),
        valor: total,
      };

      novaEncomenda.dadosPagamento = { referencia: referenciaGerada };
      await novaEncomenda.save();
    }

    if (!restaurante) {
      console.warn("❌ restaurante ID está undefined no corpo da requisição!");
    } else {
      console.log("📢 Emitindo nova-encomenda para restaurante:", restaurante);
    }

    const io = req.app.get("io");
    io.to(`restaurante_${restaurante}`).emit("nova-encomenda", {
      clienteId: req.user.id,
      nomeCliente: req.user.nome,
      encomendaId: novaEncomenda._id,
      total: novaEncomenda.total,
    });

    return res.status(201).json({
      message: "Encomenda criada com sucesso.",
      ...(referenciaGerada ? { referencia: referenciaGerada } : {}),
    });
  } catch (err) {
    console.error("Erro ao criar encomenda:", err);
    res.status(500).json({ message: "Erro ao criar encomenda." });
  }
};

exports.verEncomendas = async (req, res) => {
  try {
    const clienteId = req.user.refId; // 👈 correto!

    const encomendas = await Encomenda.find({ cliente: clienteId })
      .sort({ createdAt: -1 })
      .populate("restaurante", "nome") // 👈 aqui
      .populate("pratos.prato", "nome"); // 👈 importante também

    res.status(200).json(encomendas);
  } catch (err) {
    console.error("❌ Erro ao buscar encomendas:", err);
    res.status(500).json({ erro: "Erro ao obter encomendas" });
  }
};

exports.cancelarEncomenda = async (req, res) => {
  try {
    const encomenda = await Encomenda.findOne({
      _id: req.params.id,
      cliente: req.user.id,
      status: "pendente",
    });

    if (!encomenda) {
      return res
        .status(404)
        .json({ message: "Encomenda não encontrada ou já processada." });
    }

    const minutos = (new Date() - encomenda.createdAt) / 1000 / 60;
    if (minutos > 5) {
      return res
        .status(400)
        .json({ message: "Não é possível cancelar após 5 minutos." });
    }

    encomenda.status = "cancelada";
    await encomenda.save();

    res.status(200).json({ message: "Encomenda cancelada com sucesso." });
  } catch (err) {
    console.error("Erro ao cancelar encomenda:", err);
    res.status(500).json({ message: "Erro ao cancelar encomenda." });
  }
};

exports.listarRestaurantes = async (req, res) => {
  try {
    const restaurantes = await Restaurante.find(
      { validado: true }, 
      "nome tipoCozinha logoUrl capaUrl validado localizacao"
    );
    res.status(200).json(restaurantes);
  } catch (err) {
    console.error("Erro ao listar restaurantes:", err);
    res.status(500).json({ message: "Erro ao listar restaurantes." });
  }
};

exports.verDetalhesRestaurante = async (req, res) => {
  try {
    const restaurante = await Restaurante.findById(req.params.id);
    if (!restaurante) {
      return res.status(404).json({ mensagem: "Restaurante não encontrado" });
    }
    res.json(restaurante);
  } catch (error) {
    console.error("Erro ao buscar restaurante:", error);
    res.status(500).json({ mensagem: "Erro ao buscar restaurante" });
  }
};
