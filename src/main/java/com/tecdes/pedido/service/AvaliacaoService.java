package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Avaliacao;
import com.tecdes.pedido.repository.AvaliacaoRepository;

public class AvaliacaoService {
    
    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }
    
    public Avaliacao registrarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao.getNota() < 1 || avaliacao.getNota() > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
        if (avaliacaoRepository.findByPedidoId(avaliacao.getIdPedido()).isPresent()) {
            throw new RuntimeException("O pedido ID " + avaliacao.getIdPedido() + " já foi avaliado.");
        }
        // Aqui você faria a validação se o pedido realmente existe e foi entregue
        
        return avaliacaoRepository.save(avaliacao);
    }
    
    public Avaliacao buscarAvaliacaoPorId(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avaliação ID " + id + " não encontrada."));
    }
}