package com.tecdes.pedido.repository;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;

public interface ClienteRepository {
    void salvar(Cliente cliente);
    List<Cliente> buscarTodos();
}

