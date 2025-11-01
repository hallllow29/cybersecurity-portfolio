const express = require("express");
const router = express.Router();
const dashboardController = require("../../controllers/restaurante/dashboardController");
const autenticadoJWT = require("../../middlewares/auth/autenticadoJWT");
const { autenticadoRestaurante } = require("../../middlewares/auth/roles");

router.get("/:id", autenticadoJWT, autenticadoRestaurante, dashboardController.getDashboard);

module.exports = router;
