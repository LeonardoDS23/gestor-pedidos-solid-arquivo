package com.tecdes.pedido.repository.impl;

import com.tecdes.pedido.model.entity.Usuario;
import com.tecdes.pedido.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final Map<Long, Usuario> database = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0); 

    @Override
    public Usuario save(Usuario usuario) {
        if (usuario.getIdUsuario() == null || usuario.getIdUsuario() == 0L) {
            usuario.setIdUsuario(idGenerator.incrementAndGet());
        }
        database.put(usuario.getIdUsuario(), usuario);
        System.out.println("[DB] Usuário salvo/atualizado: ID " + usuario.getIdUsuario() + " | Tipo: " + usuario.getClass().getSimpleName());
        return usuario;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public Optional<Usuario> findByLogin(String login) {
        return database.values().stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(database.values());
    }
    
    @Override
    public List<Usuario> findAllByType(Class<? extends Usuario> type) {
        return database.values().stream()
                .filter(type::isInstance)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
        System.out.println("[DB] Usuário deletado: ID " + id);
    }

    @Override
    public boolean existsById(Long id) {
        return database.containsKey(id);
    }
}