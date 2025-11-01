// DiagnosticosPrincipais
db.getCollection('RegistosClinicos').aggregate(
  [
    { $match: { Tipo_Diagnostico: 'Principal' } }
  ],
);

// DiagnosticosSecundarios
db.getCollection('RegistosClinicos').aggregate(
  [
    { $match: { Tipo_Diagnostico: 'Secundario' } }
  ],
);



// Adicionado Tratamentos aos RegistosClinicos
db.getCollection('RegistosClinicos').aggregate(
  [
    {
      $lookup: {
        from: 'Tratamentos',
        localField: 'ID_Atendimento',
        foreignField: 'ID_Registo_Clinico',
        as: 'Tratamentos'
      }
    },
    {
      $project: {
        ID_Atendimento: 1,
        ID_Paciente: 1,
        ID_Profissional: 1,
        Data_Atendimento: 1,
        Tipo_Diagnostico: 1,
        Codigo_CID10: 1,
        Descricao_Diagnostico: 1,
        Tratamentos: 1
      }
    }
  ],
);

// Adicionado DiagnosticoCronico aos RegistosClinicos
db.getCollection('RegistosClinicos').aggregate(
  [
    {
      $lookup: {
        from: 'DoencasCronicas',
        localField: 'Codigo_CID10',
        foreignField: 'Codigo_CID10',
        as: 'Doencas_Cronicas'
      }
    },
    {
      $addFields: {
        Diagonostico_Cronico: {
          $cond: {
            if: {
              $gt: [
                { $size: '$Doencas_Cronicas' },
                0
              ]
            },
            then: 'Sim',
            else: 'Nao'
          }
        }
      }
    },
    { $unset: 'Doencas_Cronicas' },
    {
      $project: {
        ID_Atendimento: 1,
        ID_Paciente: 1,
        ID_Profissional: 1,
        Data_Atendimento: 1,
        Tipo_Diagnostico: 1,
        Codigo_CID10: 1,
        Descricao_Diagnostico: 1,
        Diagnostico_Cronico: 1,
        Tratamentos: '$Tratamentos'
      }
    }
  ],
);

// Adicionado Responsavel aos RegistosClinicos
db.getCollection('RegistosClinicos').aggregate(
  [
    {
      $lookup: {
        from: 'Profissionais',
        localField: 'ID_Profissional',
        foreignField: 'ID_Profissional',
        as: 'Responsavel'
      }
    },
    {
      $addFields: {
        Responsavel: {
          ID_Profissional: {
            $first: '$Responsavel.ID_Profissional'
          },
          Nome_Completo: {
            $first: '$Responsavel.Nome_Completo'
          },
          Especialidade: {
            $first: '$Responsavel.Especialidade'
          },
          Anos_Experienca: {
            $first: '$Responsavel.Anos_Experienca'
          }
        }
      }
    },
    {
      $project: {
        ID_Atendimento: 1,
        ID_Paciente: 1,
        ID_Profissional: 1,
        Data_Atendimento: 1,
        Responsavel: { $first: '$Responsavel' },
        Tipo_Diagnostico: '$Tipo_Diagnostico',
        Codigo_CID10: '$Codigo_CID10',
        Descricao_Diagnostico:
          '$Descricao_Diagnostico',
        Diagnostico_Cronico:
          '$Diagonostico_Cronico',
        Tratamentos: '$Tratamentos'
      }
    }
  ],
  { maxTimeMS: 0, allowDiskUse: true }
);
