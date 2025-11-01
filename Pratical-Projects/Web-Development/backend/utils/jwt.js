const jwt = require("jsonwebtoken");
const SECRET = process.env.JWT_SECRET || "segredo_muito_secreto"; 
require("dotenv").config();

exports.gerarToken = (dados) => {
  return jwt.sign(dados, SECRET, { expiresIn: "1h" });
};

exports.validarToken = (token) => {
  try {
    return jwt.verify(token, SECRET);
  } catch (err) {
    return null;
  }
};