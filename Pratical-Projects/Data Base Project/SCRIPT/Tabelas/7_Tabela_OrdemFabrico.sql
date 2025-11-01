-- Tabela OrdemFabrico
CREATE TABLE OrdemFabrico (

  ordemID INT IDENTITY(1,1) NOT NULL,
  dataEmissao date DEFAULT SYSDATETIME() NOT NULL,
  dataPrevistaConclusao date NOT NULL,
  estado VARCHAR(20) NOT NULL DEFAULT 'planeada',

  -- Restrição de estado válidos
  CONSTRAINT CHK_OrdemFabrico_estado
  CHECK (estado IN ('cancelada', 'concluida', 'em_execucao', 'planeada')),

  -- Chave primária
  CONSTRAINT PK_OrdemFabrico
  PRIMARY KEY CLUSTERED (ordemID)
);