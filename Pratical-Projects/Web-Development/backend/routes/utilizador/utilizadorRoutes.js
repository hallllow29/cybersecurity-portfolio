const express = require("express");
const router = express.Router();

const autenticadoJWT = require("../../middlewares/auth/autenticadoJWT");
const { autenticadoCliente } = require("../../middlewares/auth/roles");

const utilizadorController = require("../../controllers/utilizador/utilizadorController");
const dashboardUtilizadorCtrl = require("../../controllers/utilizador/utilizadorDashboardController");

//
// 📊 Dashboard do Utilizador
//
router.get(
  "/dashboard",
  autenticadoJWT,
  autenticadoCliente,
  dashboardUtilizadorCtrl.getDashboard
);

router.get("/restaurantes", utilizadorController.listarRestaurantes);

router.get("/restaurantes/:id", utilizadorController.verDetalhesRestaurante);

router.get("/restaurantes/:id/menu", utilizadorController.verMenu);

router.post(
  "/carrinho/adicionar",
  autenticadoJWT,
  autenticadoCliente,
  utilizadorController.adicionarAoCarrinho
);
router.get(
  "/carrinho",
  autenticadoJWT,
  autenticadoCliente,
  utilizadorController.verCarrinho
);

router.post(
  "/encomendar",
  autenticadoJWT,
  autenticadoCliente,
  utilizadorController.criarEncomenda
);
router.get(
  "/encomendas",
  autenticadoJWT,
  autenticadoCliente,
  utilizadorController.verEncomendas
);
router.post(
  "/encomendas/:id/cancelar",
  autenticadoJWT,
  autenticadoCliente,
  utilizadorController.cancelarEncomenda
);

router.put(
  "/alterar-credenciais",
  autenticadoJWT,
  autenticadoCliente,
  dashboardUtilizadorCtrl.alterarCredenciais
);

module.exports = router;
