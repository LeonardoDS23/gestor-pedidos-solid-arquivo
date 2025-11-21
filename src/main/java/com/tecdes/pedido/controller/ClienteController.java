package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.service.ClienteService;


public class ClienteController {
    
    private final ClienteService service = new ClienteService();

    // Salva
    public void salvar(String nome, String fone) {
        service.salvarCliente(nome, fone);
    }

    // Busca todos
    public List<Cliente> buscarTodos() {
        return service.buscarTodos();
    }

     // Busca por Id
     public Cliente findById(int idCliente){
        return service.buscarPorId(idCliente);
    }


    // Atualiza
    public void update(int idCliente, String nome, String fone){
        service.atualizarCliente(idCliente, nome, fone);
    }


    // Deleta
    public void delete(int idCliente){
        service.deletarCliente(idCliente);
    }
}
