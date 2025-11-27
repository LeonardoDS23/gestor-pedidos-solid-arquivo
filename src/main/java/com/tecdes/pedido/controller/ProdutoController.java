package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.ProdutoService;


public class ProdutoController {

    private final ProdutoService service;

    
    public ProdutoController(ProdutoService service) {
        this.service = service;
    }


    public void save(String nome ,  double preco, String categoria, String descricao) {
        service.salvarProduto(nome, preco, categoria, descricao);
    }


    public List<Produto> buscarTodos() {
        return service.buscarTodos();
    }


    public Produto findById(Long idProduto){
        return service.buscarPorId(idProduto);
    }


    public void update(Long idProduto, String nome ,  double preco, String categoria, String descricao){
        service.atualizarProduto(idProduto, nome, preco, categoria, descricao);
    }


    public void delete(Long idProduto){
        service.deletarProduto(idProduto);
    }
}