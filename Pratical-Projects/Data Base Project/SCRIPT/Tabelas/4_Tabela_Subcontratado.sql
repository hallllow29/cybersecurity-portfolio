-- Tabela Subcontratado
CREATE TABLE Subcontratado (

  subcontratadoID INT IDENTITY(1,1) NOT NULL,
  nome VARCHAR(100) NOT NULL,
  morada VARCHAR(255),
  contacto VARCHAR(50),
  custoServico DECIMAL(10,2) NOT NULL,

  -- Restrição de unicidade no contacto do Subcontratado
  CONSTRAINT UQ_Subcontratado_contacto
  UNIQUE NONCLUSTERED (contacto),

  -- Chave primária
  CONSTRAINT PK_Subcontratado
  PRIMARY KEY CLUSTERED (subcontratadoID)
);
