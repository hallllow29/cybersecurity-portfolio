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