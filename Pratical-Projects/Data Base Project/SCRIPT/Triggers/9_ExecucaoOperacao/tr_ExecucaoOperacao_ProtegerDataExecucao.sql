-- Proteger data execução na ExecucaoOperacao
CREATE TRIGGER tr_ExecucaoOperacao_ProtegerDataExecucao
ON ExecucaoOperacao
AFTER UPDATE
AS

BEGIN
    SET NOCOUNT ON;
    -- dataExecucao inicia como NULL antes da primeira execução real
    -- Após o primeiro registo de quantidade recebida, é preenchida automaticamente via trigger
    -- Este trigger impede que dataExecucao seja posteriormente alterada para NULL ou para uma data posterior,
    -- preservando a integridade do histórico de execução, evitando fraude ou enganos

    -- 1. Impede que dataExecucao seja apagada (tornada NULL) se já existia valor
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.execucaoID = d.execucaoID
        WHERE d.dataExecucao IS NOT NULL AND i.dataExecucao IS NULL
    )
    BEGIN
        RAISERROR('Não é permitido remover a data de execução de uma operação já registada.', 16, 1);
        ROLLBACK;
        RETURN;
    END

    -- 2. Impede que a dataExecucao seja atualizada para uma data anterior à atual
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.execucaoID = d.execucaoID
        WHERE
            i.dataExecucao IS NOT NULL
            AND i.dataExecucao < SYSDATETIME()
            AND d.dataExecucao IS NOT NULL  -- só valida se for uma atualização real
            AND i.dataExecucao <> d.dataExecucao
    )
    BEGIN
        RAISERROR('Não é permitido definir uma data de execução anterior ao momento atual.', 16, 1);
        ROLLBACK;
        RETURN;
    END
END;