package com.tecdes.pedido.model.entity;

public class Cliente {

    private int idCliente;
    private String nome;
    private String fone;

    public Cliente() {
    
    }

    public Cliente(String nome, String fone) {
        setNome(nome);
        setFone(fone);
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public String getFone() {
        return fone;
    }

    public void setFone(String fone) {
        if (fone == null) {
            this.fone = "";
        } else {
            this.fone = fone;
        }
    }

    @Override
    public String toString() {
        return nome + " (" + fone + ")";
    }
}
