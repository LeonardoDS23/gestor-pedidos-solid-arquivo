package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Avaliacao;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {

    private final Map<Long, Avaliacao> database = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0); 

    @Override
    public Avaliacao save(Avaliacao avaliacao) {
        if (avaliacao.getIdAvaliacao() == null || avaliacao.getIdAvaliacao() == 0L) {
            avaliacao.setIdAvaliacao(idGenerator.incrementAndGet());
        }
        database.put(avaliacao.getIdAvaliacao(), avaliacao);
        System.out.println("[DB] Avaliação registrada: ID " + avaliacao.getIdAvaliacao());
        return avaliacao;
    }

    @Override
    public Optional<Avaliacao> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }
    
    @Override
    public Optional<Avaliacao> findByPedidoId(Long idPedido) {
        return database.values().stream()
                .filter(a -> a.getIdPedido().equals(idPedido))
                .findFirst();
    }
}