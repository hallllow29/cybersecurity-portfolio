const express = require("express");
const router = express.Router();
const perfilController = require("../../controllers/restaurante/perfilController");
const editarController = require("../../controllers/restaurante/editarController");
const autenticadoJWT = require("../../middlewares/auth/autenticadoJWT");
const { autenticadoRestaurante } = require("../../middlewares/auth/roles");
const {
  prepararUploadRestaurante,
  uploadFiles,
} = require("../../middlewares/upload/upload");

router.get("/", autenticadoJWT, autenticadoRestaurante, perfilController.getPerfil);

router.put(
  "/",
  autenticadoJWT,
  autenticadoRestaurante,
  prepararUploadRestaurante,
  uploadFiles,
  editarController.editarPerfilAPI
);

module.exports = router;
