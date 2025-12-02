package com.tecdes.pedido.model.entity;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Pedido {
   
    // Atributos ajustados
    private Long idPedido;
    private LocalDateTime dataHora;
    private String status;
    private List<ItemPedido> produtos;
    private Double valorTotal;
    private String tipoPagamento;
    private Cliente cliente;
    private String observacoes;




    // Construtor Padrão
    public Pedido() {
        this.dataHora = LocalDateTime.now();
        this.status = "Recebido";
        this.produtos = new ArrayList<>();
        this.valorTotal = 0.0;
    }


    // Método de cálculo
    public void calcularTotal () {
        double total = 0.0;
       
        if (this.produtos != null) {
            for (ItemPedido item : this.produtos) {
                // Assumindo que ItemPedido tem o método calcularTotal()
                total += item.calcularTotal();
            }
        }
       
        this.valorTotal = total;
    }


    // -------------------------------------------------------------------
    // Getters e Setters
    // -------------------------------------------------------------------


    public Long getId() {
        return idPedido;
    }
   
    public Long getIdPedido() {
        return idPedido;
    }


    public void setId(Long idPedido) {
        this.idPedido = idPedido;
    }
   
    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }


    public LocalDateTime getDataHora() {
        return dataHora;
    }


    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public List<ItemPedido> getProdutos() {
        return produtos;
    }


    public void setProdutos(List<ItemPedido> produtos) {
        this.produtos = produtos;
    }


    public Double getValorTotal() {
        return valorTotal;
    }


    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }


    public String getTipoPagamento() {
        return tipoPagamento;
    }


    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
   
    public Cliente getCliente() {
        return cliente;
    }


    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


   
    public String getObservacoes() {
        return observacoes;
    }
   
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
