# Consultor Pai - Jakarta EE 10 CRUD Application

This project is a multi-module Jakarta EE 10 application demonstrating a basic CRUD (Create, Read, Update, Delete) functionality for a `Cliente` (Client) entity. It leverages modern Java EE specifications, Maven for build management, Wildfly as the application server, JPA for persistence, and JSF with PrimeFaces for the web user interface.

## Features

*   **Client Management:** Create, read, update, and delete client records.
*   **Data Persistence:** Clients are stored in a relational database using JPA.
*   **Web Interface:** User-friendly web interface built with JSF and PrimeFaces.
*   **Containerized Deployment:** Ready for deployment using Docker and Wildfly.

## Technologies Used

*   **Java EE 10 (Jakarta EE):** Core platform for enterprise applications.
*   **Maven:** Build automation and dependency management.
*   **Wildfly Application Server:** Application server for deploying Jakarta EE applications.
*   **EJB (Enterprise JavaBeans):** For business logic and transaction management (`ClienteService`).
*   **JPA (Jakarta Persistence API):** For object-relational mapping and data persistence (with Hibernate as provider).
*   **MariaDB:** Relational database management system.
*   **JSF (Jakarta Server Faces):** Component-based UI framework for web applications.
*   **PrimeFaces:** Rich component library for JSF.
*   **CDI (Contexts and Dependency Injection):** For dependency injection and lifecycle management (`ClienteController`).
*   **Docker:** For containerizing the application and its environment.

## Project Structure

The project is organized into three main Maven modules:

*   **`consultor-pai` (Parent Project):**
    *   Defines common dependencies, plugin management, and project-wide configurations.
    *   Aggregates `consultor-ear`, `consultor-ejb`, and `consultor-web` modules.
*   **`consultor-ejb` (EJB Module):**
    *   Contains the business logic and data model (JPA Entities).
    *   `br.com.consultor.entity.Cliente`: The JPA entity representing a client.
    *   `br.com.consultor.service.ClienteService`: A `@Stateless` EJB providing CRUD operations for `Cliente` entities.
    *   `src/main/resources/META-INF/persistence.xml`: JPA configuration for the `consultor-pu` persistence unit, using JTA transactions and a `java:jboss/datasources/MyDS` JTA data source.
*   **`consultor-web` (WAR Module):**
    *   Contains the web-tier components for the user interface.
    *   `br.com.consultor.web.ClienteController`: A `@Named` and `@ViewScoped` CDI bean that acts as the backing bean for the JSF page, interacting with the `ClienteService` EJB.
    *   `src/main/webapp/cliente.xhtml`: The JSF page (using PrimeFaces components) for displaying and managing clients.
    *   `src/main/webapp/WEB-INF/beans.xml`: CDI configuration.
    *   `src/main/webapp/WEB-INF/web.xml`: Web application deployment descriptor.
*   **`consultor-ear` (EAR Module):**
    *   The Enterprise Archive that packages `consultor-ejb.jar` and `consultor-web.war` together for deployment to an application server like Wildfly.
    *   `src/main/application/META-INF/application.xml`: Defines the modules within the EAR and the web context root (`/consultor`).
*   **`wildfly/Dockerfile`:**
    *   Defines a Docker image for Wildfly.
    *   Installs the MariaDB JDBC driver.
    *   Configures a JTA data source named `MyDS` pointing to a MariaDB database.
    *   Deploys the `consultor-ear.ear` application.
*   **`docker-compose.yml`:**
    *   Orchestrates the deployment of a MariaDB database service and the Wildfly application server (built from the `wildfly/Dockerfile`).

## Setup and Build

1.  **Prerequisites:**
    *   Java Development Kit (JDK) 11 or higher.
    *   Apache Maven 3.6.0 or higher.
    *   Docker and Docker Compose (if you plan to use the containerized setup).

2.  **Build the Project:**
    Navigate to the root directory of the project (`consultor-pai`) and run:
    ```bash
    mvn clean install
    ```
    This command will compile all modules, run tests, and package the `consultor-ejb.jar`, `consultor-web.war`, and `consultor-ear.ear` files into their respective `target/` directories.

## Database Configuration

The application expects a MariaDB database. The `persistence.xml` defines a persistence unit `consultor-pu` that looks for a JTA data source named `java:jboss/datasources/MyDS`.

When using Docker Compose, the `wildfly/Dockerfile` automatically configures this data source to connect to the `mariadb-db` service with the following credentials and database:

*   **Database Name:** `crud_db`
*   **User:** `crud_user`
*   **Password:** `crud_password`
*   **Host:** `mariadb-db` (service name in `docker-compose.yml`)
*   **Port:** `3306`

## Deployment

### 1. Using Docker Compose (Recommended)

The easiest way to get the application running is using Docker Compose, which will set up both the MariaDB database and the Wildfly application server with the deployed application.

1.  Ensure you have built the project (`mvn clean install`).
2.  Navigate to the root directory of the project.
3.  Start the services:
    ```bash
    docker-compose up --build
    ```
    This command will:
    *   Build the `wildfly-app` Docker image based on `wildfly/Dockerfile`.
    *   Start a MariaDB container (`mariadb-db`).
    *   Start a Wildfly container (`wildfly-app`) with the `consultor-ear.ear` deployed.

### 2. Manual Deployment to Wildfly

If you prefer to deploy manually to an existing Wildfly server:

1.  Ensure you have a running Wildfly 27+ server.
2.  Build the project using `mvn clean install`.
3.  Configure a MariaDB data source on your Wildfly server with the JNDI name `java:jboss/datasources/MyDS` and connect it to your MariaDB instance.
    *   You will need to install the MariaDB JDBC driver as a Wildfly module.
    *   Example CLI command for configuring datasource (adapt as needed for your DB host/credentials):
        ```
        /subsystem=datasources/jdbc-driver=mariadb:add(driver-name=mariadb,driver-module-name=org.mariadb,driver-class-name=org.mariadb.jdbc.Driver)
        /subsystem=datasources/data-source=MyDS:add(jndi-name=java:jboss/datasources/MyDS,driver-name=mariadb,connection-url="jdbc:mariadb://localhost:3306/crud_db",user-name=crud_user,password=crud_password,enabled=true)
        ```
4.  Copy the generated `consultor-ear/target/consultor-ear.ear` file to the Wildfly's `standalone/deployments/` directory. Wildfly will automatically deploy it.

## Usage

Once the application is deployed and running (either via Docker Compose or manual deployment):

1.  Open your web browser.
2.  Access the application at: `http://localhost:8080/consultor/cliente.jsf` (if running locally or via Docker Compose, assuming port 8080 is exposed).

You should see the "Cadastro de Clientes" (Client Registration) page, where you can add, view, and manage client records.
