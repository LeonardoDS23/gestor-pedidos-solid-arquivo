package com.tecdes.pedido.repository;

import java.util.List;
import com.tecdes.pedido.model.DAO.ClienteDAO;
import com.tecdes.pedido.model.entity.Cliente;

public class ClienteRepositorylmpl implements ClienteRepository {
    
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void salvar(Cliente cliente) {
        clienteDAO.inserir(cliente);
     }
        
    @Override
    public List<Cliente> buscarTodos(){
        return clienteDAO.buscarTodos();
    }
    @Override
    public Cliente buscarPorId(int id){
        return clienteDAO.buscarPorId(id);
    }
    @Override
    public void atualizar(Cliente cliente){
        clienteDAO.atualizar(cliente);
    }
    @Override
    public void deletar (int id){
        clienteDAO.deletar(id);
    }   
    
}
