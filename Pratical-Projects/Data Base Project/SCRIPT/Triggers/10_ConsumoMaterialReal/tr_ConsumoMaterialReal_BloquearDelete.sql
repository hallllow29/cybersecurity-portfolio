-- Bloquear delete no ConsumoMaterialReal
CREATE TRIGGER tr_ConsumoMaterialReal_BloquearDelete
ON ConsumoMaterialReal
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    RAISERROR('Não é permitido eliminar registos de consumo de material.', 16, 1);
END;