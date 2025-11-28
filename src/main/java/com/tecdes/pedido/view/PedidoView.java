package com.tecdes.pedido.view;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.service.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class PedidoView extends JFrame {

    private JTextField txtIdPedido;
    private JTextField txtIdCliente;
    private JTextField txtTipoPagamento;
    private JTextField txtStatus;
    private JTextField txtValorTotal;

    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;

    private PedidoService pedidoService;
    private ProdutoService produtoService;

    public PedidoView(PedidoService pedidoService, ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;

        setTitle("Gerenciamento de Pedidos");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        inicializarComponentes();
    }

    private void inicializarComponentes() {

        JLabel lblIdPedido = new JLabel("ID Pedido:");
        lblIdPedido.setBounds(20, 20, 120, 25);
        add(lblIdPedido);

        txtIdPedido = new JTextField();
        txtIdPedido.setBounds(140, 20, 100, 25);
        add(txtIdPedido);

        JLabel lblCliente = new JLabel("ID Cliente:");
        lblCliente.setBounds(20, 55, 120, 25);
        add(lblCliente);

        txtIdCliente = new JTextField();
        txtIdCliente.setBounds(140, 55, 100, 25);
        add(txtIdCliente);

        JLabel lblTipoPagamento = new JLabel("Tipo Pagamento:");
        lblTipoPagamento.setBounds(20, 90, 120, 25);
        add(lblTipoPagamento);

        txtTipoPagamento = new JTextField();
        txtTipoPagamento.setBounds(140, 90, 150, 25);
        add(txtTipoPagamento);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(20, 125, 120, 25);
        add(lblStatus);

        txtStatus = new JTextField();
        txtStatus.setBounds(140, 125, 150, 25);
        txtStatus.setEditable(false);
        add(txtStatus);

        JLabel lblValorTotal = new JLabel("Valor Total:");
        lblValorTotal.setBounds(20, 160, 120, 25);
        add(lblValorTotal);

        txtValorTotal = new JTextField();
        txtValorTotal.setBounds(140, 160, 150, 25);
        txtValorTotal.setEditable(false);
        add(txtValorTotal);

        // ============================================================
        // TABELA DE ITENS
        // ============================================================
        modeloTabela = new DefaultTableModel(new Object[]{"ID Produto", "Nome", "Qtd", "Preço Unitário"}, 0);
        tabelaItens = new JTable(modeloTabela);

        JScrollPane pane = new JScrollPane(tabelaItens);
        pane.setBounds(320, 20, 400, 250);
        add(pane);

        JButton btnAddItem = new JButton("Adicionar Item");
        btnAddItem.setBounds(320, 280, 150, 30);
        btnAddItem.addActionListener(e -> adicionarItem());
        add(btnAddItem);

        // ============================================================
        // BOTÕES PRINCIPAIS
        // ============================================================
        JButton btnFinalizar = new JButton("Finalizar Pedido");
        btnFinalizar.setBounds(20, 220, 150, 30);
        btnFinalizar.addActionListener(e -> finalizarPedido());
        add(btnFinalizar);

        JButton btnBuscar = new JButton("Buscar Pedido");
        btnBuscar.setBounds(20, 260, 150, 30);
        btnBuscar.addActionListener(e -> buscarPedido());
        add(btnBuscar);

        JButton btnCancelar = new JButton("Cancelar Pedido");
        btnCancelar.setBounds(20, 300, 150, 30);
        btnCancelar.addActionListener(e -> cancelarPedido());
        add(btnCancelar);

        JButton btnAtualizar = new JButton("Atualizar Status");
        btnAtualizar.setBounds(20, 340, 150, 30);
        btnAtualizar.addActionListener(e -> atualizarStatus());
        add(btnAtualizar);
    }

    // ===================================================================
    // ADICIONAR ITEM NA TABELA
    // ===================================================================
    private void adicionarItem() {
        String idProdutoStr = JOptionPane.showInputDialog("ID do Produto:");
        String qtdStr = JOptionPane.showInputDialog("Quantidade:");

        try {
            Long idProduto = Long.parseLong(idProdutoStr);
            int qtd = Integer.parseInt(qtdStr);

            Produto produto = produtoService.buscarPorId(idProduto);

            modeloTabela.addRow(new Object[]{
                    produto.getIdProduto(),
                    produto.getNome(),
                    qtd,
                    produto.getPreco()
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar item: " + e.getMessage());
        }
    }

    // ===================================================================
    // FINALIZAR PEDIDO
    // ===================================================================
    private void finalizarPedido() {
        try {
            Pedido pedido = new Pedido();

            Cliente cliente = new Cliente();
            cliente.setIdCliente(Long.parseLong(txtIdCliente.getText()));
            pedido.setCliente(cliente);

            pedido.setTipoPagamento(txtTipoPagamento.getText());
            pedido.setProdutos(new ArrayList<>());

            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                Long idProd = (Long) modeloTabela.getValueAt(i, 0);
                int qtd = (int) modeloTabela.getValueAt(i, 2);
                double preco = (double) modeloTabela.getValueAt(i, 3);

                Produto produto = new Produto();
                produto.setIdProduto(idProd);

                ItemPedido item = new ItemPedido();
                item.setProduto(produto);
                item.setQuantidade(qtd);
                item.setPrecoUnitario(preco);

                pedido.getProdutos().add(item);
            }

            Pedido salvo = pedidoService.finalizarPedido(pedido);

            txtIdPedido.setText(String.valueOf(salvo.getIdPedido()));
            txtStatus.setText(salvo.getStatus());
            txtValorTotal.setText(String.valueOf(salvo.getValorTotal()));

            JOptionPane.showMessageDialog(this, "Pedido finalizado com sucesso!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao finalizar: " + e.getMessage());
        }
    }

    // ===================================================================
    // BUSCAR PEDIDO
    // ===================================================================
    private void buscarPedido() {
        try {
            Long id = Long.parseLong(txtIdPedido.getText());

            Pedido pedido = pedidoService.buscarPedidoPorId(id);

            txtIdCliente.setText(String.valueOf(pedido.getCliente().getIdCliente()));
            txtTipoPagamento.setText(pedido.getTipoPagamento());
            txtStatus.setText(pedido.getStatus());
            txtValorTotal.setText(String.valueOf(pedido.getValorTotal()));

            modeloTabela.setRowCount(0);

            for (ItemPedido item : pedido.getProdutos()) {
                modeloTabela.addRow(new Object[]{
                        item.getProduto().getIdProduto(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario()
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar pedido: " + e.getMessage());
        }
    }

    // ===================================================================
    // CANCELAR PEDIDO
    // ===================================================================
    private void cancelarPedido() {
        try {
            Long id = Long.parseLong(txtIdPedido.getText());
            Pedido pedido = pedidoService.cancelarPedido(id);

            txtStatus.setText(pedido.getStatus());

            JOptionPane.showMessageDialog(this, "Pedido cancelado!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }

    // ===================================================================
    // ATUALIZAR STATUS
    // ===================================================================
    private void atualizarStatus() {
        try {
            Long id = Long.parseLong(txtIdPedido.getText());
            String novoStatus = JOptionPane.showInputDialog("Novo Status:");

            Pedido pedido = pedidoService.atualizarStatus(id, novoStatus);

            txtStatus.setText(pedido.getStatus());

            JOptionPane.showMessageDialog(this, "Status atualizado!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        }
    }
}
