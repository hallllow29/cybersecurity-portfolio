-- Bloquear data criação no Produto
CREATE TRIGGER tr_Produto_BloquearDataCriacao
ON Produto
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.produtoID = d.produtoID
        WHERE i.dataCriacao <> d.dataCriacao
    )
    BEGIN
        RAISERROR('A data de criação do produto não pode ser alterada.', 16, 1);
        RETURN;
    END;

    -- Continua o update normalmente (preserva dataCriacao original)
    UPDATE p
    SET
        modelo = i.modelo,
        variante = i.variante,
        cor = i.cor,
        nome = i.nome,
        descricao = i.descricao,
        dataAtualizacao = i.dataAtualizacao,
        precoVenda = i.precoVenda
    FROM Produto p
    JOIN inserted i ON p.produtoID = i.produtoID;
END;