package com.tecdes.pedido.controller;

import java.util.List;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.service.ProdutoService;
import com.tecdes.pedido.repository.PedidoRepositoryImpl;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;

public class PedidoController {

    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    public PedidoController() {
        ProdutoRepositoryImpl produtoRepo = new ProdutoRepositoryImpl();
        this.produtoService = new ProdutoService(produtoRepo);
        
        PedidoRepositoryImpl pedidoRepo = new PedidoRepositoryImpl();
        this.pedidoService = new PedidoService(pedidoRepo, this.produtoService);
    }

    public Pedido finalizarPedido(Pedido pedido) {
        return pedidoService.finalizarPedido(pedido);
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoService.buscarPedidoPorId(id);
    }

    public List<Pedido> buscarTodosPedidos() {
        return pedidoService.buscarTodosPedidos();
    }

    public Pedido atualizarStatus(Long id, String novoStatus) {
        return pedidoService.atualizarStatus(id, novoStatus);
    }

    public Pedido cancelarPedido(Long id) {
        return pedidoService.cancelarPedido(id);
    }
}
