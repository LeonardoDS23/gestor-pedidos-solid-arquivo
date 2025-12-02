package com.tecdes.pedido.model.entity;


import java.time.LocalDate;


public class Entrega {


   
    private Long idEntrega;
    private String enderecoEntrega;
    private String status;
    private LocalDate dataPrevista;
    private LocalDate dataEntrega;
   


   
    private Pedido pedido;


   
    public Entrega() {
        this.status = "Preparando";
       
    }
   
   
    public Entrega(String enderecoEntrega, LocalDate dataPrevista, Pedido pedido) {
        this();
        setEnderecoEntrega(enderecoEntrega);
        setDataPrevista(dataPrevista);
        setPedido(pedido);
    }


   
   
    /**
     * Atualiza o status da entrega.
     * @param novoStatus O novo status da entrega.
     */
    public void atualizarStatus(String novoStatus) {
        this.status = novoStatus;
       
       
        if ("Entregue".equalsIgnoreCase(novoStatus)) {
            this.dataEntrega = LocalDate.now();
        }
        System.out.println("Entrega " + idEntrega + " - Novo Status: " + novoStatus);
       
    }


 


    public Long getIdEntrega() {
        return idEntrega;
    }


    public void setIdEntrega(Long idEntrega) {
        this.idEntrega = idEntrega;
    }


    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }


    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public LocalDate getDataPrevista() {
        return dataPrevista;
    }


    public void setDataPrevista(LocalDate dataPrevista) {
        this.dataPrevista = dataPrevista;
    }


    public LocalDate getDataEntrega() {
        return dataEntrega;
    }


    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }


    public Pedido getPedido() {
        return pedido;
    }


    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
