------------------------------------------------------------------------------------------
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
GO

------------------------------------------------------------------------------------------
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
GO

------------------------------------------------------------------------------------------
-- Bloquear delete no Subcontratado
CREATE TRIGGER tr_Subcontratado_BloquearDelete
ON Subcontratado
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM deleted d
        JOIN ExecucaoOperacao eo ON eo.subcontratadoID = d.subcontratadoID
    )
    BEGIN
        RAISERROR('Não é possível eliminar um subcontratado que já foi usado em operações.', 16, 1);
        RETURN;
    END;

    DELETE FROM Subcontratado
    WHERE subcontratadoID IN (SELECT subcontratadoID FROM deleted);
END;
GO

------------------------------------------------------------------------------------------
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
GO

------------------------------------------------------------------------------------------
-- Atualizar data na FichaTecnica
CREATE TRIGGER tr_FichaTecnica_AtualizarData
ON FichaTecnica
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE f
    SET dataAtualizacao = SYSDATETIME()
    FROM FichaTecnica f
    JOIN inserted i ON f.fichaTecnicaID = i.fichaTecnicaID;
END;
GO

------------------------------------------------------------------------------------------
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
GO

------------------------------------------------------------------------------------------
-- Validar operações por ficha interna ou ficha externa
CREATE TRIGGER tr_DetalheFichaTecnica_ValidarInternaExterna
ON DetalheFichaTecnica
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Validação para INTERNAS
    IF EXISTS (
        SELECT dft.fichaTecnicaID
        FROM DetalheFichaTecnica dft
        JOIN FichaTecnica ft ON ft.fichaTecnicaID = dft.fichaTecnicaID
        JOIN Operacao o ON o.operacaoID = dft.operacaoID
        WHERE ft.origem = 'interna'
        GROUP BY dft.fichaTecnicaID
        HAVING
            COUNT(DISTINCT o.nome) <> 4
            OR
            SUM(CASE WHEN o.nome NOT IN ('Corte', 'Costura', 'Montagem', 'Acabamento') THEN 1 ELSE 0 END) > 0
            OR
            COUNT(DISTINCT dft.ordemExecucao) <> 4
            OR MIN(dft.ordemExecucao) <> 1
            OR MAX(dft.ordemExecucao) <> 4
    )
    BEGIN
        RAISERROR('Ficha Técnica interna deve conter exatamente 4 operações (Corte, Costura, Montagem, Acabamento) com ordemExecucao de 1 a 4.', 16, 1);
        ROLLBACK;
        RETURN;
    END


    -- Validação para EXTERNAS / PARCIAIS
    IF EXISTS (
        SELECT dft.fichaTecnicaID
        FROM DetalheFichaTecnica dft
        JOIN FichaTecnica ft ON ft.fichaTecnicaID = dft.fichaTecnicaID
        WHERE ft.origem IN ('externa', 'parcial')
        GROUP BY dft.fichaTecnicaID
        HAVING MAX(dft.ordemExecucao) > COUNT(*)
    )
    BEGIN
        RAISERROR('Ficha Técnica externa/parcial: ordemExecucao não pode ser maior que o número total de operações.', 16, 1);
        ROLLBACK;
        RETURN;
    END
END;
GO

------------------------------------------------------------------------------------------
-- Bloquear delete se ficha tiver ativa na DetalheFichaTecnica
CREATE TRIGGER tr_DetalheFichaTecnica_BloquearDeleteSeAtiva
ON DetalheFichaTecnica
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM deleted d
        JOIN FichaTecnica f ON d.fichaTecnicaID = f.fichaTecnicaID
        WHERE f.estado = 'ativa'
    )
    BEGIN
        RAISERROR('Não é permitido eliminar operações de uma ficha técnica ativa.', 16, 1);
        RETURN;
    END;

    DELETE FROM DetalheFichaTecnica
    WHERE detalheID IN (SELECT detalheID FROM deleted);
END;
GO

------------------------------------------------------------------------------------------
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
GO

------------------------------------------------------------------------------------------
-- Criar Execuções na ItemOrdemFabrico
CREATE TRIGGER tr_ItemOrdemFabrico_CriarExecucoes
ON ItemOrdemFabrico
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @itemOrdemID INT;

    DECLARE cur CURSOR FOR
        SELECT itemOrdemID FROM inserted;

    OPEN cur;

    FETCH NEXT FROM cur INTO @itemOrdemID;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        EXEC sp_CriarRegistosExecucaoPorItem @itemOrdemID;
        FETCH NEXT FROM cur INTO @itemOrdemID;
    END;

    CLOSE cur;
    DEALLOCATE cur;
END;
GO

------------------------------------------------------------------------------------------
-- Validar conclusao na OrdemFabrico
CREATE TRIGGER tr_OrdemFabrico_ValidarConclusao
ON OrdemFabrico
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Impedir alteração da data de emissão
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN OrdemFabrico o ON i.ordemID = o.ordemID
        WHERE i.dataEmissao <> o.dataEmissao
    )
    BEGIN
        RAISERROR('Não é permitido alterar a data de emissão da ordem.', 16, 1);
        RETURN;
    END;

    -- 2. Validar se o novo estado é 'concluida' e se há operações pendentes
    IF EXISTS (
        SELECT 1
        FROM inserted i
        WHERE i.estado = 'concluida'
    )
    BEGIN
        IF EXISTS (
            SELECT 1
            FROM inserted i
            JOIN ItemOrdemFabrico io ON io.ordemID = i.ordemID
            JOIN DetalheFichaTecnica d ON d.fichaTecnicaID = io.fichaTecnicaID
            WHERE NOT EXISTS (
                SELECT 1
                FROM ExecucaoOperacao eo
                WHERE eo.itemOrdemID = io.itemOrdemID
                AND eo.operacaoID = d.operacaoID
                AND eo.quantidadeRecebida > 0 -- garantir que foi feita
            )
        )
        BEGIN
            RAISERROR('Não é possível concluir a ordem pois existem operações pendentes.', 16, 1);
            RETURN;
        END
    END;

    -- 3. Aplicar o update normal
    UPDATE o
    SET
        o.dataPrevistaConclusao = i.dataPrevistaConclusao,
        o.estado = i.estado
    FROM OrdemFabrico o
    JOIN inserted i ON o.ordemID = i.ordemID;
END;
GO

------------------------------------------------------------------------------------------
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
GO


------------------------------------------------------------------------------------------
-- Gerar alerta atraso na OrdemFabrico
CREATE TRIGGER tr_OrdemFabrico_GerarAlertaAtraso
ON OrdemFabrico
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO AlertaAtraso(ordemID, dataGeracao, motivo)
    SELECT
        i.ordemID,
        SYSDATETIME(),
        'A ordem ultrapassou o prazo de conclusão'
    FROM inserted i

     -- prazo ultrapassado e ainda nao esta concluida a ordem se nao existir faz alerta, evitando duplicação de alerta
    WHERE
        i.dataPrevistaConclusao < SYSDATETIME() AND i.estado <> 'concluida'
        AND NOT EXISTS (
            SELECT 1
            FROM AlertaAtraso a
            WHERE a.ordemID = i.ordemID
        );
END;
GO

------------------------------------------------------------------------------------------
-- Proibir Delete no AlertaAtraso
CREATE TRIGGER tr_AlertaAtraso_ProibirDelete
ON AlertaAtraso
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    RAISERROR('Não é permitido eliminar alertas de atraso, por motivos de preserveração de histórico', 16, 1);
END;
GO

------------------------------------------------------------------------------------------
-- Bloquear data Geracao no AlertaAtraso
CREATE TRIGGER tr_AlertaAtraso_BloquearDataGeracao
ON AlertaAtraso
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.alertaAtrasoID = d.alertaAtrasoID
        WHERE i.dataGeracao <> d.dataGeracao
    )
    BEGIN
        RAISERROR('A data de geração do alerta não pode ser alterada.', 16, 1);
        RETURN;
    END;

    -- Permitir outras alterações válidas
    UPDATE a
    SET
        ordemID = i.ordemID,
        motivo = i.motivo
    FROM AlertaAtraso a
    JOIN inserted i ON a.alertaAtrasoID = i.alertaAtrasoID;
END;
GO

------------------------------------------------------------------------------------------
-- Proibir delete no AlertaQualidade
CREATE TRIGGER tr_AlertaQualidade_ProibirDelete
ON AlertaQualidade
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    RAISERROR('Não é permitido eliminar alertas de qualidade', 16, 1);
END;
GO

------------------------------------------------------------------------------------------
-- Bloquear data geracao no AlertaQualidade
CREATE TRIGGER tr_AlertaQualidade_BloquearDataGeracao
ON AlertaQualidade
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.alertaQualidadeID = d.alertaQualidadeID
        WHERE i.dataGeracao <> d.dataGeracao
    )
    BEGIN
        RAISERROR('A data de geração do alerta de qualidade não pode ser alterada.', 16, 1);
        RETURN;
    END;

    -- Permite alterações aos restantes campos
    UPDATE a
    SET
        descricao = i.descricao,
        execucaoID = i.execucaoID,
        subcontratadoID = i.subcontratadoID
    FROM AlertaQualidade a
    JOIN inserted i ON a.alertaQualidadeID = i.alertaQualidadeID;
END;
GO

------------------------------------------------------------------------------------------
-- Bloquear delete na ExecucaoOperacao
CREATE TRIGGER tr_ExecucaoOperacao_BloquearDelete
ON ExecucaoOperacao
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    RAISERROR('Não é permitido eliminar execuções de operações.', 16, 1);
END;
GO

------------------------------------------------------------------------------------------
-- Atualizar data execução na ExecucaoOperacao
CREATE TRIGGER tr_ExecucaoOperacao_AtualizarDataExecucao
ON ExecucaoOperacao
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE eo
    SET dataExecucao = SYSDATETIME()
    FROM ExecucaoOperacao eo
    JOIN inserted i ON eo.execucaoID = i.execucaoID
    WHERE
        eo.dataExecucao IS NULL AND
        (
            i.quantidadeRecebida > 0 OR
            (i.quantidadeEnviada > 0 AND i.quantidadeRecebida <= i.quantidadeEnviada)
        );
END;
GO

------------------------------------------------------------------------------------------
-- Proteger data execução na ExecucaoOperacao
CREATE TRIGGER tr_ExecucaoOperacao_ProtegerDataExecucao
ON ExecucaoOperacao
AFTER UPDATE
AS

BEGIN
    SET NOCOUNT ON;
    -- dataExecucao inicia como NULL antes da primeira execução real
    -- Após o primeiro registo de quantidade recebida, é preenchida automaticamente via trigger
    -- Este trigger impede que dataExecucao seja posteriormente alterada para NULL ou para uma data posterior,
    -- preservando a integridade do histórico de execução, evitando fraude ou enganos

    -- 1. Impede que dataExecucao seja apagada (tornada NULL) se já existia valor
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.execucaoID = d.execucaoID
        WHERE d.dataExecucao IS NOT NULL AND i.dataExecucao IS NULL
    )
    BEGIN
        RAISERROR('Não é permitido remover a data de execução de uma operação já registada.', 16, 1);
        ROLLBACK;
        RETURN;
    END

    -- 2. Impede que a dataExecucao seja atualizada para uma data anterior à atual
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN deleted d ON i.execucaoID = d.execucaoID
        WHERE
            i.dataExecucao IS NOT NULL
            AND i.dataExecucao < SYSDATETIME()
            AND d.dataExecucao IS NOT NULL  -- só valida se for uma atualização real
            AND i.dataExecucao <> d.dataExecucao
    )
    BEGIN
        RAISERROR('Não é permitido definir uma data de execução anterior ao momento atual.', 16, 1);
        ROLLBACK;
        RETURN;
    END
END;
GO

------------------------------------------------------------------------------------------
-- Controlo de produção na ExecucaoOperacao
CREATE TRIGGER tr_ExecucaoOperacao_ControloProducao
ON ExecucaoOperacao
INSTEAD OF UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- BLOQUEAR ALTERAÇÃO MANUAL DOS CAMPOS CALCULADOS
    IF EXISTS (
        SELECT 1 FROM inserted i
        JOIN ExecucaoOperacao eo ON eo.execucaoID = i.execucaoID
        WHERE i.custo <> eo.custo OR ISNULL(i.quantidadePerdida, 0) <> ISNULL(eo.quantidadePerdida, 0)
    )
    BEGIN
        RAISERROR('Não é permitido alterar manualmente os campos custo ou quantidadePerdida.', 16, 1);
        RETURN;
    END;

    -- IMPEDIR RECEBIDO > ENVIADO
    IF EXISTS (SELECT 1 FROM inserted WHERE quantidadeRecebida > quantidadeEnviada)
    BEGIN
        RAISERROR('Quantidade recebida não pode ser maior que a enviada.', 16, 1);
        RETURN;
    END;

    -- BLOQUEAR EXECUÇÃO SEM PREÇO EM FICHAS EXTERNAS/PARCIAIS
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN ItemOrdemFabrico io ON i.itemOrdemID = io.itemOrdemID
        JOIN FichaTecnica ft ON io.fichaTecnicaID = ft.fichaTecnicaID
        WHERE LOWER(ft.origem) IN ('externa', 'parcial')
        AND i.subcontratadoID IS NULL
        AND (i.precoPorUnidade IS NULL OR i.precoPorUnidade <= 0)
    )
    BEGIN
        RAISERROR('Fichas externas/parciais requerem precoPorUnidade se não forem subcontratadas.', 16, 1);
        RETURN;
    END;

    -- BLOQUEAR SALTOS DE EXECUÇÃO
    IF EXISTS (
        SELECT 1 FROM inserted i
        JOIN ExecucaoOperacao eo ON eo.itemOrdemID = i.itemOrdemID AND eo.execucaoID < i.execucaoID
        WHERE eo.quantidadeRecebida IS NULL OR eo.quantidadeRecebida = 0
    )
    BEGIN
        RAISERROR('Execuções devem seguir a ordem correta.', 16, 1);
        RETURN;
    END;

    -- BLOQUEAR ENVIO EXCESSIVO VS OPERAÇÃO ANTERIOR
    IF EXISTS (
        SELECT 1
        FROM inserted i
        JOIN ItemOrdemFabrico io ON io.itemOrdemID = i.itemOrdemID
        JOIN DetalheFichaTecnica atual ON atual.fichaTecnicaID = io.fichaTecnicaID AND atual.operacaoID = i.operacaoID
        JOIN DetalheFichaTecnica anterior ON anterior.fichaTecnicaID = atual.fichaTecnicaID
        AND anterior.ordemExecucao = (
            SELECT MAX(ordemExecucao)
            FROM DetalheFichaTecnica
            WHERE fichaTecnicaID = atual.fichaTecnicaID AND ordemExecucao < atual.ordemExecucao
        )
        JOIN ExecucaoOperacao eoAnterior ON eoAnterior.itemOrdemID = io.itemOrdemID AND eoAnterior.operacaoID = anterior.operacaoID
        WHERE i.quantidadeEnviada + ISNULL((
            SELECT SUM(eo2.quantidadeEnviada)
            FROM ExecucaoOperacao eo2
            WHERE eo2.itemOrdemID = i.itemOrdemID AND eo2.operacaoID = i.operacaoID AND eo2.execucaoID <> i.execucaoID
        ), 0) > eoAnterior.quantidadeRecebida
    )
    BEGIN
        RAISERROR('Quantidade enviada excede a recebida na operação anterior.', 16, 1);
        RETURN;
    END;

    -- UPDATE PRINCIPAL
    UPDATE eo
    SET
        eo.quantidadeEnviada = i.quantidadeEnviada,
        eo.quantidadeRecebida = i.quantidadeRecebida,
        eo.subcontratadoID = i.subcontratadoID,
        eo.dataExecucao = i.dataExecucao,
        eo.precoPorUnidade = i.precoPorUnidade
    FROM ExecucaoOperacao eo
    JOIN inserted i ON eo.execucaoID = i.execucaoID;

    -- CALCULAR PERDAS
    UPDATE eo
    SET eo.quantidadePerdida =
        CASE WHEN i.quantidadeEnviada >= i.quantidadeRecebida THEN i.quantidadeEnviada - i.quantidadeRecebida ELSE 0 END
    FROM ExecucaoOperacao eo
    JOIN inserted i ON eo.execucaoID = i.execucaoID;

    -- ALERTA DE QUALIDADE (> 15%)
    INSERT INTO AlertaQualidade (execucaoID, subcontratadoID, descricao, dataGeracao)
    SELECT
        i.execucaoID,
        i.subcontratadoID,
        CONCAT('Perda > 15%. Enviado: ', i.quantidadeEnviada, ', Recebido: ', i.quantidadeRecebida),
        SYSDATETIME()
    FROM inserted i
    WHERE i.quantidadeEnviada > 0
    AND (1.0 * (i.quantidadeEnviada - i.quantidadeRecebida) / NULLIF(i.quantidadeEnviada, 0)) > 0.15
    AND NOT EXISTS (
        SELECT 1 FROM AlertaQualidade aq WHERE aq.execucaoID = i.execucaoID
    );

    -- REGISTAR CONSUMO DE MATERIAL REAL
    INSERT INTO ConsumoMaterialReal (
        execucaoID, detalheID, materialID, quantidadePlaneada, quantidadeUtilizada, dataRegisto
    )
    SELECT
        i.execucaoID,
        cmr.detalheID,
        cmr.materialID,
        cmr.quantidadePlaneada,
        ROUND(cmr.quantidadePlaneada * i.quantidadeEnviada, 2),
        SYSDATETIME()
    FROM inserted i
    JOIN ItemOrdemFabrico io ON i.itemOrdemID = io.itemOrdemID
    JOIN DetalheFichaTecnica dft ON io.fichaTecnicaID = dft.fichaTecnicaID AND dft.operacaoID = i.operacaoID
    JOIN ConsumoMaterialReal cmr ON cmr.detalheID = dft.detalheID AND cmr.execucaoID IS NULL
    WHERE i.quantidadeEnviada > 0
    AND NOT EXISTS (
        SELECT 1 FROM ConsumoMaterialReal r
        WHERE r.execucaoID = i.execucaoID AND r.materialID = cmr.materialID AND r.detalheID = cmr.detalheID
    );

    -- CUSTO INTERNO
    UPDATE eo
    SET eo.custo = ROUND(i.quantidadeEnviada * ISNULL(dft.duracaoUnitariaMin, 0) * ISNULL(dft.custoMinuto, 0), 2)
    FROM ExecucaoOperacao eo
    JOIN inserted i ON eo.execucaoID = i.execucaoID
    JOIN ItemOrdemFabrico io ON i.itemOrdemID = io.itemOrdemID
    JOIN DetalheFichaTecnica dft ON io.fichaTecnicaID = dft.fichaTecnicaID AND dft.operacaoID = i.operacaoID;

    -- CUSTO EXTERNO
    UPDATE eo
    SET eo.custo = ROUND(i.quantidadeEnviada * ISNULL(s.custoServico, 0), 2)
    FROM ExecucaoOperacao eo
    JOIN inserted i ON eo.execucaoID = i.execucaoID
    JOIN Subcontratado s ON i.subcontratadoID = s.subcontratadoID
    WHERE i.subcontratadoID IS NOT NULL;

   -- ATUALIZAR ESTADO DE PRODUÇÃO NO ITEM CONFORME ÚLTIMA OPERAÇÃO EXECUTADA
WITH EstadoProducaoPorItem AS (
    SELECT 
        eo.itemOrdemID,
        estadoProducao = 
            CASE MAX(CASE op.nome
                WHEN 'Corte' THEN 1
                WHEN 'Costura' THEN 2
                WHEN 'Montagem' THEN 3
                WHEN 'Acabamento' THEN 4
                ELSE 0 END)
            WHEN 1 THEN 'Cortado'
            WHEN 2 THEN 'Costurado'
            WHEN 3 THEN 'Montado'
            WHEN 4 THEN 'Acabado'
            ELSE 'não iniciado'
            END
    FROM ExecucaoOperacao eo
    JOIN inserted i ON i.execucaoID = eo.execucaoID
    JOIN DetalheFichaTecnica dft ON eo.operacaoID = dft.operacaoID AND dft.fichaTecnicaID = (
        SELECT fichaTecnicaID FROM ItemOrdemFabrico WHERE itemOrdemID = eo.itemOrdemID
    )
    JOIN Operacao op ON op.operacaoID = eo.operacaoID
    WHERE eo.quantidadeRecebida > 0
    GROUP BY eo.itemOrdemID
)

	UPDATE iof
	SET iof.estadoProducao = ep.estadoProducao
	FROM ItemOrdemFabrico iof
	JOIN EstadoProducaoPorItem ep ON iof.itemOrdemID = ep.itemOrdemID;
	UPDATE o
	SET o.estado = 'em_execucao'
	FROM OrdemFabrico o
	WHERE o.estado = 'planeada'
	AND EXISTS (
		SELECT 1
		FROM inserted i
		JOIN ItemOrdemFabrico io ON io.itemOrdemID = i.itemOrdemID
		WHERE io.ordemID = o.ordemID
		AND i.quantidadeRecebida > 0
	);

	  UPDATE o
    SET o.estado = 'concluida'
    FROM OrdemFabrico o
    WHERE o.estado <> 'concluida'
    AND NOT EXISTS (
        SELECT 1
        FROM ItemOrdemFabrico io
        WHERE io.ordemID = o.ordemID
        AND io.estadoProducao <> 'Acabado'
    );
END;
GO

------------------------------------------------------------------------------------------
-- Bloquear delete no ConsumoMaterialReal
CREATE TRIGGER tr_ConsumoMaterialReal_BloquearDelete
ON ConsumoMaterialReal
INSTEAD OF DELETE
AS
BEGIN
    SET NOCOUNT ON;

    RAISERROR('Não é permitido eliminar registos de consumo de material.', 16, 1);
END;
GO

------------------------------------------------------------------------------------------
-- Calcular perda material no ConsumoMaterialReal
CREATE TRIGGER tr_ConsumoMaterialReal_CalcularPerda
ON ConsumoMaterialReal
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    UPDATE cmr
    SET percentagemPerdaMaterial =
        CASE
            WHEN eo.quantidadeEnviada IS NULL OR eo.quantidadeEnviada = 0 THEN NULL
            WHEN cmr.quantidadeUtilizada IS NULL THEN NULL
            ELSE ROUND(
                100.0 * (cmr.quantidadeUtilizada - (cmr.quantidadePlaneada * eo.quantidadeEnviada))
                / NULLIF(cmr.quantidadePlaneada * eo.quantidadeEnviada, 0),
                2
            )
        END
    FROM ConsumoMaterialReal cmr
    JOIN inserted i ON i.consumoID = cmr.consumoID
    JOIN ExecucaoOperacao eo ON cmr.execucaoID = eo.execucaoID
    WHERE cmr.quantidadeUtilizada IS NOT NULL;
END;
GO