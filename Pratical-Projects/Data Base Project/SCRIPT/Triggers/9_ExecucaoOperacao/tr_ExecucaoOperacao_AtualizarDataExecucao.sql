-- Atualizar data execução na ExecucaoOperacao
CREATE TRIGGER tr_ExecucaoOperacao_AtualizarDataExecucao
ON ExecucaoOperacao
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE eo
    SET dataExecucao = SYSDATETIME()
    FROM ExecucaoOperacao eo
    JOIN inserted i ON eo.execucaoID = i.execucaoID
    WHERE
        eo.dataExecucao IS NULL AND
        (
            i.quantidadeRecebida > 0 OR
            (i.quantidadeEnviada > 0 AND i.quantidadeRecebida <= i.quantidadeEnviada)
        );
END;