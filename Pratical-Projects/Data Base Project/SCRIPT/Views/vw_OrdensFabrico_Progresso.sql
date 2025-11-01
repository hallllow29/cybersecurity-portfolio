-- Consultar as ordens de fabrico em curso, com indicação do progresso (quantidade produzida vs. quantidade planeada).
CREATE VIEW vw_OrdensFabrico_Progresso AS
SELECT 
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.dataPrevistaConclusao,
    io.itemOrdemID,
    p.nome AS nomeProduto,
    CONCAT(p.modelo, ' ', p.cor, ' ', p.variante) AS produtoDetalhado,
    io.quantidadePlaneada,
    ISNULL(SUM(eo.quantidadeRecebida), 0) AS quantidadeProduzida,
    ROUND(
        100.0 * ISNULL(SUM(eo.quantidadeRecebida), 0) / NULLIF(io.quantidadePlaneada, 0),
        2
    ) AS percentagemProgresso,
    io.estadoProducao
FROM OrdemFabrico ofa
JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
JOIN Produto p ON io.produtoID = p.produtoID
LEFT JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
WHERE ofa.estado = 'em_execucao'
GROUP BY 
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.dataPrevistaConclusao,
    io.itemOrdemID,
    p.nome,
    p.modelo, p.cor, p.variante,
    io.quantidadePlaneada,
    io.estadoProducao;