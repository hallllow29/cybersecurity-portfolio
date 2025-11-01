const express = require('express');
const { MongoClient } = require('mongodb');
require('dotenv').config();

const app = express();
const PORT = process.env.PORT || 3000;

const client = new MongoClient(process.env.MONGO_URI);
let db;

//Função para conectar com o MongoDB
async function initializeMongoDB() {
  try {
    await client.connect();
    db = client.db('HERE_WE_GO_AGAIN'); //Nome da base de dados do MongoDB
    console.log('Connection to MongoDB successful!');

    // Lista de colecoes disponiveis no MongoDB
    const colecoes = await db.listCollections().toArray();
    console.log('Available Collections', colecoes);

  } catch (err) {
    console.error('Error connecting to MongoDB', err);
    process.exit(1);
  }
}

//Inicializa o mongoDB e configura as rotas
initializeMongoDB().then(() => {

  //Lista de Registos Clinicos
  const registosClinicosRoutes = require('./routes/registosClinicos')(db);
  app.use('/api/registosClinicos', registosClinicosRoutes);

  //Lista de Transferencias
  const transferenciasRoutes = require('./routes/transferencias')(db);
  app.use('/api/transferencias', transferenciasRoutes);

  app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
  });
}).catch(err => {
  console.error('Error initialing server:', err);
  process.exit(1);
});