package com.tecdes.pedido.repository;


import java.util.List;
import com.tecdes.pedido.model.entity.ItemPedido;


public interface ItemPedidoRepository {


    void save(ItemPedido item);


    ItemPedido findById(Long id);


    List<ItemPedido> findAll();


    void update(ItemPedido item);


    void delete(Long id);
}
