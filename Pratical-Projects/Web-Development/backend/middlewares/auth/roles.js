function permitirRoles(...rolesEsperados) {
  return (req, res, next) => {
    console.log("🔒 Role recebido:", req.user?.role);
    if (!rolesEsperados.includes(req.user?.role)) {
      return res
        .status(403)
        .json({ erro: "Acesso negado para este tipo de utilizador." });
    }
    next();
  };
}

module.exports = {
  autenticadoRestaurante: permitirRoles("restaurante", "admin"),
  autenticadoCliente: permitirRoles("cliente"),
  autenticadoAdmin: permitirRoles("admin"),
};
