-- Proibir Delete no AlertaAtraso
CREATE TRIGGER tr_AlertaAtraso_ProibirDelete
ON AlertaAtraso
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    RAISERROR('Não é permitido eliminar alertas de atraso, por motivos de preserveração de histórico', 16, 1);
END;