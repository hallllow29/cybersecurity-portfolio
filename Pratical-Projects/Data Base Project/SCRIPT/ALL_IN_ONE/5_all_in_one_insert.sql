-- Inserir dados na tabela Produto (focado em calçado)
INSERT INTO Produto (modelo, variante, cor, nome, descricao) VALUES
('Bota', 'Cano Alto', 'Preta', 'Bota de Couro Cano Alto Preta', 'Bota de couro genuíno preta com cano alto.'),
('Bota', 'Cano Curto', 'Castanha', 'Bota de Couro Cano Curto Castanha', 'Bota de couro genuíno castanha com cano curto.'),
('Bota', 'Tática', 'Preta', 'Bota Tática Impermeável Preta', 'Bota tática resistente e impermeável em preto.'),
('Bota', 'Montanha', 'Castanha/Preta', 'Bota de Montanha Aventura', 'Bota robusta para caminhadas e montanhismo.'),
('Sandália', 'Verão', 'Castanha', 'Sandália de Couro Verão', 'Sandália de couro confortável para os dias quentes.'),
('Sandália', 'Desportiva', 'Azul/Cinza', 'Sandália Desportiva Ajustável', 'Sandália desportiva com tiras ajustáveis para maior suporte.'),
('Sandália', 'Plataforma', 'Bege', 'Sandália Plataforma Elegante', 'Sandália de plataforma bege para ocasiões especiais.'),
('Ténis', 'Corrida', 'Branco/Azul', 'Ténis de Corrida Performance', 'Ténis leves e com bom amortecimento para corrida.'),
('Ténis', 'Casual', 'Preto/Branco', 'Ténis Casual Urbanos', 'Ténis pretos e brancos para uso diário com estilo.'),
('Ténis', 'Desportivo', 'Cinzento/Verde', 'Ténis Desportivo Treino', 'Ténis confortáveis para diversas atividades físicas.'),
('Sapatos', 'Social', 'Preto', 'Sapato Social Clássico Preto', 'Sapato social de couro preto elegante.'),
('Sapatos', 'Social', 'Castanho', 'Sapato Social Clássico Castanho', 'Sapato social de couro castanho sofisticado.'),
('Sapatos', 'Mocassim', 'Castanho', 'Mocassim de Couro Confortável', 'Mocassim de couro macio para um look casual elegante.'),
('Sapatos', 'Oxford', 'Preto', 'Sapato Oxford Formal Preto', 'Sapato Oxford preto ideal para eventos formais.'),
('Chinelos', 'Piscina', 'Azul', 'Chinelos de Piscina Antiderrapantes', 'Chinelos azuis antiderrapantes para uso em piscinas e praias.'),
('Chinelos', 'Casa', 'Cinzento', 'Chinelos de Casa Confortáveis', 'Chinelos cinzentos e quentinhos para usar em casa.'),
('Espadrilles', 'Verão', 'Bege', 'Espadrilles de Lona Bege', 'Espadrilles leves de lona bege com sola de corda.'),
('Alpargatas', 'Casual', 'Azul Marinho', 'Alpargatas de Tecido Azul Marinho', 'Alpargatas de tecido azul marinho para um look descontraído.'),
('Sabrinas', 'Clássicas', 'Pretas', 'Sabrinas Clássicas Pretas', 'Sabrinas pretas elegantes e confortáveis.'),
('Sabrinas', 'Coloridas', 'Vermelhas', 'Sabrinas Coloridas Vermelhas', 'Sabrinas vermelhas vibrantes para um toque de cor.');


-- Inserir dados na tabela Operacao (respeitando a restrição de nome)
INSERT INTO Operacao (nome, descricao) VALUES
('Corte', 'Operação de corte dos materiais seguindo os moldes.'),
('Costura', 'Operação de costura das diferentes partes do calçado.'),
('Montagem', 'Operação de montagem das solas, palmilhas e outras partes do calçado.'),
('Acabamento', 'Operação final de limpeza, inspeção e embalagem do calçado.');


-- Inserir dados na tabela Material (com unidades de medida específicas para calçado em português)
INSERT INTO Material (nome, tipo, unidadeMedida, custoUnitario) VALUES
('Pele Genuína', 'Couro', 'Pé Quadrado', 2.80),
('Sola de Borracha', 'Solas', 'Par', 3.20),
('Atacadores', 'Acessórios', 'Par', 0.50),
('Linha de Costura', 'Fios', 'Carretel', 1.10),
('Cola de Sapato', 'Químicos', 'Litro', 7.80),
('Palmilha', 'Componentes Internos', 'Par', 1.50),
('Tecido Sintético', 'Tecidos', 'Metro Linear', 1.75),
('Fivelas Metálicas', 'Acessórios', 'Unidade', 0.35),
('Fecho Éclair', 'Acessórios', 'Unidade', 0.90), 
('Ilhós', 'Acessórios', 'Milheiro', 2.15),
('Reforço Lateral', 'Componentes Internos', 'Par', 0.80),
('Etiquetas', 'Embalagem', 'Unidade', 0.06),
('Caixas de Sapatos', 'Embalagem', 'Unidade', 0.45),
('Não Tecido', 'Tecidos', 'Metro Linear', 0.58), 
('Camurça', 'Couro', 'Pé Quadrado', 3.30),
('Verniz', 'Couro', 'Pé Quadrado', 3.10),
('Elástico', 'Acessórios', 'Metro Linear', 0.25),
('Velcro', 'Acessórios', 'Metro Linear', 0.65),
('TNT (Tecido Não Tecido)', 'Tecidos', 'Metro Linear', 0.36),
('Pelúcia Sintética', 'Tecidos', 'Metro Linear', 2.50),
('Pele Ecológica', 'Couro Sintético', 'Pé Quadrado', 1.75),
('Sola de Poliuretano (PU)', 'Solas', 'Par', 4.10),
('Reforço de Biqueira', 'Componentes Internos', 'Par', 0.55),
('Contraforte', 'Componentes Internos', 'Par', 0.70),
('Cola de Poliuretano', 'Químicos', 'Litro', 9.50),
('Tachas', 'Acessórios', 'Milheiro', 13.00),
('Membrana Impermeável', 'Tecidos', 'Metro Linear', 3.70),
('Forro Têxtil', 'Componentes Internos', 'Metro Linear', 0.65),
('Palmilha Ortopédica', 'Componentes Internos', 'Par', 2.80),
('Fio de Nylon', 'Fios', 'Carretel', 1.50),
('Spray Impermeabilizante', 'Químicos', 'Unidade', 5.20),
('Papel de Embrulho', 'Embalagem', 'Metro', 0.10),
('Sacos de Proteção', 'Embalagem', 'Unidade', 0.20),
('Microfibra', 'Tecidos', 'Metro Linear', 1.36),
('Lona', 'Tecidos', 'Metro Linear', 1.43),
('Cortiça', 'Solas', 'Placa', 11.00),
('EVA (Etileno Vinil Acetato)', 'Solas', 'Placa', 12.00),
('Fita Adesiva', 'Embalagem', 'Rolo', 1.90),
('Cartão', 'Embalagem', 'Folha', 0.03), 
('Feltro', 'Tecidos', 'Metro Linear', 0.90);


-- Inserir dados na tabela Subcontratado
INSERT INTO Subcontratado (nome, morada, contacto, custoServico) VALUES
('Oficina de Costura Silva', 'Rua das Flores, 123, Lisboa', '911223344', 1.50),
('Montagem Rápida Lda.', 'Avenida Central, 45, Porto', '933445566', 0.80),
('Cortes Precisos Unipessoal', 'Travessa da Indústria, 789, Braga', '966778899', 0.50),
('Acabamentos de Qualidade SA', 'Largo do Comércio, 10, Setúbal', '922334455', 0.30),
('Colagens Industriais Oliveira', 'Zona Industrial, Lote 5, Aveiro', '955667788', 0.25),
('Pespontos Modernos', 'Rua Nova, 234, Guimarães', '944556677', 0.40),
('Embalagens Seguras Lda', 'Parque Empresarial, Edifício A, Faro', '977889900', 0.15),
('Controlo de Qualidade Total', 'Rua Direita, 567, Coimbra', '988990011', 0.20),
('Etiquetas Personalizadas', 'Avenida dos Descobrimentos, 89, Lagos', '912345678', 0.05),
('Subcontratação de Mão de Obra Especializada', 'Rua da Liberdade, 112, Évora', '934567890', 2.00),
('Serviços de Costura Fina', 'Rua Augusta, 321, Viana do Castelo', '967890123', 1.75),
('Unidade de Montagem Avançada', 'Avenida Marginal, 654, Matosinhos', '923456789', 0.90),
('Corte a Laser Industrial', 'Zona Industrial da Maia, Lote 10, Maia', '956789012', 0.60),
('Acabamentos Manuais Profissionais', 'Rua do Bonfim, 43, Vila do Conde', '947890123', 0.35),
('Colagem de Precisão', 'Estrada Nacional 109, Figueira da Foz', '978901234', 0.30),
('Pespontos Criativos', 'Rua da Igreja, 76, Barcelos', '919012345', 0.45),
('Embalagens Ecológicas', 'Parque Industrial de Famalicão, Lote 8, Vila Nova de Famalicão', '930123456', 0.18),
('Inspeção de Qualidade Rigorosa', 'Rua do Souto, 98, Braga', '961234567', 0.22),
('Etiquetagem Automática', 'Avenida da República, 150, Espinho', '924567891', 0.07),
('Fornecedor de Serviços de Subcontratação', 'Rua da Alfândega, 200, Lisboa', '957891234', 1.20);


-- Inserir dados na tabela FichaTecnica (relacionados com os produtos criados)
INSERT INTO FichaTecnica (produtoID, origem, descricao, estado) VALUES
(1, 'interna', 'Ficha técnica para Bota de Couro Cano Alto Preta.', 'ativa'),
(2, 'interna', 'Especificações da Bota de Couro Cano Curto Castanha.', 'ativa'),
(3, 'externa', 'Instruções da Bota Tática Impermeável Preta.', 'ativa'),
(4, 'interna', 'Processo de montagem da Bota de Montanha Aventura.', 'ativa'),
(5, 'interna', 'Ficha técnica da Sandália de Couro Verão.', 'ativa'),
(6, 'externa', 'Especificações da Sandália Desportiva Ajustável.', 'ativa'),
(7, 'interna', 'Ficha de produção da Sandália Plataforma Elegante.', 'inativa'),
(8, 'interna', 'Especificações do Ténis de Corrida Performance.', 'ativa'),
(9, 'interna', 'Detalhes da produção do Ténis Casual Urbano.', 'ativa'),
(10, 'externa', 'Ficha técnica do Ténis Desportivo Treino.', 'ativa'),
(11, 'interna', 'Processo de fabrico do Sapato Social Clássico Preto.', 'ativa'),
(12, 'interna', 'Especificações do Sapato Social Clássico Castanho.', 'ativa'),
(13, 'interna', 'Detalhes da montagem do Mocassim de Couro Confortável.', 'ativa'),
(14, 'externa', 'Ficha técnica do Sapato Oxford Formal Preto.', 'ativa'),
(15, 'interna', 'Processo de fabrico dos Chinelos de Piscina Antiderrapantes.', 'ativa'),
(16, 'interna', 'Especificações dos Chinelos de Casa Confortáveis.', 'ativa'),
(17, 'interna', 'Detalhes da produção das Espadrilles de Lona Bege.', 'ativa'),
(18, 'externa', 'Ficha técnica das Alpargatas de Tecido Azul Marinho.', 'ativa'),
(19, 'interna', 'Processo de fabrico das Sabrinas Clássicas Pretas.', 'ativa'),
(20, 'interna', 'Especificações das Sabrinas Coloridas Vermelhas.', 'ativa');



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


-- Inserir dados na tabela OrdemFabrico
INSERT INTO OrdemFabrico (dataEmissao, dataPrevistaConclusao) VALUES
('2025-05-10', '2025-05-15'), -- Passado
('2025-05-12', '2025-05-18'), -- Passado
('2025-05-15', '2025-05-20'), -- Passado
('2025-05-23', '2025-05-30'), -- Futuro
('2025-05-24', '2025-06-01'), -- Futuro
('2025-05-18', '2025-05-22'), -- Passado
('2025-05-20', '2025-05-25'), -- Futuro
('2025-05-11', '2025-05-16'), -- Passado
('2025-05-22', '2025-05-29'), -- Futuro
('2025-05-13', '2025-05-19'), -- Passado
('2025-05-01', '2025-05-07'), -- Passado
('2025-05-05', '2025-05-11'), -- Passado
('2025-05-08', '2025-05-14'), -- Passado
('2025-05-25', '2025-06-05'), -- Futuro
('2025-05-28', '2025-06-08'), -- Futuro
('2025-05-19', '2025-05-21'), -- Passado
('2025-05-22', '2025-05-27'), -- Futuro
('2025-05-03', '2025-05-09'), -- Passado
('2025-05-26', '2025-06-02'), -- Futuro
('2025-05-16', '2025-05-20'); -- Passado


-- Inserir dados na tabela ItemOrdemFabrico
INSERT INTO ItemOrdemFabrico (ordemID, produtoID, quantidadePlaneada, fichaTecnicaID) VALUES
(1, 1, 100, 1),  -- Ordem 1: 100 Botas Cano Alto Pretas (Ficha Técnica 1)
(1, 2, 50, 2),   -- Ordem 1: 50 Botas Cano Curto Castanhas (Ficha Técnica 2)
(2, 5, 200, 5),  -- Ordem 2: 200 Sandálias de Couro Verão (Ficha Técnica 5)
(3, 9, 150, 9),  -- Ordem 3: 150 Ténis Casual Urbanos (Ficha Técnica 9)
(4, 11, 75, 11), -- Ordem 4: 75 Sapatos Sociais Clássicos Pretos (Ficha Técnica 11)
(5, 16, 300, 16), -- Ordem 5: 300 Chinelos de Casa Confortáveis (Ficha Técnica 16)
(6, 1, 50, 1),   -- Ordem 6: 50 Botas Cano Alto Pretas (Ficha Técnica 1)
(6, 19, 100, 19), -- Ordem 6: 100 Sabrinas Clássicas Pretas (Ficha Técnica 19)
(7, 2, 75, 2),   -- Ordem 7: 75 Botas Cano Curto Castanhas (Ficha Técnica 2)
(8, 5, 120, 5),  -- Ordem 8: 120 Sandálias de Couro Verão (Ficha Técnica 5)
(9, 9, 90, 9),   -- Ordem 9: 90 Ténis Casual Urbanos (Ficha Técnica 9)
(10, 11, 40, 11), -- Ordem 10: 40 Sapatos Sociais Clássicos Pretos (Ficha Técnica 11)
(11, 16, 200, 16), -- Ordem 11: 200 Chinelos de Casa Confortáveis (Ficha Técnica 16)
(12, 3, 60, 3),  -- Ordem 12: 60 Botas Táticas Impermeáveis Pretas (Ficha Técnica 3)
(13, 6, 180, 6), -- Ordem 13: 180 Sandálias Desportivas Ajustáveis (Ficha Técnica 6)
(14, 10, 110, 10), -- Ordem 14: 110 Ténis Desportivos Treino (Ficha Técnica 10)
(15, 14, 55, 14), -- Ordem 15: 55 Sapatos Oxford Formais Pretos (Ficha Técnica 14)
(16, 18, 250, 18), -- Ordem 16: 250 Alpargatas de Tecido Azul Marinho (Ficha Técnica 18)
(17, 20, 80, 20), -- Ordem 17: 80 Sabrinas Coloridas Vermelhas (Ficha Técnica 20)
(18, 4, 30, 4);  -- Ordem 18: 30 Botas de Montanha Aventura (Ficha Técnica 4)


/* INSERIR AO FIM PARA PROPOSITOS DEMONSTRATIVOS
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
*/

