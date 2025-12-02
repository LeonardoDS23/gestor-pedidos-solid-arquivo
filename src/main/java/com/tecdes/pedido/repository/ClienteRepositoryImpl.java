package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Cliente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;




public class ClienteRepositoryImpl implements ClienteRepository {


    private final Map<Long, Cliente> database = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);


    @Override
    public Cliente save(Cliente cliente) {
        if (cliente.getIdCliente() == null || cliente.getIdCliente() == 0L) {
            cliente.setIdCliente(idGenerator.incrementAndGet());
        }
       
        database.put(cliente.getIdCliente(), cliente);
        System.out.println("[DB] Cliente salvo/atualizado: ID " + cliente.getIdCliente());
        return cliente;
    }


    @Override
    public Optional<Cliente> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }


    @Override
    public List<Cliente> findAll() {
        return new ArrayList<>(database.values());
    }


    @Override
    public void deleteById(Long id) {
        database.remove(id);
        System.out.println("[DB] Cliente deletado: ID " + id);
    }


    @Override
    public boolean existsById(Long id) {
        return database.containsKey(id);
    }


    @Override
    public Optional<Cliente> findByNome(String nome) {
        return database.values().stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }
}
