package com.tecdes.pedido.model.entity;

public class Produto {

    private Long idProduto;
    private String nome;
    private double preco;
    private String categoria;
    private String descricao;

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
// package com.tecdes.pedido.model.entity;

// public class Produto {

// private Long idProduto;
// private String nome;
// private String descricao;
// private double preco;
// private String categoria;

// public Produto() {
// }

// public Produto(String nome, String descricao, double preco, String categoria)
// {
// this.nome = nome;
// this.descricao = descricao;
// this.preco = preco;
// this.categoria = categoria;
// }

// public Long getIdProduto() {
// return idProduto;
// }

// public void setIdProduto(Long idProduto) {
// this.idProduto = idProduto;
// }

// public String getNome() {
// return nome;
// }

// public void setNome(String nome) {
// if (nome == null || nome.trim().isEmpty()) {
// throw new IllegalArgumentException("Nome do produto é obrigatório.");
// }
// this.nome = nome;
// }

// public String getDescricao() {
// return descricao;
// }

// public void setDescricao(String descricao) {
// this.descricao = descricao;
// }

// public double getPreco() {
// return preco;
// }

// public void setPreco(double preco) {
// if (preco <= 0) {
// throw new IllegalArgumentException("Preço deve ser positivo.");
// }
// this.preco = preco;
// }

// }