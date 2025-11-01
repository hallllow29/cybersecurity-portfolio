-- Bloquear inativacao da FichaTecnica
CREATE TRIGGER tr_FichaTecnica_BloquearInativacao
ON FichaTecnica
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Verifica se o estado foi alterado para 'inativa'
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.fichaTecnicaID = d.fichaTecnicaID
        WHERE i.estado = 'inativa' AND d.estado <> 'inativa'
    )
    BEGIN
        -- Verifica se há ordens de fabrico ainda ativas com essa ficha
        IF EXISTS (
            SELECT 1
            FROM ItemOrdemFabrico io
            JOIN inserted i ON io.fichaTecnicaID = i.fichaTecnicaID
            JOIN OrdemFabrico ofa ON io.ordemID = ofa.ordemID
            WHERE ofa.estado <> 'concluida'
        )
        BEGIN
            RAISERROR('Não é permitido inativar uma Ficha Técnica com Ordens de Fabrico ainda em execução ou planeadas.', 16, 1);
            ROLLBACK;
            RETURN;
        END
    END
END;