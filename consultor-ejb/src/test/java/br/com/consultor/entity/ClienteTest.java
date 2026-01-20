package br.com.consultor.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClienteTest {

    @Test
    void testGettersAndSetters() {
        Cliente cliente = new Cliente();
        Long id = 1L;
        String nome = "Teste";
        String sexo = "M";
        int idade = 30;
        double altura = 1.80;

        cliente.setId(id);
        cliente.setNome(nome);
        cliente.setSexo(sexo);
        cliente.setIdade(idade);
        cliente.setAltura(altura);

        assertEquals(id, cliente.getId());
        assertEquals(nome, cliente.getNome());
        assertEquals(sexo, cliente.getSexo());
        assertEquals(idade, cliente.getIdade());
        assertEquals(altura, cliente.getAltura());
    }
}
