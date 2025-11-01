const express = require("express");
const router = express.Router();

const crud = require("../controllers/menu/crudController");
const { uploadImagemMenu } = require("../middlewares/upload/upload");
const autenticadoJWT = require("../middlewares/auth/autenticadoJWT");
const { autenticadoRestaurante } = require("../middlewares/auth/roles");
const view = require("../controllers/menu/viewController");

router.use(autenticadoJWT, autenticadoRestaurante);

router.post("/", uploadImagemMenu, crud.postCriarMenu);

router.put("/:id", uploadImagemMenu, crud.putEditarMenu);

router.delete("/:id", crud.postApagarMenu);

router.get("/", view.listarMenusDoMeuRestaurante);


module.exports = router;
