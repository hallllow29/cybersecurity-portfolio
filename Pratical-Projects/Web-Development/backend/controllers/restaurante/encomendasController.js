const Encomenda = require("../../models/Core/Encomenda");

const ESTADOS_VALIDOS = ["pendente", "aceite", "preparacao", "enviado", "entregue", "cancelada"];

exports.listarEncomendas = async (req, res) => {
  const restauranteId = req.user.restauranteId;
  const { status } = req.query;

  try {
    const filtro = { restaurante: restauranteId };
    if (status) filtro.status = status;

    const encomendas = await Encomenda.find(filtro)
      .populate("cliente", "nome email telefone")
      .populate("pratos.prato")
      .sort({ criadoEm: -1 });

    res.status(200).json(encomendas);
  } catch (err) {
    console.error("Erro ao listar encomendas:", err);
    res.status(500).json({ message: "Erro ao listar encomendas." });
  }
};

exports.detalhesEncomenda = async (req, res) => {
  const { id } = req.params;
  const restauranteId = req.user.restauranteId;

  try {
    const encomenda = await Encomenda.findOne({ _id: id, restaurante: restauranteId })
      .populate("cliente", "nome email telefone")
      .populate("pratos.prato");

    if (!encomenda) {
      return res.status(404).json({ message: "Encomenda não encontrada." });
    }

    res.status(200).json(encomenda);
  } catch (err) {
    console.error("Erro ao buscar detalhes:", err);
    res.status(500).json({ message: "Erro ao buscar detalhes da encomenda." });
  }
};

exports.atualizarEstadoEncomenda = async (req, res) => {
  const { id } = req.params;
  const { novoEstado } = req.body;
  const restauranteId = req.user.restauranteId;

  if (!ESTADOS_VALIDOS.includes(novoEstado)) {
    return res.status(400).json({ message: "Estado inválido." });
  }

  try {
    const encomenda = await Encomenda.findOne({ _id: id, restaurante: restauranteId });

    if (!encomenda) {
      return res.status(404).json({ message: "Encomenda não encontrada." });
    }

    encomenda.status = novoEstado;
    await encomenda.save();

    res.status(200).json({ message: "Estado atualizado com sucesso." });
  } catch (err) {
    console.error("Erro ao atualizar estado:", err);
    res.status(500).json({ message: "Erro ao atualizar estado da encomenda." });
  }
};
