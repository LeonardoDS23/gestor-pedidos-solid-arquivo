package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.service.ClienteService;
import com.tecdes.pedido.repository.ClienteRepositoryImpl;

public class ClienteController {
    
    private final ClienteService service;

    public ClienteController() {
        this.service = new ClienteService(new ClienteRepositoryImpl()); 
    }

    public void salvar(String nome, String fone, String email) {
        Cliente novoCliente = new Cliente(nome, fone, email);
        service.cadastrarCliente(novoCliente);
    }

    public List<Cliente> buscarTodos() {
        return service.buscarTodosClientes();
    }

    public Cliente findById(Long idCliente){
        return service.buscarClientePorId(idCliente);
    }

    public void update(Long idCliente, String nome, String fone,String email){
        Cliente dadosNovos = new Cliente(nome, fone, email);
        service.atualizarCliente(idCliente, dadosNovos);
    }

    public void delete(Long idCliente){
        service.excluirCliente(idCliente);
    }

    public List<Cliente> listarTodos() {
        return service.buscarTodosClientes();
    }
}