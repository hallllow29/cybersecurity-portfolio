const Menu = require("../../models/Core/Menu");

exports.listarMenus = async (req, res) => {
  try {
    const restauranteId = req.query.restaurante; // opcional: pode ser filtrado
    const filtros = restauranteId ? { restaurante: restauranteId } : {};

    const menus = await Menu.find(filtros).populate("pratos");
    res.status(200).json(menus);
  } catch (err) {
    console.error("Erro ao listar menus:", err);
    res.status(500).json({ message: "Erro ao listar menus." });
  }
};

exports.obterMenuPorId = async (req, res) => {
  try {
    const menu = await Menu.findById(req.params.id).populate("pratos");
    if (!menu) {
      return res.status(404).json({ message: "Menu não encontrado." });
    }
    res.status(200).json(menu);
  } catch (err) {
    console.error("Erro ao obter menu:", err);
    res.status(500).json({ message: "Erro ao obter menu." });
  }
};

exports.verMenuDetalhes = async (req, res) => {
  try {
    const menu = await Menu.findById(req.params.id).populate("pratos").lean();

    if (!menu) {
      return res.status(404).json({ message: "Menu não encontrado." });
    }

    const categorias = {};
    menu.pratos.forEach((prato) => {
      const categoria = prato.categoria || "Outros";
      if (!categorias[categoria]) categorias[categoria] = [];
      categorias[categoria].push(prato);
    });

    res.status(200).json({
      menu,
      categorias,
    });
  } catch (error) {
    console.error("Erro ao carregar detalhes do menu:", error);
    res.status(500).json({ erro: "Erro ao carregar menu." });
  }
};

exports.listarMenusDoMeuRestaurante = async (req, res) => {
  try {
    const restauranteId = req.user?.restauranteId;

    if (!restauranteId) {
      return res.status(403).json({ message: "Restaurante não autenticado." });
    }

    const menus = await Menu.find({ restaurante: restauranteId }).populate(
      "pratos"
    );
    res.status(200).json(menus);
  } catch (err) {
    console.error("Erro ao listar menus do próprio restaurante:", err);
    res.status(500).json({ message: "Erro ao listar menus." });
  }
};
