-- Calcular o custo total de cada ordem considerando: Operações internas (baseado em tempo estimado) Operações subcontratadas (preço acordado)
CREATE VIEW vw_CustoTotalPorOrdemFabrico AS
WITH CustosInternos AS (
    SELECT
        ofa.ordemID,
        SUM(eo.quantidadeEnviada * ISNULL(dft.duracaoUnitariaMin, 0) * ISNULL(dft.custoMinuto, 0)) AS custoInterno
    FROM OrdemFabrico ofa
    JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
    JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
    JOIN DetalheFichaTecnica dft
        ON io.fichaTecnicaID = dft.fichaTecnicaID
        AND eo.operacaoID = dft.operacaoID
    WHERE eo.subcontratadoID IS NULL
    GROUP BY ofa.ordemID
),
CustosSubcontratados AS (
    SELECT
        ofa.ordemID,
        SUM(eo.quantidadeEnviada * ISNULL(s.custoServico, 0)) AS custoSubcontratado
    FROM OrdemFabrico ofa
    JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
    JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
    JOIN Subcontratado s ON eo.subcontratadoID = s.subcontratadoID
    GROUP BY ofa.ordemID
),
CustosMateriais AS (
    SELECT
        ofa.ordemID,
        SUM(cmr.quantidadeUtilizada * ISNULL(m.custoUnitario, 0)) AS custoMaterial
    FROM OrdemFabrico ofa
    JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
    JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
    JOIN ConsumoMaterialReal cmr ON cmr.execucaoID = eo.execucaoID
    JOIN Material m ON cmr.materialID = m.materialID
    GROUP BY ofa.ordemID
)

SELECT
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.dataPrevistaConclusao,
    ofa.estado,
    ISNULL(ci.custoInterno, 0) AS custoInternoTotal,
    ISNULL(cs.custoSubcontratado, 0) AS custoSubcontratadoTotal,
    ISNULL(cm.custoMaterial, 0) AS custoMaterialTotal,
    ISNULL(ci.custoInterno, 0) + ISNULL(cs.custoSubcontratado, 0) + ISNULL(cm.custoMaterial, 0) AS custoTotal
FROM OrdemFabrico ofa
LEFT JOIN CustosInternos ci ON ci.ordemID = ofa.ordemID
LEFT JOIN CustosSubcontratados cs ON cs.ordemID = ofa.ordemID
LEFT JOIN CustosMateriais cm ON cm.ordemID = ofa.ordemID;