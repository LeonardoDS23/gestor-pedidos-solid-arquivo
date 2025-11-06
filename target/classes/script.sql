DROP DATABASE IF EXISTS DB_PROJETO_SGP;
CREATE DATABASE DB_PROJETO_SGP;

-- Selecionar/Utilizar o banco de dados
USE DB_PROJETO_SGP;

-- Criando a tabela cliente
CREATE TABLE T_SGP_CLIENTE(
	-- nome_coluna  		tipo_dado  		cardinalidade_coluna
    id_cliente 				INT 		 	NOT NULL,
    nm_cliente 				VARCHAR(60) 	NOT NULL,
    nr_cadastro 			CHAR(3)     	NOT NULL,
    ds_email				CHAR(150)    	NOT NULL,
    nr_telefone				CHAR(15)		NOT NULL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_CLIENTE
	ADD CONSTRAINT PK_CLIENTE
    PRIMARY KEY(id_cliente);


CREATE TABLE T_SGP_ENDERECO(
	id_endereco 	INT 	 		NOT NULL,
	sg_estado 		CHAR(2) 		NOT NULL,
	nm_cidade 		VARCHAR(80) 	NOT NULL,
	nm_bairro 		VARCHAR(80)		NOT NULL,
	tp_logradouro	CHAR(1)			NOT NULL,
	nm_logradouro 	VARCHAR(80)		NOT NULL,
	tp_endereco 	CHAR(1)			NOT NULL,
	nr_residencia 	INT 			NOT NULL,
	ob_complemento	VARCHAR(150)	NULL -- OPCIONAL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_ENDERECO
	ADD CONSTRAINT PK_ENDERECO
    PRIMARY KEY(id_endereco);
    
-- Inserir as colunas candidatas para FK
ALTER TABLE T_SGP_ENDERECO
    ADD COLUMN id_cliente  INT NOT NULL;
    
-- Adicionar a FK de cada coluna
ALTER TABLE T_SGP_ENDERECO
	-- FK da coluna "id_endereco"
	ADD CONSTRAINT FK_ENDER_CLIENTE FOREIGN KEY(id_cliente)
		REFERENCES T_SGP_CLIENTE(id_cliente);

CREATE TABLE T_SGP_PRODUTO(
	-- nome_coluna  		tipo_dado  		cardinalidade_coluna
    id_produto 				INT 		 	NOT NULL,
    nm_produto 				VARCHAR(60) 	NOT NULL,
    tp_produto 				CHAR(1)     	NOT NULL,
    ds_produto				VARCHAR(150)	NOT NULL,
    vl_produto				DECIMAL(6,3)	NOT NULL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_PRODUTO
	ADD CONSTRAINT PK_PRODUTO
    PRIMARY KEY(id_produto);

CREATE TABLE T_SGP_PEDIDO(
	-- nome_coluna  		tipo_dado  		cardinalidade_coluna
    id_pedido 				INT 		 	NOT NULL,
    st_pedido 				CHAR(1) 		NOT NULL,
    nr_pedido 				INT     		NOT NULL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_PEDIDO
	ADD CONSTRAINT PK_PEDIDO
    PRIMARY KEY(id_pedido);
    
-- Inserir as colunas candidatas para FK
ALTER TABLE T_SGP_PEDIDO
    ADD COLUMN id_cliente  INT NOT NULL,
    ADD COLUMN id_endereco INT NOT NULL;
    
-- Adicionar a FK de cada coluna
ALTER TABLE T_SGP_PEDIDO
	-- FK da coluna "id_depto"
	ADD CONSTRAINT FK_PEDIDO_CLIENTE FOREIGN KEY(id_cliente)
		REFERENCES T_SGP_CLIENTE(id_cliente),
	-- FK da coluna "id_gestor"
	ADD CONSTRAINT FK_PEDIDO_ENDERECO FOREIGN KEY(id_endereco)
		REFERENCES T_SGP_ENDERECO(id_endereco);

CREATE TABLE T_SGP_ITEM_PEDIDO(
	-- nome_coluna  		tipo_dado  		cardinalidade_coluna
    id_item 				INT 		 	NOT NULL,
    nm_cliente 				VARCHAR(60) 	NOT NULL,
    nr_cadastro 			CHAR(3)     	NOT NULL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_ITEM_PEDIDO
	ADD CONSTRAINT PK_ITEM_PEDIDO
    PRIMARY KEY(id_item);
    
-- Inserir as colunas candidatas para FK
ALTER TABLE T_SGP_ITEM_PEDIDO
    ADD COLUMN id_pedido  INT NOT NULL,
    ADD COLUMN id_produto INT NOT NULL;
    
-- Adicionar a FK de cada coluna
ALTER TABLE T_SGP_ITEM_PEDIDO
	-- FK da coluna "id_depto"
	ADD CONSTRAINT FK_ITEM_PEDIDO FOREIGN KEY(id_pedido)
		REFERENCES T_SGP_PEDIDO(id_pedido),
	-- FK da coluna "id_gestor"
	ADD CONSTRAINT FK_ITEM_PRODUTO FOREIGN KEY(id_produto)
		REFERENCES T_SGP_PRODUTO(id_produto);

CREATE TABLE T_SGP_AVALIACAO(
	-- nome_coluna  		tipo_dado  		cardinalidade_coluna
    id_avaliacao 			INT 		 	NOT NULL,
    vl_nota 				INT 	        NOT NULL,
    dt_avaliacao 			DATETIME     	NOT NULL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_AVALIACAO
	ADD CONSTRAINT PK_AVALIACAO
    PRIMARY KEY(id_avaliacao);
    
-- Inserir as colunas candidatas para FK
ALTER TABLE T_SGP_AVALIACAO
    ADD COLUMN id_pedido  INT NOT NULL,
    ADD COLUMN id_cliente INT NOT NULL;
    
-- Adicionar a FK de cada coluna
ALTER TABLE T_SGP_AVALIACAO
	-- FK da coluna "id_depto"
	ADD CONSTRAINT FK_AVALIACAO_PEDIDO FOREIGN KEY(id_pedido)
		REFERENCES T_SGP_PEDIDO(id_pedido),
	-- FK da coluna "id_gestor"
	ADD CONSTRAINT FK_AVALIACAO_CLIENTE FOREIGN KEY(id_cliente)
		REFERENCES T_SGP_CLIENTE(id_cliente);

CREATE TABLE T_SGP_FUNCIONARIO(
	-- nome_coluna  		tipo_dado  		cardinalidade_coluna
    id_funcionario			INT 		 	NOT NULL,
    nm_funcionario			INT 	        NOT NULL,
    ds_cargo 			    DATETIME     	NOT NULL,
    nr_telefone             VARCHAR(15)     NOT NULL,
    nr_cpf                  VARCHAR(14)     NOT NULL,
    dt_admissao             DATETIME        NOT NULL,
    dt_demissao             DATETIME        NULL,
    vl_salario              DECIMAL         NOT NULL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_FUNCIONARIO
	ADD CONSTRAINT PK_FUNCIONARIO
    PRIMARY KEY(id_funcionario);
    
CREATE TABLE T_SGP_PAGAMENTO(
	-- nome_coluna  		tipo_dado  		cardinalidade_coluna
    id_pagamento 			INT 		 	NOT NULL,
    tp_pagamento 			VARCHAR(8) 	    NOT NULL,
    vl_total 			    DECIMAL(10,2)   NOT NULL,
    st_pagamento            VARCHAR(20)     NOT NULL,
    dt_pagamento            DATETIME        NOT NULL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_PAGAMENTO
	ADD CONSTRAINT PK_PAGAMENTO
    PRIMARY KEY(id_pagamento);
    
-- Inserir as colunas candidatas para FK
ALTER TABLE T_SGP_PAGAMENTO
    ADD COLUMN id_pedido  INT NOT NULL,
    ADD COLUMN id_cliente INT NOT NULL;
    
-- Adicionar a FK de cada coluna
ALTER TABLE T_SGP_PAGAMENTO
	-- FK da coluna "id_depto"
	ADD CONSTRAINT FK_PAGAMENTO_PEDIDO FOREIGN KEY(id_pedido)
		REFERENCES T_SGP_PEDIDO(id_pedido),
	-- FK da coluna "id_gestor"
	ADD CONSTRAINT FK_PAGAMENTO_CLIENTE FOREIGN KEY(id_cliente)
		REFERENCES T_SGP_CLIENTE(id_cliente);

CREATE TABLE T_SGP_ENTREGA(
	-- nome_coluna  		tipo_dado  		cardinalidade_coluna
    id_entrega 			    INT 		 	NOT NULL,
    dt_entrega 				DATETIME 	    NOT NULL,
    st_entrega			    VARCHAR(30)     NOT NULL,
    ob_entrega              VARCHAR(255)    NULL
);

-- Adicionar uma chave primária
ALTER TABLE T_SGP_ENTREGA
	ADD CONSTRAINT PK_ENTREGA
    PRIMARY KEY(id_entrega);
    
-- Inserir as colunas candidatas para FK
ALTER TABLE T_SGP_ENTREGA
    ADD COLUMN id_pedido        INT NOT NULL,
    ADD COLUMN id_funcionario   INT NOT NULL;
    
-- Adicionar a FK de cada coluna
ALTER TABLE T_SGP_ENTREGA
	-- FK da coluna "id_depto"
	ADD CONSTRAINT FK_ENTREGA_PEDIDO FOREIGN KEY(id_pedido)
		REFERENCES T_SGP_PEDIDO(id_pedido),
	-- FK da coluna "id_gestor"
	ADD CONSTRAINT FK_ENTREGA_FUNCIONARIO FOREIGN KEY(id_funcionario)
		REFERENCES T_SGP_FUNCIONARIO(id_funcionario);