module namespace medsync = "http://medsync.org/reports";

import module namespace http = "http://expath.org/ns/http-client";

declare
  %rest:path("/relatorioRegistosClinicos")
  %rest:GET
  %rest:query-param("mes", "{$mes}", "")
  %rest:query-param("ano", "{$ano}", "")
  %output:method("xml")
  %output:indent("yes")
function medsync:get-clinical-records($mes as xs:string, $ano as xs:string) 
{

let $url := concat('http://localhost:3000/api/registosClinicos?mes=', $mes, '&amp;ano=', $ano)

  let $response := try {
    http:send-request(
      <http:request method="get" href="{$url}">
        <http:header name="Accept" value="application/json"/>
      </http:request>
    )[2] 
  }
  catch * {
    <Error>Falha na requisição HTTP</Error>
  }

  let $registos := $response/json[@type="array"]/_[@type="object"]
  
  (:Informação detalhada do hospital:)
  
  let $codigoHospital := 1234
  let $nomeHospital   := "Hospital Ruben Pedro"
  let $moradaHospital := "Rua do hospital, 1234"
  let $dataRelatorio  := concat($mes, " - ", $ano)

  (:Informação dos pacientes:)
  let $info-pacientes :=
    for $r in $registos
    let $id-pac   := $r/ID_Paciente/text()
    let $faixa    := $r/Paciente/Faixa__Etaria/text()
    let $genero   := $r/Paciente/Género/text()
    return
      <pac-info id="{ $id-pac }" 
      faixa="{ $faixa }" 
      genero="{ $genero }"
      />
  
  (:Dividir e contar pacientes por faixa etaria/genero:)
  let $pacientes-por-faixa-genero :=
    for $fg in distinct-values($info-pacientes!concat(@faixa, "||", @genero))
    let $fx := substring-before($fg, "||")
    let $gn := substring-after($fg, "||")
    let $countPacientes := count($info-pacientes[@faixa = $fx and @genero = $gn])
    
    return
      <FaixaEtariaGenero>
        <TotalPacientes>{$countPacientes}</TotalPacientes>
        <FaixaEtaria>{$fx}</FaixaEtaria>
        <Genero>{$gn}</Genero>
      </FaixaEtariaGenero>

  (: Dividir em especialidades e contar quantos casos houve por especialidade :)
  let $especialidades :=
    for $esp in distinct-values($registos/Responsavel/Especialidade/text())
    let $countEsp := count($registos[Responsavel/Especialidade/text() = $esp])
    return
      <Especialidade>
        <Nome>{ $esp }</Nome>
        <Casos>{ $countEsp }</Casos>
      </Especialidade>
  
  (: Contar quantos tratamentos realizados houveram :)
  let $num-tratamentos-realizados :=
    count(
      for $r in $registos
      for $t in $r/Tratamentos/_[@type="object"]
      where $t/Realizado/text() = "Sim"
      return $t
    )
  
  (: Contar quantas condições cronicas foram dignosticadas:)
  let $num-condicoes-cronicas :=
    count(
      for $r in $registos
      where $r/Diagnostico__Cronico = "Sim"
      return $r
    )
  
  (: Construção do relatorio em XML com os dados todos:)
  return
    <RegistosMensais>
      <HospitalInfo>
        <CodigoHospital>{$codigoHospital}</CodigoHospital>
        <Nome>{$nomeHospital}</Nome>
        <Morada>{$moradaHospital}</Morada>
        <DataRelatorio>{$dataRelatorio}</DataRelatorio>
      </HospitalInfo>

      <PacientesPorFaixaEtariaGenero>
        { $pacientes-por-faixa-genero }
      </PacientesPorFaixaEtariaGenero>

      <CasosPorEspecialidade>
        { $especialidades }
      </CasosPorEspecialidade>

      <NumeroTratamentosRealizados>{$num-tratamentos-realizados}</NumeroTratamentosRealizados>
      <NumeroCondicoesCronicasDiagnosticadas>{$num-condicoes-cronicas}</NumeroCondicoesCronicasDiagnosticadas>
    </RegistosMensais>
};