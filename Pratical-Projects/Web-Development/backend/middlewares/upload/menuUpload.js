const multer = require("multer");
const path = require("path");
const fs = require("fs");

const menuStorage = multer.diskStorage({
  destination: (req, file, cb) => {
    const restauranteId = req.user?.restauranteId;
    if (!restauranteId)
      return cb(new Error("Restaurante ID ausente no token"), null);

    const pastaDestino = path.join(
      __dirname,
      "..",
      "..",
      "public",
      "images",
      "restaurantes",
      restauranteId.toString(),
      "menus"
    );
    fs.mkdirSync(pastaDestino, { recursive: true });
    cb(null, pastaDestino);
  },
  filename: (req, file, cb) => {
    const uniqueName = `menu-${Date.now()}${path.extname(file.originalname)}`;
    cb(null, uniqueName);
  },
});

const uploadImagemMenu = multer({ storage: menuStorage }).single("imagemMenu");

module.exports = {
  uploadImagemMenu,
};
