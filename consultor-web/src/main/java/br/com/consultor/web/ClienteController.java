package br.com.consultor.web;

import br.com.consultor.entity.Cliente;
import br.com.consultor.service.ClienteService;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ClienteController extends Bean implements Serializable {

    @Inject
    private ClienteService clienteService;

    private Cliente cliente;
    private List<Cliente> clientes;

    @PostConstruct
    public void init() {
        novo();
        this.clientes = clienteService.listar();
    }

    public void salvar() {
        clienteService.salvar(cliente);
        novo();
        this.clientes = clienteService.listar();
        addInfo("Cliente salvo com sucesso!");
    }

    public void remover(Long id) {
        clienteService.remover(id);
        this.clientes = clienteService.listar();
        addInfo("Cliente removido com sucesso!");
    }

    public void novo() {
        this.cliente = new Cliente();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
