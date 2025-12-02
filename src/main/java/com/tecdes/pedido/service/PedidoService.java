package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.PedidoRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;

    public PedidoService(PedidoRepository pedidoRepository, ProdutoService produtoService) {
        this.pedidoRepository = pedidoRepository;
        this.produtoService = produtoService;
    }

    public Pedido finalizarPedido(Pedido novoPedido) {
        if (novoPedido.getProdutos() == null || novoPedido.getProdutos().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }

        double total = 0.0;

        for (ItemPedido item : novoPedido.getProdutos()) {
            Produto produtoItem = item.getProduto();

            if (produtoItem == null || produtoItem.getIdProduto() <= 0) {
                throw new IllegalArgumentException("Produto inválido em um dos itens.");
            }
            
            Long idProduto = produtoItem.getIdProduto();
            Produto produto = produtoService.buscarPorId(idProduto);

            if (item.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade inválida para o item: " + produto.getNome());
            }

            item.setPrecoUnitario(produto.getPreco());
            total += item.getPrecoUnitario() * item.getQuantidade();
        }

        novoPedido.setValorTotal(total);
        novoPedido.setDataHora(LocalDateTime.now());
        novoPedido.setStatus("Em Processamento");

        return pedidoRepository.save(novoPedido);
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido ID " + id + " não encontrado."));
    }

    public List<Pedido> buscarTodosPedidos() {
        return pedidoRepository.findAll();
    }
    
    // ✅ ADICIONE ESTE MÉTODO:
    public List<Pedido> buscarTodos() {
        return buscarTodosPedidos(); // Delega ao método existente
    }

    public Pedido atualizarStatus(Long id, String novoStatus) {
        Pedido pedidoExistente = buscarPedidoPorId(id);
        pedidoExistente.setStatus(novoStatus);
        return pedidoRepository.save(pedidoExistente);
    }

    public Pedido cancelarPedido(Long id) {
        Pedido pedidoExistente = buscarPedidoPorId(id);

        if ("Entregue".equals(pedidoExistente.getStatus())) {
            throw new RuntimeException("Não é possível cancelar um pedido já entregue.");
        }

        pedidoExistente.setStatus("Cancelado");
        return pedidoRepository.save(pedidoExistente);
    }
}