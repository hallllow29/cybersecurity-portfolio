-- INSERIR AO FIM PARA PROPOSITOS DEMONSTRATIVOS
-- Inserir dados na tabela ConsumoMaterialReal
-- Estes dados representam o consumo planeado de materiais
-- para operações específicas detalhadas nas Fichas Técnicas.
-- A coluna quantidadeUtilizada é deixada como NULL, pois as operações ainda não foram realizadas.
-- Assumimos que os operacaoID's são 1=Corte, 2=Costura, 3=Montagem, 4=Acabamento.
-- Os detalheID's são sequenciais com base nas inserções anteriores de DetalheFichaTecnica.

-- Detalhes para a Ficha Técnica 1 (Bota de Couro Cano Alto Preta - Interna)
-- DetalheID's assumidos: 1 a 5
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(1, 1, 0.50),  -- Corte: Pele Genuína (0.50 Pé Quadrado por unidade)
(1, 7, 0.20),  -- Corte: Tecido Sintético (para forro, 0.20 Metro Linear por unidade)
(2, 4, 0.05),  -- Costura (Etapa 1): Linha de Costura (0.05 Carretel por unidade)
(3, 4, 0.07),  -- Costura (Etapa 2): Linha de Costura (0.07 Carretel por unidade)
(4, 2, 1.00),  -- Montagem: Sola de Borracha (1 Par por unidade)
(4, 6, 1.00),  -- Montagem: Palmilha (1 Par por unidade)
(4, 5, 0.01),  -- Montagem: Cola de Sapato (0.01 Litro por unidade)
(5, 3, 1.00),  -- Acabamento: Atacadores (1 Par por unidade)
(5, 12, 1.00); -- Acabamento: Etiquetas (1 Unidade por unidade)

-- Detalhes para a Ficha Técnica 2 (Bota de Couro Cano Curto Castanha - Interna)
-- DetalheID's assumidos: 6 a 9
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(6, 1, 0.40),  -- Corte: Pele Genuína
(7, 4, 0.04),  -- Costura: Linha de Costura
(8, 2, 1.00),  -- Montagem: Sola de Borracha
(8, 5, 0.008), -- Montagem: Cola de Sapato
(9, 13, 1.00); -- Acabamento: Caixas de Sapatos

-- Detalhes para a Ficha Técnica 3 (Bota Tática Impermeável Preta - Externa)
-- DetalheID assumido: 10
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(10, 26, 1.00), -- Montagem completa: Membrana Impermeável (1 Metro Linear por unidade, se fornecido por nós)
(10, 22, 1.00); -- Montagem completa: Sola de Poliuretano (1 Par por unidade, se fornecido por nós)

-- Detalhes para a Ficha Técnica 4 (Bota de Montanha Aventura - Interna)
-- DetalheID's assumidos: 11 a 14
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(11, 1, 0.60), -- Corte: Pele Genuína
(11, 26, 0.30),-- Corte: Membrana Impermeável
(12, 4, 0.08), -- Costura: Linha de Costura
(13, 2, 1.00), -- Montagem: Sola de Borracha
(13, 5, 0.015);-- Montagem: Cola de Sapato

-- Detalhes para a Ficha Técnica 5 (Sandália de Couro Verão - Interna)
-- DetalheID's assumidos: 15 a 18
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(15, 1, 0.20), -- Corte: Pele Genuína
(16, 4, 0.03), -- Costura: Linha de Costura
(17, 2, 1.00), -- Montagem: Sola de Borracha
(17, 5, 0.005);-- Montagem: Cola de Sapato

-- Detalhes para a Ficha Técnica 6 (Sandália Desportiva Ajustável - Externa)
-- DetalheID's assumidos: 19 a 20
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(19, 7, 0.25), -- Costura: Tecido Sintético (se fornecido)
(20, 22, 1.00);-- Montagem: Sola de Poliuretano (se fornecido)

-- Detalhes para a Ficha Técnica 7 (Sandália Plataforma Elegante - Interna)
-- DetalheID's assumidos: 21 a 24
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(21, 8, 0.10),  -- Corte: Camurça
(21, 9, 0.05),  -- Corte: Glitter
(22, 4, 0.06),  -- Costura: Linha de Costura
(23, 10, 1.00), -- Montagem: Plataforma
(23, 2, 1.00),  -- Montagem: Sola
(24, 11, 1.00); -- Acabamento: Fivela

-- Detalhes para a Ficha Técnica 8 (Ténis de Corrida Performance - Interna)
-- DetalheID's assumidos: 25 a 28
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(25, 7, 0.35),  -- Corte: Tecido Sintético Respirável
(25, 14, 0.10), -- Corte: Reforço Lateral
(26, 4, 0.07),  -- Costura: Linha de Costura
(27, 15, 1.00), -- Montagem: Entressola Amortecedora
(27, 2, 1.00),  -- Montagem: Sola de Borracha
(28, 3, 1.00);  -- Acabamento: Atacadores

-- Detalhes para a Ficha Técnica 9 (Ténis Casual Urbano - Interna)
-- DetalheID's assumidos: 29 a 32
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(29, 7, 0.30),  -- Corte: Tecido Lona
(29, 16, 0.05), -- Corte: Detalhe em Couro Sintético
(30, 4, 0.06),  -- Costura: Linha de Costura
(31, 2, 1.00),  -- Montagem: Sola de Borracha
(32, 3, 1.00);  -- Acabamento: Atacadores

-- Detalhes para a Ficha Técnica 10 (Ténis Desportivo Treino - Externa)
-- DetalheID assumido: 33
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(33, 27, 1.00); -- Montagem completa: Ténis Desportivo (se fornecemos algum componente)

-- Detalhes para a Ficha Técnica 11 (Sapato Social Clássico Preto - Interna)
-- DetalheID's assumidos: 34 a 37
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(34, 1, 0.45),  -- Corte: Pele Genuína
(35, 4, 0.08),  -- Costura: Linha de Costura
(36, 17, 1.00), -- Montagem: Sola de Couro
(36, 18, 1.00), -- Montagem: Salto
(37, 19, 1.00); -- Acabamento: Graxa para Couro

-- Detalhes para a Ficha Técnica 12 (Sapato Social Clássico Castanho - Interna)
-- DetalheID's assumidos: 38 a 41
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(38, 1, 0.45),  -- Corte: Pele Genuína
(39, 4, 0.08),  -- Costura: Linha de Costura
(40, 17, 1.00), -- Montagem: Sola de Couro
(40, 18, 1.00), -- Montagem: Salto
(41, 19, 1.00); -- Acabamento: Graxa para Couro

-- Detalhes para a Ficha Técnica 13 (Mocassim de Couro Confortável - Interna)
-- DetalheID's assumidos: 42 a 45
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(42, 1, 0.35),  -- Corte: Pele Genuína
(43, 4, 0.06),  -- Costura: Linha de Costura
(44, 17, 1.00), -- Montagem: Sola de Couro
(45, 20, 1.00); -- Acabamento: Palmilha Confortável

-- Detalhes para a Ficha Técnica 14 (Sapato Oxford Formal Preto - Externa)
-- DetalheID assumido: 46
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(46, 28, 1.00); -- Montagem completa: Sapato Oxford (se fornecemos algum componente)

-- Detalhes para a Ficha Técnica 15 (Chinelos de Piscina Antiderrapantes - Interna)
-- DetalheID's assumidos: 47 a 50
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(47, 21, 0.15), -- Corte: Borracha Antiderrapante
(48, 21, 0.10), -- Corte: Tira de Borracha
(49, 5, 0.003), -- Montagem: Cola
(50, 29, 1.00); -- Acabamento: Embalagem

-- Detalhes para a Ficha Técnica 16 (Chinelos de Casa Confortáveis - Interna)
-- DetalheID's assumidos: 51 a 54
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(51, 23, 0.20), -- Corte: Tecido Felpudo
(51, 24, 0.15), -- Corte: Espuma
(52, 4, 0.03),  -- Costura: Linha de Costura
(53, 25, 1.00), -- Montagem: Sola de EVA
(54, 29, 1.00); -- Acabamento: Embalagem

-- Detalhes para a Ficha Técnica 17 (Espadrilles de Lona Bege - Interna)
-- DetalheID's assumidos: 55 a 58
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(55, 7, 0.30),  -- Corte: Lona
(56, 4, 0.05),  -- Costura: Linha de Costura
(57, 30, 1.00), -- Montagem: Sola de Corda
(58, 31, 1.00); -- Acabamento: Etiqueta

-- Detalhes para a Ficha Técnica 18 (Alpargatas de Tecido Azul Marinho - Externa)
-- DetalheID assumido: 59
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(59, 32, 1.00); -- Montagem completa: Alpargatas (se fornecemos algum componente)

-- Detalhes para a Ficha Técnica 19 (Sabrinas Clássicas Pretas - Interna)
-- DetalheID's assumidos: 60 a 63
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(60, 1, 0.25),  -- Corte: Pele Genuína
(61, 4, 0.04),  -- Costura: Linha de Costura
(62, 2, 1.00),  -- Montagem: Sola de Borracha
(63, 29, 1.00); -- Acabamento: Embalagem

-- Detalhes para a Ficha Técnica 20 (Sabrinas Coloridas Vermelhas - Interna)
-- DetalheID's assumidos: 64 a 67
INSERT INTO ConsumoMaterialReal (detalheID, materialID, quantidadePlaneada) VALUES
(64, 33, 0.25), -- Corte: Pele Sintética Colorida
(65, 4, 0.04),  -- Costura: Linha de Costura
(66, 2, 1.00),  -- Montagem: Sola de Borracha
(67, 29, 1.00); -- Acabamento: Embalagem
