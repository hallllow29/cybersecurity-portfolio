-- Tabela Material
CREATE TABLE Material (

  materialID INT IDENTITY(1,1) NOT NULL,
  nome VARCHAR(50) NOT NULL,
  tipo VARCHAR(50) NULL,
  unidadeMedida VARCHAR(20) NULL,
  custoUnitario DECIMAL(10,2) NOT NULL,

  -- Restrição de unicidade no nome do Material
  CONSTRAINT UQ_Material_Nome
  UNIQUE NONCLUSTERED (nome),

  -- Chave primária
  CONSTRAINT PK_Material PRIMARY KEY CLUSTERED (materialID),
)