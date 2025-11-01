-- Identificar automaticamente ordens com atrasos (data prevista de conclusão ultrapassada).
CREATE VIEW vw_OrdensAtrasadas AS
SELECT
    ordemID,
    dataPrevistaConclusao,
    estado,
    DATEDIFF(DAY, dataPrevistaConclusao, CAST(GETDATE() AS DATE)) AS diasAtraso
FROM OrdemFabrico
WHERE
    estado NOT IN ('concluida', 'cancelada')
    AND dataPrevistaConclusao < CAST(GETDATE() AS DATE);