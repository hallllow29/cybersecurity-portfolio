const mongoose = require("mongoose");

const EncomendaSchema = new mongoose.Schema({
  cliente: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Cliente",
    required: true,
  },
  restaurante: {
    type: mongoose.Schema.Types.ObjectId,
    ref: "Restaurante",
    required: true,
  },
  pratos: [
    {
      prato: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Prato",
        required: true,
      },
      dose: String,
      quantidade: Number,
      precoUnitario: Number,
    },
  ],
  total: { type: Number, required: true },
  metodoPagamento: { type: String, required: true },
  moradaEntrega: { type: String, required: true },
  dadosPagamento: { type: mongoose.Schema.Types.Mixed },
  status: {
    type: String,
    enum: [
      "pendente",
      "aceite",
      "preparacao",
      "enviado",
      "entregue",
      "cancelada",
    ],
    default: "pendente",
  },
  criadoEm: { type: Date, default: Date.now },
});

module.exports = mongoose.model("Encomenda", EncomendaSchema);
