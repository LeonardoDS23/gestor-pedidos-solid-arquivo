package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Avaliacao;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoRepository {

    private List<Avaliacao> avaliacoes = new ArrayList<>();

    public void salvar(Avaliacao avaliacao){
        avaliacoes.add(avaliacao);
    }

    public List<Avaliacao> listar(){
        return avaliacoes;
    }
}
