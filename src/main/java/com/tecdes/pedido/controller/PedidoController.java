package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.service.PedidoService;

// Intermediário entre a View e o Service
public class PedidoController {

    private final PedidoService service = new PedidoService();

    public void salvar(int id, String dataHora) {
        service.salvarPedido(id, dataHora); 
    }

    public List<Pedido> listarTodos() {
        return service.buscarTodos();
    }
} 
