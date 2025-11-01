-- Tabela FichaTecnica
CREATE TABLE FichaTecnica (

  fichaTecnicaID INT IDENTITY(1,1) NOT NULL,
  produtoID INT NOT NULL,
  origem VARCHAR(50) NOT NULL DEFAULT 'interna',
  descricao VARCHAR(255),
  dataCriacao DATETIME2 DEFAULT SYSDATETIME(),
  dataAtualizacao DATETIME2,
  estado varchar(50) NOT NULL,

  -- Restrição de estado válidos
  CONSTRAINT CHK_FichaTecnica_estado_valido
  CHECK (estado IN ('inativa', 'ativa')),

  -- Restrição de origem válidos
  CONSTRAINT CHK_FichaTecnica_origem_valida
  CHECK (origem IN ('interna','externa')),

  -- Chave Estrangeira que refrencia a tabela Produto
  CONSTRAINT FK_FichaTecnica_Produto
  FOREIGN KEY (produtoID) REFERENCES Produto(produtoID),

  -- Chave primária
  CONSTRAINT PK_FichaTecnica PRIMARY KEY CLUSTERED (fichaTecnicaID)
);