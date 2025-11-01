// Tipo_Paciente adicionado aos Pacientes
[
  {
    $lookup: {
      from: "RegistosClinicos",
      localField: "ID_Paciente",
      foreignField: "ID_Paciente",
      as: "Atendimentos"
    }
  },
  {
    $lookup: {
      from: "DoencasCronicas",
      localField: "Atendimentos.Codigo_CID10",
      foreignField: "Codigo_CID10",
      as: "Condicoes_Cronicas"
    }
  },
  // {
  //   $addFields: {
  //     Contacto: {
  //       Telefone: {
  //         $cond: {
  //           if: {
  //             $in: [
  //               "$Telefone",
  //               [null, "", "desconhecido"]
  //             ]
  //           },
  //           then: "não fornecido",
  //           else: "$Telefone"
  //         }
  //       },
  //       Email: {
  //         $cond: {
  //           if: {
  //             $in: [
  //               "$Email",
  //               [null, "", "desconhecido"]
  //             ]
  //           },
  //           then: "não fornecido",
  //           else: "$Email"
  //         }
  //       }
  //     }
  //   }
  // },
  {
    $addFields: {
      Atendimentos: {
        $sortArray: {
          input: "$Atendimentos",
          sortBy: {
            Data_Atendimento: 1
          }
        }
      }
    }
  },
  // {
  //   $addFields: {
  //     Total_Atendimentos: {
  //       $size: "$Atendimentos"
  //     },
  //     Data_Registo: "$Data_Registo",
  //     Anos_Desde_Registo: {
  //       $dateDiff: {
  //         startDate: "$Data_Registo",
  //         endDate: "$$NOW",
  //         unit: "year"
  //       }
  //     },
  //     Primeira_Visita: {
  //       $min: "$Atendimentos.Data_Atendimento"
  //     },
  //     Ultima_Visita: {
  //       $max: "$Atendimentos.Data_Atendimento"
  //     }
  //   }
  // }
  {
    $addFields: {
      Primeira_Visita: {
        $min: "$Atendimentos.Data_Atendimento"
      },
      Ultima_Visita: {
        $max: "$Atendimentos.Data_Atendimento"
      }
    }
  },
  // {
  //   $addFields: {
  //     Anos_Desde_Primeira_Visita: {
  //       $dateDiff: {
  //         startDate: "$Historico.Primeira_Visita",
  //         endDate: "$$NOW",
  //         unit: "year"
  //       }
  //     },
  //     Anos_Desde_Ultima_Visita: {
  //       $dateDiff: {
  //         startDate: "$Historico.Ultima_Visita",
  //         endDate: "$$NOW",
  //         unit: "year"
  //       }
  //     },
  //     Atendimentos_Antigos: {
  //       $slice: ["$Atendimentos", 3]
  //     },
  //     Atendimentos_Recentes: {
  //       $slice: ["$Atendimentos", -3]
  //     },
  //     Condicoes_Cronicas: "$Condicoes_Cronicas"
  //   }
  // }
  // {
  //   $addFields: {
  //     Tipo_Paciente: {
  //       Novo: {
  //         $eq: ["$Historico.Total_Atendimentos", 1]
  //       },
  //       Regular: {
  //         $lte: [
  //           "$Historico.Anos_Desde_Ultima_Visita",
  //           5
  //         ]
  //       },
  //       Cronico: {
  //         $gt: [
  //           {
  //             $size: "$Historico.Condicoes_Cronicas"
  //           },
  //           0
  //         ]
  //       }
  //     }
  //   }
  // }
  {
    $addFields: {
      Anos_Desde_Ultima_Visita: {
        $dateDiff: {
          startDate: "$Ultima_Visita",
          endDate: "$$NOW",
          unit: "year"
        }
      },
      Tipo_Paciente: {
        Novo: {
          $eq: ["Total_Atendimentos", 1]
        },
        Regular: {
          $lte: ["$Anos_Desde_Ultima_Visita", 5]
        },
        Cronico: {
          $gt: [
            {
              $size: "$Condicoes_Cronicas"
            },
            0
          ]
        }
      }
    }
  },
  {
    $project: {
      ID_Paciente: 1,
      Nome_Completo: 1,
      Data_Nascimento: 1,
      Género: 1,
      Contacto: 1,
      Data_Registo: 1,
      /*Historico: {
    Total_Atendimentos: 1,
    //Data_Registo: 1,
    Anos_Desde_Registo: 1,
     Primeira_Visita: 1,
    Ultima_Visita: 1,
    Anos_Desde_Ultima_Visita: 1,
     Atendimentos_Recentes: 1,
    Atendimentos_Antigos: 1,
     Doencas_Cronicas: 1,
    },*/
      Tipo_Paciente: 1
    }
  },
  {
    $out: "PacientesAtendidos"
  }
]


// Contacto adicionado aos Pacientes
db.getCollection('Pacientes').aggregate(
  [
   {
      $addFields: {
        Telefone: {
          $cond: {
            if: {
              $in: [
                "$Telefone",
                [null, "", "desconhecido"]
              ]
            },
            then: "não fornecido",
            else: "$Telefone"
          }
        },

        Email: {
          $cond: {
            if: {
              $in: [
                "$Email",
                [null, "", "desconhecido"]
              ]
            },
            then: "não fornecido",
            else: "$Email"
          }
        }
      }
    },

    {
      $project: {
        ID_Paciente: 1,
        Nome_Completo: 1,
        Data_Nascimento: 1,
        Género: 1,
        Contacto: {
          Telefone: '$Telefone',
          Email: '$Email'
        },
        Tipo_Paciente: '$Tipo_Paciente',
      }
    }
  ],
);

// Registos_Clinicos adicionados aos Pacientes
db.getCollection('Pacientes').aggregate(
  [
    {
      $lookup: {
        from: 'RegistosClinicos',
        localField: 'ID_Paciente',
        foreignField: 'ID_Paciente',
        as: 'Registos'
      }
    },
    {
      $addFields: {
        Principais: {
          $filter: {
            input: '$Registos',
            as: 'registo',
            cond: {
              $eq: [
                '$$registo.Tipo_Diagnostico',
                'Principal'
              ]
            }
          }
        },
        Secundarios: {
          $filter: {
            input: '$Registos',
            as: 'registo',
            cond: {
              $eq: [
                '$$registo.Tipo_Diagnostico',
                'Secundario'
              ]
            }
          }
        }
      }
    },
    { $unset: 'Registos' },
    {
      $project: {
        ID_Paciente: 1,
        Nome_Completo: 1,
        Data_Nascimento: 1,
        Género: 1,
        Contacto: 1,
        Tipo_Paciente: 1,
        Registos_Clinicos: {
          Principais: '$Principais',
          Secundarios: '$Secundarios'
        }
      }
    }
  ],
);

// Registos_Tratamentos adicionado aos Pacientes
db.getCollection('Pacientes').aggregate(
  [
    {
      $lookup: {
        from: 'Tratamentos',
        localField: 'ID_Paciente',
        foreignField: 'ID_Paciente',
        as: 'Tratamentos'
      }
    },
    {
      $addFields: {
        Realizados: {
          $filter: {
            input: '$Tratamentos',
            as: 'tratamento',
            cond: {
              $eq: [
                '$$tratamento.Realizado',
                'Sim'
              ]
            }
          }
        },
        Nao_Realizados: {
          $filter: {
            input: '$Tratamentos',
            as: 'tratamento',
            cond: {
              $eq: [
                '$$tratamento.Realizado',
                'Nao'
              ]
            }
          }
        }
      }
    },
    { $unset: 'Tratamentos' },
    {
      $project: {
        ID_Paciente: 1,
        Nome_Completo: 1,
        Data_Nascimento: 1,
        Género: 1,
        Contacto: 1,
        Tipo_Paciente: 1,
        Registos_Clinicos: 1,
        Registos_Tratamentos: {
          Realizados: '$Realizados',
          Nao_Realizados: '$Nao_Realizados'
        }
      }
    }
  ],
  { maxTimeMS: 0, allowDiskUse: true }
);

// Registos_Transferencias adionado aos Pacientes
db.getCollection('Pacientes').aggregate(
  [
    {
      $lookup: {
        from: 'Transferencias',
        localField: 'ID_Paciente',
        foreignField: 'ID_Paciente',
        as: 'Transferencias'
      }
    },
    {
      $addFields: {
        Urgentes: {
          $filter: {
            input: '$Transferencias',
            as: 'transferencia',
            cond: {
              $eq: [
                '$$transferencia.Tipo_Transferencia',
                'Urgente'
              ]
            }
          }
        },
        Elativas: {
          $filter: {
            input: '$Transferencias',
            as: 'transferencia',
            cond: {
              $eq: [
                '$$transferencia.Tipo_Transferencia',
                'Elativa'
              ]
            }
          }
        }
      }
    },
    { $unset: 'Transferencias' },
    {
      $project: {
        ID_Paciente: 1,
        Nome_Completo: 1,
        Data_Nascimento: 1,
        Género: 1,
        Contacto: 1,
        Tipo_Paciente: 1,
        Registos_Clinicos: 1,
        Registos_Tratamentos: 1,
        Registos_Transferencias: {
          Urgentes: '$Urgente',
          Elativas: '$Elativa'
    }
      }
    }
  ],
);

// Adicionado a aba Registos_Clinicos nos Pacientes os Diagnosticos Cronicos
db.getCollection('Pacientes').aggregate(
  [
    {
      $lookup: {
        from: 'RegistosClinicos',
        localField: 'ID_Paciente',
        foreignField: 'ID_Paciente',
        as: 'Registos'
      }
    },
    {
      $addFields: {
        Principais: {
          $filter: {
            input: '$Registos',
            as: 'registo',
            cond: {
              $eq: [
                '$$registo.Tipo_Diagnostico',
                'Principal'
              ]
            }
          }
        },
        Secundarios: {
          $filter: {
            input: '$Registos',
            as: 'registo',
            cond: {
              $eq: [
                '$$registo.Tipo_Diagnostico',
                'Secundario'
              ]
            }
          }
        },
        Cronicos: {
          $filter: {
            input: '$Registos',
            as: 'registo',
            cond: {
              $eq: [
                '$$registo.Diagnostico_Cronico',
                'Sim'
              ]
            }
          }
        }
      }
    },
    { $unset: 'Registos' },
    {
      $project: {
        ID_Paciente: 1,
        Nome_Completo: 1,
        Data_Nascimento: 1,
        Género: 1,
        Contacto: 1,
        Tipo_Paciente: 1,
        Registos_Clinicos: {
          Principais: '$Principais',
          Secundarios: '$Secundarios',
          Cronicos: '$Cronicos'
        },
        Registos_Tratamentos:
          '$Registos_Tratamentos',
        Registos_Transferncias:
          '$Registos_Transferencias'
      }
    }
  ],
);