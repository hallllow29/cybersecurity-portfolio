CREATE TABLE ConsumoMaterialReal (

  consumoID INT IDENTITY(1,1) NOT NULL,
  execucaoID INT NULL,
  detalheID INT NOT NULL,
  materialID INT NOT NULL,
  quantidadePlaneada DECIMAL(10,2) NOT NULL,
  quantidadeUtilizada DECIMAL(10,2) NULL,
  dataRegisto DATETIME2 DEFAULT SYSDATETIME(),
  percentagemPerdaMaterial DECIMAL(5,2),

  -- Restrição de quantidade utilizada válida
  CONSTRAINT CK_ConsumoMaterialReal_quantidadeUtilizada_pos
  CHECK (quantidadeUtilizada IS NULL OR quantidadeUtilizada >= 0)

  -- Restrição de quantidade planeada válida
  CONSTRAINT CK_ConsumoMaterialReal_quantidadePlaneada_pos
  CHECK (quantidadePlaneada >= 0),

  -- Chave Estrangeira que referencia a tabela ExecucaoOperacao
  CONSTRAINT FK_ConsumoMaterialReal_ExecucaoOperacao
  FOREIGN KEY (execucaoID) REFERENCES ExecucaoOperacao(execucaoID),

  -- Chave Estrangeira que referencia a tabela Material
  CONSTRAINT FK_ConsumoMaterialReal_Material
  FOREIGN KEY (materialID) REFERENCES Material(materialID),

  -- Chave Estrangeira que referencia a tabela DetalheFichaTecnica
  CONSTRAINT FK_ConsumoMaterialReal_DetalheFichaTecnica
  FOREIGN KEY (detalheID) REFERENCES DetalheFichaTecnica(detalheID),

  -- Chave Primária
  CONSTRAINT PK_ConsumoMaterialReal
  PRIMARY KEY CLUSTERED (consumoID),
);
