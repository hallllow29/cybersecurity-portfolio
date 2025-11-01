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