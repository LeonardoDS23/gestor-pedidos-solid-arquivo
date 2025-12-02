package com.tecdes.pedido.controller;


import java.util.List;


import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.service.ItemPedidoService;


public class ItemPedidoController {


    private final ItemPedidoService service = new ItemPedidoService();


    public void salvar(ItemPedido item) {
        service.salvar(item);
    }


    public ItemPedido buscar(Long id) {
        return service.buscarPorId(id);
    }


    public List<ItemPedido> listarTodos() {
        return service.listarTodos();
    }


    public void atualizar(ItemPedido item) {
        service.atualizar(item);
    }


    public void deletar(Long id) {
        service.deletar(id);
    }
}
