const express = require("express");
const router = express.Router();
const pratoCrud = require("../controllers/prato/crudController");
const { uploadImagemPrato } = require("../middlewares/upload/upload");
const autenticadoJWT = require("../middlewares/auth/autenticadoJWT");
const { autenticadoRestaurante } = require("../middlewares/auth/roles");
const { buscarInfoNutricional } = require('../services/openfood');
const pratoView = require ('../controllers/prato/viewController')

router.use(autenticadoJWT, autenticadoRestaurante);

router.get("/", pratoCrud.listarTodos);

router.post("/", uploadImagemPrato, pratoCrud.postAdicionar);

router.get("/:id", pratoView.getDetalhes);

router.put("/:id", uploadImagemPrato, pratoCrud.postEditar);

router.delete("/:id", pratoCrud.postApagarUm);

router.post("/apagar-selecionados", pratoCrud.postApagarSelecionados);

module.exports = router;
