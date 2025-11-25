package com.tecdes.pedido.repository;

import java.util.List;
import com.tecdes.pedido.model.entity.ItemPedido;

public interface ItemPedidoRepository {

    void save(ItemPedido item);

    ItemPedido findById(int id);

    List<ItemPedido> findAll();

    void update(ItemPedido item);

    void delete(int id);
}
