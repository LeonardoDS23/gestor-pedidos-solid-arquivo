package com.tecdes.pedido.service;

public class PixPagamento implements PagamentoStrategy {

    @Override
    public boolean pagar(double valor) {
        System.out.println("Pagamento de R$ " + valor + " realizado via PIX.");
        return true;
    }
}
