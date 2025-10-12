-- Script de criação do schema para Oracle Database
-- Baseado no dump MySQL original

-- Tabela categoria
CREATE TABLE categoria (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  descricao VARCHAR2(255) NOT NULL
);

-- Tabela endereco
CREATE TABLE endereco (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  descricao VARCHAR2(255) NOT NULL,
  cep VARCHAR2(10) NOT NULL,
  latitude NUMBER,
  longitude NUMBER
);

-- Tabela usuario
CREATE TABLE usuario (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nome VARCHAR2(100) NOT NULL,
  sobrenome VARCHAR2(100) NOT NULL,
  fk_endereco NUMBER NOT NULL,
  telephone VARCHAR2(15),
  tipo VARCHAR2(2) NOT NULL CHECK (tipo IN ('PF', 'PJ')),
  CONSTRAINT fk_usuario_endereco FOREIGN KEY (fk_endereco) REFERENCES endereco(id)
);

CREATE INDEX idx_usuario_endereco ON usuario(fk_endereco);

-- Tabela pessoa_fisica
CREATE TABLE pessoa_fisica (
  fk_usuario_id NUMBER PRIMARY KEY,
  data_nascimento DATE NOT NULL,
  CONSTRAINT fk_pf_usuario FOREIGN KEY (fk_usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela pessoa_juridica
CREATE TABLE pessoa_juridica (
  fk_usuario_id NUMBER PRIMARY KEY,
  razao_social VARCHAR2(255) NOT NULL,
  nome_fantasia VARCHAR2(255),
  cnpj VARCHAR2(18) NOT NULL,
  inscricao_estadual VARCHAR2(18),
  isento NUMBER(1) DEFAULT 0,
  CONSTRAINT fk_pj_usuario FOREIGN KEY (fk_usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela autenticacao
CREATE TABLE autenticacao (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  email VARCHAR2(255) NOT NULL UNIQUE,
  senha VARCHAR2(255) NOT NULL,
  fk_usuario_id NUMBER NOT NULL,
  CONSTRAINT fk_auth_usuario FOREIGN KEY (fk_usuario_id) REFERENCES usuario(id)
);

CREATE INDEX idx_autenticacao_usuario ON autenticacao(fk_usuario_id);

-- Tabela produtos
CREATE TABLE produtos (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  nome VARCHAR2(255) NOT NULL,
  fk_categoria_id NUMBER NOT NULL,
  preco NUMBER(10,2) NOT NULL,
  promocao NUMBER(5),
  estoque NUMBER(5) DEFAULT 0 NOT NULL,
  imagem CLOB,
  descricao CLOB,
  desconto NUMBER(5),
  CONSTRAINT fk_produtos_categoria FOREIGN KEY (fk_categoria_id) REFERENCES categoria(id)
);

CREATE INDEX idx_produtos_categoria ON produtos(fk_categoria_id);

-- Tabela loja
CREATE TABLE loja (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  fk_endereco_id NUMBER NOT NULL,
  CONSTRAINT fk_loja_endereco FOREIGN KEY (fk_endereco_id) REFERENCES endereco(id)
);

CREATE INDEX idx_loja_endereco ON loja(fk_endereco_id);

-- Tabela loja_produtos (relacionamento N:N)
CREATE TABLE loja_produtos (
  fk_loja_id NUMBER NOT NULL,
  fk_produto_id NUMBER NOT NULL,
  PRIMARY KEY (fk_loja_id, fk_produto_id),
  CONSTRAINT fk_loja_produtos_loja FOREIGN KEY (fk_loja_id) REFERENCES loja(id),
  CONSTRAINT fk_loja_produtos_produto FOREIGN KEY (fk_produto_id) REFERENCES produtos(id)
);

-- Tabela carrinho
CREATE TABLE carrinho (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  fk_usuario_id NUMBER NOT NULL,
  CONSTRAINT fk_carrinho_usuario FOREIGN KEY (fk_usuario_id) REFERENCES usuario(id)
);

-- Tabela item_do_carrinho
CREATE TABLE item_do_carrinho (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  quantidade NUMBER(5) DEFAULT 1 NOT NULL,
  fk_produto_id NUMBER NOT NULL,
  CONSTRAINT fk_item_produto FOREIGN KEY (fk_produto_id) REFERENCES produtos(id)
);

-- Tabela carrinho_items (relacionamento N:N)
CREATE TABLE carrinho_items (
  fk_carrinho_id NUMBER NOT NULL,
  fk_item_carrinho_id NUMBER NOT NULL,
  PRIMARY KEY (fk_carrinho_id, fk_item_carrinho_id),
  CONSTRAINT fk_carrinho_items_carrinho FOREIGN KEY (fk_carrinho_id) REFERENCES carrinho(id),
  CONSTRAINT fk_carrinho_items_item FOREIGN KEY (fk_item_carrinho_id) REFERENCES item_do_carrinho(id)
);

-- Tabela vendas_comuns
CREATE TABLE vendas_comuns (
  id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  data_venda TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fk_usuario_id NUMBER NOT NULL,
  CONSTRAINT fk_vendas_usuario FOREIGN KEY (fk_usuario_id) REFERENCES usuario(id)
);

CREATE INDEX idx_vendas_usuario ON vendas_comuns(fk_usuario_id);

-- Tabela vendas_produtos (relacionamento N:N)
CREATE TABLE vendas_produtos (
  fk_venda_id NUMBER NOT NULL,
  fk_produto_id NUMBER NOT NULL,
  quantidade NUMBER(5) DEFAULT 1 NOT NULL,
  preco_unitario_venda NUMBER(10,2) NOT NULL,
  PRIMARY KEY (fk_venda_id, fk_produto_id),
  CONSTRAINT fk_vendas_produtos_venda FOREIGN KEY (fk_venda_id) REFERENCES vendas_comuns(id),
  CONSTRAINT fk_vendas_produtos_produto FOREIGN KEY (fk_produto_id) REFERENCES produtos(id)
);

-- Dados de exemplo
-- Categorias
INSERT INTO categoria (descricao) VALUES ('Eletrônicos');
INSERT INTO categoria (descricao) VALUES ('Roupas e Acessórios');
INSERT INTO categoria (descricao) VALUES ('Livros');
INSERT INTO categoria (descricao) VALUES ('Alimentos e Bebidas');
INSERT INTO categoria (descricao) VALUES ('Esportes e Fitness');
INSERT INTO categoria (descricao) VALUES ('Casa e Decoração');
INSERT INTO categoria (descricao) VALUES ('Beleza e Cuidados Pessoais');
INSERT INTO categoria (descricao) VALUES ('Brinquedos e Jogos');

-- Endereços
INSERT INTO endereco (descricao, cep, latitude, longitude) 
VALUES ('Rua das Flores, 123 - Centro', '01234-567', -23550520, -46633308);

INSERT INTO endereco (descricao, cep, latitude, longitude) 
VALUES ('Av. Paulista, 1000 - Bela Vista', '01310-100', -23561414, -46656667);

-- Produtos de exemplo
INSERT INTO produtos (nome, fk_categoria_id, preco, promocao, estoque, descricao, desconto) 
VALUES ('Smartphone Samsung Galaxy S23', 1, 3499.99, 10, 50, 'Smartphone top de linha com câmera de alta resolução', 5);

INSERT INTO produtos (nome, fk_categoria_id, preco, estoque, descricao) 
VALUES ('Notebook Dell Inspiron 15', 1, 4299.00, 30, 'Notebook com processador Intel i7, 16GB RAM e SSD 512GB');

COMMIT;

