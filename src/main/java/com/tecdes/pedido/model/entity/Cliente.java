package com.tecdes.pedido.model.entity;

public class Cliente {
     private String nome;
     private String fone;

    public Cliente(String nome, String fone) {
        setNome(nome);
        setFone(fone);
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("nome invalido");
    }
    public String getFone() {
        return fone;
    }
    public void setFone(String fone2) {
        if (fone == null) fone = "";
        this.fone = fone;
    }
}
