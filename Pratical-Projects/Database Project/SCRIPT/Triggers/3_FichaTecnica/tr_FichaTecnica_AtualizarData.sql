-- Atualizar data na FichaTecnica
CREATE TRIGGER tr_FichaTecnica_AtualizarData
ON FichaTecnica
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE f
    SET dataAtualizacao = SYSDATETIME()
    FROM FichaTecnica f
    JOIN inserted i ON f.fichaTecnicaID = i.fichaTecnicaID;
END;