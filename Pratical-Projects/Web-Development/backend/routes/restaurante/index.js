const express = require("express");
const router = express.Router();

router.use("/dashboard", require("./dashboardRoutes"));
router.use("/encomendas", require("./encomendaRoutes"));
router.use("/perfil", require("./perfilRoutes"));

router.get("/editado", (req, res) => {
  res.render("restaurante/restaurante-editado");
});

module.exports = router;
