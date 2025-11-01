-- Tabela ExecucaoOperacao
CREATE TABLE ExecucaoOperacao (

  execucaoID INT IDENTITY(1,1) NOT NULL,
  itemOrdemID INT NOT NULL,
  operacaoID INT NOT NULL,
  subcontratadoID INT,
  precoPorUnidade DECIMAL(10,2),
  quantidadeEnviada INT NOT NULL,
  quantidadeRecebida INT NOT NULL,
  dataExecucao date,
  custo decimal(10,2) NOT NULL,
  quantidadePerdida INT,

  -- Restrição para quantidades válidas
  CONSTRAINT CHK_ExecucaoOperacao_quantidades_naoNegativa
  CHECK (quantidadeEnviada >= 0 AND quantidadeRecebida >= 0),

  -- Restrição de unicidade no ordem do item e na operação da ExecucaoOperacao
  CONSTRAINT UQ_ExecucaoOperacao
  UNIQUE NONCLUSTERED (itemOrdemID, operacaoID),

  -- Chave Estrangeira que refrencia a tabela ItemOrdemFabrico
  CONSTRAINT FK_ExecucaoOperacao_ItemOrdemFabrico
  FOREIGN KEY (itemOrdemID) REFERENCES ItemOrdemFabrico(itemOrdemID),

  -- Chave Estrangeira que refrencia a tabela Operacao
  CONSTRAINT FK_ExecucaoOperacao_Operacao
  FOREIGN KEY (operacaoID) REFERENCES Operacao(operacaoID),

  -- Chave Estrangeira que refrencia a tabela Subcontratado
  CONSTRAINT FK_ExecucaoOperacao_Subcontratado
  FOREIGN KEY (subcontratadoID) REFERENCES Subcontratado(subcontratadoID),

   -- Chave Primária
  CONSTRAINT PK_ExecucaoOperacao
  PRIMARY KEY CLUSTERED (execucaoID),
);