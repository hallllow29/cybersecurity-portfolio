const express = require("express");
const cors = require("cors");
const path = require("path");
require("dotenv").config();

const routes = require("./routes");
const publicRoutes = require('./routes/publicRoutes');

const app = express();

const swaggerUi = require("swagger-ui-express");
const swaggerDocument = require("./docs/swagger.json"); // <-- ajusta o path

app.use("/api-docs", swaggerUi.serve, swaggerUi.setup(swaggerDocument));
app.use("/api/stripe/pagamento/webhook", express.raw({ type: "application/json" }))
//
// 🌐 Middlewares globais
//
app.use(
	cors({
		origin: "*", // ou use o domínio do frontend para segurança em produção
		exposedHeaders: ["Content-Type"],
	})
);
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

//
// 🖼 Arquivos estáticos
//
app.use(
	"/images",
	(req, res, next) => {
		res.setHeader("Cross-Origin-Resource-Policy", "cross-origin");
		next();
	},
	express.static(path.join(__dirname, "public/images"))
);
//
// 🔀 Rotas da API
//
app.use("/api", routes);
app.use('/api/public', publicRoutes);

//
// ⚠️ Fallback para rotas inexistentes
//
app.use((req, res) => {
	res.status(404).json({ message: "Endpoint não encontrado." });
});

app.use(express.static(path.join(__dirname, 'dist')));

// Redireciona todas as outras rotas para index.html
app.get('*', (req, res) => {
	res.sendFile(path.join(__dirname, 'dist/index.html'));
});

module.exports = app;
