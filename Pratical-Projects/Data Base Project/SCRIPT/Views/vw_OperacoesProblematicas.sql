-- Mostrar operações problemáticas (com maior índice de perdas)
CREATE VIEW vw_OperacoesProblematicas AS
SELECT
    eo.operacaoID,
    op.nome AS nomeOperacao,
    COUNT(*) AS totalExecucoes,
    CAST(
        AVG(
            CASE 
                WHEN eo.quantidadeEnviada > 0 
                THEN 100.0 * (eo.quantidadeEnviada - eo.quantidadeRecebida) / eo.quantidadeEnviada
                ELSE 0
            END
        ) AS DECIMAL(5,2)
    ) AS mediaPerdas
FROM ExecucaoOperacao eo
JOIN Operacao op ON eo.operacaoID = op.operacaoID
GROUP BY eo.operacaoID, op.nome
HAVING AVG(
    CASE 
        WHEN eo.quantidadeEnviada > 0 
        THEN 100.0 * (eo.quantidadeEnviada - eo.quantidadeRecebida) / eo.quantidadeEnviada
        ELSE 0
    END
) > 0;