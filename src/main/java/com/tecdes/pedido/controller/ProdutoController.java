package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.ProdutoService;

public class ProdutoController {

private final ProdutoService service = new ProdutoService();
    
public void save(String nome, Double preco){
    service.SalvarProduto(nome, preco);
}

// public List<Produto> buscarTodos() {
//         return service.findAll();
//     }
// }
