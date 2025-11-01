-- Tabela Produto
CREATE TABLE Produto (

  produtoID INT IDENTITY(1,1) NOT NULL,
  modelo VARCHAR(50) NOT NULL,
  variante VARCHAR(50),
  cor VARCHAR(50) NOT NULL,
  nome VARCHAR(50) NOT NULL,
  descricao VARCHAR(255),
  dataCriacao DATETIME2 DEFAULT SYSDATETIME(),
  dataAtualizacao DATETIME2,

  -- Restrição de unicidade no modelo e a variante do Produto
  CONSTRAINT UQ_Produto_modelo_variante
  UNIQUE NONCLUSTERED (modelo, variante),

  -- Chave primária
  CONSTRAINT PK_Produto PRIMARY KEY CLUSTERED (produtoID)
);