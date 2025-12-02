package com.tecdes.pedido.service;


public class DinheiroPagamento implements PagamentoStrategy {


    @Override
    public boolean pagar(double valor) {
        System.out.println("Pagamento de R$ " + valor + " realizado em DINHEIRO.");
        return true;
    }
}
