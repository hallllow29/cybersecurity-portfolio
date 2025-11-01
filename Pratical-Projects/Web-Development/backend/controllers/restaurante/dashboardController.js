const Restaurante = require("../../models/Accounts/Restaurante");
const Encomenda = require("../../models/Core/Encomenda");
const Prato = require("../../models/Core/Prato");
const Menu = require("../../models/Core/Menu");

exports.getDashboard = async (req, res) => {
  const restauranteId = req.user?.restauranteId;

  if (!restauranteId) {
    return res.status(400).json({ message: "Restaurante não autenticado." });
  }

  try {
    const restaurante = await Restaurante.findById(restauranteId);
    if (!restaurante) {
      return res.status(404).json({ message: "Restaurante não encontrado." });
    }

    const [menus, pratos, ultimosPratos, totalEncomendas] = await Promise.all([
      Menu.find({ restaurante: restauranteId }).populate("pratos"),
      Prato.find({ restaurante: restauranteId }),
      Prato.find({ restaurante: restauranteId }).sort({ createdAt: -1 }).limit(5),
      Encomenda.countDocuments({ restaurante: restauranteId }),
    ]);

    res.status(200).json({
      restaurante,
      estatisticas: {
        numEncomendas: totalEncomendas,
        pratosAtivos: pratos.length,
        menusAtivos: menus.length,
      },
      menus,
      pratos: ultimosPratos,
    });
  } catch (err) {
    console.error("Erro ao carregar dashboard:", err);
    res.status(500).json({ message: "Erro interno ao carregar o dashboard." });
  }
};
