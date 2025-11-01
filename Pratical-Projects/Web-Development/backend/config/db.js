const mongoose = require('mongoose');
require('dotenv').config();

mongoose.connect(process.env.MONGO_URI)
.then(() => console.log('MongoDB ligado'))
.catch(err => console.error('Erro ao ligar à base de dados:', err));

