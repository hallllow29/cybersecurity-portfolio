-- Criar Execuções na ItemOrdemFabrico
CREATE TRIGGER tr_ItemOrdemFabrico_CriarExecucoes
ON ItemOrdemFabrico
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @itemOrdemID INT;

    DECLARE cur CURSOR FOR
        SELECT itemOrdemID FROM inserted;

    OPEN cur;

    FETCH NEXT FROM cur INTO @itemOrdemID;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        EXEC sp_CriarRegistosExecucaoPorItem @itemOrdemID;
        FETCH NEXT FROM cur INTO @itemOrdemID;
    END;

    CLOSE cur;
    DEALLOCATE cur;
END;