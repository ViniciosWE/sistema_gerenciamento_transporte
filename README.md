# 🚚 Sistema de Gerenciamento de Transporte Mobile

Sistema desenvolvido para gerenciamento e rastreamento de produtos em transporte, permitindo o controle de usuários, produtos e eventos logísticos em tempo real.

## 📌 Sobre o Projeto

O **Sistema de Gerenciamento de Transporte** tem como objetivo facilitar o acompanhamento de entregas, registrando cada etapa do processo logístico desde a origem até o destino final.

O sistema permite:
- Cadastro de usuários
- Cadastro de produtos com código de rastreio
- Registro de eventos (movimentações do produto)
- Atualização automática do status do produto

---

## ⚙️ Tecnologias Utilizadas
- Android Studio
- Java
- PHP - para usar banco externo
- MySQL 
- XML
- XAMPP (ambiente local)

---

## 🗄️ Estrutura do Banco de Dados

### 👤 Tabela: `usuario`

Armazena os dados dos usuários do sistema.

| Campo | Tipo | Descrição |
|------|------|----------|
| id | INT | Identificador único |
| nome | VARCHAR(30) | Nome do usuário |
| cpf | VARCHAR(11) | CPF (único) |
| senha | VARCHAR(30) | Senha |

---

### 📦 Tabela: `produto`

Responsável pelo cadastro dos produtos transportados.

| Campo | Tipo | Descrição |
|------|------|----------|
| id | INT | Identificador |
| codigorastreio | VARCHAR(50) | Código de rastreio |
| empresa | VARCHAR(100) | Empresa responsável |
| servico | VARCHAR(100) | Tipo de serviço |
| estatus | VARCHAR(50) | Status atual |
| origem | VARCHAR(150) | Local de origem |
| destino | VARCHAR(150) | Destino final |
| datadeprevistadeentraga | DATE | Data prevista |

---

### 📍 Tabela: `evento`

Registra todos os eventos relacionados ao produto.

| Campo | Tipo | Descrição |
|------|------|----------|
| id | INT | Identificador |
| produto_id | INT | Referência ao produto |
| data | DATETIME | Data do evento |
| local | VARCHAR(150) | Local do evento |
| status | VARCHAR(100) | Status do evento |
| detalhes | TEXT | Informações adicionais |

---

## 🔄 Trigger Automática

Sempre que um novo evento é inserido, o status do produto é atualizado automaticamente:

```sql
CREATE TRIGGER tg_evento_after_insert
AFTER INSERT ON evento
FOR EACH ROW
UPDATE produto
SET estatus = NEW.status
WHERE id = NEW.produto_id;
```
---

## Autor

* **Vinícios Weide Ebling** - [vinicioswe2005@gmail.com](mailto:vinicioswe2005@gmail.com)

