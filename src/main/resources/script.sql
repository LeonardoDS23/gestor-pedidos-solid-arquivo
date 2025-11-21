DROP DATABASE IF EXISTS DB_PROJETO_SGP;
CREATE DATABASE DB_PROJETO_SGP;

USE DB_PROJETO_SGP;

CREATE TABLE T_SGP_CLIENTE(
    id_cliente      INT             NOT NULL,
    nm_cliente      VARCHAR(60)     NOT NULL,
    nr_cadastro     CHAR(3)         NOT NULL,
    ds_email        CHAR(150)       NOT NULL,
    nr_telefone     CHAR(15)        NOT NULL
);

ALTER TABLE T_SGP_CLIENTE
    ADD CONSTRAINT PK_CLIENTE
    PRIMARY KEY(id_cliente),
    ADD CONSTRAINT UN_CLIENTE_CADASTRO 
    UNIQUE (nr_cadastro),
    ADD CONSTRAINT UN_CLIENTE_TELEFONE
    UNIQUE (nr_telefone),
    ADD CONSTRAINT CK_CLIENTE_EMAIL
    CHECK (ds_email LIKE '%@%.%');

CREATE TABLE T_SGP_ENDERECO(
    id_endereco     INT             NOT NULL,
    sg_estado       CHAR(2)         NOT NULL,
    nm_cidade       VARCHAR(80)     NOT NULL,
    nm_bairro       VARCHAR(80)     NOT NULL,
    tp_logradouro   CHAR(1)         NOT NULL,
    nm_logradouro   VARCHAR(80)     NOT NULL,
    tp_endereco     CHAR(1)         NOT NULL,
    nr_residencia   INT             NOT NULL,
    ob_complemento  VARCHAR(150)    NULL,
    id_cliente      INT             NOT NULL
);

ALTER TABLE T_SGP_ENDERECO
    ADD CONSTRAINT PK_ENDERECO
    PRIMARY KEY(id_endereco),
    ADD CONSTRAINT FK_ENDERECO_CLIENTE 
    FOREIGN KEY(id_cliente)
        REFERENCES T_SGP_CLIENTE(id_cliente),
    ADD CONSTRAINT CK_ESTADO_ENDERECO
    CHECK (LENGTH(sg_estado) = 2),
    ADD CONSTRAINT CK_TIPO_LOGRADOURO
    CHECK (tp_logradouro IN ('R', 'A', 'P', 'V', 'B')),
    ADD CONSTRAINT CK_TIPO_ENDERECO
    CHECK (tp_endereco IN ('R', 'A', 'C')),
    ADD CONSTRAINT CK_NUM_RESID 
    CHECK (nr_residencia > 0);

CREATE TABLE T_SGP_PRODUTO(
    id_produto      INT             NOT NULL,
    nm_produto      VARCHAR(60)     NOT NULL,
    tp_produto      CHAR(1)         NOT NULL,
    ds_produto      VARCHAR(150)    NOT NULL,
    vl_produto      DECIMAL(6,3)    NOT NULL
);

ALTER TABLE T_SGP_PRODUTO
    ADD CONSTRAINT PK_PRODUTO
    PRIMARY KEY(id_produto),
    ADD CONSTRAINT UN_PRODUTO_NOME
    UNIQUE (nm_produto),
    ADD CONSTRAINT CK_PRODUTO_TIPO
    CHECK (tp_produto IN ('L', 'B', 'C')),
    ADD CONSTRAINT CK_PRODUTO_VALOR
    CHECK (vl_produto > 0);

CREATE TABLE T_SGP_PEDIDO(
    id_pedido       INT             NOT NULL,
    st_pedido       CHAR(1)         NOT NULL,
    nr_pedido       INT             NOT NULL,
    id_cliente      INT             NOT NULL,
    id_endereco     INT             NOT NULL
);

ALTER TABLE T_SGP_PEDIDO
    ADD CONSTRAINT PK_PEDIDO
    PRIMARY KEY(id_pedido),
    ADD CONSTRAINT UN_PEDIDO_NUMERO
    UNIQUE (nr_pedido),
    ADD CONSTRAINT FK_PEDIDO_CLIENTE 
    FOREIGN KEY(id_cliente)
        REFERENCES T_SGP_CLIENTE(id_cliente),
    ADD CONSTRAINT FK_PEDIDO_ENDERECO 
    FOREIGN KEY(id_endereco)
        REFERENCES T_SGP_ENDERECO(id_endereco),
    ADD CONSTRAINT CK_PEDIDO_STATUS
    CHECK (st_pedido IN ('A', 'E', 'P', 'C'));

CREATE TABLE T_SGP_ITEM_PEDIDO(
    id_item         INT             NOT NULL,
    qt_produto      INT             NOT NULL, 
    id_pedido       INT             NOT NULL,
    id_produto      INT             NOT NULL
);

ALTER TABLE T_SGP_ITEM_PEDIDO
    ADD CONSTRAINT PK_ITEM_PEDIDO
    PRIMARY KEY(id_item),
    ADD CONSTRAINT FK_ITEM_PEDIDO_PEDIDO 
    FOREIGN KEY(id_pedido)
        REFERENCES T_SGP_PEDIDO(id_pedido),
    ADD CONSTRAINT FK_ITEM_PEDIDO_PRODUTO 
    FOREIGN KEY(id_produto)
        REFERENCES T_SGP_PRODUTO(id_produto),
    ADD CONSTRAINT CK_ITEM_QTDE
    CHECK (qt_produto > 0);

CREATE TABLE T_SGP_AVALIACAO(
    id_avaliacao    INT             NOT NULL,
    id_pedido       INT             NOT NULL,
    id_cliente      INT             NOT NULL,
    ds_avaliacao    VARCHAR(255)    NULL,
    dt_avaliacao    DATETIME        NOT NULL,
    vl_nota         INT             NOT NULL
);

ALTER TABLE T_SGP_AVALIACAO
    ADD CONSTRAINT PK_AVALIACAO
    PRIMARY KEY(id_avaliacao),
    ADD CONSTRAINT FK_AVALIACAO_PEDIDO 
    FOREIGN KEY(id_pedido)
        REFERENCES T_SGP_PEDIDO(id_pedido),
    ADD CONSTRAINT FK_AVALIACAO_CLIENTE 
    FOREIGN KEY(id_cliente)
        REFERENCES T_SGP_CLIENTE(id_cliente),
    ADD CONSTRAINT CK_VALOR_NOTA
    CHECK (vl_nota >= 0);

CREATE TABLE T_SGP_FUNCIONARIO(
    id_funcionario  INT             NOT NULL,
    nm_funcionario  VARCHAR(100)    NOT NULL, 
    ds_cargo        VARCHAR(50)     NOT NULL, 
    nr_telefone     CHAR(15)        NOT NULL, 
    nr_cpf          CHAR(14)        NOT NULL, 
    dt_admissao     DATETIME        NOT NULL,
    dt_demissao     DATETIME        NULL,
    vl_salario      DECIMAL(6, 2)   NOT NULL
);

ALTER TABLE T_SGP_FUNCIONARIO
    ADD CONSTRAINT PK_FUNCIONARIO
    PRIMARY KEY(id_funcionario),
    ADD CONSTRAINT UN_FUNCIONARIO_CPF
    UNIQUE (nr_cpf),
    ADD CONSTRAINT UN_FUNCIONARIO_TEL
    UNIQUE (nr_telefone),
    ADD CONSTRAINT CK_DT_DEMISSAO
    CHECK (dt_demissao IS NULL OR dt_demissao >= dt_admissao),
    ADD CONSTRAINT CK_VL_SALARIO
    CHECK (vl_salario > 0);

CREATE TABLE T_SGP_PAGAMENTO(
    id_pagamento    INT             NOT NULL,
    id_cliente      INT             NOT NULL,
    id_pedido       INT             NOT NULL,
    tp_pagamento    VARCHAR(8)      NOT NULL,
    vl_total        DECIMAL(6,2)    NOT NULL, 
    st_pagamento    VARCHAR(9)      NOT NULL, 
    dt_pagamento    DATETIME        NOT NULL
);

ALTER TABLE T_SGP_PAGAMENTO
    ADD CONSTRAINT PK_PAGAMENTO
    PRIMARY KEY(id_pagamento),
    ADD CONSTRAINT FK_PAGAMENTO_CLIENTE 
    FOREIGN KEY(id_cliente)
        REFERENCES T_SGP_CLIENTE(id_cliente),
    ADD CONSTRAINT FK_PAGAMENTO_PEDIDO 
    FOREIGN KEY(id_pedido)
        REFERENCES T_SGP_PEDIDO(id_pedido),
    ADD CONSTRAINT CK_TP_PAGAMENTO
    CHECK (tp_pagamento IN ('Credito', 'Debito', 'Pix', 'Dinheiro')),
    ADD CONSTRAINT CK_VL_TOTAL
    CHECK (vl_total > 0),
    ADD CONSTRAINT CK_ST_PAGAMENTO
    CHECK (st_pagamento IN ('Paga', 'Não Pago'));

CREATE TABLE T_SGP_ENTREGA(
    id_entrega      INT             NOT NULL,
    id_funcionario  INT             NOT NULL,
    id_pedido       INT             NOT NULL,
    dt_entrega_prev DATETIME        NOT NULL, 
    dt_entrega_real DATETIME        NULL, 
    st_entrega      VARCHAR(9)      NOT NULL, 
    ob_entrega      VARCHAR(255)    NULL
);

ALTER TABLE T_SGP_ENTREGA
    ADD CONSTRAINT PK_ENTREGA
    PRIMARY KEY(id_entrega),
    ADD CONSTRAINT FK_ENTREGA_FUNCIONARIO 
    FOREIGN KEY(id_funcionario)
        REFERENCES T_SGP_FUNCIONARIO(id_funcionario),
    ADD CONSTRAINT FK_ENTREGA_PEDIDO     
    FOREIGN KEY(id_pedido)
        REFERENCES T_SGP_PEDIDO(id_pedido),
    ADD CONSTRAINT CK_ST_ENTREGA
    CHECK (st_entrega IN ('Saiu', 'Entregue'));
