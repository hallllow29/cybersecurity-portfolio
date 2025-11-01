module namespace medsync = 'http://medsync.org/reports';

import module namespace http = "http://expath.org/ns/http-client";

declare
  %rest:path("/relatorioTransferencias")
  %rest:GET
  %rest:query-param("mes", "{$mes}", "")
  %rest:query-param("ano", "{$ano}", "")
  %output:method("xml")
  %output:indent("yes")
function medsync:get-relatorio-transferencias($mes as xs:string, $ano as xs:string) {

  let $url := concat('http://localhost:3000/api/transferencias?mes=', $mes, '&amp;ano=', $ano)
  
  let $response := http:send-request(<http:request method="get" href="{$url}"/>)

  let $transferencias := $response/json[@type="array"]/_[@type="object"]

  
  (: Contar quantas trasnferencias houveram :)
  let $totalTransferencias := count($transferencias)

  
  (: Total  transferencias por motivo:)
  let $transferenciasPorMotivo :=
    for $motivo in distinct-values($transferencias/Motivo)
    let $valorMotivo := string($motivo) 
    return
      <Motivo>
        <Descricao>{$valorMotivo}</Descricao>
        <Total>{count($transferencias[Motivo = $motivo])}</Total>
      </Motivo>

(: Total por Tipo :)
  let $transferenciasPorTipo :=
    for $tipo in distinct-values($transferencias/Tipo__Transferencia)
    let $valorTipo := string($tipo)
    return
      <Tipo>
        <Descricao>{$valorTipo}</Descricao>
        <Total>{count($transferencias[Tipo__Transferencia = $tipo])}</Total>
      </Tipo>
  (: Total de trasnferencias por hospital:)
  let $transferenciasPorHospital :=
    for $hospital in distinct-values($transferencias/Hospital__Origem)
    let $valorHospital := string($hospital)
    return
      <Hospital>
        <Nome>{$valorHospital}</Nome>
        <Total>{count($transferencias[Hospital__Origem = $hospital])}</Total>
      </Hospital>
  
  (: Construção final do relatorio com os dados todos :)
  return
    <RelatorioTransferenciasMensais>
      <TotalTransferencias>{$totalTransferencias}</TotalTransferencias>
      <TransferenciasPorMotivo>{$transferenciasPorMotivo}</TransferenciasPorMotivo>
      <TransferenciasPorTipo>{$transferenciasPorTipo}</TransferenciasPorTipo>
      <TransferenciasPorHospital>{$transferenciasPorHospital}</TransferenciasPorHospital>
    </RelatorioTransferenciasMensais>
};