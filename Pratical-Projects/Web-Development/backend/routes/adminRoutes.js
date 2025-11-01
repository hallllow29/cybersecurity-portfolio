const express = require("express");
const router = express.Router();

const adminController = require("../controllers/admin/adminController");
const autenticadoJWT = require("../middlewares/auth/autenticadoJWT");
const { autenticadoAdmin } = require("../middlewares/auth/roles");
const ActivityLog = require("../models/Core/ActivityLog");


router.use(autenticadoJWT, autenticadoAdmin);

router.get("/logs", async (req, res) => {
  try {
    const logs = await ActivityLog.find()
      .populate("userId", "email role")
      .sort({ createdAt: -1 })
      .limit(100);
    res.json(logs);
  } catch (error) {
    res.status(500).json({ message: "Erro ao buscar logs." });
  }
});

router.get("/dashboard", adminController.getDashboard);

router.post("/restaurante/:id/validar", adminController.validarRestaurante);
router.post("/restaurante/:id/remover", adminController.removerRestaurante);

router.post("/utilizador/:id/remover", adminController.removerUtilizador);

module.exports = router;
