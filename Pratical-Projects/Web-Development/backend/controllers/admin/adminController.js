const mongoose = require("mongoose");
const User = require("../../models/Accounts/User");
const Restaurante = require("../../models/Accounts/Restaurante");

exports.getDashboard = async (req, res) => {
  try {
    const utilizadores = await User.find().lean();
    const restaurantes = await Restaurante.find().lean();

    res.status(200).json({
      totalUtilizadores: utilizadores.length,
      totalRestaurantes: restaurantes.length,
      utilizadores,
      restaurantes,
    });
  } catch (error) {
    console.error("Erro ao obter dashboard do admin:", error);
    res.status(500).json({ error: "Erro interno ao obter dashboard." });
  }
};


exports.validarRestaurante = async (req, res) => {
  const { id } = req.params;

  if (!mongoose.isValidObjectId(id)) {
    return res.status(400).json({ error: "ID de restaurante inválido." });
  }

  try {
    const result = await Restaurante.findByIdAndUpdate(id, { validado: true });

    if (!result) {
      return res.status(404).json({ error: "Restaurante não encontrado." });
    }

    res.json({ message: "Restaurante validado com sucesso." });
  } catch (error) {
    console.error("Erro ao validar restaurante:", error);
    res.status(500).json({ error: "Erro interno ao validar restaurante." });
  }
};

exports.removerRestaurante = async (req, res) => {
  const { id } = req.params;

  if (!mongoose.isValidObjectId(id)) {
    return res.status(400).json({ error: "ID inválido." });
  }

  try {
    const result = await Restaurante.findByIdAndDelete(id);
    if (!result) {
      return res.status(404).json({ error: "Restaurante não encontrado." });
    }

    res.json({ message: "Restaurante removido com sucesso." });
  } catch (error) {
    console.error("Erro ao remover restaurante:", error);
    res.status(500).json({ error: "Erro interno ao remover restaurante." });
  }
};

exports.removerUtilizador = async (req, res) => {
  const { id } = req.params;

  if (!mongoose.isValidObjectId(id)) {
    return res.status(400).json({ error: "ID inválido." });
  }

  try {
    const result = await User.findByIdAndDelete(id);
    if (!result) {
      return res.status(404).json({ error: "Utilizador não encontrado." });
    }

    res.json({ message: "Utilizador removido com sucesso." });
  } catch (error) {
    console.error("Erro ao remover utilizador:", error);
    res.status(500).json({ error: "Erro interno ao remover utilizador." });
  }
};
