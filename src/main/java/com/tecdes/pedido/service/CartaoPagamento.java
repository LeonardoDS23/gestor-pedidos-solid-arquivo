package com.tecdes.pedido.service;

public class CartaoPagamento implements PagamentoStrategy {

    @Override
    public boolean pagar(double valor) {
        System.out.println("Pagamento de R$ " + valor + " realizado no CARTÃO.");
        return true;
    }
}
