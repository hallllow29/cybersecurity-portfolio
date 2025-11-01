-- Tabela Operacao
CREATE TABLE Operacao (

  operacaoID INT IDENTITY(1,1) NOT NULL,
  nome VARCHAR(20) NOT NULL,
  descricao VARCHAR(255),

  -- Restrição de nome válidos
  CONSTRAINT CHK_Operacao_Nome_Valido
  CHECK (nome IN ('Acabamento', 'Montagem', 'Costura', 'Corte')),

  -- Chave primária
  CONSTRAINT PK_Operacao PRIMARY KEY CLUSTERED (operacaoID)
);