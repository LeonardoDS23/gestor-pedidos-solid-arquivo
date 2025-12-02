package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Cliente;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface PedidoRepository {


    Pedido save(Pedido pedido);
    Optional<Pedido> findById(Long id);
    List<Pedido> findAll();
   
    // Métodos específicos de busca
    List<Pedido> findByCliente(Cliente cliente);
    List<Pedido> findByStatusAndDataHoraBetween(String status, LocalDateTime dataInicial, LocalDateTime dataFinal);
}
