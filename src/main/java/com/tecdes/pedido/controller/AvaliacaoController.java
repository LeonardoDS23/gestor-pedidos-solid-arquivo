package com.tecdes.pedido.controller;

import com.tecdes.pedido.model.entity.Avaliacao;
import com.tecdes.pedido.service.AvaliacaoService;
import com.tecdes.pedido.repository.AvaliacaoRepositoryImpl;

public class AvaliacaoController {
    
    private final AvaliacaoService avaliacaoService;

    public AvaliacaoController() {
        // Inicialização manual das dependências
        AvaliacaoRepositoryImpl repository = new AvaliacaoRepositoryImpl();
        this.avaliacaoService = new AvaliacaoService(repository);
    }

    public void avaliarPedido(Avaliacao avaliacao) {
        try {
            avaliacaoService.registrarAvaliacao(avaliacao);
        } catch (IllegalArgumentException e) {
            System.err.println("Erro de validação: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Erro ao registrar: " + e.getMessage());
        }
    }

    public Avaliacao buscar(Long id) {
        try {
            return avaliacaoService.buscarAvaliacaoPorId(id);
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}