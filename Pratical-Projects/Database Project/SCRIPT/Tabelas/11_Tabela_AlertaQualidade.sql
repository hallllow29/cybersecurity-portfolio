-- Tabela AlertaQualidade
CREATE TABLE AlertaQualidade (

  alertaQualidadeID INT IDENTITY(1,1) NOT NULL,
  execucaoID INT NOT NULL,
  subcontratadoID INT,
  descricao VARCHAR(255),
  dataGeracao DATE NOT NULL,

  -- Restrição de unicidade na execução de AlertaQualidade
  CONSTRAINT UQ_AlertaQualidade_execucao
  UNIQUE NONCLUSTERED (execucaoID),

  -- Chave Estrangeira que refrencia a tabela ExecucaoOperacao
  CONSTRAINT FK_AlertaQualidade_ExecucaoOperacao
  FOREIGN KEY (execucaoID) REFERENCES ExecucaoOperacao(execucaoID),

  -- Chave Estrangeira que refrencia a tabela Subcontratado
  CONSTRAINT FK_AlertaQualidade_Subcontratado
  FOREIGN KEY (subcontratadoID) REFERENCES Subcontratado(subcontratadoID),

  -- Chave Primária
  CONSTRAINT PK_AlertaQualidade
  PRIMARY KEY CLUSTERED (alertaQualidadeID),
);