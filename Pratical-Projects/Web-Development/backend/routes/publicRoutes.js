const express = require('express');
const router = express.Router();
const Restaurante = require('../models/Accounts/Restaurante');

router.get('/restaurantes', async (req, res) => {
  try {
    const restaurantes = await Restaurante.find({ ativo: true, validado: true }).lean();
    res.json(restaurantes);
  } catch (err) {
    console.error('Erro ao listar restaurantes públicos:', err);
    res.status(500).json({ erro: 'Erro ao listar restaurantes.' });
  }
});

router.get('/categorias', (req, res) => {
  res.json([
    { nome: 'Portuguesa', icon: '🇵🇹' },
    { nome: 'Italiana', icon: '🍕' },
    { nome: 'Japonesa', icon: '🍣' },
    { nome: 'Vegetariana', icon: '🥗' },
    { nome: 'Outro', icon: '🍲' }
  ]);
});

module.exports = router;
