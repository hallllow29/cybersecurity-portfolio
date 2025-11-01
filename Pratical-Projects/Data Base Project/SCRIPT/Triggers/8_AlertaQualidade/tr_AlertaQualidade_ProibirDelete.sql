-- Proibir delete no AlertaQualidade
CREATE TRIGGER tr_AlertaQualidade_ProibirDelete
ON AlertaQualidade
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    RAISERROR('Não é permitido eliminar alertas de qualidade', 16, 1);
END;