package com.tecdes.pedido.model.entity;

import java.time.LocalDateTime;

public class Avaliacao {

    
    private Long idAvaliacao;
    private int nota; 
    private String comentario;
    private LocalDateTime dataAvaliacao;
    
    
    private Pedido pedido; 
    private Cliente cliente;

    
    public Avaliacao() {
        this.dataAvaliacao = LocalDateTime.now(); 
    }

    
    public Avaliacao(int nota, String comentario, Pedido pedido, Cliente cliente) {
        this(); 
        setNota(nota);
        setComentario(comentario);
        setPedido(pedido);
        setCliente(cliente);
    }
    
    
    public void cadastrarAvaliacao() {
        if (this.nota < 1 || this.nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
        System.out.println("Avaliação do Pedido " + (pedido != null ? pedido.getId() : "N/A") + 
                           " cadastrada com sucesso! Nota: " + this.nota);
        
    }

    
    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        if (nota < 1 || nota > 5) {
             throw new IllegalArgumentException("A nota deve ser um valor entre 1 e 5.");
        }
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
 
    @Override
    public String toString() {
        String clienteNome = cliente != null ? cliente.getNome() : "Desconhecido";
        String pedidoId = pedido != null ? String.valueOf(pedido.getId()) : "N/A";
        
        return "Avaliacao [id: " + idAvaliacao + 
               ", Cliente: " + clienteNome + 
               ", Pedido: " + pedidoId + 
               ", Nota: " + nota + 
               ", Comentário: '" + comentario + "']";
    }
}