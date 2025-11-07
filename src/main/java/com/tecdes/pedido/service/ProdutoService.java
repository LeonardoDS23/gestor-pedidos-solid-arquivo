package com.tecdes.pedido.service;


import java.util.List;


import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.ProdutoRepository;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;


public class ProdutoService {


    private final ProdutoRepository repository = new ProdutoRepositoryImpl();


    // --- Criar ---
    public void salvarProduto(String nome, Double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo produto é obrigatório.");
        }
        if (preco == null) {
            throw new IllegalArgumentException("O campo preço é obrigatório.");
        }


        Produto produto = new Produto(nome, preco);
        repository.save(produto);
    }


    // --- Listar todos ---
    public List<Produto> buscarTodos() {
        return repository.findAll();
    }


    // --- Buscar por ID ---
    public Produto buscarPorId(int id) {
        Produto produto = repository.findById(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }
        return produto;
    }


    // --- Atualizar ---
    public void atualizarProduto(int id, String nome, Double preco) {
        Produto produtoExistente = repository.findById(id);
        if (produtoExistente == null) {
            throw new IllegalArgumentException("Produto não encontrado para atualização. ID: " + id);
        }


        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo produto é obrigatório.");
        }
        if (preco == null) {
            throw new IllegalArgumentException("O campo preço é obrigatório.");
        }


        produtoExistente.setNome(nome);
        produtoExistente.setPreco(preco);


        repository.update(produtoExistente);
    }


    // --- Deletar ---
    public void deletarProduto(int id) {
        Produto produto = repository.findById(id);
        if (produto == null) {
            throw new IllegalArgumentException("Produto não encontrado para exclusão. ID: " + id);
        }
        repository.delete(id);
    }
}


