-- Bloquear delete se ficha tiver ativa na DetalheFichaTecnica
CREATE TRIGGER tr_DetalheFichaTecnica_BloquearDeleteSeAtiva
ON DetalheFichaTecnica
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM deleted d
        JOIN FichaTecnica f ON d.fichaTecnicaID = f.fichaTecnicaID
        WHERE f.estado = 'ativa'
    )
    BEGIN
        RAISERROR('Não é permitido eliminar operações de uma ficha técnica ativa.', 16, 1);
        RETURN;
    END;

    DELETE FROM DetalheFichaTecnica
    WHERE detalheID IN (SELECT detalheID FROM deleted);
END;