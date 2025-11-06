package com.tecdes.pedido.repository;

import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoRepositoryImpl implements PedidoRepository {

    @Override
    public void save(Pedido pedido) {
        String sql = "INSERT INTO pedido (data_hora, status, valor_total, tipo_pagamento) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(pedido.getDataHora()));
            stmt.setString(2, pedido.getStatus());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getTipoPagamento());
            
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pedido findById(int id) {
        Pedido pedido = null;
        String sql = "SELECT id_pedido, data_hora, status, valor_total, tipo_pagamento FROM pedido WHERE id_pedido = ?";
        
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
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id_pedido, data_hora, status, valor_total, tipo_pagamento FROM pedido";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("id_pedido"));
                pedido.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                pedido.setStatus(rs.getString("status"));
                pedido.setValorTotal(rs.getDouble("valor_total"));
                pedido.setTipoPagamento(rs.getString("tipo_pagamento"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    @Override
    public void update(Pedido pedido) {
        String sql = "UPDATE pedido SET data_hora = ?, status = ?, valor_total = ?, tipo_pagamento = ? WHERE id_pedido = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setTimestamp(1, Timestamp.valueOf(pedido.getDataHora()));
            stmt.setString(2, pedido.getStatus());
            stmt.setDouble(3, pedido.getValorTotal());
            stmt.setString(4, pedido.getTipoPagamento());
            stmt.setInt(5, pedido.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
