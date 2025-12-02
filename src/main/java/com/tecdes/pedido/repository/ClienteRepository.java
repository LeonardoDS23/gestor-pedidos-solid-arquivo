package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Cliente;
import java.util.Optional;
import java.util.List;


public interface ClienteRepository {


    Optional<Cliente> findByNome(String nome);
   
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Long id);
    List<Cliente> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
