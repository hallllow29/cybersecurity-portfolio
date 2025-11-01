const mongoose = require("mongoose");

const menuSchema = new mongoose.Schema(
  {
    nome: { type: String, required: true },
    descricao: { type: String },
    restaurante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Restaurante",
      required: true,
    },
    pratos: [
      {
        type: mongoose.Schema.Types.ObjectId,
        ref: "Prato",
      },
    ],
    ativo: { type: Boolean, default: true },
    imagem: { type: String},
  },
  { timestamps: true }
);

module.exports = mongoose.model("Menu", menuSchema);
