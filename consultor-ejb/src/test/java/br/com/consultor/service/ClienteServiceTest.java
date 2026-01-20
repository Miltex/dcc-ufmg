package br.com.consultor.service;

import br.com.consultor.entity.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void testSalvar() {
        Cliente cliente = new Cliente();
        cliente.setNome("Teste");

        clienteService.salvar(cliente);

        verify(em, times(1)).merge(cliente);
    }

    @Test
    void testListar() {
        Cliente cliente = new Cliente();
        cliente.setNome("Teste");
        List<Cliente> clientes = Collections.singletonList(cliente);

        TypedQuery<Cliente> typedQuery = mock(TypedQuery.class);
        when(em.createQuery("select c from Cliente c", Cliente.class)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Teste", resultado.get(0).getNome());
        verify(em, times(1)).createQuery("select c from Cliente c", Cliente.class);
    }

    @Test
    void testRemover() {
        Long clienteId = 1L;
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);

        when(em.find(Cliente.class, clienteId)).thenReturn(cliente);

        clienteService.remover(clienteId);

        verify(em, times(1)).find(Cliente.class, clienteId);
        verify(em, times(1)).remove(cliente);
    }
}
