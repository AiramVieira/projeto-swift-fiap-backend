# 🗄️ Oracle Database - Swift Backend

Documentação completa para instalação e configuração do Oracle Database no projeto Swift Backend.

---

## 🚀 Início Rápido

Siga estes 3 comandos para ter seu ambiente funcionando em minutos:

```powershell
.\scripts\setup-oracle-docker.ps1
.\scripts\run-schema.ps1
.\scripts\test-connection.ps1
```

**Detalhes:** [INSTALACAO_RAPIDA.md](INSTALACAO_RAPIDA.md)

---

## 📚 Índice de Documentação

### 📖 Guias de Instalação

| Documento | Descrição | Quando Usar |
|-----------|-----------|-------------|
| [**INSTALACAO_RAPIDA.md**](INSTALACAO_RAPIDA.md) | Comandos rápidos - 3 passos | Instalação expressa |
| [**GUIA_INSTALACAO_ORACLE.md**](GUIA_INSTALACAO_ORACLE.md) | Guia completo e detalhado | Primeira instalação ou problemas |
| [**CHECKLIST_INSTALACAO.md**](CHECKLIST_INSTALACAO.md) | Checklist passo a passo | Acompanhar progresso |

### 🛠️ Scripts de Automação

| Script | Descrição |
|--------|-----------|
| [**setup-oracle-docker.ps1**](scripts/setup-oracle-docker.ps1) | Instala Oracle via Docker |
| [**run-schema.ps1**](scripts/run-schema.ps1) | Executa o schema SQL |
| [**test-connection.ps1**](scripts/test-connection.ps1) | Testa a conexão |
| [**oracle-cli.ps1**](scripts/oracle-cli.ps1) | Abre SQLPlus |

**Documentação:** [scripts/README.md](scripts/README.md)

### 💾 Arquivos SQL

| Arquivo | Descrição |
|---------|-----------|
| [**oracle-schema.sql**](sql/oracle-schema.sql) | Schema completo do banco |
| [**queries-exemplo.sql**](sql/queries-exemplo.sql) | Queries de exemplo e testes |
| [**DIAGRAMA_DATABASE.md**](sql/DIAGRAMA_DATABASE.md) | Diagrama da estrutura |

---

## 🏗️ Estrutura do Banco de Dados

### 📊 14 Tabelas

**Usuários e Autenticação:**
- `usuario` - Dados principais
- `pessoa_fisica` - CPF e data nascimento
- `pessoa_juridica` - CNPJ e razão social
- `autenticacao` - Email e senha
- `endereco` - Endereços

**Produtos:**
- `categoria` - Categorias de produtos
- `produtos` - Catálogo de produtos
- `loja` - Lojas físicas/online
- `loja_produtos` - Relação loja ↔ produtos

**Carrinho:**
- `carrinho` - Carrinhos de compras
- `item_do_carrinho` - Items individuais
- `carrinho_items` - Relação carrinho ↔ items

**Vendas:**
- `vendas_comuns` - Registro de vendas
- `vendas_produtos` - Produtos vendidos

**Ver diagrama completo:** [sql/DIAGRAMA_DATABASE.md](sql/DIAGRAMA_DATABASE.md)

---

## 🔌 Configuração de Conexão

### application.properties

```properties
db.url=jdbc:oracle:thin:@localhost:1521/XE
db.username=system
db.password=SenhaForte123
db.driver=oracle.jdbc.OracleDriver
```

### Credenciais Padrão

| Propriedade | Valor |
|-------------|-------|
| **Host** | localhost |
| **Port** | 1521 |
| **SID** | XE |
| **Username** | system |
| **Password** | SenhaForte123 |
| **JDBC URL** | jdbc:oracle:thin:@localhost:1521/XE |

---

## 🧪 Testando a Instalação

### 1. Teste de Conexão Manual

```powershell
.\scripts\oracle-cli.ps1
```

```sql
SELECT COUNT(*) FROM categoria;
SELECT COUNT(*) FROM produtos;
EXIT;
```

### 2. Teste da Aplicação

```powershell
mvn clean compile
mvn exec:exec
```

```powershell
curl http://localhost:8080/health
curl http://localhost:8080/categorias
```

---

## 🛠️ Comandos Úteis

### Gerenciar Container Docker

```powershell
docker ps
docker start oracle-xe
docker stop oracle-xe
docker logs oracle-xe -f
```

### Executar Queries

```powershell
.\scripts\oracle-cli.ps1
@C:\Users\bluin\projects\projeto-swift-fiap-backend\sql\queries-exemplo.sql
```

### Backup do Banco

```powershell
docker exec oracle-xe sh -c "expdp system/SenhaForte123@XE directory=DATA_PUMP_DIR dumpfile=backup.dmp full=y"
docker cp oracle-xe:/opt/oracle/admin/XE/dpdump/backup.dmp ./
```

---

## 📊 Dados de Exemplo

Após executar o schema, o banco terá:

### 8 Categorias
- Eletrônicos
- Roupas e Acessórios
- Livros
- Alimentos e Bebidas
- Esportes e Fitness
- Casa e Decoração
- Beleza e Cuidados Pessoais
- Brinquedos e Jogos

### 2 Endereços
- Rua das Flores, 123 - Centro
- Av. Paulista, 1000 - Bela Vista

### 2 Produtos
- Smartphone Samsung Galaxy S23
- Notebook Dell Inspiron 15

**Ver queries de exemplo:** [sql/queries-exemplo.sql](sql/queries-exemplo.sql)

---

## ⚠️ Solução de Problemas

### Container não inicia

```powershell
docker logs oracle-xe
```

Verifique:
- Porta 1521 livre
- Memória RAM suficiente (4GB+)
- Docker Desktop rodando

### Aplicação não conecta

1. Teste manual:
```powershell
.\scripts\test-connection.ps1
```

2. Verifique `application.properties`:
   - URL, usuário e senha corretos
   - SID deve ser `XE`

3. Veja logs da aplicação:
```powershell
mvn exec:exec
```

### Erro "TNS:could not resolve"

- Verifique se o container está rodando: `docker ps`
- Confirme o SID: deve ser `XE`, não `orcl`
- Reinicie o container: `docker restart oracle-xe`

**Guia completo:** [GUIA_INSTALACAO_ORACLE.md](GUIA_INSTALACAO_ORACLE.md#troubleshooting)

---

## 📈 Próximos Passos

Após a instalação bem-sucedida:

1. **Explorar o Banco**
   - Abra SQL Developer: `choco install sqldeveloper -y`
   - Execute queries de exemplo: [sql/queries-exemplo.sql](sql/queries-exemplo.sql)

2. **Desenvolver a Aplicação**
   - Teste os endpoints REST
   - Implemente novos DAOs
   - Adicione validações

3. **Otimização**
   - Analise performance das queries
   - Adicione índices conforme necessário
   - Configure connection pool

4. **Produção**
   - Crie usuário específico (não usar `system`)
   - Configure backup automático
   - Ajuste parâmetros de memória

---

## 🔐 Segurança

### ⚠️ IMPORTANTE para Produção

**NÃO use em produção:**
- Usuário `system` (crie usuário específico)
- Senha `SenhaForte123` (use senha complexa)
- Senhas em texto claro no código

**Recomendações:**
1. Crie usuário específico para a aplicação
2. Use variáveis de ambiente para credenciais
3. Configure SSL/TLS para conexões
4. Implemente rotação de senhas
5. Use segredos gerenciados (Azure Key Vault, AWS Secrets, etc.)

**Exemplo de usuário específico:**
```sql
CREATE USER swift_app IDENTIFIED BY "S3nha@Compl3x@2024!";
GRANT CONNECT, RESOURCE TO swift_app;
GRANT UNLIMITED TABLESPACE TO swift_app;
```

---

## 🎓 Recursos de Aprendizado

### Documentação Oracle
- [Oracle Database Documentation](https://docs.oracle.com/en/database/)
- [Oracle XE 21c](https://docs.oracle.com/en/database/oracle/oracle-database/21/xeinw/)
- [SQL Language Reference](https://docs.oracle.com/en/database/oracle/oracle-database/21/sqlrf/)

### SQL Developer
- [Download SQL Developer](https://www.oracle.com/tools/downloads/sqldev-downloads.html)
- [SQL Developer Documentation](https://docs.oracle.com/en/database/oracle/sql-developer/)

### JDBC
- [Oracle JDBC Driver](https://docs.oracle.com/en/database/oracle/oracle-database/21/jjdbc/)
- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)

---

## 📝 Estrutura de Arquivos

```
projeto-swift-fiap-backend/
│
├── sql/
│   ├── oracle-schema.sql          # Schema completo
│   ├── queries-exemplo.sql        # Queries de teste
│   └── DIAGRAMA_DATABASE.md       # Diagrama visual
│
├── scripts/
│   ├── setup-oracle-docker.ps1    # Instalação
│   ├── run-schema.ps1             # Executar schema
│   ├── test-connection.ps1        # Testar conexão
│   ├── oracle-cli.ps1             # Abrir SQLPlus
│   └── README.md                  # Doc dos scripts
│
├── src/main/resources/
│   └── application.properties     # Config da aplicação
│
├── INSTALACAO_RAPIDA.md           # Guia rápido
├── GUIA_INSTALACAO_ORACLE.md      # Guia completo
├── CHECKLIST_INSTALACAO.md        # Checklist
└── README_ORACLE.md               # Este arquivo
```

---

## 🤝 Contribuindo

Encontrou algum problema ou tem sugestões?

1. Documente o problema
2. Verifique a seção de troubleshooting
3. Consulte os logs: `docker logs oracle-xe`
4. Reporte com detalhes (versão, SO, logs)

---

## 📄 Licença

Este projeto utiliza Oracle Database Express Edition (XE), que é gratuito para uso em desenvolvimento, distribuição e produção.

**Oracle XE License:** [Oracle Technology Network License Agreement](https://www.oracle.com/downloads/licenses/database-11g-express-license.html)

---

## ✅ Status do Projeto

- [x] Script de instalação automatizado
- [x] Schema Oracle completo
- [x] Dados de exemplo
- [x] Scripts de gerenciamento
- [x] Documentação completa
- [x] Guia de troubleshooting
- [x] Queries de exemplo

---

## 📞 Suporte

**Documentação Interna:**
- Instalação Rápida: [INSTALACAO_RAPIDA.md](INSTALACAO_RAPIDA.md)
- Guia Completo: [GUIA_INSTALACAO_ORACLE.md](GUIA_INSTALACAO_ORACLE.md)
- Checklist: [CHECKLIST_INSTALACAO.md](CHECKLIST_INSTALACAO.md)

**Recursos Externos:**
- Oracle Community: https://community.oracle.com/
- Stack Overflow: https://stackoverflow.com/questions/tagged/oracle
- Oracle Forums: https://forums.oracle.com/

---

**🎉 Pronto para começar? Execute:** `.\scripts\setup-oracle-docker.ps1`

