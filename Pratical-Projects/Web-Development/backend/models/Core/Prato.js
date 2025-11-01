const mongoose = require("mongoose");

const pratoSchema = new mongoose.Schema(
  {
    nome: { type: String, required: true },
    descricao: { type: String },
    categoria: { type: String, required: true },
    subcategoria: { type: String, required: true},
    restaurante: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Restaurante",
      required: true,
    },
    infoNutricional: {
      calorias: { type: Number,  },
      proteinas: { type: Number, },
      hidratos: { type: Number, },
      gorduras: { type: Number, }
    },
    alergenios: [String],
    doses: [
      {
        nome: String,
        preco: Number
      }
    ],
    imagem: { type: String, required: true},
    ativo: { type: Boolean, default: true },
  },
  { timestamps: true }
);

module.exports = mongoose.model("Prato", pratoSchema);
