-- Tabela AlertaAtraso
CREATE TABLE AlertaAtraso (

  alertaAtrasoID INT IDENTITY(1,1) NOT NULL,
  ordemID INT NOT NULL,
  dataGeracao DATE NOT NULL,
  motivo VARCHAR(255),

  -- Chave Estrangeira que refrencia a tabela OrdemFabrico
  CONSTRAINT [FK_AlertaAtraso_OrdemFabrico]
  FOREIGN KEY (ordemID) REFERENCES OrdemFabrico(ordemID),

  -- Chave primária
  CONSTRAINT PK_AlertaAtraso
  PRIMARY KEY CLUSTERED (alertaAtrasoID),
);