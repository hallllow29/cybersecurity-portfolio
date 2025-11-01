------------------------------------------------------------------------------------------
-- Apresentar custos comparativos entre produção interna e subcontratada
CREATE VIEW vw_CustosComparativos_Interno_Subcontratado AS
SELECT
    dados.tipoProducao,
    SUM(dados.quantidadeRecebida) AS totalProduzido,
    SUM(dados.custo) AS custoTotal,
    ROUND(SUM(dados.custo) / NULLIF(SUM(dados.quantidadeRecebida), 0), 2) AS custoMedioUnidade,
    ROUND(
        100.0 * SUM(dados.quantidadeRecebida) / NULLIF(
            (SELECT SUM(quantidadeRecebida) FROM ExecucaoOperacao WHERE quantidadeRecebida > 0), 0
        ), 2
    ) AS percentagemProducao
FROM (
    SELECT 
        quantidadeRecebida,
        custo,
        CASE 
            WHEN subcontratadoID IS NULL THEN 'Interna'
            ELSE 'Subcontratada'
        END AS tipoProducao
    FROM ExecucaoOperacao
    WHERE quantidadeRecebida > 0
) AS dados
GROUP BY dados.tipoProducao;
GO

------------------------------------------------------------------------------------------
-- Lucro por ordem de fabrico
CREATE VIEW vw_LucroPorOrdemFabrico AS
SELECT
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.estado,
    p.modelo,
    p.nome AS nomeProduto,
    p.precoVenda,
    io.quantidadePlaneada,

    -- Receita esperada
    ROUND(p.precoVenda * io.quantidadePlaneada, 2) AS receitaPrevista,

    -- Custo total
    ROUND(SUM(
        ISNULL(eo.custo, 0) + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)
    ), 2) AS custoTotalReal,

    -- Lucro estimado
    ROUND(
        (p.precoVenda * io.quantidadePlaneada)
        - SUM(ISNULL(eo.custo, 0) + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)),
        2
    ) AS lucroPrevisto

FROM OrdemFabrico ofa
JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
JOIN Produto p ON p.produtoID = io.produtoID
JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
LEFT JOIN ConsumoMaterialReal cmr ON cmr.execucaoID = eo.execucaoID
LEFT JOIN Material m ON m.materialID = cmr.materialID

GROUP BY
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.estado,
    p.modelo,
    p.nome,
    p.precoVenda,
    io.quantidadePlaneada;

GO
------------------------------------------------------------------------------------------
-- Lucro da produção
CREATE VIEW vw_LucroPorTipoProducao AS
SELECT
    tipo.tipoProducao,
    COUNT(DISTINCT io.itemOrdemID) AS totalItens,
    SUM(io.quantidadePlaneada * p.precoVenda) AS receitaTotal,

    -- Custo das execuções (interno ou subcontratado)
    SUM(eo.custo) AS custoExecucaoTotal,

    -- Custo de materiais usados
    SUM(ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)) AS custoMaterialTotal,

    -- Custo total
    SUM(eo.custo + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)) AS custoTotal,

    -- Lucro
    SUM(io.quantidadePlaneada * p.precoVenda) -
    SUM(eo.custo + ISNULL(cmr.quantidadeUtilizada * m.custoUnitario, 0)) AS lucroTotal

FROM ExecucaoOperacao eo
JOIN ItemOrdemFabrico io ON eo.itemOrdemID = io.itemOrdemID
JOIN Produto p ON p.produtoID = io.produtoID
LEFT JOIN ConsumoMaterialReal cmr ON cmr.execucaoID = eo.execucaoID
LEFT JOIN Material m ON m.materialID = cmr.materialID

-- Distingue produção interna vs subcontratada
CROSS APPLY (
    SELECT
        CASE
            WHEN eo.subcontratadoID IS NULL THEN 'Interna'
            ELSE 'Subcontratada'
        END AS tipoProducao
) AS tipo

GROUP BY tipo.tipoProducao;

GO
------------------------------------------------------------------------------------------
-- Calcular o custo total de cada ordem considerando: Operações internas (baseado em tempo estimado) Operações subcontratadas (preço acordado)
CREATE VIEW vw_CustoTotalPorOrdemFabrico AS
WITH CustosInternos AS (
    SELECT
        ofa.ordemID,
        SUM(eo.quantidadeEnviada * ISNULL(dft.duracaoUnitariaMin, 0) * ISNULL(dft.custoMinuto, 0)) AS custoInterno
    FROM OrdemFabrico ofa
    JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
    JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
    JOIN DetalheFichaTecnica dft
        ON io.fichaTecnicaID = dft.fichaTecnicaID
        AND eo.operacaoID = dft.operacaoID
    WHERE eo.subcontratadoID IS NULL
    GROUP BY ofa.ordemID
),
CustosSubcontratados AS (
    SELECT
        ofa.ordemID,
        SUM(eo.quantidadeEnviada * ISNULL(s.custoServico, 0)) AS custoSubcontratado
    FROM OrdemFabrico ofa
    JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
    JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
    JOIN Subcontratado s ON eo.subcontratadoID = s.subcontratadoID
    GROUP BY ofa.ordemID
),
CustosMateriais AS (
    SELECT
        ofa.ordemID,
        SUM(cmr.quantidadeUtilizada * ISNULL(m.custoUnitario, 0)) AS custoMaterial
    FROM OrdemFabrico ofa
    JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
    JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
    JOIN ConsumoMaterialReal cmr ON cmr.execucaoID = eo.execucaoID
    JOIN Material m ON cmr.materialID = m.materialID
    GROUP BY ofa.ordemID
)

SELECT
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.dataPrevistaConclusao,
    ofa.estado,
    ISNULL(ci.custoInterno, 0) AS custoInternoTotal,
    ISNULL(cs.custoSubcontratado, 0) AS custoSubcontratadoTotal,
    ISNULL(cm.custoMaterial, 0) AS custoMaterialTotal,
    ISNULL(ci.custoInterno, 0) + ISNULL(cs.custoSubcontratado, 0) + ISNULL(cm.custoMaterial, 0) AS custoTotal
FROM OrdemFabrico ofa
LEFT JOIN CustosInternos ci ON ci.ordemID = ofa.ordemID
LEFT JOIN CustosSubcontratados cs ON cs.ordemID = ofa.ordemID
LEFT JOIN CustosMateriais cm ON cm.ordemID = ofa.ordemID;

GO

------------------------------------------------------------------------------------------
-- Determinar a eficiência média por operação e por período, neste caso a escolha foi mensalmente porque é a que faz mais sentido segundo o negocio
CREATE VIEW vw_EficienciaMediaPorOperacaoPeriodoMensal AS
SELECT
    eo.operacaoID,
    op.nome AS nomeOperacao,
    FORMAT(eo.dataExecucao, 'yyyy-MM') AS periodo,
    CAST(SUM(eo.quantidadeRecebida) * 1.0 / NULLIF(SUM(eo.quantidadeEnviada), 0) AS DECIMAL(5,4)) AS eficienciaMedia
FROM ExecucaoOperacao eo
JOIN Operacao op ON eo.operacaoID = op.operacaoID
GROUP BY
    eo.operacaoID,
    op.nome,
    FORMAT(eo.dataExecucao, 'yyyy-MM');

GO

------------------------------------------------------------------------------------------
-- Mostrar operações problemáticas (com maior índice de perdas)
CREATE VIEW vw_OperacoesProblematicas AS
SELECT
    eo.operacaoID,
    op.nome AS nomeOperacao,
    COUNT(*) AS totalExecucoes,
    CAST(
        AVG(
            CASE 
                WHEN eo.quantidadeEnviada > 0 
                THEN 100.0 * (eo.quantidadeEnviada - eo.quantidadeRecebida) / eo.quantidadeEnviada
                ELSE 0
            END
        ) AS DECIMAL(5,2)
    ) AS mediaPerdas
FROM ExecucaoOperacao eo
JOIN Operacao op ON eo.operacaoID = op.operacaoID
GROUP BY eo.operacaoID, op.nome
HAVING AVG(
    CASE 
        WHEN eo.quantidadeEnviada > 0 
        THEN 100.0 * (eo.quantidadeEnviada - eo.quantidadeRecebida) / eo.quantidadeEnviada
        ELSE 0
    END
) > 0;

GO

------------------------------------------------------------------------------------------
-- Consultar as ordens de fabrico em curso, com indicação do progresso (quantidade produzida vs. quantidade planeada).
CREATE VIEW vw_OrdensFabrico_Progresso AS
SELECT 
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.dataPrevistaConclusao,
    io.itemOrdemID,
    p.nome AS nomeProduto,
    CONCAT(p.modelo, ' ', p.cor, ' ', p.variante) AS produtoDetalhado,
    io.quantidadePlaneada,
    ISNULL(SUM(eo.quantidadeRecebida), 0) AS quantidadeProduzida,
    ROUND(
        100.0 * ISNULL(SUM(eo.quantidadeRecebida), 0) / NULLIF(io.quantidadePlaneada, 0),
        2
    ) AS percentagemProgresso,
    io.estadoProducao
FROM OrdemFabrico ofa
JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
JOIN Produto p ON io.produtoID = p.produtoID
LEFT JOIN ExecucaoOperacao eo ON eo.itemOrdemID = io.itemOrdemID
WHERE ofa.estado = 'em_execucao'
GROUP BY 
    ofa.ordemID,
    ofa.dataEmissao,
    ofa.dataPrevistaConclusao,
    io.itemOrdemID,
    p.nome,
    p.modelo, p.cor, p.variante,
    io.quantidadePlaneada,
    io.estadoProducao;

GO

------------------------------------------------------------------------------------------
-- Identificar automaticamente ordens com atrasos (data prevista de conclusão ultrapassada).
CREATE VIEW vw_OrdensAtrasadas AS
SELECT
    ordemID,
    dataPrevistaConclusao,
    estado,
    DATEDIFF(DAY, dataPrevistaConclusao, CAST(GETDATE() AS DATE)) AS diasAtraso
FROM OrdemFabrico
WHERE
    estado NOT IN ('concluida', 'cancelada')
    AND dataPrevistaConclusao < CAST(GETDATE() AS DATE);
GO

------------------------------------------------------------------------------------------
-- Listar as operações mais frequentemente subcontratadas e os respetivos custos.
CREATE VIEW vw_OperacoesMaisSubcontratadas AS
SELECT
    eo.operacaoID,
    op.nome AS nomeOperacao,
    COUNT(*) AS nExecucoesSubcontratadas,
    SUM(ISNULL(so.custoServico, 0) * eo.quantidadeEnviada) AS custoTotalSubcontratado,
    COUNT(DISTINCT eo.subcontratadoID) AS nSubcontratados
FROM ExecucaoOperacao eo
JOIN Operacao op ON eo.operacaoID = op.operacaoID
LEFT JOIN Subcontratado so ON eo.subcontratadoID = so.subcontratadoID
WHERE eo.subcontratadoID IS NOT NULL
GROUP BY eo.operacaoID, op.nome;

GO

------------------------------------------------------------------------------------------
-- Consultar a lista de operações pendentes para cada ordem, ordenadas por prioridade.
CREATE VIEW vw_OperacoesPendentesPorPrioridade AS
SELECT 
    ofa.ordemID,
    io.itemOrdemID,
    p.nome AS nomeProduto,
    CONCAT(p.modelo, ' ', p.cor, ' ', ISNULL(p.variante, '')) AS produtoDetalhado,
    o.operacaoID,
    o.nome AS nomeOperacao,
    dft.ordemExecucao AS prioridade,
    io.quantidadePlaneada,
    ISNULL(eoTotal.totalRecebido, 0) AS quantidadeRecebida,
    CASE 
        WHEN ISNULL(eoTotal.totalRecebido, 0) = 0 THEN 'pendente'
        WHEN ISNULL(eoTotal.totalRecebido, 0) < io.quantidadePlaneada THEN 'parcial'
        ELSE 'concluída'
    END AS estadoOperacao
FROM OrdemFabrico ofa
JOIN ItemOrdemFabrico io ON io.ordemID = ofa.ordemID
JOIN Produto p ON io.produtoID = p.produtoID
JOIN DetalheFichaTecnica dft ON io.fichaTecnicaID = dft.fichaTecnicaID
JOIN Operacao o ON dft.operacaoID = o.operacaoID
OUTER APPLY (
    SELECT SUM(eo.quantidadeRecebida) AS totalRecebido
    FROM ExecucaoOperacao eo
    WHERE eo.itemOrdemID = io.itemOrdemID
      AND eo.operacaoID = dft.operacaoID
) eoTotal
WHERE ISNULL(eoTotal.totalRecebido, 0) < io.quantidadePlaneada;

GO

------------------------------------------------------------------------------------------
-- Calcular a eficiência média de produção (percentagem de materiais aproveitados) por período (dia, semana, mês).
CREATE VIEW vw_EficienciaOperacao_DiaSemanaMes AS
WITH Ef_Diaria AS (
    SELECT
        eo.operacaoID,
        CAST(eo.dataExecucao AS date) AS periodo,
        CAST(SUM(eo.quantidadeRecebida * 1.0) / NULLIF(SUM(eo.quantidadeEnviada), 0) AS DECIMAL(5,4)) AS eficienciaDiaria
    FROM ExecucaoOperacao eo
    GROUP BY eo.operacaoID, CAST(eo.dataExecucao AS date)
),
Ef_Semanal AS (
    SELECT
        eo.operacaoID,
        FORMAT(eo.dataExecucao, 'yyyy-ww') AS periodo,
        CAST(SUM(eo.quantidadeRecebida * 1.0) / NULLIF(SUM(eo.quantidadeEnviada), 0) AS DECIMAL(5,4)) AS eficienciaSemanal
    FROM ExecucaoOperacao eo
    GROUP BY eo.operacaoID, FORMAT(eo.dataExecucao, 'yyyy-ww')
),
Ef_Mensal AS (
    SELECT
        eo.operacaoID,
        FORMAT(eo.dataExecucao, 'yyyy-MM') AS periodo,
        CAST(SUM(eo.quantidadeRecebida * 1.0) / NULLIF(SUM(eo.quantidadeEnviada), 0) AS DECIMAL(5,4)) AS eficienciaMensal
    FROM ExecucaoOperacao eo
    GROUP BY eo.operacaoID, FORMAT(eo.dataExecucao, 'yyyy-MM')
)
SELECT 
    d.operacaoID,
    op.nome AS nomeOperacao,
    d.periodo AS periodoDiario,
    d.eficienciaDiaria,
    s.eficienciaSemanal,
    m.eficienciaMensal
FROM Ef_Diaria d
LEFT JOIN Ef_Semanal s 
    ON s.operacaoID = d.operacaoID 
   AND FORMAT(d.periodo, 'yyyy-ww') = s.periodo
LEFT JOIN Ef_Mensal m 
    ON m.operacaoID = d.operacaoID 
   AND FORMAT(d.periodo, 'yyyy-MM') = m.periodo
JOIN Operacao op ON op.operacaoID = d.operacaoID;

GO