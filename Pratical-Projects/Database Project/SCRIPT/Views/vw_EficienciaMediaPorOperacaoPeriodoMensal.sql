-- Determinar a eficiência média por operação e por período, neste caso a escolha foi mensalmente porque é a que faz mais sentido segundo o negocio
CREATE VIEW vw_EficienciaMediaPorOperacaoPeriodoMensal AS
SELECT
    eo.operacaoID,
    op.nome AS nomeOperacao,
    FORMAT(eo.dataExecucao, 'yyyy-MM') AS periodo,
    CAST(SUM(eo.quantidadeRecebida) * 1.0 / NULLIF(SUM(eo.quantidadeEnviada), 0) AS DECIMAL(5,4)) AS eficienciaMedia
FROM ExecucaoOperacao eo
JOIN Operacao op ON eo.operacaoID = op.operacaoID
GROUP BY
    eo.operacaoID,
    op.nome,
    FORMAT(eo.dataExecucao, 'yyyy-MM');