package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Avaliacao;
import com.tecdes.pedido.repository.AvaliacaoRepository;

public class AvaliacaoService {

    private AvaliacaoRepository repository = new AvaliacaoRepository();

    public void avaliar(Avaliacao avaliacao){
        if(avaliacao.getNota() < 1 || avaliacao.getNota() > 5){
            throw new IllegalArgumentException("Nota deve ser de 1 a 5.");
        }
        repository.salvar(avaliacao);
    }
}
