-- Bloquear delete na ExecucaoOperacao
CREATE TRIGGER tr_ExecucaoOperacao_BloquearDelete
ON ExecucaoOperacao
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    RAISERROR('Não é permitido eliminar execuções de operações.', 16, 1);
END;