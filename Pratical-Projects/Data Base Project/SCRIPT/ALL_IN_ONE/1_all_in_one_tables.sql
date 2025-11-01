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
  precoVenda DECIMAL (10,2),

  -- Chave primária
  CONSTRAINT PK_Produto PRIMARY KEY CLUSTERED (produtoID)
);

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

  -- Chave Estrangeira que referencia a tabela Produto
  CONSTRAINT FK_FichaTecnica_Produto
  FOREIGN KEY (produtoID) REFERENCES Produto(produtoID),

  -- Chave primária
  CONSTRAINT PK_FichaTecnica
  PRIMARY KEY CLUSTERED (fichaTecnicaID)
);

-- Tabela DetalheFichaTecnica
CREATE TABLE DetalheFichaTecnica (
  detalheID INT IDENTITY(1,1) NOT NULL,
  fichaTecnicaID INT NOT NULL,
  operacaoID INT NOT NULL,
  ordemExecucao INT NOT NULL,
  vezesPorUnidade DECIMAL(10,2) NOT NULL,
  descricao VARCHAR(255),
  duracaoUnitariaMin DECIMAL(5,2),
  custoMinuto DECIMAL(10,2),

  CONSTRAINT UQ_DetalheFichaTecnica_FichaTecnica_OrdemExecucao
    UNIQUE NONCLUSTERED (fichaTecnicaID, ordemExecucao),

  CONSTRAINT FK_DetalheFichaTecnica_FichaTecnica
    FOREIGN KEY (fichaTecnicaID) REFERENCES FichaTecnica(fichaTecnicaID),

  CONSTRAINT FK_DetalheFichaTecnica_Operacao
    FOREIGN KEY (operacaoID) REFERENCES Operacao(operacaoID),

  CONSTRAINT PK_DetalheFichaTecnica
    PRIMARY KEY CLUSTERED (detalheID)
);


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

  -- Chave Estrangeira que referencia a tabela FichaTecnica
  CONSTRAINT FK_ItemOrdemFabrico_FichaTecnica
  FOREIGN KEY (fichaTecnicaID) REFERENCES FichaTecnica(fichaTecnicaID),

  -- Chave Estrangeira que referencia a tabela OrdemFabrico
  CONSTRAINT FK_ItemOrdemFabrico_OrdemFabrico
  FOREIGN KEY (ordemID) REFERENCES OrdemFabrico(ordemID),

  -- Chave Estrangeira que referencia a tabela Produto
  CONSTRAINT FK_ItemOrdemFabrico_Produto
  FOREIGN KEY (produtoID) REFERENCES Produto(produtoID),

  -- Chave Primária
  CONSTRAINT PK_ItemOrdemFabrico
  PRIMARY KEY CLUSTERED (itemOrdemID),
);

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

  -- Chave Estrangeira que referencia a tabela ItemOrdemFabrico
  CONSTRAINT FK_ExecucaoOperacao_ItemOrdemFabrico
  FOREIGN KEY (itemOrdemID) REFERENCES ItemOrdemFabrico(itemOrdemID),

  -- Chave Estrangeira que referencia a tabela Operacao
  CONSTRAINT FK_ExecucaoOperacao_Operacao
  FOREIGN KEY (operacaoID) REFERENCES Operacao(operacaoID),

  -- Chave Estrangeira que referencia a tabela Subcontratado
  CONSTRAINT FK_ExecucaoOperacao_Subcontratado
  FOREIGN KEY (subcontratadoID) REFERENCES Subcontratado(subcontratadoID),

   -- Chave Primária
  CONSTRAINT PK_ExecucaoOperacao
  PRIMARY KEY CLUSTERED (execucaoID),
);


-- Tabela AlertaAtraso
CREATE TABLE AlertaAtraso (

  alertaAtrasoID INT IDENTITY(1,1) NOT NULL,
  ordemID INT NOT NULL,
  dataGeracao DATE NOT NULL,
  motivo VARCHAR(255),

  -- Chave Estrangeira que referencia a tabela OrdemFabrico
  CONSTRAINT FK_AlertaAtraso_OrdemFabrico
  FOREIGN KEY (ordemID) REFERENCES OrdemFabrico(ordemID),

  -- Chave primária
  CONSTRAINT PK_AlertaAtraso
  PRIMARY KEY CLUSTERED (alertaAtrasoID),
);


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

  -- Chave Estrangeira que referencia a tabela ExecucaoOperacao
  CONSTRAINT FK_AlertaQualidade_ExecucaoOperacao
  FOREIGN KEY (execucaoID) REFERENCES ExecucaoOperacao(execucaoID),

  -- Chave Estrangeira que referencia a tabela Subcontratado
  CONSTRAINT FK_AlertaQualidade_Subcontratado
  FOREIGN KEY (subcontratadoID) REFERENCES Subcontratado(subcontratadoID),

  -- Chave Primária
  CONSTRAINT PK_AlertaQualidade
  PRIMARY KEY CLUSTERED (alertaQualidadeID),
);

CREATE TABLE ConsumoMaterialReal (

    consumoID INT IDENTITY(1,1) NOT NULL,
    execucaoID INT NULL,
    detalheID INT NOT NULL,
    materialID INT NOT NULL,
    quantidadePlaneada DECIMAL(10,2) NOT NULL,
    quantidadeUtilizada DECIMAL(10,2) NULL,
    dataRegisto DATETIME2 DEFAULT SYSDATETIME(),
    percentagemPerdaMaterial DECIMAL(5,2),

    -- Restrição de quantidade utilizada válida
    CONSTRAINT CK_ConsumoMaterialReal_quantidadeUtilizada_pos
    CHECK (quantidadeUtilizada IS NULL OR quantidadeUtilizada >= 0)

    -- Restrição de quantidade planeada válida
    CONSTRAINT CK_ConsumoMaterialReal_quantidadePlaneada_pos
    CHECK (quantidadePlaneada >= 0),

    -- Chave Estrangeira que referencia a tabela ExecucaoOperacao
    CONSTRAINT FK_ConsumoMaterialReal_ExecucaoOperacao
    FOREIGN KEY (execucaoID) REFERENCES ExecucaoOperacao(execucaoID),

    -- Chave Estrangeira que referencia a tabela Material
    CONSTRAINT FK_ConsumoMaterialReal_Material
    FOREIGN KEY (materialID) REFERENCES Material(materialID),

    -- Chave Estrangeira que referencia a tabela DetalheFichaTecnica
    CONSTRAINT FK_ConsumoMaterialReal_DetalheFichaTecnica
    FOREIGN KEY (detalheID) REFERENCES DetalheFichaTecnica(detalheID),

    -- Chave Primária
    CONSTRAINT PK_ConsumoMaterialReal
    PRIMARY KEY CLUSTERED (consumoID),
);
