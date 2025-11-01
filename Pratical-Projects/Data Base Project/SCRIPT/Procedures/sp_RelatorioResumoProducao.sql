-- Relatorio mensal
CREATE PROCEDURE sp_RelatorioResumoProducao
AS
BEGIN
    SET NOCOUNT ON;

    -- Resumo geral por mês
    SELECT
        FORMAT(eo.dataExecucao, 'yyyy-MM') AS periodo,
        SUM(eo.quantidadeRecebida) AS totalProduzido,
        SUM(ISNULL(eo.quantidadePerdida, 0)) AS totalPerdido,
        CAST(
            100.0 * SUM(eo.quantidadeRecebida) / NULLIF(SUM(eo.quantidadeRecebida + ISNULL(eo.quantidadePerdida, 0)), 0)
            AS DECIMAL(5,2)
        ) AS eficienciaMediaGlobalPercent,

        -- Subqueries inline para contar ordens
        (SELECT COUNT(*) FROM OrdemFabrico WHERE estado = 'concluida') AS ordensConcluidas,
        (SELECT COUNT(*) FROM OrdemFabrico WHERE estado = 'em_execucao') AS ordensEmExecucao

    FROM ExecucaoOperacao eo
    WHERE eo.dataExecucao IS NOT NULL
    GROUP BY FORMAT(eo.dataExecucao, 'yyyy-MM')
    ORDER BY periodo DESC;
END;