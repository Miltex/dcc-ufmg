# Projeto: Sistema de Gestão Enterprise (Jakarta EE 10)

## Contexto Técnico para o Desenvolvedor
Sou um desenvolvedor Java Sênior. Numca executar nenhum comandos git sem a supervisão do operador.
Este é um projeto jakarta Enterprise Edition 10 focado em alta escalabilidade e conformidade com os padrões Jakarta EE. 
O desenvolvedor é um perfil **Senior**, portanto, priorize soluções que utilizem design patterns (SOLID, Clean Code), performance de memória e segurança.
Numca realizar operações no git sem a revisão do operador. Nunca realizar commits automaticos.
## Stack Tecnológica
- **Linguagem:** Java 21
- **Framework:** Jakarta EE 10 (Full Profile)
- **Servidor de Aplicação:** WildFly 30+ ou Payara 6
- **Persistência:** JPA 3.1 com Hibernate como provedor.
- **Injeção de Dependência:** CDI 4.0.
- **APIs:** Jakarta RESTful Web Services (JAX-RS), Jakarta Bean Validation, Jakarta Security.
- **Banco de Dados:** PostgreSQL 15.
- **Build Tool:** Maven 3.9+.

## Arquitetura e Padrões
- **Camadas:** Controller (JAX-RS Resource) -> Service (Stateless EJB ou CDI Bean) -> Repository (JPA).
- **DTOs:** Obrigatório o uso de Record types para transferência de dados entre camadas.
- **Tratamento de Exceções:** Implementado via `ExceptionMapper` global para garantir respostas HTTP semânticas.
- **Segurança:** Implementação de JWT via MicroProfile JWT Auth.

## Diretrizes de Resposta
1.  **Foco em Performance:** Ao sugerir queries JPA, priorize `NamedQueries` e evite o problema N+1.
2.  **Boilerplate Zero:** Use as anotações padrão do Jakarta EE para evitar código repetitivo.
3.  **Escalabilidade:** Considere o estado da aplicação (Stateful vs Stateless) ao propor soluções de negócio.
4.  **Testabilidade:** Sugira código que facilite a criação de testes de integração com Testcontainers ou Arquillian.

## Comandos Úteis
- Use `@src/main/java` para ler a lógica de negócio.
- Use `@src/main/resources/META-INF/persistence.xml` para entender o mapeamento do banco.
- Use `@pom.xml` para verificar versões de bibliotecas e dependências.
