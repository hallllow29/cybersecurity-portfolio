-- Bloquear delete no Subcontratado
CREATE TRIGGER tr_Subcontratado_BloquearDelete
ON Subcontratado
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM deleted d
        JOIN ExecucaoOperacao eo ON eo.subcontratadoID = d.subcontratadoID
    )
    BEGIN
        RAISERROR('Não é possível eliminar um subcontratado que já foi usado em operações.', 16, 1);
        RETURN;
    END;

    DELETE FROM Subcontratado
    WHERE subcontratadoID IN (SELECT subcontratadoID FROM deleted);
END;