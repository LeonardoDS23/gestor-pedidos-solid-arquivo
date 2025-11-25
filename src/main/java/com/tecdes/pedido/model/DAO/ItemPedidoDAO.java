package com.tecdes.pedido.model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Produto;

public class ItemPedidoDAO {

    private Connection conn;

    public ItemPedidoDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(ItemPedido item) throws SQLException {
        String sql = "INSERT INTO item_pedido (id_produto, quantidade, preco_unitario, observacoes) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, item.getProduto().getIdProduto());
        ps.setInt(2, item.getQuantidade());
        ps.setDouble(3, item.getPrecoUnitario());
        ps.setString(4, item.getObservacoes());
        ps.executeUpdate();
    }

    public List<ItemPedido> listar() throws SQLException {
        List<ItemPedido> lista = new ArrayList<>();

        String sql = "SELECT * FROM item_pedido";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ItemPedido item = new ItemPedido();
            Produto p = new Produto();

            item.setIdItem(rs.getInt("id"));
            p.setIdProduto(rs.getInt("id_produto"));
            item.setProduto(p);

            item.setQuantidade(rs.getInt("quantidade"));
            item.setPrecoUnitario(rs.getDouble("preco_unitario"));
            item.setObservacoes(rs.getString("observacoes"));

            lista.add(item);
        }
        return lista;
    }

    public ItemPedido buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM item_pedido WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            ItemPedido item = new ItemPedido();
            Produto p = new Produto();

            item.setIdItem(rs.getInt("id"));
            p.setIdProduto(rs.getInt("id_produto"));
            item.setProduto(p);

            item.setQuantidade(rs.getInt("quantidade"));
            item.setPrecoUnitario(rs.getDouble("preco_unitario"));
            item.setObservacoes(rs.getString("observacoes"));

            return item;
        }
        return null;
    }

    public void atualizar(ItemPedido item) throws SQLException {
        String sql = "UPDATE item_pedido SET id_produto=?, quantidade=?, preco_unitario=?, observacoes=? WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, item.getProduto().getIdProduto());
        ps.setInt(2, item.getQuantidade());
        ps.setDouble(3, item.getPrecoUnitario());
        ps.setString(4, item.getObservacoes());
        ps.setInt(5, item.getIdItem());

        ps.executeUpdate();
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM item_pedido WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
