package br.com.consultor.web;

import br.com.consultor.entity.Cliente;
import br.com.consultor.service.ClienteService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private FacesContext facesContext;

    @InjectMocks
    private ClienteController clienteController;

    private MockedStatic<FacesContext> mockedFacesContext;

    @BeforeEach
    void setUp() {
        // Mock the static FacesContext.getCurrentInstance() call
        mockedFacesContext = mockStatic(FacesContext.class);
        mockedFacesContext.when(FacesContext::getCurrentInstance).thenReturn(facesContext);
    }

    @AfterEach
    void tearDown() {
        // Close the static mock after each test
        mockedFacesContext.close();
    }

    @Test
    void testInit() {
        List<Cliente> clientes = new ArrayList<>();
        when(clienteService.listar()).thenReturn(clientes);

        clienteController.init();

        verify(clienteService, times(1)).listar();
        assertNotNull(clienteController.getCliente());
        assertNotNull(clienteController.getClientes());
        assertEquals(clientes, clienteController.getClientes());
    }

    @Test
    void testSalvar() {
        Cliente clienteParaSalvar = new Cliente();
        clienteParaSalvar.setNome("Novo Cliente");
        clienteController.setCliente(clienteParaSalvar);

        List<Cliente> clientesAtualizados = new ArrayList<>();
        when(clienteService.listar()).thenReturn(clientesAtualizados);

        clienteController.salvar();

        ArgumentCaptor<FacesMessage> messageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        verify(facesContext).addMessage(isNull(), messageCaptor.capture());
        assertEquals("Cliente salvo com sucesso!", messageCaptor.getValue().getSummary());

        verify(clienteService, times(1)).salvar(clienteParaSalvar);
        verify(clienteService, times(1)).listar();
        assertNotNull(clienteController.getCliente());
        assertNotEquals(clienteParaSalvar, clienteController.getCliente());
        assertEquals(clientesAtualizados, clienteController.getClientes());
    }

    @Test
    void testRemover() {
        Long clienteId = 1L;
        List<Cliente> clientesAtualizados = new ArrayList<>();
        when(clienteService.listar()).thenReturn(clientesAtualizados);

        clienteController.remover(clienteId);

        ArgumentCaptor<FacesMessage> messageCaptor = ArgumentCaptor.forClass(FacesMessage.class);
        verify(facesContext).addMessage(isNull(), messageCaptor.capture());
        assertEquals("Cliente removido com sucesso!", messageCaptor.getValue().getSummary());

        verify(clienteService, times(1)).remover(clienteId);
        verify(clienteService, times(1)).listar();
        assertEquals(clientesAtualizados, clienteController.getClientes());
    }

    @Test
    void testNovo() {
        clienteController.novo();
        assertNotNull(clienteController.getCliente());
        assertNull(clienteController.getCliente().getId());
        assertNull(clienteController.getCliente().getNome());
    }
}
