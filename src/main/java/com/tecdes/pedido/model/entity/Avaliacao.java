package com.tecdes.pedido.model.entity;


public class Avaliacao {


    private Long idAvaliacao;
    private Long idPedido; // Referência ao pedido avaliado
    private int nota;      // 1 a 5
    private String comentario;


    public Avaliacao(Long idPedido, int nota, String comentario) {
        this.idPedido = idPedido;
        this.nota = nota;
        this.comentario = comentario;
    }
   
    // Construtor padrão
    public Avaliacao() {}


    // -------------------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------------------


    public Long getIdAvaliacao() {
        return idAvaliacao;
    }


    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }


    public Long getIdPedido() {
        return idPedido;
    }


    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }


    public int getNota() {
        return nota;
    }


    public void setNota(int nota) {
        this.nota = nota;
    }


    public String getComentario() {
        return comentario;
    }


    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
