const express = require("express");
const router = express.Router();
const view = require("../controllers/menu/viewController");


router.get("/", view.listarMenus); 

router.get("/:id", view.obterMenuPorId);

module.exports = router;
