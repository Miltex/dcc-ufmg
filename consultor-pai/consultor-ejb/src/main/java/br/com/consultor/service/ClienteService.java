package br.com.consultor.service;

import br.com.consultor.entity.Cliente;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ClienteService {

    @PersistenceContext
    private EntityManager em;

    public void salvar(Cliente cliente) {
        em.merge(cliente);
    }

    public List<Cliente> listar() {
        return em.createQuery("select c from Cliente c", Cliente.class).getResultList();
    }

    public void remover(Long id) {
        Cliente cliente = em.find(Cliente.class, id);
        em.remove(cliente);
    }
}
