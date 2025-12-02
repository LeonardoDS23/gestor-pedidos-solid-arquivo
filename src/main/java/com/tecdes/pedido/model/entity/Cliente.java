package com.tecdes.pedido.model.entity;

public class Cliente {

    private Long idCliente; 
    private String nome;
    private String fone;
    private String email;

    public Cliente() {
    
    }

    public Cliente(String nome, String fone, String email) {
        this.nome = nome;
        this.fone = fone;
        this.email = email;
    }



    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
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

    public String getEmail()
    {   return email;    
    }
    public void setEmail(String email) 
    { 
        this.email = email; 
    }


    @Override
    public String toString() {
        return nome + " (" + fone + ")";
    }
}