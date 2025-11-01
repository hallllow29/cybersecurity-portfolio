const express = require("express");
const router = express.Router();
const encomendaController = require("../../controllers/restaurante/encomendasController");
const autenticadoJWT = require("../../middlewares/auth/autenticadoJWT");
const { autenticadoRestaurante } = require("../../middlewares/auth/roles");

router.use(autenticadoJWT, autenticadoRestaurante);

router.get("/", encomendaController.listarEncomendas);
router.get("/:id", encomendaController.detalhesEncomenda);
router.put("/:id/estado", encomendaController.atualizarEstadoEncomenda);

module.exports = router;
