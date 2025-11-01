-- Tabela ItemOrdemFabrico
CREATE TABLE ItemOrdemFabrico (

  itemOrdemID INT IDENTITY(1,1) NOT NULL,
  ordemID INT NOT NULL,
  produtoID INT NOT NULL,
  quantidadePlaneada INT NOT NULL,
  fichaTecnicaID INT NOT NULL,
  estadoProducao VARCHAR(50) DEFAULT 'nao inciado',

  -- Restrição de unicidade no ordem e no produto do ItemOrdemFabrico
  CONSTRAINT UQ_ItemOrdemFabrico_ordemID_produtoID
  UNIQUE NONCLUSTERED (ordemID, produtoID),

  -- Chave Estrangeira que refrencia a tabela FichaTecnica
  CONSTRAINT FK_ItemOrdemFabrico_FichaTecnica
  FOREIGN KEY (fichaTecnicaID) REFERENCES FichaTecnica(fichaTecnicaID),

  -- Chave Estrangeira que refrencia a tabela OrdemFabrico
  CONSTRAINT FK_ItemOrdemFabrico_OrdemFabrico
  FOREIGN KEY (ordemID) REFERENCES OrdemFabrico(ordemID),

  -- Chave Estrangeira que refrencia a tabela Produto
  CONSTRAINT FK_ItemOrdemFabrico_Produto
  FOREIGN KEY (produtoID) REFERENCES Produto(produtoID),

  -- Chave Primária
  CONSTRAINT PK_ItemOrdemFabrico
  PRIMARY KEY CLUSTERED (itemOrdemID),
);