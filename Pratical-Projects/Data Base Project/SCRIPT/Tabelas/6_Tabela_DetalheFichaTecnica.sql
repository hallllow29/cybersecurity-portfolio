-- Tabela DetalheFichaTecnica
CREATE TABLE DetalheFichaTecnica (

  detalheID INT IDENTITY(1,1) NOT NULL,
  fichaTecnicaID INT NOT NULL,
  operacaoID INT NOT NULL,
  materialID INT,
  ordemExecucao INT NOT NULL,
  vezesPorUnidade DECIMAL(10,2) NOT NULL,
  descricao VARCHAR(255),
  duracaoUnitariaMin decimal(5,2),
  custoMinuto decimal(10,2),

  -- Restrição de unicidade no ficha tecnica e na ordem de execução de DetalheFichaTecnica
  CONSTRAINT UQ_DetalheFichaTecnica_FichaTecnica_OrdemExecucao
  UNIQUE NONCLUSTERED (fichaTecnicaID, ordemExecucao),

  -- Chave Estrangeira que refrencia a tabela FichaTecnica
  CONSTRAINT FK_DetalheFichaTecnica_FichaTecnica
  FOREIGN KEY (fichaTecnicaID) REFERENCES FichaTecnica(fichaTecnicaID),

  -- Chave Estrangeira que refrencia a tabela Operacao
  CONSTRAINT FK_DetalheFichaTecnica_Operacao
  FOREIGN KEY (operacaoID) REFERENCES Operacao(operacaoID),

  -- Chave primária
  CONSTRAINT PK_DetalheFichaTecnica
  PRIMARY KEY CLUSTERED (detalheID)
);