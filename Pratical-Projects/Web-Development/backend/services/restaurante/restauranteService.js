const mongoose = require("mongoose");
const validator = require("validator");

const Restaurante = require("../../models/Accounts/Restaurante");
const { obterCoordenadas } = require("../../utils/localizacao");

async function criarRestaurante(userId, dados, ficheiros, ip, restauranteId) {
  let horarioFuncionamento = [];

  if (dados.horarioFuncionamento) {
    try {
      horarioFuncionamento = JSON.parse(dados.horarioFuncionamento);

      for (const horario of horarioFuncionamento) {
        if (!horario.fechado && horario.abre && horario.fecha) {
          if (horario.abre >= horario.fecha) {
            throw new Error(`No dia ${horario.dia}, hora de abertura deve ser antes da hora de fecho.`);
          }
        }
      }

    } catch (e) {
      console.error("Erro no horário de funcionamento:", e);
      throw new Error("Formato inválido do horário de funcionamento.");
    }
  }

  const {
    restaurante_nome,
    restaurante_morada,
    restaurante_nomeResponsavel,
    restaurante_telefone,
    restaurante_nif,
    restaurante_tipoCozinha,
    restaurante_tempoConfecao,
    restaurante_tempoEntrega,
    restaurante_raioEntrega,
    restaurante_maxEncomendas,
    metodosPagamento,
    restaurante_codigoPostal,
  } = dados;

  const obrigatorios = [
    restaurante_nome,
    restaurante_morada,
    restaurante_nomeResponsavel,
    restaurante_telefone,
    restaurante_nif,
  ];

  if (obrigatorios.some((v) => !v)) {
    throw new Error("Todos os campos obrigatórios do restaurante devem ser preenchidos.");
  }

  if (!validator.isMobilePhone(restaurante_telefone, "pt-PT")) {
    throw new Error("Telefone do restaurante inválido.");
  }

  if (!validator.isNumeric(restaurante_nif) || restaurante_nif.length !== 9) {
    throw new Error("NIF do restaurante inválido.");
  }

  const coordenadas = await obterCoordenadas(restaurante_codigoPostal);

  return await Restaurante.create({
    _id: restauranteId,
    ownerId: userId,
    nome: validator.escape(restaurante_nome),
    morada: validator.escape(restaurante_morada),
    nif: restaurante_nif,
    pessoaResponsavel: validator.escape(restaurante_nomeResponsavel),
    telefone: restaurante_telefone,
    tipoCozinha: restaurante_tipoCozinha || "Portuguesa",
    tempoConfecao: parseInt(restaurante_tempoConfecao, 10) || 0,
    tempoEntrega: parseInt(restaurante_tempoEntrega, 10) || 0,
    raioEntregaKm: parseInt(restaurante_raioEntrega, 10) || 0,
    maxEncomendasPorHora: parseInt(restaurante_maxEncomendas, 10) || 1,
    metodosDePagamento: Array.isArray(metodosPagamento) ? metodosPagamento : [metodosPagamento],
    logoUrl: ficheiros?.logo ? `/images/restaurantes/${restauranteId}/${ficheiros.logo[0].filename}` : null,
    capaUrl: ficheiros?.capa ? `/images/restaurantes/${restauranteId}/${ficheiros.capa[0].filename}` : null,
    ipRegisto: ip,
    localizacao: {
      type: "Point",
      coordinates: coordenadas || [-8.611, 41.149],
    },
    horarioFuncionamento,
  });
}

module.exports = { criarRestaurante };
