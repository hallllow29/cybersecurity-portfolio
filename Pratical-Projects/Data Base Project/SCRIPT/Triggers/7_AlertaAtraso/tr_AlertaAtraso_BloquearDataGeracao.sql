-- Bloquear data Geracao no AlertaAtraso
CREATE TRIGGER tr_AlertaAtraso_BloquearDataGeracao
ON AlertaAtraso
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.alertaAtrasoID = d.alertaAtrasoID
        WHERE i.dataGeracao <> d.dataGeracao
    )
    BEGIN
        RAISERROR('A data de geração do alerta não pode ser alterada.', 16, 1);
        RETURN;
    END;

    -- Permitir outras alterações válidas
    UPDATE a
    SET
        ordemID = i.ordemID,
        motivo = i.motivo
    FROM AlertaAtraso a
    JOIN inserted i ON a.alertaAtrasoID = i.alertaAtrasoID;
END;