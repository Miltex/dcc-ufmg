package br.com.consultor.web.acceptation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ClienteAcceptationTest extends AbstractAcceptanceTest {

    @Test
    public void deveInteragirComCadastroDeClientes() {
        // 1. Navegar para a página de clientes
        driver.get(baseUrl + "/cliente.xhtml");
        
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // 2. Verificar título
        wait.until(ExpectedConditions.titleIs("Cadastro de Clientes"));
        takeSnapshot("1_pagina_carregada");

        // 3. Preencher formulário (IDs do JSF/PrimeFaces geralmente são compostos, ex: form:nome)
        WebElement inputNome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("form:nome")));
        inputNome.sendKeys("Joao Silva");

        driver.findElement(By.id("form:sexo")).sendKeys("M");
        
        // InputNumber do PrimeFaces as vezes requer interação com o input interno que tem sufixo _input ou o id direto dependendo da versao
        // Tentando seletor direto primeiro, se falhar, usar _input
        WebElement inputIdade = driver.findElement(By.id("form:idade_input")); 
        inputIdade.clear();
        inputIdade.sendKeys("30");

        WebElement inputAltura = driver.findElement(By.id("form:altura_input"));
        inputAltura.clear();
        inputAltura.sendKeys("10");
        
        takeSnapshot("2_formulario_preenchido");

        // 4. Salvar (buscar botão pelo texto ou classe, já que ID pode ser dinâmico ou complexo)
        // Aqui usamos um seletor CSS que busca o botão dentro do form
        WebElement btnSalvar = driver.findElement(By.cssSelector("button[id*='form'] span.ui-button-text.ui-c"));
        // Se o span não for clicável, clicamos no botão pai
        if (!btnSalvar.getText().equals("Salvar")) {
             // Fallback simples
             btnSalvar = driver.findElement(By.xpath("//button[contains(.,'Salvar')]"));
        }
        
        // Clicar no pai do span se necessário, ou direto no elemento achado via xpath
        if(btnSalvar.getTagName().equalsIgnoreCase("span")) {
            btnSalvar.findElement(By.xpath("..")).click();
        } else {
            btnSalvar.click();
        }

        // 5. Verificar mensagem de sucesso
        try {
            // Aguarda a mensagem de sucesso no componente p:messages
            // O ID do messages é 'form:messages' ou apenas 'messages' dependendo de como o JSF renderiza o ID absoluto
            // O texto "Cliente salvo com sucesso!" deve aparecer
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.className("ui-messages-info-summary"), "Cliente salvo com sucesso!"));
        } catch (Exception e) {
            // Fallback: tentar procurar no corpo da página se a classe específica não for encontrada
             wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Cliente salvo com sucesso!"));
        }

        takeSnapshot("3_apos_salvar");
        
        // Verificação final (redundante se o wait passar, mas boa para clareza)
        String pageSource = driver.getPageSource();
        Assertions.assertTrue(pageSource.contains("Cliente salvo com sucesso!"), "A mensagem de sucesso deveria ser exibida.");
    }
}
