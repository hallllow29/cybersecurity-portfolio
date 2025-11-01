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