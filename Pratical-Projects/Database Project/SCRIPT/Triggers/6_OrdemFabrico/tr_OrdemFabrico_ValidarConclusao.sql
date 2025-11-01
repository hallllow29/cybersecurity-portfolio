-- Validar conclusao na OrdemFabrico
CREATE TRIGGER tr_OrdemFabrico_ValidarConclusao
ON OrdemFabrico
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Impedir alteração da data de emissão
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN OrdemFabrico o ON i.ordemID = o.ordemID
        WHERE i.dataEmissao <> o.dataEmissao
    )
    BEGIN
        RAISERROR('Não é permitido alterar a data de emissão da ordem.', 16, 1);
        RETURN;
    END;

    -- 2. Validar se o novo estado é 'concluida' e se há operações pendentes
    IF EXISTS (
        SELECT 1
        FROM inserted i
        WHERE i.estado = 'concluida'
    )
    BEGIN
        IF EXISTS (
            SELECT 1
            FROM inserted i
            JOIN ItemOrdemFabrico io ON io.ordemID = i.ordemID
            JOIN DetalheFichaTecnica d ON d.fichaTecnicaID = io.fichaTecnicaID
            WHERE NOT EXISTS (
                SELECT 1
                FROM ExecucaoOperacao eo
                WHERE eo.itemOrdemID = io.itemOrdemID
                AND eo.operacaoID = d.operacaoID
                AND eo.quantidadeRecebida > 0 -- garantir que foi feita
            )
        )
        BEGIN
            RAISERROR('Não é possível concluir a ordem pois existem operações pendentes.', 16, 1);
            RETURN;
        END
    END;

    -- 3. Aplicar o update normal
    UPDATE o
    SET
        o.dataPrevistaConclusao = i.dataPrevistaConclusao,
        o.estado = i.estado
    FROM OrdemFabrico o
    JOIN inserted i ON o.ordemID = i.ordemID;
END;