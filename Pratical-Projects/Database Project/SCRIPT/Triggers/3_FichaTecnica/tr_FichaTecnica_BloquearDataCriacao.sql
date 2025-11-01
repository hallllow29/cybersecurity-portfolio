-- Bloquear data criação na FichaTecnica
CREATE TRIGGER tr_FichaTecnica_BloquearDataCriacao
ON FichaTecnica
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Verifica se alguém tentou mudar a dataCriacao
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.fichaTecnicaID = d.fichaTecnicaID
        WHERE i.dataCriacao <> d.dataCriacao
    )
    BEGIN
        RAISERROR('Não é permitido alterar a data de criação da ficha técnica.', 16, 1);
        RETURN;
    END

    -- Permite o resto da atualização, mas preservando a dataCriacao original
    UPDATE f
    SET
        f.produtoID = i.produtoID,
        f.descricao = i.descricao,
        f.dataAtualizacao = i.dataAtualizacao,
        f.estado = i.estado
    FROM FichaTecnica f
    JOIN inserted i ON f.fichaTecnicaID = i.fichaTecnicaID;
END;