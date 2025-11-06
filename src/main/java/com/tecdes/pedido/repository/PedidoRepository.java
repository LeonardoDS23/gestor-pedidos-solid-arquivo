package com.tecdes.pedido.repository;

import java.util.List;

import com.tecdes.pedido.model.entity.Pedido;

public interface PedidoRepository {

    void save(Pedido pedido);
    Pedido findById(int id);
    List<Pedido> findAll();
    void update(Pedido pedido);
    void delete(int id);
}
