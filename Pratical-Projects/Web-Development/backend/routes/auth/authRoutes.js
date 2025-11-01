const express = require("express");
const router = express.Router();
const authController = require("../../controllers/auth/authController");
const { prepararUploadRestaurante, uploadFiles } = require("../../middlewares/upload/upload");

router.post("/login", authController.login);
router.post("/logout", authController.logout);

router.get("/registar", authController.getRegistoPage);
router.post("/registar", prepararUploadRestaurante, uploadFiles, authController.postRegisto);

module.exports = router;
