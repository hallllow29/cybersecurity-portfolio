-- Validar operações por ficha interna ou ficha externa
CREATE TRIGGER tr_DetalheFichaTecnica_ValidarInternaExterna
ON DetalheFichaTecnica
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Validação para INTERNAS
    IF EXISTS (
        SELECT dft.fichaTecnicaID
        FROM DetalheFichaTecnica dft
        JOIN FichaTecnica ft ON ft.fichaTecnicaID = dft.fichaTecnicaID
        JOIN Operacao o ON o.operacaoID = dft.operacaoID
        WHERE ft.origem = 'interna'
        GROUP BY dft.fichaTecnicaID
        HAVING
            COUNT(DISTINCT o.nome) <> 4
            OR
            SUM(CASE WHEN o.nome NOT IN ('Corte', 'Costura', 'Montagem', 'Acabamento') THEN 1 ELSE 0 END) > 0
            OR
            COUNT(DISTINCT dft.ordemExecucao) <> 4
            OR MIN(dft.ordemExecucao) <> 1
            OR MAX(dft.ordemExecucao) <> 4
    )
    BEGIN
        RAISERROR('Ficha Técnica interna deve conter exatamente 4 operações (Corte, Costura, Montagem, Acabamento) com ordemExecucao de 1 a 4.', 16, 1);
        ROLLBACK;
        RETURN;
    END


    -- Validação para EXTERNAS / PARCIAIS
    IF EXISTS (
        SELECT dft.fichaTecnicaID
        FROM DetalheFichaTecnica dft
        JOIN FichaTecnica ft ON ft.fichaTecnicaID = dft.fichaTecnicaID
        WHERE ft.origem IN ('externa', 'parcial')
        GROUP BY dft.fichaTecnicaID
        HAVING MAX(dft.ordemExecucao) > COUNT(*)
    )
    BEGIN
        RAISERROR('Ficha Técnica externa/parcial: ordemExecucao não pode ser maior que o número total de operações.', 16, 1);
        ROLLBACK;
        RETURN;
    END
END;