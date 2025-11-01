const Menu = require("../../models/Core/Menu");

exports.postCriarMenu = async (req, res) => {
  const restauranteId = req.user.restauranteId;

  try {
    const { nome, descricao } = req.body;
    if (!nome || !req.body.pratos) {
      return res.status(400).json({ message: "Todos os campos são obrigatórios." });
    }

    const pratos = Array.isArray(req.body.pratos)
      ? req.body.pratos
      : JSON.parse(req.body.pratos);

    if (pratos.length > 10) {
      return res.status(400).json({ message: "Um menu só pode ter no máximo 10 pratos." });
    }

    const imagem = req.file
      ? `/images/restaurantes/${restauranteId}/menus/${req.file.filename}`
      : null;

    const novoMenu = new Menu({
      nome: nome.trim(),
      descricao: descricao?.trim() || "",
      pratos,
      imagem,
      restaurante: restauranteId,
    });

    await novoMenu.save();
    res.status(201).json({ message: "Menu criado com sucesso." });
  } catch (err) {
    console.error("Erro ao criar menu:", err);
    res.status(500).json({ message: "Erro ao criar menu." });
  }
};

exports.putEditarMenu = async (req, res) => {
  const { id } = req.params;
  const restauranteId = req.user.restauranteId;

  try {
    const { nome, descricao } = req.body;
    if (!nome || !req.body.pratos) {
      return res.status(400).json({ message: "Todos os campos são obrigatórios." });
    }

    const pratos = Array.isArray(req.body.pratos)
      ? req.body.pratos
      : JSON.parse(req.body.pratos);

    if (pratos.length > 10) {
      return res.status(400).json({ message: "Um menu só pode ter no máximo 10 pratos." });
    }

    const imagem = req.file
      ? `/images/restaurantes/${restauranteId}/menus/${req.file.filename}`
      : undefined;

    const updateFields = {
      nome: nome.trim(),
      descricao: descricao?.trim() || "",
      pratos,
    };

    if (imagem) updateFields.imagem = imagem;

    await Menu.findOneAndUpdate(
      { _id: id, restaurante: restauranteId },
      updateFields
    );

    res.status(200).json({ message: "Menu atualizado com sucesso." });
  } catch (err) {
    console.error("Erro ao editar menu:", err);
    res.status(500).json({ message: "Erro ao editar menu." });
  }
};

exports.postApagarMenu = async (req, res) => {
  const { id } = req.params;
  const restauranteId = req.user.restauranteId;

  try {
    await Menu.deleteOne({ _id: id, restaurante: restauranteId });
    res.status(200).json({ message: "Menu apagado com sucesso." });
  } catch (err) {
    console.error("Erro ao apagar menu:", err);
    res.status(500).json({ message: "Erro ao apagar menu." });
  }
};
