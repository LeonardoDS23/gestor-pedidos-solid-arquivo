package com.tecdes.pedido.model.entity;

public class Entrega {

    private int id;
    private String endereco;
    private String status;

    public Entrega(int id, String endereco) {
        this.id = id;
        this.endereco = endereco;
        this.status = "Pendente";
    }

    public void atualizarStatus(String status){
        this.status = status;
    }
}
