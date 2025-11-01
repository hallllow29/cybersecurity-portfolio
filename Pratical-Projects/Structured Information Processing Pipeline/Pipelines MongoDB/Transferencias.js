// Adicionado Registos_Clinicos ás Transferencias
db.getCollection('Transferencias').aggregate(
  [
  {
    $lookup: {
      from: "RegistosClinicos",
      localField: "ID_Paciente",
      foreignField: "ID_Paciente",
      pipeline: [
        {
          $sort: {
            Data_Atendimento: 1
          }
        }
      ],
      as: "Registos_Clinicos"
    }
  },
  {
    $unwind: "$Registos_Clinicos"
  },
  {
    $match: {
      $expr: {
        $lte: [
          "$Registos_Clinicos.Data_Atendimento",
          "$Data_Transferencia"
        ]
      }
    }
  },
  {
    $group: {
      _id: "$_id",
      ID_Transferencia: {
        $first: "$ID_Transferencia"
      },
      ID_Paciente: {
        $first: "$ID_Paciente"
      },
      ID_Profisional: {
        $first: "$ID_Profissional"
      },
      Data_Transferencia: {
        $first: "$Data_Transferencia"
      },
      Motivo: {
        $first: "$Motivo"
      },
      Destino: {
        $first: "$Destino"
      },
      Tipo_Transferencia: {
        $first: "$Tipo_Transferencia"
      },
      Hospital_Origem: {
        $first: "$Hospital_Destino"
      },
      Relatorio_Medico: {
        $push: "$Registos_Clinicos"
      }
    }
  }
],
);