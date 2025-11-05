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

-- ==========================================
--   SEQUÊNCIAS PARA AUTO-INCREMENTO
-- ==========================================

CREATE SEQUENCE seq_autenticacao START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_usuario START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_categoria START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tipo_investimento START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_gasto START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_recebimento START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_investimento START WITH 1 INCREMENT BY 1;

-- ==========================================
--   INSERTS DE DADOS DE EXEMPLO
-- ==========================================

-- INSERTS PARA t_fin_categoria
insert into t_fin_categoria (cd_categoria, nm_categoria, tp_categoria)
values (1, 'Recebimento', 1);
insert into t_fin_categoria (cd_categoria, nm_categoria, tp_categoria)
values (2, 'Pagamento', 2);

-- INSERTS PARA t_fin_gastos (cd_categoria = 2 - Pagamento)
INSERT INTO t_fin_gastos (cd_gasto, cd_usuario, cd_categoria, nm_gasto, dt_gasto, vl_gasto, ds_gasto)
VALUES (1, 1, 2, 'Supermercado', TO_DATE('2024-01-15', 'YYYY-MM-DD'), 350.50, 'Compra mensal de alimentos');

INSERT INTO t_fin_gastos (cd_gasto, cd_usuario, cd_categoria, nm_gasto, dt_gasto, vl_gasto, ds_gasto)
VALUES (2, 1, 2, 'Aluguel', TO_DATE('2024-01-05', 'YYYY-MM-DD'), 1200.00, 'Pagamento mensal do aluguel');

INSERT INTO t_fin_gastos (cd_gasto, cd_usuario, cd_categoria, nm_gasto, dt_gasto, vl_gasto, ds_gasto)
VALUES (3, 1, 2, 'Combustível', TO_DATE('2024-01-20', 'YYYY-MM-DD'), 280.75, 'Abastecimento do veículo');

INSERT INTO t_fin_gastos (cd_gasto, cd_usuario, cd_categoria, nm_gasto, dt_gasto, vl_gasto, ds_gasto)
VALUES (4, 1, 2, 'Restaurante', TO_DATE('2024-01-18', 'YYYY-MM-DD'), 85.00, 'Jantar com amigos');

INSERT INTO t_fin_gastos (cd_gasto, cd_usuario, cd_categoria, nm_gasto, dt_gasto, vl_gasto, ds_gasto)
VALUES (5, 1, 2, 'Farmácia', TO_DATE('2024-01-22', 'YYYY-MM-DD'), 120.30, 'Medicamentos e produtos de higiene');

-- INSERTS PARA t_fin_recebimentos (cd_categoria = 1 - Recebimento)
INSERT INTO t_fin_recebimentos (cd_recebimento, cd_usuario, cd_categoria, nm_recebimento, dt_recebimento, vl_recebimento, ds_recebimento)
VALUES (1, 1, 1, 'Salário', TO_DATE('2024-01-05', 'YYYY-MM-DD'), 5000.00, 'Salário mensal');

INSERT INTO t_fin_recebimentos (cd_recebimento, cd_usuario, cd_categoria, nm_recebimento, dt_recebimento, vl_recebimento, ds_recebimento)
VALUES (2, 1, 1, 'Freelance', TO_DATE('2024-01-12', 'YYYY-MM-DD'), 800.00, 'Projeto de desenvolvimento');

INSERT INTO t_fin_recebimentos (cd_recebimento, cd_usuario, cd_categoria, nm_recebimento, dt_recebimento, vl_recebimento, ds_recebimento)
VALUES (3, 1, 1, 'Venda Online', TO_DATE('2024-01-19', 'YYYY-MM-DD'), 250.00, 'Venda de produtos usados');

INSERT INTO t_fin_recebimentos (cd_recebimento, cd_usuario, cd_categoria, nm_recebimento, dt_recebimento, vl_recebimento, ds_recebimento)
VALUES (4, 1, 1, 'Reembolso', TO_DATE('2024-01-25', 'YYYY-MM-DD'), 150.00, 'Reembolso de despesas');

INSERT INTO t_fin_recebimentos (cd_recebimento, cd_usuario, cd_categoria, nm_recebimento, dt_recebimento, vl_recebimento, ds_recebimento)
VALUES (5, 1, 1, 'Aluguel Recebido', TO_DATE('2024-01-10', 'YYYY-MM-DD'), 2000.00, 'Aluguel de imóvel alugado');