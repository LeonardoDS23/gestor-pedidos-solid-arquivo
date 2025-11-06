package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.service.PedidoService;

public class ClienteController {
    
    private final PedidoService service = new PedidoService();

    public void salvar(String nome, String fone) {
        service.salvarCliente(nome, fone);
    }

    public List<Cliente> listarTodos() {
        return (List<Cliente>) service.buscarTodos();
    }
}
