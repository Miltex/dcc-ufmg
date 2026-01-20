Estou recebendo o seguinte erro ao realizar o deploy no wildfly latest: {"WFLYCTL0080: Failed services" => {
"jboss.module.service.\"deployment.consultor-ear.ear.consultor-web.war\".main" => "WFLYSRV0179: Failed to load module: deployment.consultor-ear.ear.consultor-web.war
Caused by: org.jboss.modules.ModuleNotFoundException: jakarta.faces",
"jboss.module.service.\"deployment.consultor-ear.ear\".main" => "WFLYSRV0179: Failed to load module: deployment.consultor-ear.ear
Caused by: org.jboss.modules.ModuleNotFoundException: jakarta.faces",
"jboss.module.service.\"deployment.consultor-ear.ear.consultor-ejb.jar\".main" => "WFLYSRV0179: Failed to load module: deployment.consultor-ear.ear.consultor-ejb.jar
Caused by: org.jboss.modules.ModuleNotFoundException: jakarta.faces"
}}

Corrija o erro relatado ao realizar o deploy no servidor de aplicação wildfly.


Ainda continuo recebendo o seguinte erro ao realizar o deploy no wildfly latest: {"WFLYCTL0080: Failed services" => {
"jboss.module.service.\"deployment.consultor-ear.ear.consultor-web.war\".main" => "WFLYSRV0179: Failed to load module: deployment.consultor-ear.ear.consultor-web.war
Caused by: org.jboss.modules.ModuleNotFoundException: jakarta.faces",
"jboss.module.service.\"deployment.consultor-ear.ear\".main" => "WFLYSRV0179: Failed to load module: deployment.consultor-ear.ear
Caused by: org.jboss.modules.ModuleNotFoundException: jakarta.faces",
"jboss.module.service.\"deployment.consultor-ear.ear.consultor-ejb.jar\".main" => "WFLYSRV0179: Failed to load module: deployment.consultor-ear.ear.consultor-ejb.jar
Caused by: org.jboss.modules.ModuleNotFoundException: jakarta.faces"
}}

Corrija o erro relatado ao realizar o deploy no servidor de aplicação wildfly.


Agora está dando o seguinte erro ao realizar o deploy no wildfly latest: {"WFLYCTL0080: Failed services" => {"jboss.deployment.subunit.\"consultor-ear.ear\".\"consultor-web.war\".undertow-deployment.UndertowDeploymentInfoService" => "java.lang.ClassNotFoundException: jakarta.faces.webapp.FacesServlet from [Module \"deployment.consultor-ear.ear.consultor-web.war\" from Service Module Loader]
Caused by: java.lang.ClassNotFoundException: jakarta.faces.webapp.FacesServlet from [Module \"deployment.consultor-ear.ear.consultor-web.war\" from Service Module Loader]"}}
Corrija o erro relatado ao realizar o deploy no servidor de aplicação wildfly.