const multer = require("multer");
const path = require("path");
const fs = require("fs");
const mongoose = require("mongoose");

const prepararUploadRestaurante = (req, res, next) => {
  req.restauranteId = new mongoose.Types.ObjectId().toString();
  next();
};

const logoECapaStorage = multer.diskStorage({
  destination: (req, file, cb) => {
    const restauranteId = req.restauranteId;
    if (!restauranteId)
      return cb(new Error("Restaurante não autenticado para upload."), null);
    req.restauranteId = restauranteId;

    const pastaDestino = path.join(
      __dirname,
      "..",
      "..",
      "public",
      "images",
      "restaurantes",
      restauranteId
    );
    fs.mkdirSync(pastaDestino, { recursive: true });
    cb(null, pastaDestino);
  },
  filename: (req, file, cb) => {
    const uniqueName = `${file.fieldname}-${Date.now()}${path.extname(
      file.originalname
    )}`;
    cb(null, uniqueName);
  },
});

const uploadFiles = multer({ storage: logoECapaStorage }).fields([
  { name: "logo", maxCount: 1 },
  { name: "capa", maxCount: 1 },
]);

const { uploadImagemPrato } = require("./pratoUpload");
const { uploadImagemMenu } = require("./menuUpload");

module.exports = {
  prepararUploadRestaurante,
  uploadFiles,
  uploadImagemPrato,
  uploadImagemMenu,
};
