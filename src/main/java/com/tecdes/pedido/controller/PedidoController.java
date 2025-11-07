package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.model.entity.ItemPedido;
import java.time.LocalDateTime;


// Intermediário entre a View e o Service
public class PedidoController {

    private final PedidoService service = new PedidoService();

    public void salvarPedido(List<ItemPedido> itens, String dataHora) {
        service.salvarPedido(itens, dataHora); 
    }

    public List<Cliente> listarTodos() {
        return service.buscarTodos();
    }
} 
