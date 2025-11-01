const express = require('express');
const router = express.Router();

module.exports = (db) => {
  //Script para listar os registos clinicos de um determinado mes/ano
  router.get('/', async (req, res) => {
    try {
      
      //Extrai os parametros mes e ano do URL
      const { mes, ano } = req.query;

      if (!mes || !ano) {
        return res.status(400).json({ erro: "Please inform the month and year" });
      }
      //Converte o mes/ano para numero
      const mesInt = parseInt(mes); 
      const anoInt = parseInt(ano);
      
      //O inicio e o fim da data de pesquisa
      const inicio = new Date(anoInt, mesInt - 1, 1); 
      const fim = new Date(anoInt, mesInt, 1);     

      console.log(`Searching for services between ${inicio.toISOString()} and ${fim.toISOString()}`);

      //Procura na coleção 'RegistosClinicosAPI' pela Data_Atendimento e ve se e maior e igual ao primeiro dia do mes e menor que o primeiro dia do mes seguinte
      const atendimentos = await db.collection('RegistosClinicosAPI')
        .find({
          Data_Atendimento: {
            $gte: inicio,
            $lt: fim
          }
        })
        .toArray();

      //Veririfca se há resultados
      if (atendimentos.length === 0) {
        return res.status(404).json({ erro: "No service found for this month and year" });
      }
      
      //Se houver resultados retorna a lista de registos clinicos encontradas no MongoDB
      return res.json(atendimentos);

    } catch (err) {
      console.error('Error obtaining services...', err);
      return res.status(500).json({ erro: 'Error obtaining services...' });
    }
  });

  //Retorna o router configurado para ser usado no servidor principal (index.js)
  return router;
};