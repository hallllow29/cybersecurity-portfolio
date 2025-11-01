
module namespace medsync = 'http://medsync.org/reports';

import module namespace http = "http://expath.org/ns/http-client";

declare
  %rest:path("/registosClinicos")
  %rest:GET
  %rest:query-param("mes", "{$mes}", "")
  %rest:query-param("ano", "{$ano}", "")
  %output:method("xml")
  %output:indent("yes")
function medsync:get-clinical-records($mes as xs:string, $ano as xs:string) {
  let $url := concat('http://localhost:3000/api/registosClinicos?mes=', $mes, '&amp;ano=', $ano)

  let $response := http:send-request(
    <http:request method="get" href="{$url}">
      <http:header name="Accept" value="application/json"/>
    </http:request>
  )[2]
  
  (:Informação Detalhada do hospital:)
  let $hospitalInfo :=
    <HospitalInfo>
      <CodigoHospital>1234</CodigoHospital>
      <Nome>Hospital Ruben Pedro</Nome>
      <Morada>Rua do hospital, 1234</Morada>
      <DataRelatorio>{$mes} - {$ano}</DataRelatorio>
    </HospitalInfo>

  return 
    element RegistosClinicos {
      $hospitalInfo,
      for $registo in $response/json[@type="array"]/_[@type="object"]
      return
        element RegistoClinico {
          element ID_Atendimento { $registo/ID__Atendimento/text()},
          element ID_Paciente {$registo/ID__Paciente/text()},
          element ID_Profissional {$registo/ID__Profissional/text()},
          element Data_Atendimento { substring-before($registo/Data__Atendimento/text(), "T") },
          
          element Responsavel {
                  element ID_Profissional { $registo/Responsavel/ID__Profissional/text() },
                  element Nome_Completo { $registo/Responsavel/Nome__Completo/text() },
                  element Especialidade { $registo/Responsavel/Especialidade/text() }
                },
          
          element Tipo_Diagnostico { $registo/Tipo__Diagnostico/text() },
          element Codigo_CID10 { $registo/Codigo__CID10/text() },
          element Descricao_Diagnostico { $registo/Descricao__Diagnostico/text()},
          
          element Tratamentos {
                  for $tratamento in $registo/Tratamentos/_[@type="object"]
                  return
                    element Tratamento {
                      element ID_Tratamento { $tratamento/ID__Tratamento/text() },
                      element ID_Paciente { $tratamento/ID__Paciente/text() },
                      element ID_Registo_Clinico {$tratamento/ID__Registo__Clinico/text()},
                      element Tipo_Tratamento { $tratamento/Tipo__Tratamento/text() },
                      element Realizado { $tratamento/Realizado/text() }
                    }
                },
          
          element Paciente {
            element Nome_Completo {$registo/Paciente/Nome__Completo/text() },
            element Data_Nascimento { substring-before($registo/Paciente/Data__Nascimento/text(), "T") },
            element Faixa_Etaria {$registo/Paciente/Faixa__Etaria/text()},
            element Género {$registo/Paciente/Género/text() },
            element Telefone {$registo/Paciente/Telefone/text() },
            element Email {$registo/Paciente/Email/text() },
            element ID_Paciente {$registo/ID__Paciente/text() },
            element Tipo_Paciente {$registo/Paciente/Tipo__Paciente/text()}
          }
        }
    }
};
