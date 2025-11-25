package com.tecdes.pedido.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.tecdes.pedido.model.DAO.ItemPedidoDAO;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.config.ConnectionFactory;

public class ItemPedidoRepositoryImpl implements ItemPedidoRepository {

    private final ItemPedidoDAO itemPedidoDAO;

    public ItemPedidoRepositoryImpl() {
        try {
            Connection conn = ConnectionFactory.getConnection();
            this.itemPedidoDAO = new ItemPedidoDAO(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco", e);
        }
    }
    

    @Override
    public void save(ItemPedido item) {
        try {
            itemPedidoDAO.salvar(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItemPedido findById(int id) {
        try {
            return itemPedidoDAO.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ItemPedido> findAll() {
        try {
            return itemPedidoDAO.listar();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(ItemPedido item) {
        try {
            itemPedidoDAO.atualizar(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            itemPedidoDAO.deletar(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
