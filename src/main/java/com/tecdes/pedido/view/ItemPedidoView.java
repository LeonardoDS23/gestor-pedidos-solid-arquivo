package com.tecdes.pedido.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tecdes.pedido.controller.ItemPedidoController;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Produto;

public class ItemPedidoView extends JFrame {

    private JTextField txtId;
    private JTextField txtProdutoId;
    private JTextField txtQtd;
    private JTextField txtPreco;
    private JTextArea txtObs;

    private final ItemPedidoController controller = new ItemPedidoController();

    public ItemPedidoView() {
        setTitle("Cadastro Item Pedido");
        setSize(350, 350);
        setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 120, 20);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(150, 20, 150, 20);
        add(txtId);

        JLabel lblProduto = new JLabel("Produto ID:");
        lblProduto.setBounds(20, 60, 120, 20);
        add(lblProduto);

        txtProdutoId = new JTextField();
        txtProdutoId.setBounds(150, 60, 150, 20);
        add(txtProdutoId);

        JLabel lblQtd = new JLabel("Quantidade:");
        lblQtd.setBounds(20, 100, 120, 20);
        add(lblQtd);

        txtQtd = new JTextField();
        txtQtd.setBounds(150, 100, 150, 20);
        add(txtQtd);

        JLabel lblPreco = new JLabel("Preço Unitário:");
        lblPreco.setBounds(20, 140, 120, 20);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(150, 140, 150, 20);
        add(txtPreco);

        JLabel lblObs = new JLabel("Observações:");
        lblObs.setBounds(20, 180, 120, 20);
        add(lblObs);

        txtObs = new JTextArea();
        txtObs.setBounds(150, 180, 150, 60);
        add(txtObs);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(100, 260, 120, 30);
        add(btnSalvar);

        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ItemPedido item = new ItemPedido();
                item.setIdItem(Integer.parseInt(txtId.getText()));

                Produto prod = new Produto();
                prod.setIdProduto(Integer.parseInt(txtProdutoId.getText()));
                item.setProduto(prod);

                item.setQuantidade(Integer.parseInt(txtQtd.getText()));
                item.setPrecoUnitario(Double.parseDouble(txtPreco.getText()));
                item.setObservacoes(txtObs.getText());

                controller.salvar(item);

                JOptionPane.showMessageDialog(null, "Item salvo com sucesso!");
            }
        });

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
