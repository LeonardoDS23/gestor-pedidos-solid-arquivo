package com.tecdes.pedido.service;

import java.util.List;

import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.repository.PedidoRepository;
import com.tecdes.pedido.repository.PedidoRepositoryImpl;

public class PedidoService {

    private final PedidoRepository repository = new PedidoRepositoryImpl();

    public void salvarPedido(List<ItemPedido> itens, String tipoPagamento) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter pelo menos um item.");
        }
        if (tipoPagamento == null || tipoPagamento.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de pagamento é obrigatório.");
        }

        double valorTotal = itens.stream()
                                 .mapToDouble(ItemPedido::getSubtotal)
                                 .sum();

        Pedido pedido = new Pedido();
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setProdutos(itens);
        pedido.setValorTotal(valorTotal);
        pedido.setTipoPagamento(tipoPagamento);

        repository.salvar(pedido);
    }

    public List<Pedido> buscarTodos() {
        return repository.buscarTodos();
    }
}