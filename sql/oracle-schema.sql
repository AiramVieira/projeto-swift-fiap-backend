-- ==========================================
--   CRIAÇÃO DAS TABELAS - ORDEM CORRETA
-- ==========================================

-- 1. AUTENTICAÇÃO
CREATE TABLE t_fin_autenticacao (
    cd_autenticacao   NUMBER(5)       NOT NULL,
    cd_usuario        NUMBER(5)       NOT NULL,
    email             VARCHAR2(50)    NOT NULL,
    senha             VARCHAR2(50)    NOT NULL,
    status_conta      VARCHAR2(20)    NOT NULL,
    CONSTRAINT pk_t_fin_autenticacao PRIMARY KEY (cd_autenticacao)
);

-- 2. USUÁRIO
CREATE TABLE t_fin_usuario (
    cd_usuario       NUMBER(5)       NOT NULL,
    cd_autenticacao  NUMBER(5),
    nm_usuario       VARCHAR2(60)    NOT NULL,
    dt_nascimento    DATE            NOT NULL,
    nr_telefone      VARCHAR2(20),
    ativo            CHAR(1),
    vl_saldo         NUMBER(12,2)    DEFAULT 0 NOT NULL,
    CONSTRAINT pk_t_fin_usuario PRIMARY KEY (cd_usuario),
    CONSTRAINT fk_usuario_autenticacao FOREIGN KEY (cd_autenticacao)
        REFERENCES t_fin_autenticacao (cd_autenticacao)
);

-- 3. CATEGORIA
CREATE TABLE t_fin_categoria (
    cd_categoria   NUMBER(5)        NOT NULL,
    nm_categoria   VARCHAR2(30)     NOT NULL,
    tp_categoria   CHAR(1)          NOT NULL,
    CONSTRAINT pk_t_fin_categoria PRIMARY KEY (cd_categoria)
);

-- 4. TIPOS DE INVESTIMENTO
CREATE TABLE t_fin_tipos_investimento (
    cd_tipo   NUMBER(5)       NOT NULL,
    risco     CHAR(1)         NOT NULL,
    nm_tipo   VARCHAR2(40)    NOT NULL,
    CONSTRAINT pk_t_fin_tipos_investimento PRIMARY KEY (cd_tipo)
);

-- 5. BANCO USUÁRIO
CREATE TABLE t_fin_banco_usuario (
    cd_usuario   NUMBER(5)    NOT NULL,
    nr_agencia   NUMBER(5)    NOT NULL,
    nr_conta     NUMBER(12)   NOT NULL,
    cpf          NUMBER(11)   NOT NULL,
    cd_banco     NUMBER(3)    NOT NULL,
    CONSTRAINT pk_t_fin_banco_usuario PRIMARY KEY (cd_usuario),
    CONSTRAINT fk_banco_usuario FOREIGN KEY (cd_usuario)
        REFERENCES t_fin_usuario (cd_usuario)
);

-- 6. GASTOS
CREATE TABLE t_fin_gastos (
    cd_gasto       NUMBER(5)        NOT NULL,
    cd_usuario     NUMBER(5)        NOT NULL,
    cd_categoria   NUMBER(5)        NOT NULL,
    nm_gasto       VARCHAR2(30)     NOT NULL,
    dt_gasto       DATE             NOT NULL,
    vl_gasto       NUMBER(12,2)     NOT NULL,
    ds_gasto       VARCHAR2(100),
    CONSTRAINT pk_t_fin_gastos PRIMARY KEY (cd_gasto),
    CONSTRAINT fk_gastos_usuario FOREIGN KEY (cd_usuario)
        REFERENCES t_fin_usuario (cd_usuario),
    CONSTRAINT fk_gastos_categoria FOREIGN KEY (cd_categoria)
        REFERENCES t_fin_categoria (cd_categoria)
);

-- 7. RECEBIMENTOS
CREATE TABLE t_fin_recebimentos (
    cd_recebimento   NUMBER(5)       NOT NULL,
    cd_usuario       NUMBER(5)       NOT NULL,
    cd_categoria     NUMBER(5)       NOT NULL,
    nm_recebimento   VARCHAR2(30)    NOT NULL,
    dt_recebimento   DATE            NOT NULL,
    vl_recebimento   NUMBER(12,2)    NOT NULL,
    ds_recebimento   VARCHAR2(100),
    CONSTRAINT pk_t_fin_recebimentos PRIMARY KEY (cd_recebimento),
    CONSTRAINT fk_recebimentos_usuario FOREIGN KEY (cd_usuario)
        REFERENCES t_fin_usuario (cd_usuario),
    CONSTRAINT fk_recebimentos_categoria FOREIGN KEY (cd_categoria)
        REFERENCES t_fin_categoria (cd_categoria)
);

-- 8. INVESTIMENTO
CREATE TABLE t_fin_investimento (
    cd_investimento         NUMBER(5)       NOT NULL,
    cd_usuario              NUMBER(5)       NOT NULL,
    cd_tipo                 NUMBER(5)       NOT NULL,
    vl_investimento         NUMBER(12,2)    NOT NULL,
    dt_investimento         DATE            NOT NULL,
    rentabilidade_estimada  NUMBER(12,2),
    dt_vencimento           DATE,
    CONSTRAINT pk_t_fin_investimento PRIMARY KEY (cd_investimento),
    CONSTRAINT fk_investimento_usuario FOREIGN KEY (cd_usuario)
        REFERENCES t_fin_usuario (cd_usuario),
    CONSTRAINT fk_investimento_tipo FOREIGN KEY (cd_tipo)
        REFERENCES t_fin_tipos_investimento (cd_tipo)
);
