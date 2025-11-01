-- Validar ficha que estejam ativas na itemOrdemFabrico
CREATE TRIGGER tr_ItemOrdemFabrico_ValidarFichaTecnicaAtiva
ON ItemOrdemFabrico
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN FichaTecnica ft ON i.fichaTecnicaID = ft.fichaTecnicaID
        WHERE ft.estado = 'inativa'
    )
    BEGIN
        RAISERROR('Ficha Técnica está inativa e não pode ser usada em ordens de fabrico.', 16, 1);
        ROLLBACK;
        RETURN;
    END
END;