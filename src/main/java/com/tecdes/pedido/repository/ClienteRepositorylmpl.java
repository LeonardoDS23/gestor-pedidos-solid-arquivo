package com.tecdes.pedido.repository;

import java.util.List;

import com.tecdes.pedido.model.impl.ClienteRepositorylmpl;
import com.tecdes.pedido.model.entity.Cliente;

public class ClienteRepositorylmpl implements ClienteRepository {
    
    private final ClienteRepositorylmpl clienteRepositorylmpl = new ClienteRepositorylmpl();

    @Override
    public void salvar(Cliente cliente) {
        clienteRepositorylmpl.inserir(cliente);
    }

    @Override
    public List<Cliente> buscarTodos(){
        return clienteRepositorylmpl.buscarTodos();
    }
}
