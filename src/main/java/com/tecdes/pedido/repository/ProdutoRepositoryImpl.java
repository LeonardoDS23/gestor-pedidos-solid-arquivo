package com.tecdes.pedido.repository;

import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepository {

    
    @Override
    public void save(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, preco, categoria) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setString(4, produto.getCategoria());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setIdProduto(rs.getInt(1));
                }
            }
            System.out.println(" Produto cadastrado com ID: " + produto.getIdProduto());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    @Override
    public Produto findById(int id) {
        Produto produto = null;
        String sql = "SELECT id_produto, nome, descricao, preco, categoria FROM produto WHERE id_produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto(
                        rs.getInt("id_produto"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getString("categoria")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produto;
    }

    
    @Override
    public List<Produto> findAll() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT id_produto, nome, descricao, preco, categoria FROM produto";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                produtos.add(new Produto(
                    rs.getInt("id_produto"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("preco"),
                    rs.getString("categoria")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return produtos;
    }
    
    // Método UPDATE
    @Override
    public void update(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, descricao = ?, preco = ?, categoria = ? WHERE id_produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setString(4, produto.getCategoria());
            stmt.setInt(5, produto.getIdProduto()); 
            stmt.executeUpdate();   
            System.out.println("Produto ID " + produto.getIdProduto() + " atualizado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Método DELETE
    @Override
    public void delete(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Produto ID " + id + " removido.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}