const mongoose = require("mongoose");
const Cliente = require("../../models/Accounts/Cliente");
const Encomenda = require("../../models/Core/Encomenda");

exports.getDashboard = async (req, res) => {
  try {
    const clienteId = req.user.refId;
    const utilizador = await Cliente.findById(clienteId);

    const totalEncomendas = await Encomenda.countDocuments({
      cliente: clienteId,
    });

    const ultimasEncomendas = await Encomenda.find({ cliente: clienteId })
      .sort({ createdAt: -1 })
      .limit(5)
      .populate("pratos");

    res.status(200).json({
      utilizador,
      totalEncomendas,
      ultimasEncomendas,
    });
  } catch (err) {
    console.error("Erro no dashboard do cliente:", err);
    res.status(500).json({ message: "Erro ao carregar dashboard." });
  }
};

exports.alterarCredenciais = async (req, res) => {
  try {
    const clienteId = req.user.refId;
    const { nome, email, password } = req.body;

    const updateData = {};
    if (novoEmail) updateData.email = email.toLowerCase();
    if (novaPassword) {
      if (novaPassword.length < 6) {
        return res.status(400).json({
          erros: { geral: "A nova password deve ter pelo menos 6 caracteres." },
        });
      }
      updateData.password = novaPassword;
    }

    await User.findByIdAndUpdate(clienteId, updateData);
    res.status(200).json({ message: "Credenciais atualizadas com sucesso." });
  } catch (err) {
    console.error("Erro ao atualizar credenciais:", err);
    res.status(500).json({ message: "Erro ao atualizar credenciais." });
  }
};

