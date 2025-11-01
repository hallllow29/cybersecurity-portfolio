module namespace medsync = 'http://medsync.org/reports';

import module namespace http = "http://expath.org/ns/http-client";

declare
  %rest:path("/transferencias")
  %rest:GET
  %rest:query-param("mes", "{$mes}", "")
  %rest:query-param("ano", "{$ano}", "")
  %output:method("xml")
  %output:indent("yes")
function medsync:get-clinical-records($mes as xs:string, $ano as xs:string) {

  let $url := concat('http://localhost:3000/api/transferencias?mes=', $mes, '&amp;ano=', $ano)

  (: Fazendo a requisição HTTP e recebendo a resposta em JSON :)  
  let $response := http:send-request(<http:request method="get" href="{$url}"/>)
  
  (: Parse da resposta JSON :)  
  let $json := json:parse($response)
  
  (: Convertendo a resposta JSON para XML usando element :)  
  return <Transferencias>{
        for $transferencia in $response/json[@type="array"]/_[@type="object"]
        return <Transferencia>
            <ID_Transferencia>{$transferencia/ID__Transferencia/text()}</ID_Transferencia>
            <ID_Paciente>{$transferencia/ID__Paciente/text()}</ID_Paciente>
            <Data_Transferencia>{substring-before($transferencia/Data__Transferencia/text(), "T")}</Data_Transferencia>
            <Motivo>{$transferencia/Motivo/text()}</Motivo>
            <Destino>{$transferencia/Destino/text()}</Destino>
            <Tipo_Transferencia>{$transferencia/Tipo__Transferencia/text()}</Tipo_Transferencia>
            <Hospital_Origem>{$transferencia/Hospital__Origem/text()}</Hospital_Origem>
            <Relatorio_Medico>{
                for $relatorio in $transferencia/Relatorio__Medico/_[@type="object"]
                return <Relatorio>
                    <ID_Atendimento>{$relatorio/ID__Atendimento/text()}</ID_Atendimento>
                    <Data_Atendimento>{substring-before($relatorio/Data__Atendimento/text(), "T")}</Data_Atendimento>
                    <Responsavel>
                        <ID_Profissional>{$relatorio/Responsavel/ID__Profissional/text()}</ID_Profissional>
                        <Nome_Completo>{$relatorio/Responsavel/Nome__Completo/text()}</Nome_Completo>
                        <Especialidade>{$relatorio/Responsavel/Especialidade/text()}</Especialidade>
                    </Responsavel>
                    <Diagnostico>
                        <Tipo_Diagnostico>{$relatorio/Tipo__Diagnostico/text()}</Tipo_Diagnostico>
                        <Codigo_CID10>{$relatorio/Codigo__CID10/text()}</Codigo_CID10>
                        <Descricao_Diagnostico>{$relatorio/Descricao__Diagnostico/text()}</Descricao_Diagnostico>
                        <Diagnostico_Cronico>{$relatorio/Diagnostico__Cronico/text()}</Diagnostico_Cronico>
                    </Diagnostico>
                    <Tratamentos>{
                        for $tratamento in $relatorio/Tratamentos/_[@type="object"]
                        return <Tratamento>
                            <ID_Tratamento>{$tratamento/ID__Tratamento/text()}</ID_Tratamento>
                            <Tipo_Tratamento>{$tratamento/Tipo__Tratamento/text()}</Tipo_Tratamento>
                            <Realizado>{$tratamento/Realizado/text()}</Realizado>
                        </Tratamento>
                    }</Tratamentos>
                </Relatorio>
            }</Relatorio_Medico>
        </Transferencia>
    }</Transferencias>
};