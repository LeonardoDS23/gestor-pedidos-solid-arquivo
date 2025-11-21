package com.tecdes.pedido.service;

import java.time.LocalDateTime;
import java.util.List;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.repository.PedidoRepository;
import com.tecdes.pedido.repository.PedidoRepositoryImpl;

public class PedidoService {

    private final PedidoRepository repository = new PedidoRepositoryImpl();

    public void salvarPedido(List<ItemPedido> itens, String status, String tipoPagamento) {

        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido deve conter ao menos 1 item.");
        }

        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("O status do pedido é obrigatório.");
        }

        if (tipoPagamento == null || tipoPagamento.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de pagamento é obrigatório.");
        }

        double valorTotal = itens.stream().mapToDouble(
            i -> i.getQuantidade() * i.getProduto().getPreco()
        ).sum();

        Pedido pedido = new Pedido();
        pedido.setDataHora(LocalDateTime.now());
        pedido.setStatus(status);
        pedido.setTipoPagamento(tipoPagamento);
        pedido.setValorTotal(valorTotal);
        

        repository.save(pedido);
    }

    public List<Pedido> buscarTodos() {
        return repository.findAll();
    }

    public Pedido buscarPorId(int id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado com ID: " + id);
        }
        return pedido;
    }

    public void atualizarPedido(int id, List<ItemPedido> itens, String status, String tipoPagamento) {

        Pedido pedidoExistente = repository.findById(id);
        if (pedidoExistente == null) {
            throw new IllegalArgumentException("Pedido não encontrado para atualização. ID: " + id);
        }

        double valorTotal = itens.stream().mapToDouble(
            i -> i.getQuantidade() * i.getProduto().getPreco()
        ).sum();

       
        pedidoExistente.setStatus(status);
        pedidoExistente.setTipoPagamento(tipoPagamento);
        pedidoExistente.setValorTotal(valorTotal);

        repository.update(pedidoExistente);
    }

    public void deletarPedido(int id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado para exclusão. ID: " + id);
        }
        repository.delete(id);
    }
}
