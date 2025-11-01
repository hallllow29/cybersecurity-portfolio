const Cliente = require("../../models/Accounts/Cliente");
const validator = require("validator");

async function criarCliente(userId, dados) {
  const { cliente_nome, cliente_telefone, cliente_morada, cliente_nif } = dados;

  if (!cliente_nome || !cliente_telefone || !cliente_morada || !cliente_nif) {
    throw new Error("Todos os campos do cliente são obrigatórios.");
  }

  if (!validator.isMobilePhone(cliente_telefone, "pt-PT")) {
    throw new Error("Telefone inválido.");
  }

  if (!validator.isNumeric(cliente_nif) || cliente_nif.length !== 9) {
    throw new Error("NIF inválido.");
  }

  return await Cliente.create({
    userId,
    nome: validator.escape(cliente_nome).trim(),
    telefone: cliente_telefone,
    morada: validator.escape(cliente_morada).trim(),
    nif: cliente_nif,
  });
}

module.exports = { criarCliente };
