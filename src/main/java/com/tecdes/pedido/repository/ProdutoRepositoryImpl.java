package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Produto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


public class ProdutoRepositoryImpl implements ProdutoRepository {


    private final Map<Long, Produto> database = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);


    @Override
    public Produto save(Produto produto) {
        if (produto.getIdProduto() == null || produto.getIdProduto() == 0L) {
            produto.setIdProduto(idGenerator.incrementAndGet());
        }
        database.put(produto.getIdProduto(), produto);
        System.out.println("[DB] Produto salvo/atualizado: ID " + produto.getIdProduto());
        return produto;
    }


    @Override
    public Optional<Produto> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }


    @Override
    public List<Produto> findAll() {
        return new ArrayList<>(database.values());
    }


    @Override
    public void delete(Long id) {
        if (database.containsKey(id)) {
            database.remove(id);
            System.out.println("[DB] Produto ID " + id + " removido.");
        } else {
            // Em uma implementação real, lançaríamos uma exceção, mas aqui apenas ignoramos
        }
    }


    // Métodos de busca específicos
   
    @Override
    public List<Produto> findByCategoria(String categoria) {
        return database.values().stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }


    @Override
    public List<Produto> findByNomeContaining(String nomeParcial) {
        if (nomeParcial == null || nomeParcial.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String termoBusca = nomeParcial.toLowerCase();
       
        return database.values().stream()
                .filter(p -> p.getNome() != null && p.getNome().toLowerCase().contains(termoBusca))
                .collect(Collectors.toList());
    }
}
