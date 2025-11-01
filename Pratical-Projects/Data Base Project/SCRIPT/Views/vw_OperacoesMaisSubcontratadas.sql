-- Listar as operações mais frequentemente subcontratadas e os respetivos custos.
CREATE VIEW vw_OperacoesMaisSubcontratadas AS
SELECT
    eo.operacaoID,
    op.nome AS nomeOperacao,
    COUNT(*) AS nExecucoesSubcontratadas,
    SUM(ISNULL(so.custoServico, 0) * eo.quantidadeEnviada) AS custoTotalSubcontratado,
    COUNT(DISTINCT eo.subcontratadoID) AS nSubcontratados
FROM ExecucaoOperacao eo
JOIN Operacao op ON eo.operacaoID = op.operacaoID
LEFT JOIN Subcontratado so ON eo.subcontratadoID = so.subcontratadoID
WHERE eo.subcontratadoID IS NOT NULL
GROUP BY eo.operacaoID, op.nome;