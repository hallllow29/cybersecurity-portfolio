const Prato = require("../../models/Core/Prato");
const validator = require("validator");

exports.listar = async (req, res) => {
  const restauranteId = req.query.restaurante;

  if (!restauranteId || !validator.isMongoId(restauranteId)) {
    return res.status(400).json({ message: "ID de restaurante inválido." });
  }

  try {
    const pratos = await Prato.find({ restaurante: restauranteId });
    res.status(200).json(pratos);
  } catch (error) {
    console.error("Erro ao listar pratos:", error);
    res.status(500).json({ message: "Erro ao listar pratos." });
  }
};

exports.getDetalhes = async (req, res) => {
  const { id } = req.params;

  if (!validator.isMongoId(id)) {
    return res.status(400).json({ message: "ID inválido." });
  }

  try {
    const prato = await Prato.findById(id);
    if (!prato) return res.status(404).json({ message: "Prato não encontrado." });

    res.status(200).json(prato);
  } catch (error) {
    console.error("Erro ao obter prato:", error);
    res.status(500).json({ message: "Erro ao obter prato." });
  }
};
