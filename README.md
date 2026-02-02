# Aplicação Jakarta EE 10 - Java 21 - Utilizando Gemini CLI

Este projeto é uma aplicação multi-módulos Jakarta EE 10 demonstrando uma funcionalidade CRUD (Criar, Ler, Atualizar, Deletar) básica para uma entidade `Cliente`. Ele aproveita as especificações modernas do Java EE, Maven para gerenciamento de build, Wildfly como servidor de aplicação, JPA para persistência e JSF com PrimeFaces para a interface de usuário web.

## Funcionalidades

*   **Gerenciamento de Clientes:** Crie, leia, atualize e exclua registros de clientes.
*   **Persistência de Dados:** Clientes são armazenados em um banco de dados relacional usando JPA.
*   **Interface Web:** Interface web amigável construída com JSF e PrimeFaces.
*   **Implantação Containerizada:** Pronto para implantação usando Docker e Wildfly.

## Tecnologias Utilizadas

*   **Java EE 10 (Jakarta EE):** Plataforma principal para aplicações corporativas.
*   **Maven:** Automação de build e gerenciamento de dependências.
*   **Wildfly Application Server:** Servidor de aplicação para implantar aplicações Jakarta EE.
*   **EJB (Enterprise JavaBeans):** Para lógica de negócios e gerenciamento de transações (`ClienteService`).
*   **JPA (Jakarta Persistence API):** Para mapeamento objeto-relacional e persistência de dados (com Hibernate como provedor).
*   **MariaDB:** Sistema de gerenciamento de banco de dados relacional.
*   **JSF (Jakarta Server Faces):** Framework de UI baseado em componentes para aplicações web.
*   **PrimeFaces:** Biblioteca de componentes ricos para JSF.
*   **CDI (Contexts and Dependency Injection):** Para injeção de dependência e gerenciamento de ciclo de vida (`ClienteController`).
*   **Docker:** Para conteinerizar a aplicação e seu ambiente.

## Estrutura do Projeto

O projeto está organizado em três módulos Maven principais:

*   **`consultor-pai` (Projeto Pai):**
    *   Define dependências comuns, gerenciamento de plugins e configurações de todo o projeto.
    *   Agrega os módulos `consultor-ear`, `consultor-ejb` e `consultor-web`.
*   **`consultor-ejb` (Módulo EJB):**
    *   Contém a lógica de negócios e o modelo de dados (Entidades JPA).
    *   `br.com.consultor.entity.Cliente`: A entidade JPA que representa um cliente.
    *   `br.com.consultor.service.ClienteService`: Um EJB `@Stateless` que fornece operações CRUD para entidades `Cliente`.
    *   `src/main/resources/META-INF/persistence.xml`: Configuração JPA para a unidade de persistência `consultor-pu`, usando transações JTA e um data source JTA `java:jboss/datasources/MyDS`.
*   **`consultor-web` (Módulo WAR):**
    *   Contém os componentes da camada web para a interface do usuário.
    *   `br.com.consultor.web.ClienteController`: Um bean CDI `@Named` e `@ViewScoped` que atua como o backing bean para a página JSF, interagindo com o EJB `ClienteService`.
    *   `src/main/webapp/cliente.xhtml`: A página JSF (usando componentes PrimeFaces) para exibir e gerenciar clientes.
    *   `src/main/webapp/WEB-INF/beans.xml`: Configuração CDI.
    *   `src/main/webapp/WEB-INF/web.xml`: Descritor de implantação de aplicação web.
*   **`consultor-ear` (Módulo EAR):**
    *   O Enterprise Archive que empacota `consultor-ejb.jar` e `consultor-web.war` juntos para implantação em um servidor de aplicação como o Wildfly.
    *   `src/main/application/META-INF/application.xml`: Define os módulos dentro do EAR e o context root da web (`/consultor`).
*   **`wildfly/Dockerfile`:**
    *   Define uma imagem Docker para o Wildfly.
    *   Instala o driver JDBC do MariaDB.
    *   Configura um data source JTA chamado `MyDS` apontando para um banco de dados MariaDB.
    *   Implanta a aplicação `consultor-ear.ear`.
*   **`docker-compose.yml`:**
    *   Orquestra a implantação de um serviço de banco de dados MariaDB e do servidor de aplicação Wildfly (construído a partir do `wildfly/Dockerfile`).

## Configuração e Build

1.  **Pré-requisitos:**
    *   Java Development Kit (JDK) 21 ou superior.
    *   Apache Maven 3.6.0 ou superior.
    *   Docker e Docker Compose (se você planeja usar a configuração conteinerizada).

2.  **Construa o Projeto:**
    Navegue até o diretório raiz do projeto (`consultor-pai`) e execute:
    ```bash
    mvn clean install
    ```
    Este comando irá compilar todos os módulos, executar testes e empacotar os arquivos `consultor-ejb.jar`, `consultor-web.war` e `consultor-ear.ear` em seus respectivos diretórios `target/`.

## Configuração do Banco de Dados

A aplicação espera um banco de dados MariaDB. O `persistence.xml` define uma unidade de persistência `consultor-pu` que procura por um data source JTA chamado `java:jboss/datasources/MyDS`.

Ao usar o Docker Compose, o `wildfly/Dockerfile` configura automaticamente este data source para se conectar ao serviço `mariadb-db` com as seguintes credenciais e banco de dados:

*   **Nome do Banco de Dados:** `crud_db`
*   **Usuário:** `crud_user`
*   **Senha:** `crud_password`
*   **Host:** `mariadb-db` (nome do serviço em `docker-compose.yml`)
*   **Porta:** `3306`

## Implantação

### 1. Usando Docker Compose (Recomendado)

A maneira mais fácil de fazer a aplicação funcionar é usando o Docker Compose, que configurará tanto o banco de dados MariaDB quanto o servidor de aplicação Wildfly com a aplicação implantada.

1.  Certifique-se de ter construído o projeto (`mvn clean install`).
2.  Navegue até o diretório raiz do projeto.
3.  Inicie os serviços:
    ```bash
    docker-compose up --build
    ```
    Este comando irá:
    *   Construir a imagem Docker `wildfly-app` baseada em `wildfly/Dockerfile`.
    *   Iniciar um contêiner MariaDB (`mariadb-db`).
    *   Iniciar um contêiner Wildfly (`wildfly-app`) com o `consultor-ear.ear` implantado.

### 2. Implantação Manual no Wildfly

Se você preferir implantar manualmente em um servidor Wildfly existente:

1.  Certifique-se de ter um servidor Wildfly 30+ em execução.
2.  Construa o projeto usando `mvn clean install`.
3.  Configure um data source MariaDB em seu servidor Wildfly com o nome JNDI `java:jboss/datasources/MyDS` e conecte-o à sua instância MariaDB.
    *   Você precisará instalar o driver JDBC do MariaDB como um módulo do Wildfly.
    *   Exemplo de comando CLI para configurar o data source (adapte conforme necessário para seu host/credenciais de DB):
        ```
        /subsystem=datasources/jdbc-driver=mariadb:add(driver-name=mariadb,driver-module-name=org.mariadb,driver-class-name=org.mariadb.jdbc.Driver)
        /subsystem=datasources/data-source=MyDS:add(jndi-name=java:jboss/datasources/MyDS,driver-name=mariadb,connection-url="jdbc:mariadb://localhost:3306/crud_db",user-name=crud_user,password=crud_password,enabled=true)
        ```
4.  Copie o arquivo `consultor-ear/target/consultor-ear.ear` gerado para o diretório `standalone/deployments/` do Wildfly. O Wildfly o implantará automaticamente.

## Uso

Assim que a aplicação estiver implantada e em execução (seja via Docker Compose ou implantação manual):

1.  Abra seu navegador web.
2.  Acesse a aplicação em: `http://localhost:8080/consultor/cliente.jsf` (se estiver executando localmente ou via Docker Compose, assumindo que a porta 8080 está exposta).

Você deverá ver a página "Cadastro de Clientes", onde pode adicionar, visualizar e gerenciar registros de clientes.


## Configs:

Para rodar o Sonarqube tem ajustar a utilização de memoria com o comando abaixo utilizando o Rancher Desktop por causa do ElasticSearch integrado.

```
rdctl shell sudo sysctl -w vm.max_map_count=262144
```

Antes de integrar com o sonar deve-se subir o ambiente com o comando abaixo:

```
cd sonarqube
docker compose up -d
```

Gerar token no sonar e setar a variavel de ambinte com o valor:

```
export SONAR_TOKEN="seu_token_aqui"
```

Integrar com o Sonarqube e enviar relatorios para a instancia do Sonarqube:

```
mvn sonar:sonar
```
