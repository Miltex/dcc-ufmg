## Configuração de Testes de Integração Automatizados
Gerenciar o lifecycle através do ShrinkWrap e utilzar as extensões drone e graphene para testes funcionais de interfaces web, 
integrando o Selenium ao ciclo de vida do Arquillian. Realizar configurações necessárias ao arquivos de configurações como o pom.xml. 
Executar testes dentro do servidor de aplicação para validar injeção de dependências, tais como: CDI, EJBs e recursos JNDI reais. 
Convencionar os nomes das classes de testes de integração com o sufixo, exemplo, nome da classe com o final *IT.java 
Arquillian, por realizar testes de integração, deve ser gerenciado pelo maven-failsafe-plugin, que busca as classes com o 
sufixo IT durante a fase integration-test do ciclo de vida. As classes de testes de integrações devem estar no 
diretorio src/test/java. Embora não seja uma regra técnica rígida para a classe, é uma convenção forte que o método anotado
com @Deployment tenha um nome descritivo. Os testes devem ser executados quando rodar o comando mvn test após o 
ambiente conteinerizado subir e após a construção da imagem do wildfly.
No projeto tem um dockerfile que constroi um container com a imagem wildfly:latest-jdk11. Temos uma stack (docker-compose) necessaria, com o
banco de dados mariadb, phpmyadmin e o wildfly. Antes de rodar os testes de integração temos que subir esse ambinete containerizado.
O arquillian deverar rodar no ambiente conteinerizado de forma apartada.
