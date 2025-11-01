-- Calcular a eficiência média de produção (percentagem de materiais aproveitados) por período (dia, semana, mês).
CREATE VIEW vw_EficienciaOperacao_DiaSemanaMes AS
WITH Ef_Diaria AS (
    SELECT
        eo.operacaoID,
        CAST(eo.dataExecucao AS date) AS periodo,
        CAST(SUM(eo.quantidadeRecebida * 1.0) / NULLIF(SUM(eo.quantidadeEnviada), 0) AS DECIMAL(5,4)) AS eficienciaDiaria
    FROM ExecucaoOperacao eo
    GROUP BY eo.operacaoID, CAST(eo.dataExecucao AS date)
),
Ef_Semanal AS (
    SELECT
        eo.operacaoID,
        FORMAT(eo.dataExecucao, 'yyyy-ww') AS periodo,
        CAST(SUM(eo.quantidadeRecebida * 1.0) / NULLIF(SUM(eo.quantidadeEnviada), 0) AS DECIMAL(5,4)) AS eficienciaSemanal
    FROM ExecucaoOperacao eo
    GROUP BY eo.operacaoID, FORMAT(eo.dataExecucao, 'yyyy-ww')
),
Ef_Mensal AS (
    SELECT
        eo.operacaoID,
        FORMAT(eo.dataExecucao, 'yyyy-MM') AS periodo,
        CAST(SUM(eo.quantidadeRecebida * 1.0) / NULLIF(SUM(eo.quantidadeEnviada), 0) AS DECIMAL(5,4)) AS eficienciaMensal
    FROM ExecucaoOperacao eo
    GROUP BY eo.operacaoID, FORMAT(eo.dataExecucao, 'yyyy-MM')
)
SELECT
    d.operacaoID,
    op.nome AS nomeOperacao,
    d.periodo AS periodoDiario,
    d.eficienciaDiaria,
    s.eficienciaSemanal,
    m.eficienciaMensal
FROM Ef_Diaria d
LEFT JOIN Ef_Semanal s
    ON s.operacaoID = d.operacaoID
   AND FORMAT(d.periodo, 'yyyy-ww') = s.periodo
LEFT JOIN Ef_Mensal m
    ON m.operacaoID = d.operacaoID
   AND FORMAT(d.periodo, 'yyyy-MM') = m.periodo
JOIN Operacao op ON op.operacaoID = d.operacaoID;