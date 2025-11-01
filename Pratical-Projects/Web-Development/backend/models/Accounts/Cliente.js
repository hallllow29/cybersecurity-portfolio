const mongoose = require("mongoose");

const clienteSchema = new mongoose.Schema({
  userId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "User",
  },
  nome: {
    type: String,
    required: true,
  },
  telefone: {
    type: String,
    required: true,
  },
  morada: {
    type: String,
    required: true,
  },
  nif: {
    type: String,
    required: true,
    length: 9,
  },
  dataRegisto: {
    type: Date,
    default: Date.now,
  },
  ativo: {
    type: Boolean,
    default: true,
  },
  numeroCancelamentos: {
    type: Number,
    default: 0,
  },
  bloqueadoAte: {
    type: Date,
    default: null,
  },
  fotoPerfil: {
    type: String,
    default: null,
  },
});

module.exports =
  mongoose.models.Cliente || mongoose.model("Cliente", clienteSchema);
