-- Registar execuções
/*
Exemplo de uso:
EXEC dbo.sp_RegistarExecucaoOperacao
    @execucaoID = 7,
    @quantidadeEnviada = 120,
    @quantidadeRecebida = 115,
    @subcontratadoID = NULL (No caso de externa, definir)
    @precoPorUnidade = NULL; (No caso de externo, definir)
*/
CREATE PROCEDURE sp_RegistarExecucaoOperacao
    @execucaoID INT,
    @quantidadeEnviada INT,
    @quantidadeRecebida INT,
    @subcontratadoID INT = NULL,
    @precoPorUnidade DECIMAL(10,2)
AS
BEGIN
    SET NOCOUNT ON;

    -- Validação: existe a execução?
    IF NOT EXISTS (SELECT 1 FROM ExecucaoOperacao WHERE execucaoID = @execucaoID)
    BEGIN
        RAISERROR('Execução não encontrada.', 16, 1);
        RETURN;
    END;

    -- Validação: quantidades
    IF @quantidadeEnviada < 0 OR @quantidadeRecebida < 0
    BEGIN
        RAISERROR('Quantidade enviada ou recebida não pode ser negativa.', 16, 1);
        RETURN;
    END;

    -- Validação: subcontratado existe (se fornecido)
    IF @subcontratadoID IS NOT NULL AND NOT EXISTS (
        SELECT 1 FROM Subcontratado WHERE subcontratadoID = @subcontratadoID
    )
    BEGIN
        RAISERROR('Subcontratado inválido.', 16, 1);
        RETURN;
    END;

    -- Atualização (dataExecucao será definida no trigger ou default)
    UPDATE ExecucaoOperacao
    SET
        quantidadeEnviada = @quantidadeEnviada,
        quantidadeRecebida = @quantidadeRecebida,
        subcontratadoID = @subcontratadoID,
        precoPorUnidade = @precoPorUnidade
    WHERE execucaoID = @execucaoID;

    SELECT 'Execução atualizada com sucesso.' AS mensagem;
END;