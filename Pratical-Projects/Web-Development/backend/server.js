const http = require('http');
const { Server } = require('socket.io');
const mongoose = require('mongoose');
const app = require('./app');


const PORT = process.env.PORT || 3000;

//
// 🛢 Ligação à base de dados
//
mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

mongoose.connection.once('open', () => {
  console.log('🟢 Conectado à base de dados MongoDB');
});
mongoose.connection.on('error', (err) => {
  console.error('🔴 Erro de conexão com o MongoDB:', err);
});

//
// 🚀 Criar servidor + configurar WebSocket
//
const server = http.createServer(app);
const io = new Server(server, {
  cors: {
    origin: '*', // Ajusta isto em produção
    methods: ['GET', 'POST'],
  },
});

// Disponibilizar o `io` para acesso nas rotas/controllers
app.set('io', io);

//
// 🔌 WebSocket listeners
//
io.on('connection', (socket) => {
  console.log('📡 Conectado via WebSocket');

  socket.on('joinRestauranteRoom', (restauranteId) => {
    socket.join(`restaurante_${restauranteId}`);
    console.log(`➡️ Restaurante entrou na sala: restaurante_${restauranteId}`);
  });

  socket.on('disconnect', () => {
    console.log('🔌 Desconectado');
  });
});

//
// ▶️ Iniciar servidor
//
server.listen(PORT, () => {
  console.log(`🚀 Servidor a correr em http://localhost:${PORT}`);

  
});

