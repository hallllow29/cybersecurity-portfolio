const bcrypt = require("bcrypt");
const validator = require("validator");

const User = require("../../models/Accounts/User");
const Cliente = require("../../models/Accounts/Cliente");
const Restaurante = require("../../models/Accounts/Restaurante");

const { autenticarUser } = require("../../services/auth/loginService");
const { criarCliente } = require("../../services/cliente/clienteService");
const {
  criarRestaurante,
} = require("../../services/restaurante/restauranteService");
const { gerarToken } = require("../../utils/jwt");
const ActivityLog = require("../../models/Core/ActivityLog");

exports.login = async (req, res) => {
  const { email, password } = req.body;
  const erros = {};

  if (!email) erros.email = "O email é obrigatório.";
  else if (!validator.isEmail(email))
    erros.email = "Formato de email inválido.";

  if (!password) erros.password = "A password é obrigatória.";

  if (Object.keys(erros).length > 0) {
    return res.status(400).json({ erros });
  }

  try {
    const user = await autenticarUser(email, password);

    if (!user) {
      return res.status(401).json({
        erros: { geral: "Utilizador não encontrado ou password incorreta." },
      });
    }

    let restauranteId = null;
    if (user.role === "restaurante") {
      const restaurante = await Restaurante.findOne({ ownerId: user._id });
      if (!restaurante) {
        return res.status(404).json({
          erros: { geral: "Restaurante não encontrado para este utilizador." },
        });
      }
      restauranteId = restaurante._id;
    }

    const token = gerarToken({
      id: user._id,
      role: user.role,
      refId: user.refId,
      restauranteId: restauranteId || null,
    });

    await ActivityLog.create({
      userId: user._id,
      action: "Login",
      ip: req.ip,
      userAgent: req.headers["user-agent"],
    });

    res.status(200).json({
      message: "Login efetuado com sucesso.",
      token,
      role: user.role,
      userId: restauranteId || user._id,
    });
  } catch (err) {
    console.error("Erro no login:", err);
    res.status(500).json({
      erros: { geral: "Erro interno ao fazer login." },
    });
  }
};


exports.logout = (req, res) => {
  res.clearCookie("token");
  res.redirect("/login");
};


exports.getRegistoPage = (req, res) => {
  res.status(200).json({ message: "Endpoint de registo ativo." });
};


exports.postRegisto = async (req, res) => {

  const email = req.body?.email;
  const password = req.body?.password;
  const conf_password = req.body?.conf_password;
  const tipo = req.body?.tipo;
  const dados = req.body || {};
  const erros = {};

  if (!email) erros.email = "O email é obrigatório.";
  else if (!validator.isEmail(email)) erros.email = "Email inválido.";

  if (!password) erros.password = "A password é obrigatória.";
  else if (!validator.isLength(password, { min: 6 }))
    erros.password = "A password deve ter pelo menos 6 caracteres.";

  if (!conf_password) erros.conf_password = "Deves confirmar a password.";
  else if (password !== conf_password)
    erros.conf_password = "As passwords não coincidem.";

  if (!tipo) erros.tipo = "Tens de escolher o tipo de utilizador.";

  if (email) {
    const emailExiste = await User.findOne({ email: email.toLowerCase() });
    if (emailExiste) erros.email = "Já existe uma conta com esse email.";
  }

  if (tipo === "cliente") {
    const nifExiste = await Cliente.findOne({ nif: dados.cliente_nif });
    if (nifExiste) erros.cliente_nif = "Já existe um cliente com este NIF.";

    const telExiste = await Cliente.findOne({
      telefone: dados.cliente_telefone,
    });
    if (telExiste)
      erros.cliente_telefone =
        "Já existe um cliente com este número de telefone.";
  }

  if (tipo === "restaurante") {
    const nifExiste = await Restaurante.findOne({ nif: dados.restaurante_nif });
    if (nifExiste)
      erros.restaurante_nif = "Já existe um restaurante com este NIF.";

    const telExiste = await Restaurante.findOne({
      telefone: dados.restaurante_telefone,
    });
    if (telExiste)
      erros.restaurante_telefone =
        "Já existe um restaurante com este número de telefone.";
  }

  if (Object.keys(erros).length > 0) {
    return res.status(400).json({ erros });
  }

  try {
    const hashedPassword = await bcrypt.hash(password, 10);
    const novoUser = new User({
      email: validator.normalizeEmail(email),
      password: hashedPassword,
      role: tipo.toLowerCase().trim(),
    });

    if (tipo === "cliente") {
      const cliente = await criarCliente(novoUser._id, req.body);
      novoUser.refId = cliente._id;
    } else if (tipo === "restaurante") {
      const restaurante = await criarRestaurante(
        novoUser._id,
        req.body,
        req.files,
        req.ip,
        req.restauranteId
      );
      novoUser.refId = restaurante._id;
    }

    await novoUser.save();
    res.status(201).json({ message: "Conta criada com sucesso." });
  } catch (err) {
    console.error("ERRO COMPLETO AO REGISTAR:", err);

    res.status(400).json({
      erros: { geral: err.message || "Erro ao registar. Tenta novamente." },
    });
  }
};
