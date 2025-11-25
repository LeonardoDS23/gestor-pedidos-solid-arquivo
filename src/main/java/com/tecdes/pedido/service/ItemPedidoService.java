package com.tecdes.pedido.service;

import java.util.List;

import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.repository.ItemPedidoRepository;
import com.tecdes.pedido.repository.ItemPedidoRepositoryImpl;

public class ItemPedidoService {

    private final ItemPedidoRepository repository = new ItemPedidoRepositoryImpl();

    public void salvar(ItemPedido item) {
        if (item.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        repository.save(item);
    }

    public ItemPedido buscarPorId(int id) {
        return repository.findById(id);
    }

    public List<ItemPedido> listarTodos() {
        return repository.findAll();
    }

    public void atualizar(ItemPedido item) {
        repository.update(item);
    }

    public void deletar(int id) {
        repository.delete(id);
    }
}
