# Projeto de Gestão Financeira

Este projeto é uma API de Gestão Financeira desenvolvida com Spring Boot, projetada para gerenciar categorias, transações financeiras, orçamentos e relatórios financeiros. Ele também inclui autenticação e autorização usando JWT, bem como integração com PostgreSQL e Flyway para gerenciamento de banco de dados.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.3**
- **PostgreSQL**
- **Flyway**
- **Spring Security com JWT**
- **Thymeleaf**
- **Lombok**
- **OpenAPI/Swagger**

## Estrutura do Projeto

O projeto está dividido nas seguintes camadas principais:

### Modelos (Entidades)

- **Categoria**: Representa as categorias das transações financeiras, que podem ser de diferentes tipos (`TipoCategoria`).
- **Orçamento**: Define o orçamento para uma categoria específica dentro de um determinado plano orçamentário.
- **Plano Orçamentário**: Agrupa diferentes orçamentos, permitindo o controle financeiro por período.
- **Relatório Financeiro**: Gera relatórios baseados em transações e orçamentos.
- **Transação Financeira**: Registra transações financeiras, categorizadas por `Categoria`.
- **Usuário**: Representa os usuários da aplicação, com seus respectivos papéis (`Role`).

### Repositórios

- **CategoriaRepository**: Interface para operações de CRUD com `Categoria`.
- **OrcamentoRepository**: Interface para operações de CRUD com `Orçamento`.
- **PlanoOrcamentarioRepository**: Interface para operações de CRUD com `Plano Orçamentário`.
- **RelatorioFinanceiroRepository**: Interface para operações de CRUD com `Relatório Financeiro`.
- **TransacaoFinanceiraRepository**: Interface para operações de CRUD com `Transação Financeira`.
- **UsuarioRepository**: Interface para operações de CRUD com `Usuário`.

### Serviços

- **CategoriaService**: Contém a lógica de negócios para manipulação de categorias.
- **OrcamentoService**: Contém a lógica de negócios para manipulação de orçamentos.
- **PlanoOrcamentarioService**: Contém a lógica de negócios para manipulação de planos orçamentários.
- **RelatorioFinanceiroService**: Contém a lógica de negócios para geração de relatórios financeiros.
- **TransacaoFinanceiraService**: Contém a lógica de negócios para manipulação de transações financeiras.
- **UsuarioService**: Contém a lógica de negócios para manipulação de usuários.
- **UserDetailsService**: Implementa a lógica para carregar detalhes do usuário, necessária para autenticação.

### Exceções e Manipuladores

- **BusinessRuleException**: Exceção customizada para regras de negócios.
- **GlobalExceptionHandler**: Manipulador global de exceções.
- **ResourceNotFoundException**: Exceção lançada quando um recurso não é encontrado.

### Segurança

- **SecurityConfig**: Configurações de segurança para a aplicação.
- **CustomUserDetails**: Implementa os detalhes do usuário customizados para o Spring Security.
- **JwtAuthenticationEntryPoint**: Configura o ponto de entrada para autenticação JWT.
- **JwtAuthenticationFilter**: Filtro para autenticação de tokens JWT.
- **JwtTokenProvider**: Provedor de tokens JWT.
- **ValidCPF**: Validador customizado para CPF.
- **ValidPassword**: Validador customizado para senhas.

### Controladores

- **AuthController**: Controlador responsável pela autenticação e autorização.
- **CategoriaController**: Controlador para operações com categorias.
- **OrcamentoController**: Controlador para operações com orçamentos.
- **PlanoOrcamentoController**: Controlador para operações com planos orçamentários.
- **RelatorioFinanceiroController**: Controlador para geração de relatórios financeiros.
- **TransacaoFinanceiraController**: Controlador para operações com transações financeiras.
- **UsuarioController**: Controlador para operações com usuários.

### Configurações

- **application.properties**: Contém as configurações da aplicação, como detalhes de conexão com o banco de dados.
- **pom.xml**: Configurações do Maven, incluindo as dependências do projeto.

## Configuração do Ambiente de Desenvolvimento

1. **Pré-requisitos**:
    - JDK 21 instalado.
    - PostgreSQL configurado.
    - Maven instalado.

2. **Configuração do Banco de Dados**:
    - Certifique-se de que o PostgreSQL esteja rodando e configurado conforme as informações em `application.properties`.
    - Flyway será utilizado para gerenciar as migrações do banco de dados.

3. **Build e Execução**:
    - Execute o comando `mvn clean install` para construir o projeto.
    - Inicie a aplicação com o comando `mvn spring-boot:run`.

4. **Documentação da API**:
    - A documentação da API está disponível através do Swagger em `http://localhost:8080/swagger-ui.html`.

## Desenvolvido por

[<img align="left" height="84px" src="https://avatars.githubusercontent.com/LucasFelip?size=64">](https://github.com/LucasFelip)
**Lucas Ferreira**
