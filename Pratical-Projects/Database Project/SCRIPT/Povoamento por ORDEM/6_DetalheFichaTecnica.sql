
-- Detalhes para a Ficha Técnica 1 (produtoID 1, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(1, 1, 1, 1.00, 'Corte do couro.', 15.00, 0.10),
(1, 2, 2, 1.00, 'Costura das partes.', 25.00, 0.08),
(1, 3, 3, 1.00, 'Montagem da sola.', 20.00, 0.12),
(1, 4, 4, 1.00, 'Acabamento e inspeção.', 10.00, 0.05);

-- Detalhes para a Ficha Técnica 2 (produtoID 2, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(2, 1, 1, 1.00, 'Corte do couro.', 12.00, 0.10),
(2, 2, 2, 1.00, 'Costura das partes.', 20.00, 0.08),
(2, 3, 3, 1.00, 'Montagem da sola.', 18.00, 0.12),
(2, 4, 4, 1.00, 'Acabamento e inspeção.', 8.00, 0.05);

-- Detalhes para a Ficha Técnica 3 (produtoID 3, externa)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(3, 3, 1, 1.00, 'Montagem completa (externo).', 40.00, 0.20);

-- Detalhes para a Ficha Técnica 4 (produtoID 4, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(4, 1, 1, 1.00, 'Corte dos materiais.', 18.00, 0.11),
(4, 2, 2, 1.00, 'Costura das partes.', 30.00, 0.09),
(4, 3, 3, 1.00, 'Montagem da sola.', 22.00, 0.14),
(4, 4, 4, 1.00, 'Acabamento e inspeção.', 15.00, 0.07);

-- Detalhes para a Ficha Técnica 5 (produtoID 5, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(5, 1, 1, 1.00, 'Corte das tiras.', 8.00, 0.10),
(5, 2, 2, 3.00, 'Costura das tiras.', 15.00, 0.08),
(5, 3, 3, 1.00, 'Montagem da sola.', 10.00, 0.12),
(5, 4, 4, 1.00, 'Acabamento.', 5.00, 0.05);

-- Detalhes para a Ficha Técnica 6 (produtoID 6, externa)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(6, 2, 1, 1.00, 'Costura das tiras (externo).', 25.00, 0.10),
(6, 3, 2, 1.00, 'Montagem final (externo).', 15.00, 0.15);

-- Detalhes para a Ficha Técnica 7 (produtoID 7, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(7, 1, 1, 1.00, 'Corte dos materiais.', 10.00, 0.12),
(7, 2, 2, 1.00, 'Costura das partes.', 20.00, 0.09),
(7, 3, 3, 1.00, 'Montagem da plataforma.', 18.00, 0.15),
(7, 4, 4, 1.00, 'Acabamento.', 7.00, 0.06);

-- Detalhes para a Ficha Técnica 8 (produtoID 8, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(8, 1, 1, 1.00, 'Corte do tecido.', 10.00, 0.10),
(8, 2, 2, 1.00, 'Costura das partes.', 20.00, 0.08),
(8, 3, 3, 1.00, 'Montagem da sola.', 15.00, 0.13),
(8, 4, 4, 1.00, 'Acabamento.', 7.00, 0.05);

-- Detalhes para a Ficha Técnica 9 (produtoID 9, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(9, 1, 1, 1.00, 'Corte do tecido.', 12.00, 0.09),
(9, 2, 2, 1.00, 'Costura das partes.', 22.00, 0.07),
(9, 3, 3, 1.00, 'Montagem da sola.', 16.00, 0.11),
(9, 4, 4, 1.00, 'Acabamento.', 8.00, 0.05);

-- Detalhes para a Ficha Técnica 10 (produtoID 10, externa)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(10, 3, 1, 1.00, 'Montagem completa (externo).', 35.00, 0.18);

-- Detalhes para a Ficha Técnica 11 (produtoID 11, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(11, 1, 1, 1.00, 'Corte do couro.', 18.00, 0.10),
(11, 2, 2, 1.00, 'Costura das partes.', 35.00, 0.08),
(11, 3, 3, 1.00, 'Montagem da sola.', 25.00, 0.15),
(11, 4, 4, 1.00, 'Acabamento e lustro.', 12.00, 0.06);

-- Detalhes para a Ficha Técnica 12 (produtoID 12, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(12, 1, 1, 1.00, 'Corte do couro.', 17.00, 0.11),
(12, 2, 2, 1.00, 'Costura das partes.', 33.00, 0.09),
(12, 3, 3, 1.00, 'Montagem da sola.', 23.00, 0.14),
(12, 4, 4, 1.00, 'Acabamento e lustro.', 11.00, 0.07);

-- Detalhes para a Ficha Técnica 13 (produtoID 13, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(13, 1, 1, 1.00, 'Corte do couro.', 14.00, 0.09),
(13, 2, 2, 1.00, 'Costura das partes.', 28.00, 0.07),
(13, 3, 3, 1.00, 'Montagem da sola.', 20.00, 0.12),
(13, 4, 4, 1.00, 'Acabamento e palmilha.', 9.00, 0.06);

-- Detalhes para a Ficha Técnica 14 (produtoID 14, externa)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(14, 2, 1, 1.00, 'Costura (externo).', 40.00, 0.12),
(14, 3, 2, 1.00, 'Montagem (externo).', 20.00, 0.20);

-- Detalhes para a Ficha Técnica 15 (produtoID 15, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(15, 1, 1, 2.00, 'Corte do material.', 5.00, 0.05),
(15, 3, 2, 1.00, 'Montagem da sola.', 8.00, 0.10),
(15, 4, 3, 1.00, 'Acabamento.', 3.00, 0.04),
(15, 2, 4, 1.00, 'Costura (se aplicável).', 6.00, 0.03); -- Adicionando costura, pode ser opcional

-- Detalhes para a Ficha Técnica 16 (produtoID 16, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(16, 1, 1, 2.00, 'Corte do material.', 8.00, 0.08),
(16, 2, 2, 1.00, 'Costura das partes.', 12.00, 0.06),
(16, 3, 3, 1.00, 'Montagem da sola.', 5.00, 0.10),
(16, 4, 4, 1.00, 'Acabamento.', 3.00, 0.04);

-- Detalhes para a Ficha Técnica 17 (produtoID 17, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(17, 1, 1, 1.00, 'Corte da lona.', 7.00, 0.07),
(17, 2, 2, 1.00, 'Costura da lona.', 15.00, 0.06),
(17, 3, 3, 1.00, 'Montagem da sola.', 18.00, 0.11),
(17, 4, 4, 1.00, 'Acabamento.', 6.00, 0.05);

-- Detalhes para a Ficha Técnica 18 (produtoID 18, externa)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(18, 3, 1, 1.00, 'Montagem completa (externo).', 25.00, 0.10);

-- Detalhes para a Ficha Técnica 19 (produtoID 19, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(19, 1, 1, 1.00, 'Corte da pele.', 9.00, 0.08),
(19, 2, 2, 1.00, 'Costura das partes.', 18.00, 0.07),
(19, 3, 3, 1.00, 'Montagem da sola.', 12.00, 0.10),
(19, 4, 4, 1.00, 'Acabamento.', 6.00, 0.05);

-- Detalhes para a Ficha Técnica 20 (produtoID 20, interna)
INSERT INTO DetalheFichaTecnica (fichaTecnicaID, operacaoID, ordemExecucao, vezesPorUnidade, descricao, duracaoUnitariaMin, custoMinuto) VALUES
(20, 1, 1, 1.00, 'Corte da pele.', 10.00, 0.09),
(20, 2, 2, 1.00, 'Costura das partes.', 20.00, 0.08),
(20, 3, 3, 1.00, 'Montagem da sola.', 14.00, 0.11),
(20, 4, 4, 1.00, 'Acabamento.', 7.00, 0.06);
