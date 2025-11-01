-- Lucro por ordem de fabrico
CREATE VIEW vw_LucroPorOrdemFabrico AS
SELECT
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.estado,
    p.modelo,
    p.nome AS nomeProduto,
    p.precoVenda,
    io.quantidadePlaneada,

    -- Receita esperada
    ROUND(p.precoVenda * io.quantidadePlaneada, 2) AS receitaPrevista,

    -- Custo total
    ROUND(SUM(
        ISNULL(eo.custo, 0) + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)
    ), 2) AS custoTotalReal,

    -- Lucro estimado
    ROUND(
        (p.precoVenda * io.quantidadePlaneada)
        - SUM(ISNULL(eo.custo, 0) + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)),
        2
    ) AS lucroPrevisto

FROM OrdemFabrico ofa
JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
JOIN Produto p ON p.produtoID = io.produtoID
JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
LEFT JOIN ConsumoMaterialReal cmr ON cmr.execucaoID = eo.execucaoID
LEFT JOIN Material m ON m.materialID = cmr.materialID

GROUP BY
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.estado,
    p.modelo,
    p.nome,
    p.precoVenda,
    io.quantidadePlaneada;