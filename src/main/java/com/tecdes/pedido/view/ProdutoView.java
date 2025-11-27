package com.tecdes.pedido.view;


import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.ProdutoService;

public class ProdutoView extends JFrame {

    private JTextField txtId, txtNome, txtPreco, txtCategoria;
    private JTextArea txtDescricao;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private final ProdutoService service = new ProdutoService();

    public ProdutoView() {
        setTitle("Cadastro de Produtos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // ---------- CAMPOS ----------
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(20, 20, 100, 25);
        add(lblId);

        txtId = new JTextField();
        txtId.setBounds(120, 20, 100, 25);
        add(txtId);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20, 60, 100, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(120, 60, 200, 25);
        add(txtNome);

        JLabel lblPreco = new JLabel("Preço:");
        lblPreco.setBounds(20, 100, 100, 25);
        add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(120, 100, 100, 25);
        add(txtPreco);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(20, 140, 100, 25);
        add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(120, 140, 200, 25);
        add(txtCategoria);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(20, 180, 100, 25);
        add(lblDescricao);

        txtDescricao = new JTextArea();
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        scrollDesc.setBounds(120, 180, 200, 100);
        add(scrollDesc);

        // ---------- BOTÕES ----------
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(20, 300, 100, 30);
        add(btnSalvar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(130, 300, 100, 30);
        add(btnAtualizar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(240, 300, 100, 30);
        add(btnExcluir);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBounds(20, 340, 100, 30);
        add(btnBuscar);

        JButton btnListar = new JButton("Listar Todos");
        btnListar.setBounds(130, 340, 120, 30);
        add(btnListar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.setBounds(260, 340, 80, 30);
        add(btnLimpar);

        // ---------- TABELA ----------
        String[] colunas = {"ID", "Nome", "Preço", "Categoria", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBounds(350, 20, 420, 400);
        add(scrollTabela);

        // ===== EVENTOS =====

        // Salvar
        btnSalvar.addActionListener(e -> salvarProduto());

        // Atualizar
        btnAtualizar.addActionListener(e -> atualizarProduto());

        // Excluir
        btnExcluir.addActionListener(e -> excluirProduto());

        // Buscar
        btnBuscar.addActionListener(e -> buscarProduto());

        // Listar todos
        btnListar.addActionListener(e -> listarTodos());

        // Limpar campos
        btnLimpar.addActionListener(e -> limparCampos());

        setVisible(true);
    }

    // --------------- MÉTODOS DE AÇÃO ---------------

    private void salvarProduto() {
        try {
            String nome = txtNome.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            String categoria = txtCategoria.getText();
            String descricao = txtDescricao.getText();

            service.salvarProduto(nome, preco, categoria, descricao);

            JOptionPane.showMessageDialog(this, "Produto salvo com sucesso!");
            listarTodos();
            limparCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void atualizarProduto() {
        try {
            Long id = Long.parseLong(txtId.getText());
            String nome = txtNome.getText();
            double preco = Double.parseDouble(txtPreco.getText());
            String categoria = txtCategoria.getText();
            String descricao = txtDescricao.getText();

            service.atualizarProduto(id, nome, preco, categoria, descricao);

            JOptionPane.showMessageDialog(this, "Produto atualizado!");
            listarTodos();
            limparCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
        }
    }

    private void excluirProduto() {
        try {
            Long id = Long.parseLong(txtId.getText());
            service.deletarProduto(id);

            JOptionPane.showMessageDialog(this, "Produto excluído!");
            listarTodos();
            limparCampos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void buscarProduto() {
        try {
            Long id = Long.parseLong(txtId.getText());
            Produto p = service.buscarProdutoPorId(id);

            txtNome.setText(p.getNome());
            txtPreco.setText(String.valueOf(p.getPreco()));
            txtCategoria.setText(p.getCategoria());
            txtDescricao.setText(p.getDescricao());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar: " + ex.getMessage());
        }
    }

    private void listarTodos() {
        modeloTabela.setRowCount(0);

        List<Produto> lista = service.buscarTodos();

        for (Produto p : lista) {
            modeloTabela.addRow(new Object[]{
                p.getIdProduto(),
                p.getNome(),
                p.getPreco(),
                p.getCategoria(),
                p.getDescricao()
            });
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtPreco.setText("");
        txtCategoria.setText("");
        txtDescricao.setText("");
    }
}
