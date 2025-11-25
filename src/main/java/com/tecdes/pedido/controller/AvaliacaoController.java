package com.tecdes.pedido.controller;

import com.tecdes.pedido.model.entity.Avaliacao;
import com.tecdes.pedido.service.AvaliacaoService;

public class AvaliacaoController {

    private AvaliacaoService service = new AvaliacaoService();

    public void avaliarPedido(Avaliacao avaliacao){
        service.avaliar(avaliacao);
    }
}
