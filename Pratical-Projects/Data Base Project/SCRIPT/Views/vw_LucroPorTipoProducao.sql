-- Lucro da produção
CREATE VIEW vw_LucroPorTipoProducao AS
SELECT
    tipo.tipoProducao,
    COUNT(DISTINCT io.itemOrdemID) AS totalItens,
    SUM(io.quantidadePlaneada * p.precoVenda) AS receitaTotal,

    -- Custo das execuções (interno ou subcontratado)
    SUM(eo.custo) AS custoExecucaoTotal,

    -- Custo de materiais usados
    SUM(ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)) AS custoMaterialTotal,

    -- Custo total
    SUM(eo.custo + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)) AS custoTotal,

    -- Lucro
    SUM(io.quantidadePlaneada * p.precoVenda) -
    SUM(eo.custo + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)) AS lucroTotal

FROM ExecucaoOperacao eo
JOIN ItemOrdemFabrico io ON eo.itemOrdemID = io.itemOrdemID
JOIN Produto p ON p.produtoID = io.produtoID
LEFT JOIN ConsumoMaterialReal cmr ON cmr.execucaoID = eo.execucaoID
LEFT JOIN Material m ON m.materialID = cmr.materialID

-- Distingue produção interna vs subcontratada
CROSS APPLY (
    SELECT
        CASE
            WHEN eo.subcontratadoID IS NULL THEN 'Interna'
            ELSE 'Subcontratada'
        END AS tipoProducao
) AS tipo

GROUP BY tipo.tipoProducao;