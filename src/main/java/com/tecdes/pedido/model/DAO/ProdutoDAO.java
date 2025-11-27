package com.tecdes.pedido.model.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.pedido.config.ConnectionFactory;
import com.tecdes.pedido.model.entity.Produto;

public class ProdutoDAO {

    // 🔹 INSERIR PRODUTO
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, categoria, descricao) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getCategoria());
            stmt.setString(4, produto.getDescricao());
            stmt.executeUpdate();

            System.out.println("Produto inserido com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir produto.", e);
        }
    }

    // 🔹 BUSCAR TODOS
    public List<Produto> buscarTodos() {
        String sql = "SELECT id_produto, nome, preco, categoria, descricao FROM produto";
        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getLong("id_produto")); // Mudou para getLong
                p.setNome(rs.getString("nome"));
                p.setPreco(rs.getDouble("preco"));
                p.setCategoria(rs.getString("categoria"));
                p.setDescricao(rs.getString("descricao"));
                produtos.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produtos.", e);
        }

        return produtos;
    }

    // 🔹 BUSCAR POR ID
    public Produto buscarPorId(Long id) {
        String sql = "SELECT id_produto, nome, preco, categoria, descricao FROM produto WHERE id_produto = ?";
        Produto produto = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setIdProduto(rs.getLong("id_produto")); // Mudou para getLong
                    produto.setNome(rs.getString("nome"));
                    produto.setPreco(rs.getDouble("preco"));
                    produto.setCategoria(rs.getString("categoria"));
                    produto.setDescricao(rs.getString("descricao"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar produto por ID.", e);
        }

        return produto;
    }

    // 🔹 ATUALIZAR
    public void atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, categoria = ?, descricao = ? WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, produto.getNome());
                stmt.setDouble(2, produto.getPreco());
                stmt.setString(3, produto.getCategoria());
                stmt.setString(4, produto.getDescricao());
                stmt.setLong(5, produto.getIdProduto()); // Mudou para setLong
                stmt.executeUpdate();

            System.out.println("Produto atualizado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar produto.", e);
        }
    }

    // 🔹 DELETAR
    public void deletar(Long id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

            System.out.println("Produto deletado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar produto.", e);
        }
    }
}
