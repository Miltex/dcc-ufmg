package br.com.consultor.service;

import br.com.consultor.entity.Cliente;

import br.com.consultor.msg.dto.NotificacaoRecord;
import br.com.consultor.msg.producer.NotificacaoProducer;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ClienteService {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private NotificacaoProducer produtor;

    public void salvar(Cliente cliente) {
        produtor.enviarNotificacao(new NotificacaoRecord("NotificacaoQueue","Usuario Adicionado"));
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
