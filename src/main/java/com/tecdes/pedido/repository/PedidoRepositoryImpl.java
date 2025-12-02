package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Cliente;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


public class PedidoRepositoryImpl implements PedidoRepository {


    private final Map<Long, Pedido> database = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);


    @Override
    public Pedido save(Pedido pedido) {
        if (pedido.getId() == null || pedido.getId() == 0L) {
            pedido.setId(idGenerator.incrementAndGet());
        }
        database.put(pedido.getId(), pedido);
        System.out.println("[DB] Pedido salvo/atualizado: ID " + pedido.getId());
        return pedido;
    }


    @Override
    public Optional<Pedido> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }


    @Override
    public List<Pedido> findAll() {
        return new ArrayList<>(database.values());
    }


    @Override
    public List<Pedido> findByCliente(Cliente cliente) {
        if (cliente == null) return new ArrayList<>();
       
        return database.values().stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getIdCliente().equals(cliente.getIdCliente()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Pedido> findByStatusAndDataHoraBetween(String status, LocalDateTime dataInicial, LocalDateTime dataFinal) {
        return database.values().stream()
                .filter(p -> p.getStatus().equalsIgnoreCase(status))
                .filter(p -> p.getDataHora() != null &&
                             !p.getDataHora().isBefore(dataInicial) &&
                             !p.getDataHora().isAfter(dataFinal))
                .collect(Collectors.toList());
    }
}
