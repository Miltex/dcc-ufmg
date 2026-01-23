package br.com.consultor.web;

import br.com.consultor.entity.Cliente;
import br.com.consultor.service.ClienteService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.net.URL;

@ExtendWith(ArquillianExtension.class)
@RunAsClient
public class ClienteIT {

    private static final String WEBAPP_SRC = "src/main/webapp";

    @Deployment
    public static WebArchive createDeployment() {
        File[] files = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importCompileAndRuntimeDependencies()
                .resolve()
                .withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class, "test-consultor.war")
                .addClass(Cliente.class)
                .addClass(ClienteService.class)
                .addClass(Bean.class)
                .addClass(ClienteController.class)
                .addAsLibraries(files)
                .addAsResource(new File("../consultor-ejb/src/main/resources/META-INF/persistence.xml"), "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/faces-config.xml"), "faces-config.xml")
                .addAsWebInfResource(new File(WEBAPP_SRC, "WEB-INF/web.xml"), "web.xml")
                .addAsWebResource(new File(WEBAPP_SRC, "index.xhtml"), "index.xhtml")
                .addAsWebResource(new File(WEBAPP_SRC, "cliente.xhtml"), "cliente.xhtml");
    }

    @Drone
    private WebDriver browser;

    @ArquillianResource
    private URL deploymentUrl;

    @Test
    public void testIndexPage() {
        browser.get(deploymentUrl.toExternalForm() + "index.xhtml");
        String title = browser.getTitle();
        Assertions.assertNotNull(title);
    }
}
