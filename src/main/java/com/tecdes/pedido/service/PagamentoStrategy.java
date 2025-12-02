package com.tecdes.pedido.service;


public interface PagamentoStrategy {
    boolean pagar(double valor);
}
