const jwt = require("jsonwebtoken");

module.exports = (req, res, next) => {
  const headerToken = req.headers.authorization?.split(" ")[1];
  const cookieToken = req.cookies?.token;
  const token = cookieToken || headerToken;


  if (!token) {
    return res.status(401).json({ erro: "Token não fornecido" });
  }

  try {
    const decoded = jwt.verify(token, process.env.JWT_SECRET || "segredo_muito_secreto");
    req.user = decoded;
    res.locals.user = decoded;
    next();
  } catch (err) {
    console.error("❌ Token inválido:", err);
    return res.status(401).json({ erro: "Token inválido" });
  }
};
