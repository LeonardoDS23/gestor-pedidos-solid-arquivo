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
    
    public ItemPedido adicionarItemAoPedido(Long idPedido, ItemPedido itemPedido) {
        try {
            // Configura o ID do pedido no item
            itemPedido.setIdPedido(idPedido);
            
            // Validações adicionais
            if (itemPedido.getProduto() == null) {
                throw new IllegalArgumentException("Produto é obrigatório.");
            }
            
            if (itemPedido.getQuantidade() <= 0) {
                throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
            }
            
            // Salva o item (save() retorna void)
            repository.save(itemPedido);
            
            // Este já tem o ID do pedido e foi validado
            return itemPedido;
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar item ao pedido: " + e.getMessage(), e);
        }
    }

    public ItemPedido buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<ItemPedido> listarTodos() {
        return repository.findAll();
    }

    public void atualizar(ItemPedido item) {
        repository.update(item);
    }

    public void deletar(Long id) {
        repository.delete(id);
    }
    
    public List<ItemPedido> buscarItensPorPedido(Long idPedido) {
        try {
            List<ItemPedido> todosItens = listarTodos();
            if (todosItens == null) {
                return List.of();
            }
            return todosItens.stream()
                .filter(item -> item != null && idPedido.equals(item.getIdPedido()))
                .toList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar itens do pedido: " + e.getMessage(), e);
        }
    }
}