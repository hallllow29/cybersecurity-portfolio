const axios = require("axios");

async function buscarInfoNutricional(nomePrato) {
  try {
    const response = await axios.get(
      "https://world.openfoodfacts.org/cgi/search.pl",
      {
        params: {
          search_terms: nomePrato,
          search_simple: 1,
          action: "process",
          json: 1,
        },
      }
    );

    const produtos = response.data.products;
    if (!produtos || produtos.length === 0) return null;

    const produto = produtos.find(
      (p) =>
        p.product_name &&
        p.product_name.toLowerCase().trim() === nomePrato.toLowerCase().trim()
    );

    if (!produto) {
      console.warn("❌ Nenhum produto com nome exato:", nomePrato);
      return null; 
    }

    const nutriments = produto.nutriments || {};

    return {
      nome: produto.product_name || nomePrato,
      descricao: produto.ingredients_text || '',
      imagem: produto.image_url || null,
      calorias: nutriments["energy-kcal_100g"] ?? null,
      proteinas: nutriments["proteins_100g"] ?? null,
      hidratos: nutriments["carbohydrates_100g"] ?? null,
      gorduras: nutriments["fat_100g"] ?? null,
      alergenios: (produto.allergens_tags || []).map((a) =>
        a.replace("en:", "")
      ),
    };
  } catch (error) {
    console.error("Erro OpenFoodFacts:", error.message);
    return null;
  }
}


module.exports = { buscarInfoNutricional };
