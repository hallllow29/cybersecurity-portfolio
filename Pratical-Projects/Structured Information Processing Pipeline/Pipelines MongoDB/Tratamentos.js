// TratamentosNaoRealizados
db.getCollection('Tratamentos').aggregate(
  [
    { $match: { Realizado: 'Nao' } },
    { $sort: { ID_Tratamento: 1 } },
  ],
);

// TratamentosRealizados
db.getCollection('Tratamentos').aggregate(
  [
    { $match: { Realizado: 'Sim' } },
    { $sort: { ID_Tratamento: 1 } },
  ],
);

// TratamentosAtualizados
db.getCollection('TratamentosNaoRealizados').aggregate(
  [
    {
      $lookup: {
        from: 'AtualizacaoTratamentos',
        localField: 'ID_Tratamento',
        foreignField: 'ID_Tratamento',
        as: 'Tratamentos_A_Atualizar'
      }
    },
    {
      $set: {
        Realizado: {
          $cond: {
            if: {
              $gt: [
                {
                  $size:
                    '$Tratamentos_A_Atualizar'
                },
                0
              ]
            },
            then: 'Sim',
            else: '$Realizado'
          }
        }
      }
    },
    {
      $project: {
        Tratamentos_Para_Atualizar: 0
      }
    }
  ]
);


// Uniao de Tratamentos + TratamentosAtualizados
db.getCollection('Tratamentos').aggregate(
  [
    { $unionWith: 'TratamentosAtualizados' },
    { $sort: { ID_Tratamento: 1 } }
  ],
);