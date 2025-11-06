package com.tecdes.pedido.service;

import java.util.List;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.ProdutoRepository;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;

public class ProdutoService {

    private final ProdutoRepository repository = new ProdutoRepositoryImpl();

    public void SalvarProduto(String nome, Double preco) {
        
    if(nome == null || nome.trim().isEmpty()){
        throw new IllegalArgumentException("O campo produto é obrigatório.");
    }
    if(preco == null){
        throw new IllegalArgumentException("O campo preço é obrigatório.");
    }

    Produto produto = new Produto(nome, preco);

    repository.save(produto);
    }

    public List<Produto> buscarTodos() {
        return repository.findAll();
    }
}
