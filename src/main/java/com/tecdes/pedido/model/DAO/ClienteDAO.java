package com.tecdes.pedido.model.DAO;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Cliente;


public class ClienteDAO {


    // INSERIR CLIENTE
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, fone) VALUES (?, ?)";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getFone());
            stmt.executeUpdate();


            System.out.println("Cliente inserido com sucesso!");


        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir cliente: " + e.getMessage(), e);
        }
    }


    // BUSCAR TODOS
    public List<Cliente> buscarTodos() {
        String sql = "SELECT id_cliente, nome, fone FROM cliente";
        List<Cliente> clientes = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {


            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getLong("id_cliente"));
                c.setNome(rs.getString("nome"));
                c.setFone(rs.getString("fone"));
                clientes.add(c);
            }


        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar clientes: " + e.getMessage(), e);
        }


        return clientes;
    }


    // BUSCAR POR ID
    public Cliente buscarPorId(Long id) {
        String sql = "SELECT id_cliente, nome, fone FROM cliente WHERE id_cliente = ?";
        Cliente cliente = null;


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getLong("id_cliente"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setFone(rs.getString("fone"));
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente por ID: " + e.getMessage(), e);
        }


        return cliente;
    }


    // ATUALIZAR CLIENTE
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, fone = ? WHERE id_cliente = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getFone());
            stmt.setLong(3, cliente.getIdCliente());
            stmt.executeUpdate();


            System.out.println("Cliente atualizado com sucesso!");


        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }


    // DELETAR CLIENTE
    public void deletar(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, id);
            stmt.executeUpdate();


            System.out.println("Cliente removido com sucesso!");


        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }
}
