const express = require("express");
const router = express.Router();

const publicRoutes = require('./publicRoutes');


router.use("/stripe", require("./stripeRoutes"));
router.use("/admin/pratos", require("./pratoRoutes"));
router.use("/admin/menus", require("./menuRoutes")); 

router.use("/admin", require("./adminRoutes"));

router.use("/pratos", require("./pratosPublicRoutes"));
router.use("/menus", require("./menusPublicRoutes"));
router.use('/public', publicRoutes);

router.use("/auth", require("./auth/authRoutes"));
router.use("/restaurante", require("./restaurante"));
router.use("/utilizador", require("./utilizador/utilizadorRoutes"));

module.exports = router;
