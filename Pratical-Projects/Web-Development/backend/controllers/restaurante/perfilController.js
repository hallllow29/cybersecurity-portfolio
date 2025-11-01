const Restaurante = require("../../models/Accounts/Restaurante");
const User = require("../../models/Accounts/User");

exports.getPerfil = async (req, res) => {
  try {
    const restauranteId = req.user.restauranteId;
    if (!restauranteId) {
      return res.status(400).json({ message: "Restaurante não autenticado." });
    }

    const restaurante = await Restaurante.findById(restauranteId);
    if (!restaurante) {
      return res.status(404).json({ message: "Restaurante não encontrado." });
    }

    const user = await User.findById(restaurante.ownerId);
    res.status(200).json({
      ...restaurante.toObject(),
      email: user?.email || "Não disponível",
    });
  } catch (err) {
    console.error("Erro ao carregar perfil:", err);
    res.status(500).json({ message: "Erro ao carregar perfil do restaurante." });
  }
};
