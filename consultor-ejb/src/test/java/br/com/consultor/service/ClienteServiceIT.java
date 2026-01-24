package br.com.consultor.service;

import br.com.consultor.entity.Cliente;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;
import java.util.List;

@ExtendWith(ArquillianExtension.class)
@Transactional(TransactionMode.ROLLBACK)
public class ClienteServiceIT {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClass(Cliente.class)
                .addClass(ClienteService.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private ClienteService clienteService;

    @Test
    public void testSalvarEListar() {
        Cliente cliente = new Cliente();
        cliente.setNome("Joao Silva");
        cliente.setIdade(30);
        cliente.setSexo("M");
        cliente.setAltura(1.75);

        clienteService.salvar(cliente);

        List<Cliente> clientes = clienteService.listar();
        Assertions.assertFalse(clientes.isEmpty());
        Assertions.assertEquals("Joao Silva", clientes.get(0).getNome());
    }

    /*@AfterAll
    public static void limparEstadoDB(){
        ClienteService clienteService = new ClienteService();
        Long id = clienteService.listar().get(0).getId();
        clienteService.remover(id);
    }*/
}
