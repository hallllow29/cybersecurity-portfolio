const Restaurante = require("../../models/Accounts/Restaurante");

exports.getRestaurantes = async (req, res) => {
  try {
    const restaurantes = await Restaurante.find();
    res.status(200).json(restaurantes);
  } catch (err) {
    console.error("Erro ao buscar restaurantes:", err);
    res.status(500).json({ message: "Erro ao buscar restaurantes." });
  }
};
