const fetch = require("node-fetch");
const cheerio = require("cheerio");

async function obterCoordenadas(codigoPostal) {
  const url = `https://codigo-postal.pt/${codigoPostal}/`;

  try {
    const response = await fetch(url);
    const body = await response.text();

    const $ = cheerio.load(body);

    const coordenadasTexto = $("span.gps")
      .first()
      .text()
      .trim()
      .replace("GPS:", "")
      .trim();

    if (coordenadasTexto) {
      const coordenadasArray = coordenadasTexto
        .split(",")
        .map((coord) => parseFloat(coord.trim()));

      console.log(`Coordenadas (array):`, coordenadasArray);
      return coordenadasArray;
    } else {
      console.log("Coordenadas não encontradas.");
      return null;
    }
  } catch (error) {
    console.error("Erro ao buscar os dados:", error);
    return null;
  }
}

module.exports = { obterCoordenadas };
