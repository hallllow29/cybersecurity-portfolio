-- Bloquear data geracao no AlertaQualidade
CREATE TRIGGER tr_AlertaQualidade_BloquearDataGeracao
ON AlertaQualidade
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.alertaQualidadeID = d.alertaQualidadeID
        WHERE i.dataGeracao <> d.dataGeracao
    )
    BEGIN
        RAISERROR('A data de geração do alerta de qualidade não pode ser alterada.', 16, 1);
        RETURN;
    END;

    -- Permite alterações aos restantes campos
    UPDATE a
    SET
        descricao = i.descricao,
        execucaoID = i.execucaoID,
        subcontratadoID = i.subcontratadoID
    FROM AlertaQualidade a
    JOIN inserted i ON a.alertaQualidadeID = i.alertaQualidadeID;
END;