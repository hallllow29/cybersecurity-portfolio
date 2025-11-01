const mongoose = require("mongoose");

const restauranteSchema = new mongoose.Schema(
  {
    ownerId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "User",
      required: true,
    },
    nome: {
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
      unique: true,
    },
    pessoaResponsavel: {
      type: String,
      required: true,
    },
    telefone: {
      type: String,
      required: true,
    },
    tipoCozinha: {
      type: String,
      required: true,
      enum: ["Italiana", "Japonesa", "Portuguesa", "Vegetariana", "Outros"],
    },
    tempoConfecao: {
      type: Number,
      required: true,
      min: 0,
    },
    tempoEntrega: {
      type: Number,
      required: true,
      min: 0,
    },
    raioEntregaKm: {
      type: Number,
      required: true,
      min: 0,
    },
    maxEncomendasPorHora: {
      type: Number,
      required: true,
      min: 1,
    },
    metodosPagamento: {
      type: [String],
      default: [
        "Dinheiro",
        "MB Way",
        "Cartão de Crédito/Débito",
        "Multibanco",
        "Outro",
      ],
    },
    logoUrl: {
      type: String,
      required: true,
      trim: true,
    },
    capaUrl: {
      type: String,
      trim: true,
    },
    ipRegisto: {
      type: String,
    },
    horarioFuncionamento: [
      {
        dia: {
          type: String,
          required: true,
          enum: [
            "Segunda-feira",
            "Terça-feira",
            "Quarta-feira",
            "Quinta-feira",
            "Sexta-feira",
            "Sábado",
            "Domingo",
          ],
        },
        abre: {
          type: String,
        },
        fecha: {
          type: String,
        },
        fechado: {
          type: Boolean,
          required: true,
        },
      },
    ],
    localizacao: {
      type: {
        type: String,
        enum: ["Point"],
        default: "Point",
      },
      coordinates: {
        type: [Number],
        required: true,
        validate: {
          validator: (coords) =>
            coords.length === 2 &&
            coords.every((val) => typeof val === "number"),
          message:
            "As coordenadas devem ter dois números (latitude e longitude)",
        },
      },
    },
    validado: {
      type: Boolean,
      default: false,
    },
    ativo: {
      type: Boolean,
      default: true,
    },
  },
  {
    timestamps: true,
  }
);

restauranteSchema.index({ localizacao: "2dsphere" });

module.exports =
  mongoose.models.Restaurante ||
  mongoose.model("Restaurante", restauranteSchema);
