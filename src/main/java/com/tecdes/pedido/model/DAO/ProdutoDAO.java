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

    public void inserir(Produto produto) {
        String sql = "INSERT INTO Produto (nome, preco) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = ConnectionFactory.getConnection(); // 🌟 Obtém(pede) a conexão com o banco
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.executeUpdate(); // Executa a inserção
            System.out.println("Produto inserido com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir Produto.", e);
        } finally {
            // Garante o fechamento dos recursos
            ConnectionFactory.closeConnection(conn);
        }
    }

    public List<Produto> buscarTodos() {
        String sql = "SELECT id_produto, nome, preco FROM Produto";
        List<Produto> produto = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery(); // Executa a consulta

            while (rs.next()) {
                Produto m = new Produto(sql, 0);
                m.setIdProduto(rs.getInt("id_produto"));
                m.setNome(rs.getString("nome"));
                m.setPreco(rs.getDouble("preco"));
                produto.add(m);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Produto.", e);
        } finally {
            // Fechar RS, STMT, CONN (boas práticas)
            ConnectionFactory.closeConnection(conn);
        }
        return produto;
    }
}
