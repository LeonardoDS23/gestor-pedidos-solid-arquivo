package com.tecdes.pedido.controller;


import java.util.List;


import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.ProdutoService;


public class ProdutoController {


    private final ProdutoService service = new ProdutoService();


    // Salva
    public void save(String nome, Double preco) {
        service.salvarProduto(nome, preco);
    }


    // Busca todos
    public List<Produto> buscarTodos() {
        return service.buscarTodos();
    }


     // Busca por Id
     public Produto findById(int id){
        return service.buscarPorId(id);
    }


    // Atualiza
    public void update(int id, String nome, Double preco){
        service.atualizarProduto(id, nome, preco);
    }


    // Deleta
    public void delete(int id){
        service.deletarProduto(id);
    }
}


