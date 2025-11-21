package com.tecdes.pedido.controller;

import java.util.List;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.service.PedidoService;

public class PedidoController {

    private final PedidoService service = new PedidoService();

    // Criar Pedido
    public void save(List<ItemPedido> itens, String status, String tipoPagamento) {
        service.salvarPedido(itens, status, tipoPagamento);
    }

    // Buscar todos
    public List<Pedido> buscarTodos() {
        return service.buscarTodos();
    }

    // Buscar por ID
    public Pedido findById(int id) {
        return service.buscarPorId(id);
    }

    // Atualizar
    public void update(int id, List<ItemPedido> itens, String status, String tipoPagamento) {
        service.atualizarPedido(id, itens, status, tipoPagamento);
    }

    // Deletar
    public void delete(int id) {
        service.deletarPedido(id);
    }
}
