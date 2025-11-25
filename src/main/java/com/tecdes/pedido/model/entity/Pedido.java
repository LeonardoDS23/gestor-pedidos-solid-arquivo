package com.tecdes.pedido.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int IdPedido;
    private LocalDateTime dataHora;
    private String status;
    private List<ItemPedido> produtos;
    private double valorTotal;
    private String TipoPagamento;
    @SuppressWarnings("rawtypes")
    private ArrayList itens;

    
    
        public void pedido() {
            this.dataHora = LocalDateTime.now();
            this.status = "Recebido";
            this.itens = new ArrayList<>();
            this.valorTotal = 0.0;

    }

    public void calcularTotal () {
        double total = 0.0;
        for (Object item : itens) {
            if (item instanceof ItemPedido) {
                total += ((ItemPedido) item).calcularTotal();
            }
        }
        
        this.valorTotal = total;
    }

    public int getIdPedido() {
        return IdPedido;
    }

    public void setId(int IdPedido) {
        this.IdPedido = IdPedido;
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

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTipoPagamento() {
        return TipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        TipoPagamento = tipoPagamento;
    }

    @SuppressWarnings("rawtypes")
    public ArrayList getItens() {
        return itens;
    }

    public void setItens(@SuppressWarnings("rawtypes") ArrayList itens) {
        this.itens = itens;
    }



}