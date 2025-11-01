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