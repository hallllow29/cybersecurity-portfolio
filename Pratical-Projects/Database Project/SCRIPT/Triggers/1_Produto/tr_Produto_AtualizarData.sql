-- Atualizar data no Produto
CREATE TRIGGER tr_Produto_AtualizarData
ON Produto
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE Produto
    SET dataAtualizacao = SYSDATETIME()
    FROM Produto p
    INNER JOIN inserted i ON p.produtoID = i.produtoID;
END;