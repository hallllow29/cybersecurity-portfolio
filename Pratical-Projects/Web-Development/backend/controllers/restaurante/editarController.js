const Restaurante = require("../../models/Accounts/Restaurante");

exports.editarPerfilAPI = async (req, res) => {
  try {
    const restauranteId = req.user?.restauranteId;
    if (!restauranteId) {
      return res.status(400).json({ erro: "Restaurante não autenticado." });
    }

    const restaurante = await Restaurante.findById(restauranteId);
    if (!restaurante) {
      return res.status(404).json({ erro: "Restaurante não encontrado." });
    }

    const campos = [
      "nome", "pessoaResponsavel", "telefone", "nif", "morada",
      "tipoCozinha", "tempoConfecao", "tempoEntrega", "raioEntregaKm",
      "maxEncomendasPorHora",
    ];

    campos.forEach((campo) => {
      if (req.body[campo]) {
        restaurante[campo] = req.body[campo];
      }
    });

    if (req.body.codigoPostal) {
      restaurante.codigoPostal = req.body.codigoPostal.trim();
    }

    if (req.body.metodosDePagamento || req.body.metodosPagamento) {
      try {
        // Aceita ambos para compatibilidade, mas guarda sempre em metodosPagamento
        const metodos = req.body.metodosDePagamento
          ? JSON.parse(req.body.metodosDePagamento)
          : JSON.parse(req.body.metodosPagamento);
        restaurante.metodosPagamento = metodos;
      } catch (e) {
        console.error("Métodos de pagamento inválidos:", e);
      }
    }

    if (req.body.horarioFuncionamento) {
      try {
        restaurante.horarioFuncionamento = JSON.parse(req.body.horarioFuncionamento);
      } catch (e) {
        return res.status(400).json({ erro: "Formato inválido para horário." });
      }
    }

    if (req.files?.logo?.[0]) {
      restaurante.logoUrl = `/images/restaurantes/${restauranteId}/${req.files.logo[0].filename}`;
    }
    if (req.files?.capa?.[0]) {
      restaurante.capaUrl = `/images/restaurantes/${restauranteId}/${req.files.capa[0].filename}`;
    }

    await restaurante.save();
    res.status(200).json({ sucesso: true, restaurante });
  } catch (err) {
    console.error("Erro ao atualizar perfil:", err);
    res.status(500).json({ erro: "Erro interno ao atualizar perfil." });
  }
};
