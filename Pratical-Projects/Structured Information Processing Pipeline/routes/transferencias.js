const express = require('express');
const router = express.Router();

module.exports = (db) => {
  //Script para listar todas as transferencias de um determinado mes/ano
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

      console.log(`Seraching for transfers between ${inicio.toISOString()} and ${fim.toISOString()}`);

      //Procura na coleção TransferenciasAPI e filtra pela Data_Transferencia
      const transferencias = await db.collection('TransferenciasAPI')
        .find({
          Data_Transferencia: {
            $gte: inicio,
            $lt: fim
          }
        })
        .toArray();

       //Veririfca se há resultados
      if (transferencias.length === 0) {
        return res.status(404).json({ erro: "No transfer found on this month and year" });
      }
      //Se houver resultados retorna a lista de transferencias encontradas no MongoDB
      res.json(transferencias);

    } catch (err) {
      console.error('Error obtaining transfers...', err);
      res.status(500).json({ erro: 'Error obtaining transfers...' });
    }
  });

  //Retorna o router configurado para ser usado no servidor principal (index.js)
  return router;
};