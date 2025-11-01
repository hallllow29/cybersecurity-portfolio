-- Consultar a lista de operações pendentes para cada ordem, ordenadas por prioridade.
CREATE VIEW vw_OperacoesPendentesPorPrioridade AS
SELECT 
    ofa.ordemID,
    io.itemOrdemID,
    p.nome AS nomeProduto,
    CONCAT(p.modelo, ' ', p.cor, ' ', ISNULL(p.variante, '')) AS produtoDetalhado,
    o.operacaoID,
    o.nome AS nomeOperacao,
    dft.ordemExecucao AS prioridade,
    io.quantidadePlaneada,
    ISNULL(eoTotal.totalRecebido, 0) AS quantidadeRecebida,
    CASE 
        WHEN ISNULL(eoTotal.totalRecebido, 0) = 0 THEN 'pendente'
        WHEN ISNULL(eoTotal.totalRecebido, 0) < io.quantidadePlaneada THEN 'parcial'
        ELSE 'concluída'
    END AS estadoOperacao
FROM OrdemFabrico ofa
JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
JOIN Produto p ON io.produtoID = p.produtoID
JOIN DetalheFichaTecnica dft ON io.fichaTecnicaID = dft.fichaTecnicaID
JOIN Operacao o ON dft.operacaoID = o.operacaoID
OUTER APPLY (
    SELECT SUM(eo.quantidadeRecebida) AS totalRecebido
    FROM ExecucaoOperacao eo
    WHERE eo.itemOrdemID = io.itemOrdemID
      AND eo.operacaoID = dft.operacaoID
) eoTotal
WHERE ISNULL(eoTotal.totalRecebido, 0) < io.quantidadePlaneada;