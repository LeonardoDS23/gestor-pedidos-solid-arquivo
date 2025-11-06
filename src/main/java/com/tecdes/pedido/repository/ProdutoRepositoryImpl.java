package com.tecdes.pedido.repository;

import java.util.List;
import com.tecdes.pedido.model.DAO.ProdutoDAO;
import com.tecdes.pedido.model.entity.Produto;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    
    private final ProdutoDAO produtoDAO = new ProdutoDAO(); 

    @Override
    public void save(Produto produto) {
        produtoDAO.inserir(produto);
    }

    @Override
    public Produto findById(int id) {
        return produtoDAO.buscarPorId(id);
    }

    @Override
    public List<Produto> findAll() {
        return produtoDAO.buscarTodos();
    }

    @Override
    public void update(Produto produto) {
        produtoDAO.atualizar(produto);
    }

    @Override
    public void delete(int id) {
        produtoDAO.deletar(id);
    }
}//alteraçoes básicas.