-- Atualiza preco venda consoante o custo da ordem
CREATE TRIGGER tr_OrdemFabrico_AtualizarPrecoVendoQuandoOrdemConcluida
ON OrdemFabrico
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @margemLucro DECIMAL(5,2) = 0.30;

    -- Atualizar precoVenda apenas se houver produção final (última operação)
    UPDATE p
    SET p.precoVenda = ROUND(
        (dados.custoTotal / NULLIF(dados.qtdFinal, 0)) * (1 + @margemLucro),
        2
    )
    FROM Produto p
    JOIN (
        SELECT 
            io.produtoID,
            SUM(eo.custo + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)) AS custoTotal,
            SUM(
                CASE 
                    WHEN dft.ordemExecucao = ultimas.ordemMax THEN eo.quantidadeRecebida
                    ELSE 0
                END
            ) AS qtdFinal
        FROM inserted i
        JOIN ItemOrdemFabrico io ON io.ordemID = i.ordemID
        JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
        JOIN DetalheFichaTecnica dft 
            ON dft.fichaTecnicaID = io.fichaTecnicaID 
            AND dft.operacaoID = eo.operacaoID
        LEFT JOIN ConsumoMaterialReal cmr ON cmr.execucaoID = eo.execucaoID
        LEFT JOIN Material m ON m.materialID = cmr.materialID
        JOIN (
            SELECT fichaTecnicaID, MAX(ordemExecucao) AS ordemMax
            FROM DetalheFichaTecnica
            GROUP BY fichaTecnicaID
        ) AS ultimas ON ultimas.fichaTecnicaID = dft.fichaTecnicaID
        WHERE i.estado = 'concluida'
        GROUP BY io.produtoID
    ) AS dados ON dados.produtoID = p.produtoID
    WHERE dados.qtdFinal > 0;
END;