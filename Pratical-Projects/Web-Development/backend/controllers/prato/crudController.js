const validator = require("validator");
const Prato = require("../../models/Core/Prato");
const { buscarInfoNutricional } = require("../../services/openfood");

exports.postAdicionar = async (req, res) => {
	try {
		if (!req.user?.restauranteId) {
			return res.status(401).json({ message: "Restaurante não autenticado." });
		}

		const {
			nome,
			descricao,
			categoria,
			subcategoria,
			doses,
			infoNutricional,
			alergenios,
			imagemUrl 
		} = req.body;

		if (!nome || !descricao || !categoria || !doses || (!req.file && !imagemUrl)) {
			return res.status(400).json({ message: "Todos os campos obrigatórios." });
		}

		let dosesArray;
		try {
			dosesArray = JSON.parse(doses);
			if (!Array.isArray(dosesArray) || dosesArray.length === 0)
				throw new Error();
		} catch {
			return res.status(400).json({ message: "Formato de doses inválido." });
		}

		let infoNut, alerg;
		try {
			infoNut = JSON.parse(infoNutricional);
			alerg = JSON.parse(alergenios);

			if (
				typeof infoNut.calorias !== "number" ||
				typeof infoNut.proteinas !== "number" ||
				typeof infoNut.hidratos !== "number" ||
				typeof infoNut.gorduras !== "number"
			) {
				return res.status(400).json({ message: "Informação nutricional inválida." });
			}

			if (!Array.isArray(alerg)) {
				return res.status(400).json({ message: "Alergénios inválidos." });
			}
		} catch {
			return res.status(400).json({ message: "Erro ao processar dados nutricionais ou alergénios." });
		}

		let imagemFinal = '';
		if (req.file) {
			imagemFinal = `/images/restaurantes/${req.user.restauranteId}/pratos/${req.file.filename}`;
		} else if (imagemUrl && imagemUrl.startsWith('http')) {
			imagemFinal = imagemUrl;
		} else {
			return res.status(400).json({ message: "Imagem inválida." });
		}

		const prato = new Prato({
			nome: nome.trim(),
			descricao: descricao.trim(),
			categoria: categoria.trim(),
			subcategoria: subcategoria?.trim() || "",
			restaurante: req.user.restauranteId,
			infoNutricional: infoNut,
			alergenios: alerg,
			doses: dosesArray,
			imagem: imagemFinal,
		});

		await prato.save();

		return res.status(201).json({ message: "Prato criado com sucesso!", prato });
	} catch (err) {
		console.error("Erro ao adicionar prato:", err);
		return res.status(500).json({ message: "Erro ao adicionar prato." });
	}
};

exports.postEditar = async (req, res) => {
	const { id } = req.params;
	const restaurante = req.user.restauranteId;

	const { nome, descricao, categoria, subcategoria, doses } = req.body;

	if (!validator.isMongoId(id)) {
		return res.status(400).json({ message: "ID inválido." });
	}

	if (!nome || !descricao || !categoria || !doses) {
		return res
			.status(400)
			.json({ message: "Todos os campos são obrigatórios." });
	}

	let dosesArray;
	try {
		dosesArray = JSON.parse(doses);
		if (!Array.isArray(dosesArray)) throw new Error();
	} catch {
		return res.status(400).json({ message: "Formato de doses inválido." });
	}

	const dadosNutricionaisAPI = await buscarInfoNutricional(nome);

	const update = {
		nome: validator.escape(nome.trim()),
		descricao: validator.escape(descricao.trim()),
		categoria: validator.escape(categoria.trim()),
		subcategoria: validator.escape(subcategoria.trim()),
		infoNutricional: dadosNutricionaisAPI
			? {
				calorias: dadosNutricionaisAPI.calorias,
				proteinas: dadosNutricionaisAPI.proteinas,
				hidratos: dadosNutricionaisAPI.hidratos,
				gorduras: dadosNutricionaisAPI.gorduras,
				}
			: JSON.parse(req.body.infoNutricional), 
		alergenios: dadosNutricionaisAPI
			? dadosNutricionaisAPI.alergenios
			: JSON.parse(req.body.alergenios),
		doses: dosesArray,
	};

	if (req.file) {
		update.imagem = `/images/restaurantes/${restaurante}/pratos/${req.file.filename}`;
	}

	try {
		await Prato.updateOne({ _id: id, restaurante }, update);
		res.status(200).json({ message: "Prato atualizado com sucesso." });
	} catch (error) {
		console.error("Erro ao atualizar prato:", error);
		res.status(500).json({ message: "Erro ao atualizar prato." });
	}
};

exports.postEditar = async (req, res) => {
	const { id } = req.params;
	const restaurante = req.user.restauranteId;

	const {
		nome,
		descricao,
		categoria,
		subcategoria,
		infoNutricional,
		alergenios,
		doses,
	} = req.body;

	if (!validator.isMongoId(id)) {
		return res.status(400).json({ message: "ID inválido." });
	}

	if (
		!nome ||
		!descricao ||
		!categoria ||
		!infoNutricional ||
		!alergenios ||
		!doses
	) {
		return res
			.status(400)
			.json({ message: "Todos os campos são obrigatórios." });
	}

	let dosesArray;
	try {
		dosesArray = JSON.parse(doses);
		if (!Array.isArray(dosesArray)) throw new Error();
	} catch {
		return res.status(400).json({ message: "Formato de doses inválido." });
	}

	const update = {
		nome: validator.escape(nome.trim()),
		descricao: validator.escape(descricao.trim()),
		categoria: validator.escape(categoria.trim()),
		subcategoria: validator.escape(subcategoria.trim()),
		infoNutricional: JSON.parse(infoNutricional),
		alergenios: JSON.parse(alergenios),
		doses: dosesArray,
	};

	if (req.file) {
		update.imagem = `/images/restaurantes/${restaurante}/pratos/${req.file.filename}`;
	}

	try {
		await Prato.updateOne({ _id: id, restaurante }, update);
		res.status(200).json({ message: "Prato atualizado com sucesso." });
	} catch (error) {
		console.error("Erro ao atualizar prato:", error);
		res.status(500).json({ message: "Erro ao atualizar prato." });
	}
};


exports.postApagarUm = async (req, res) => {
	const { id } = req.params;
	const restaurante = req.user.restauranteId;

	if (!validator.isMongoId(id)) {
		return res.status(400).json({ message: "ID inválido." });
	}

	try {
		await Prato.deleteOne({ _id: id, restaurante });
		res.status(200).json({ message: "Prato apagado com sucesso." });
	} catch (error) {
		res.status(500).json({ message: "Erro ao apagar prato." });
	}
};

exports.postApagarSelecionados = async (req, res) => {
	const restaurante = req.user.restauranteId;
	const { pratosSelecionados } = req.body;

	if (!Array.isArray(pratosSelecionados) || pratosSelecionados.length === 0) {
		return res.status(400).json({ message: "Nenhum prato selecionado." });
	}

	const idsValidos = pratosSelecionados.filter((id) => validator.isMongoId(id));

	try {
		await Prato.deleteMany({ _id: { $in: idsValidos }, restaurante });
		res.status(200).json({ message: "Pratos apagados com sucesso." });
	} catch (error) {
		res.status(500).json({ message: "Erro ao apagar pratos." });
	}
};

exports.listarTodos = async (req, res) => {
	const restaurante = req.user.restauranteId;

	try {
		const pratos = await Prato.find({ restaurante });
		res.status(200).json(pratos);
	} catch (error) {
		console.error("Erro ao buscar pratos:", error);
		res.status(500).json({ message: "Erro ao buscar pratos." });
	}
};
