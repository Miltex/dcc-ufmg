package br.com.consultor.web.acceptation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assumptions;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Classe base para testes de aceitação com Selenium WebDriver.
 * Configura o driver e captura screenshots automaticamente.
 */
public abstract class AbstractAcceptanceTest {

    protected WebDriver driver;
    
    // URL base da aplicação (pode ser sobrescrita via propriedade do sistema)
    protected String baseUrl = System.getProperty("base.url", "http://localhost:8080/consultor");

    @BeforeEach
    public void setup() {
        try (Socket socket = new Socket("localhost", 8080)) {
            // Servidor online
        } catch (IOException e) {
            Assumptions.assumeTrue(false, "Servidor não está rodando na porta 8080. Pulando teste de aceitação.");
        }

        WebDriverManager.chromedriver().setup();
        // Selenium Manager (incluído no Selenium 4.6+) gerencia automaticamente o driver
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        
        // Comente a linha abaixo para ver o navegador abrindo
        // options.addArguments("--headless=new"); 
        
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");

        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Extension para capturar screenshot ao final de cada teste (sucesso ou falha).
     */
    @RegisterExtension
    public final TestWatcher screenshotWatcher = new TestWatcher() {
        @Override
        public void testSuccessful(ExtensionContext context) {
            captureScreenshot(context, "SUCCESS");
        }

        @Override
        public void testFailed(ExtensionContext context, Throwable cause) {
            captureScreenshot(context, "FAILED");
        }
        
        @Override
        public void testAborted(ExtensionContext context, Throwable cause) {
            captureScreenshot(context, "ABORTED");
        }
    };

    protected void captureScreenshot(ExtensionContext context, String status) {
        if (driver instanceof TakesScreenshot) {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String testName = context.getDisplayName().replace(" ", "_").replace("/", "-");
            String fileName = String.format("%s_%s_%s.png", timestamp, status, testName);
            
            Path destPath = Paths.get("target/screenshots", context.getRequiredTestClass().getSimpleName(), fileName);
            
            try {
                Files.createDirectories(destPath.getParent());
                Files.copy(srcFile.toPath(), destPath);
                System.out.println("Screenshot salvo em: " + destPath.toAbsolutePath());
            } catch (IOException e) {
                System.err.println("Erro ao salvar screenshot: " + e.getMessage());
            }
        }
    }
    
    /**
     * Método auxiliar para tirar screenshot manual durante o teste
     */
    protected void takeSnapshot(String stepName) {
        if (driver instanceof TakesScreenshot) {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = String.format("%s_STEP_%s.png", timestamp, stepName);
             Path destPath = Paths.get("target/screenshots", this.getClass().getSimpleName(), fileName);
            
            try {
                Files.createDirectories(destPath.getParent());
                Files.copy(srcFile.toPath(), destPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
