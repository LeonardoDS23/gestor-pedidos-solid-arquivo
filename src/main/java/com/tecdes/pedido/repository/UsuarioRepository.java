package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Usuario;
import java.util.List;
import java.util.Optional;


public interface UsuarioRepository {


    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByLogin(String login);
    List<Usuario> findAll();
    List<Usuario> findAllByType(Class<? extends Usuario> type);
    void deleteById(Long id);
    boolean existsById(Long id);
}
