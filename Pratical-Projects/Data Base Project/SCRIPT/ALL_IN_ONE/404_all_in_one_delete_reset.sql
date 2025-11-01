-- DELETE & RESET
-- 1. Dados dependentes
DELETE FROM ConsumoMaterialReal;
DELETE FROM AlertaQualidade;
DELETE FROM AlertaAtraso;
DELETE FROM ExecucaoOperacao;

-- 2. Tabelas intermédias
DELETE FROM ItemOrdemFabrico;
DELETE FROM OrdemFabrico;

-- 3. Estrutura de produto
DELETE FROM DetalheFichaTecnica;
DELETE FROM FichaTecnica;

-- 4. Tabelas base
DELETE FROM Subcontratado;
DELETE FROM Material;
DELETE FROM Operacao;
DELETE FROM Produto;

-- 5. Reset Internal Seeds
DBCC CHECKIDENT ('ConsumoMaterialReal', RESEED, 0);
DBCC CHECKIDENT ('AlertaQualidade', RESEED, 0);
DBCC CHECKIDENT ('AlertaAtraso', RESEED, 0);
DBCC CHECKIDENT ('ExecucaoOperacao', RESEED, 0);
DBCC CHECKIDENT ('ItemOrdemFabrico', RESEED, 0);
DBCC CHECKIDENT ('OrdemFabrico', RESEED, 0);
DBCC CHECKIDENT ('DetalheFichaTecnica', RESEED, 0);
DBCC CHECKIDENT ('FichaTecnica', RESEED, 0);
DBCC CHECKIDENT ('Subcontratado', RESEED, 0);
DBCC CHECKIDENT ('Material', RESEED, 0);
DBCC CHECKIDENT ('Operacao', RESEED, 0);
DBCC CHECKIDENT ('Produto', RESEED, 0);
