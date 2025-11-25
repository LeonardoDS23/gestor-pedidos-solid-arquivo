package com.tecdes.pedido.view;

import com.tecdes.pedido.model.entity.Pedido;

public class ComandaVirtualView {

    public void exibirComanda(Pedido pedido){
        System.out.println("=== COMANDA VIRTUAL ===");
        System.out.println("Valor total: R$ " + pedido.getValorTotal());
        System.out.println("Status: " + pedido.getStatus());
    }
}

