package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Avaliacao;
import java.util.Optional;

public interface AvaliacaoRepository {
    
    Avaliacao save(Avaliacao avaliacao);
    Optional<Avaliacao> findById(Long id);
    // Adicionar busca por ID do Pedido, se necessário
    Optional<Avaliacao> findByPedidoId(Long idPedido);
}