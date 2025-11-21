package com.tecdes.pedido.model.DAO;

import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // INSERIR
    public void inserir(Pedido pedido) {
        String sql = "INSERT INTO pedido (data_hora, status, valor_total, tipo_pagamento) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(pedido.getDataHora()));
            stmt.setString(2, pedido.getStatus());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getTipoPagamento());
            stmt.executeUpdate();

            System.out.println("Pedido inserido com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir pedido.", e);
        }
    }

    // BUSCAR TODOS
    public List<Pedido> buscarTodos() {
        String sql = "SELECT * FROM pedido";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setId(rs.getInt("id_pedido"));
                p.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                p.setStatus(rs.getString("status"));
                p.setValorTotal(rs.getDouble("valor_total"));
                p.setTipoPagamento(rs.getString("tipo_pagamento"));

                pedidos.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedidos.", e);
        }

        return pedidos;
    }

    // BUSCAR POR ID
    public Pedido buscarPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        Pedido pedido = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido = new Pedido();
                    pedido.setId(rs.getInt("id_pedido"));
                    pedido.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                    pedido.setStatus(rs.getString("status"));
                    pedido.setValorTotal(rs.getDouble("valor_total"));
                    pedido.setTipoPagamento(rs.getString("tipo_pagamento"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pedido por ID.", e);
        }

        return pedido;
    }

    // ATUALIZAR
    public void atualizar(Pedido pedido) {
        String sql = "UPDATE pedido SET data_hora=?, status=?, valor_total=?, tipo_pagamento=? WHERE id_pedido=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(pedido.getDataHora()));
            stmt.setString(2, pedido.getStatus());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getTipoPagamento());
            stmt.setInt(5, pedido.getId());

            stmt.executeUpdate();

            System.out.println("Pedido atualizado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pedido.", e);
        }
    }

    // DELETAR
    public void deletar(int id) {
        String sql = "DELETE FROM pedido WHERE id_pedido=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Pedido deletado com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pedido.", e);
        }
    }
}
