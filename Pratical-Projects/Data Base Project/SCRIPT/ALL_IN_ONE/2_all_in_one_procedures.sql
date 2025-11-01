------------------------------------------------------------------------------------------
-- Cancelar ordem de fabrico
CREATE PROCEDURE sp_CancelarOrdemFabrico
    @ordemID INT
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE OrdemFabrico
    SET estado = 'cancelada'
    WHERE ordemID = @ordemID;

    DELETE FROM ExecucaoOperacao
    WHERE itemOrdemID IN (
        SELECT itemOrdemID FROM ItemOrdemFabrico WHERE ordemID = @ordemID
    );
END;

GO

------------------------------------------------------------------------------------------
-- Criar registos execucao por item
CREATE PROCEDURE sp_CriarRegistosExecucaoPorItem
    @itemOrdemID INT
AS
BEGIN
    SET NOCOUNT ON

    DECLARE @fichaTecnicaID INT;

    -- Aqui obtemos a ficha técnica associada à ordem
    SELECT @fichaTecnicaID = fichaTecnicaID
    FROM ItemOrdemFabrico
    WHERE itemOrdemID = @itemOrdemID;

    -- Inserimos um registo de execução vazio para cada operação de ficha técnica
    INSERT INTO ExecucaoOperacao ( itemOrdemID, operacaoID, subcontratadoID, quantidadeEnviada,quantidadeRecebida, dataExecucao, custo)
    SELECT
        @itemOrdemID,
        d.operacaoID,
        NULL,
        0,
        0,
        NULL, -- O presente nao vem antes do passado.
        0.00
    FROM DetalheFichaTecnica d
    WHERE d.fichaTecnicaID = @fichaTecnicaID;
END;

GO

------------------------------------------------------------------------------------------
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

GO

------------------------------------------------------------------------------------------
-- Realtorio mensal
CREATE PROCEDURE sp_RelatorioResumoProducao
AS
BEGIN
    SET NOCOUNT ON;

    -- Resumo geral por mês
    SELECT
        FORMAT(eo.dataExecucao, 'yyyy-MM') AS periodo,
        SUM(eo.quantidadeRecebida) AS totalProduzido,
        SUM(ISNULL(eo.quantidadePerdida, 0)) AS totalPerdido,
        CAST(
            100.0 * SUM(eo.quantidadeRecebida) / NULLIF(SUM(eo.quantidadeRecebida + ISNULL(eo.quantidadePerdida, 0)), 0)
            AS DECIMAL(5,2)
        ) AS eficienciaMediaGlobalPercent,

        -- Subqueries inline para contar ordens
        (SELECT COUNT(*) FROM OrdemFabrico WHERE estado = 'concluida') AS ordensConcluidas,
        (SELECT COUNT(*) FROM OrdemFabrico WHERE estado = 'em_execucao') AS ordensEmExecucao

    FROM ExecucaoOperacao eo
    WHERE eo.dataExecucao IS NOT NULL
    GROUP BY FORMAT(eo.dataExecucao, 'yyyy-MM')
    ORDER BY periodo DESC;
END;