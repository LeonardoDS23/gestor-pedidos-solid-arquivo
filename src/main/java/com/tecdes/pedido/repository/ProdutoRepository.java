package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Produto;
import java.util.List;
import java.util.Optional;


public interface ProdutoRepository {


    Produto save(Produto produto);
    Optional<Produto> findById(Long id);
    List<Produto> findAll();
    void delete(Long id);
   
    // Métodos de busca específicos
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByNomeContaining(String nomeParcial);
}
