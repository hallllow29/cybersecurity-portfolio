const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const User = require('../models/Accounts/User');
require('dotenv').config();

async function criarAdmin() {
  await mongoose.connect(process.env.MONGO_URI);

  const passwordEncriptada = await bcrypt.hash('admin', 10);

  const admin = new User({
    email: 'admin@admin.com',
    password: passwordEncriptada,
    role: 'admin',
  });

  await admin.save();
  console.log('Admin criado com sucesso!');
  mongoose.disconnect();
}

criarAdmin();
