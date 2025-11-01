const express = require("express");
const router = express.Router();
const viewController = require("../controllers/prato/viewController");
const { buscarInfoNutricional } = require("../services/openfood"); 

router.get("/check-exists", async (req, res) => {
	const nome = req.query.nome;
	console.log("check-exists chamada com:", req.query.nome);
	if (!nome)
		return res.status(400).json({ error: "Nome do prato é obrigatório" });

	try {
		const info = await buscarInfoNutricional(nome);
		const exists = info !== null;
		res.json({ exists });
	} catch (error) {
		console.error("Erro ao verificar existência do prato:", error);
		res.status(500).json({ error: "Erro interno no servidor" });
	}
});

router.get("/detalhes", async (req, res) => {
	const nome = req.query.nome;

	if (!nome) {
		return res.status(400).json({ error: "Nome do prato é obrigatório" });
	}

	try {
		const info = await buscarInfoNutricional(nome);

		if (!info) {
			return res.json(null); 
		}

		const alergeneos = [];

		if (info.descricao?.toLowerCase().includes("leite")) alergeneos.push("lactose");
		if (info.descricao?.toLowerCase().includes("gluten")) alergeneos.push("gluten");
		if (info.descricao?.toLowerCase().includes("ovo")) alergeneos.push("ovo");

		return res.json({
			nome: info.nome,
			infoNutricional: {
				calorias: info.calorias || null,
				proteinas: info.proteinas || null,
				hidratos: info.hidratos || null,
				gorduras: info.gorduras || null,
			},
			alergenios: alergeneos,
			imagem: info.imagem || null
		});

	} catch (err) {
		console.error("❌ Erro ao buscar info nutricional:", err);
		return res.status(500).json({ error: "Erro ao obter detalhes do prato" });
	}
});


router.get("/", viewController.listar); 

router.get("/:id", viewController.getDetalhes);

module.exports = router;
