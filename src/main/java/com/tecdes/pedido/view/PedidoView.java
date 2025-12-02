package com.tecdes.pedido.view;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.service.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class PedidoView extends JFrame {

    private JTextField txtIdPedido;
    private JTextField txtIdCliente;
    private JComboBox<String> cbTipoPagamento;
    private JTextField txtStatus;
    private JTextField txtValorTotal;
    private JTextArea txtObservacoes;

    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;

    private PedidoService pedidoService;
    private ProdutoService produtoService;

    public PedidoView(PedidoService pedidoService, ProdutoService produtoService) {
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;

        setTitle("📋 Gerenciamento de Pedidos");
        setSize(850, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        getContentPane().setLayout(null);
        
        // ============================================================
        // CABEÇALHO
        // ============================================================
        JLabel lblTitulo = new JLabel("PEDIDOS - CONTROLE E GERENCIAMENTO");
        lblTitulo.setBounds(250, 10, 350, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);
        
        // ============================================================
        // PAINEL DE DADOS DO PEDIDO
        // ============================================================
        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setBounds(20, 50, 350, 300);
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados do Pedido"));
        
        JLabel lblIdPedido = new JLabel("ID Pedido:");
        lblIdPedido.setBounds(10, 30, 120, 25);
        painelDados.add(lblIdPedido);

        txtIdPedido = new JTextField();
        txtIdPedido.setBounds(140, 30, 180, 25);
        txtIdPedido.setEditable(false);
        painelDados.add(txtIdPedido);

        JLabel lblCliente = new JLabel("ID Cliente:");
        lblCliente.setBounds(10, 70, 120, 25);
        painelDados.add(lblCliente);

        txtIdCliente = new JTextField();
        txtIdCliente.setBounds(140, 70, 180, 25);
        painelDados.add(txtIdCliente);

        JLabel lblTipoPagamento = new JLabel("Tipo Pagamento:");
        lblTipoPagamento.setBounds(10, 110, 120, 25);
        painelDados.add(lblTipoPagamento);

        String[] tiposPagamento = {"Cartão", "Dinheiro", "PIX", "Cartão Débito", "Cartão Crédito"};
        cbTipoPagamento = new JComboBox<>(tiposPagamento);
        cbTipoPagamento.setBounds(140, 110, 180, 25);
        painelDados.add(cbTipoPagamento);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(10, 150, 120, 25);
        painelDados.add(lblStatus);

        txtStatus = new JTextField();
        txtStatus.setBounds(140, 150, 180, 25);
        txtStatus.setEditable(false);
        txtStatus.setBackground(new Color(240, 240, 240));
        painelDados.add(txtStatus);

        JLabel lblValorTotal = new JLabel("Valor Total:");
        lblValorTotal.setBounds(10, 190, 120, 25);
        painelDados.add(lblValorTotal);

        txtValorTotal = new JTextField();
        txtValorTotal.setBounds(140, 190, 180, 25);
        txtValorTotal.setEditable(false);
        txtValorTotal.setBackground(new Color(240, 240, 240));
        painelDados.add(txtValorTotal);
        
        JLabel lblObservacoes = new JLabel("Observações:");
        lblObservacoes.setBounds(10, 230, 120, 25);
        painelDados.add(lblObservacoes);
        
        txtObservacoes = new JTextArea();
        txtObservacoes.setLineWrap(true);
        JScrollPane scrollObservacoes = new JScrollPane(txtObservacoes);
        scrollObservacoes.setBounds(140, 230, 180, 60);
        painelDados.add(scrollObservacoes);
        
        add(painelDados);

        // ============================================================
        // PAINEL DE ITENS DO PEDIDO
        // ============================================================
        JPanel painelItens = new JPanel();
        painelItens.setLayout(null);
        painelItens.setBounds(400, 50, 400, 300);
        painelItens.setBorder(BorderFactory.createTitledBorder("Itens do Pedido"));

        String[] colunas = {"ID Produto", "Nome", "Qtd", "Preço Unit.", "Subtotal"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável diretamente
            }
        };
        
        tabelaItens = new JTable(modeloTabela);
        tabelaItens.getColumnModel().getColumn(4).setPreferredWidth(80);
        
        JScrollPane scrollTabela = new JScrollPane(tabelaItens);
        scrollTabela.setBounds(10, 20, 380, 220);
        painelItens.add(scrollTabela);
        
        JButton btnAddItem = new JButton("➕ Adicionar Item");
        btnAddItem.setBounds(10, 250, 150, 30);
        btnAddItem.addActionListener(e -> adicionarItem());
        painelItens.add(btnAddItem);
        
        JButton btnRemoverItem = new JButton("➖ Remover Item");
        btnRemoverItem.setBounds(170, 250, 150, 30);
        btnRemoverItem.addActionListener(e -> removerItemSelecionado());
        painelItens.add(btnRemoverItem);
        
        JButton btnLimparItens = new JButton("🗑️ Limpar Tudo");
        btnLimparItens.setBounds(240, 250, 150, 30);
        btnLimparItens.addActionListener(e -> limparItens());
        painelItens.add(btnLimparItens);
        
        add(painelItens);

        // ============================================================
        // PAINEL DE BOTÕES DE AÇÃO
        // ============================================================
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(null);
        painelBotoes.setBounds(20, 370, 780, 150);
        painelBotoes.setBorder(BorderFactory.createTitledBorder("Ações"));
        
        JButton btnNovoPedido = new JButton("📝 Novo Pedido");
        btnNovoPedido.setBounds(10, 30, 150, 35);
        btnNovoPedido.addActionListener(e -> novoPedido());
        painelBotoes.add(btnNovoPedido);

        JButton btnFinalizar = new JButton("✅ Finalizar Pedido");
        btnFinalizar.setBounds(170, 30, 150, 35);
        btnFinalizar.addActionListener(e -> finalizarPedido());
        painelBotoes.add(btnFinalizar);

        JButton btnBuscar = new JButton("🔍 Buscar Pedido");
        btnBuscar.setBounds(330, 30, 150, 35);
        btnBuscar.addActionListener(e -> buscarPedido());
        painelBotoes.add(btnBuscar);

        JButton btnCancelar = new JButton("❌ Cancelar Pedido");
        btnCancelar.setBounds(490, 30, 150, 35);
        btnCancelar.addActionListener(e -> cancelarPedido());
        painelBotoes.add(btnCancelar);
        
        JButton btnAtualizar = new JButton("🔄 Atualizar Status");
        btnAtualizar.setBounds(10, 80, 150, 35);
        btnAtualizar.addActionListener(e -> atualizarStatus());
        painelBotoes.add(btnAtualizar);
        
        JButton btnImprimir = new JButton("🖨️ Imprimir Comanda");
        btnImprimir.setBounds(170, 80, 150, 35);
        btnImprimir.addActionListener(e -> imprimirComanda());
        painelBotoes.add(btnImprimir);
        
        JButton btnLimpar = new JButton("🧹 Limpar Campos");
        btnLimpar.setBounds(330, 80, 150, 35);
        btnLimpar.addActionListener(e -> limparCampos());
        painelBotoes.add(btnLimpar);
        
        JButton btnVoltar = new JButton("⬅️ Voltar ao Menu");
        btnVoltar.setBounds(490, 80, 150, 35);
        btnVoltar.addActionListener(e -> voltarAoMenu());
        painelBotoes.add(btnVoltar);
        
        add(painelBotoes);
        
        // ============================================================
        // RODAPÉ
        // ============================================================
        JLabel lblRodape = new JLabel("Total de Itens: 0 | Valor Total: R$ 0,00");
        lblRodape.setBounds(20, 530, 400, 20);
        lblRodape.setFont(new Font("Arial", Font.ITALIC, 12));
        add(lblRodape);
    }
    
    // ===================================================================
    // MÉTODOS AUXILIARES
    // ===================================================================
    
    private void atualizarValorTotal() {
        double total = 0;
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            int qtd = (int) modeloTabela.getValueAt(i, 2);
            double precoUnit = (double) modeloTabela.getValueAt(i, 3);
            double subtotal = qtd * precoUnit;
            modeloTabela.setValueAt(subtotal, i, 4);
            total += subtotal;
        }
        txtValorTotal.setText(String.format("R$ %.2f", total));
    }
    
    private void novoPedido() {
        limparCampos();
        txtStatus.setText("EM ABERTO");
        txtIdPedido.setText("AUTO");
        txtObservacoes.setText("");
        JOptionPane.showMessageDialog(this, "Novo pedido criado! Adicione os itens.");
    }
    
    private void limparItens() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Limpar todos os itens do pedido?", 
            "Confirmação", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            modeloTabela.setRowCount(0);
            atualizarValorTotal();
        }
    }
    
    private void removerItemSelecionado() {
        int linha = tabelaItens.getSelectedRow();
        if (linha >= 0) {
            modeloTabela.removeRow(linha);
            atualizarValorTotal();
            JOptionPane.showMessageDialog(this, "Item removido!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um item para remover.");
        }
    }
    
    private void imprimirComanda() {
        if (modeloTabela.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Adicione itens ao pedido primeiro!");
            return;
        }
        
        StringBuilder comanda = new StringBuilder();
        comanda.append("=== COMANDA ===\n");
        comanda.append("ID Pedido: ").append(txtIdPedido.getText()).append("\n");
        comanda.append("Cliente: ").append(txtIdCliente.getText()).append("\n");
        comanda.append("Forma Pagto: ").append(cbTipoPagamento.getSelectedItem()).append("\n");
        comanda.append("\nITENS:\n");
        
        for (int i = 0; i < modeloTabela.getRowCount(); i++) {
            comanda.append(String.format("%d. %s - %d x R$ %.2f = R$ %.2f\n",
                i + 1,
                modeloTabela.getValueAt(i, 1),
                modeloTabela.getValueAt(i, 2),
                modeloTabela.getValueAt(i, 3),
                modeloTabela.getValueAt(i, 4)
            ));
        }
        
        comanda.append("\nTOTAL: ").append(txtValorTotal.getText()).append("\n");
        comanda.append("Observações: ").append(txtObservacoes.getText()).append("\n");
        comanda.append("=================");
        
        JOptionPane.showMessageDialog(this, comanda.toString(), "Comanda", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void voltarAoMenu() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja voltar ao menu principal?\nPedido não finalizado será perdido.", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }

    // ===================================================================
    // ADICIONAR ITEM NA TABELA
    // ===================================================================
    private void adicionarItem() {
        try {
            // Diálogo para entrada de dados
            JPanel panel = new JPanel(new GridLayout(3, 2));
            JTextField txtIdProduto = new JTextField();
            JTextField txtQuantidade = new JTextField("1");
            JTextField txtPrecoUnitario = new JTextField();
            
            panel.add(new JLabel("ID do Produto:"));
            panel.add(txtIdProduto);
            panel.add(new JLabel("Quantidade:"));
            panel.add(txtQuantidade);
            panel.add(new JLabel("Preço Unitário (opcional):"));
            panel.add(txtPrecoUnitario);
            
            int result = JOptionPane.showConfirmDialog(this, panel, 
                "Adicionar Item", JOptionPane.OK_CANCEL_OPTION);
            
            if (result == JOptionPane.OK_OPTION) {
                Long idProduto = Long.parseLong(txtIdProduto.getText().trim());
                int qtd = Integer.parseInt(txtQuantidade.getText().trim());
                
                if (qtd <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero!");
                    return;
                }
                
                Produto produto = produtoService.buscarPorId(idProduto);
                
                double precoUnit;
                if (txtPrecoUnitario.getText().trim().isEmpty()) {
                    precoUnit = produto.getPreco();
                } else {
                    precoUnit = Double.parseDouble(txtPrecoUnitario.getText().trim());
                }
                
                double subtotal = qtd * precoUnit;
                
                modeloTabela.addRow(new Object[]{
                    produto.getIdProduto(),
                    produto.getNome(),
                    qtd,
                    precoUnit,
                    subtotal
                });
                
                atualizarValorTotal();
                JOptionPane.showMessageDialog(this, "✅ Item adicionado com sucesso!");
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Erro: ID e Quantidade devem ser números válidos!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao adicionar item: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================================================================
    // FINALIZAR PEDIDO
    // ===================================================================
    private void finalizarPedido() {
        try {
            // Validações
            if (txtIdCliente.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ ID do Cliente é obrigatório!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (modeloTabela.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "❌ Adicione pelo menos um item ao pedido!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Pedido pedido = new Pedido();

            Cliente cliente = new Cliente();
            cliente.setIdCliente(Long.parseLong(txtIdCliente.getText().trim()));
            pedido.setCliente(cliente);

            pedido.setTipoPagamento(cbTipoPagamento.getSelectedItem().toString());
            pedido.setProdutos(new ArrayList<>());
            pedido.setObservacoes(txtObservacoes.getText().trim());

            for (int i = 0; i < modeloTabela.getRowCount(); i++) {
                Long idProd = Long.parseLong(modeloTabela.getValueAt(i, 0).toString());
                int qtd = Integer.parseInt(modeloTabela.getValueAt(i, 2).toString());
                double preco = Double.parseDouble(modeloTabela.getValueAt(i, 3).toString());

                Produto produto = new Produto();
                produto.setIdProduto(idProd);
                produto.setNome(modeloTabela.getValueAt(i, 1).toString());

                ItemPedido item = new ItemPedido();
                item.setProduto(produto);
                item.setQuantidade(qtd);
                item.setPrecoUnitario(preco);

                pedido.getProdutos().add(item);
            }

            Pedido salvo = pedidoService.finalizarPedido(pedido);

            txtIdPedido.setText(String.valueOf(salvo.getIdPedido()));
            txtStatus.setText(salvo.getStatus());
            txtValorTotal.setText(String.format("R$ %.2f", salvo.getValorTotal()));

            JOptionPane.showMessageDialog(this, 
                "✅ Pedido #" + salvo.getIdPedido() + " finalizado com sucesso!\n" +
                "Valor Total: R$ " + String.format("%.2f", salvo.getValorTotal()),
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ ID do Cliente deve ser um número válido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao finalizar pedido: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================================================================
    // BUSCAR PEDIDO
    // ===================================================================
    private void buscarPedido() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "Digite o ID do Pedido:");
            if (idStr == null || idStr.trim().isEmpty()) return;
            
            Long id = Long.parseLong(idStr.trim());
            Pedido pedido = pedidoService.buscarPedidoPorId(id);

            if (pedido == null) {
                JOptionPane.showMessageDialog(this, "❌ Pedido não encontrado!", 
                    "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            limparCampos();
            
            txtIdPedido.setText(String.valueOf(pedido.getIdPedido()));
            txtIdCliente.setText(String.valueOf(pedido.getCliente().getIdCliente()));
            
            // Configurar tipo de pagamento
            for (int i = 0; i < cbTipoPagamento.getItemCount(); i++) {
                if (cbTipoPagamento.getItemAt(i).equalsIgnoreCase(pedido.getTipoPagamento())) {
                    cbTipoPagamento.setSelectedIndex(i);
                    break;
                }
            }
            
            txtStatus.setText(pedido.getStatus());
            txtValorTotal.setText(String.format("R$ %.2f", pedido.getValorTotal()));
            if (pedido.getObservacoes() != null) {
                txtObservacoes.setText(pedido.getObservacoes());
            }

            modeloTabela.setRowCount(0);

            for (ItemPedido item : pedido.getProdutos()) {
                modeloTabela.addRow(new Object[]{
                    item.getProduto().getIdProduto(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getPrecoUnitario(),
                    item.getQuantidade() * item.getPrecoUnitario()
                });
            }

            JOptionPane.showMessageDialog(this, "✅ Pedido carregado com sucesso!");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ ID deve ser um número válido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao buscar pedido: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================================================================
    // CANCELAR PEDIDO
    // ===================================================================
    private void cancelarPedido() {
        try {
            if (txtIdPedido.getText().isEmpty() || txtIdPedido.getText().equals("AUTO")) {
                JOptionPane.showMessageDialog(this, "❌ Nenhum pedido selecionado para cancelar!", 
                    "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Long id = Long.parseLong(txtIdPedido.getText().trim());
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja cancelar o pedido #" + id + "?\nEsta ação não pode ser desfeita.",
                "Confirmar Cancelamento",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                Pedido pedido = pedidoService.cancelarPedido(id);
                txtStatus.setText(pedido.getStatus());
                JOptionPane.showMessageDialog(this, "✅ Pedido #" + id + " cancelado com sucesso!");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ ID do pedido inválido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao cancelar pedido: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===================================================================
    // ATUALIZAR STATUS
    // ===================================================================
    private void atualizarStatus() {
        try {
            if (txtIdPedido.getText().isEmpty() || txtIdPedido.getText().equals("AUTO")) {
                JOptionPane.showMessageDialog(this, "❌ Nenhum pedido selecionado!", 
                    "Erro", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Long id = Long.parseLong(txtIdPedido.getText().trim());
            
            String[] statusOptions = {"EM ABERTO", "EM PREPARO", "PRONTO", "ENTREGUE", "CANCELADO"};
            String novoStatus = (String) JOptionPane.showInputDialog(this,
                "Selecione o novo status:",
                "Atualizar Status",
                JOptionPane.QUESTION_MESSAGE,
                null,
                statusOptions,
                txtStatus.getText());
            
            if (novoStatus != null && !novoStatus.trim().isEmpty()) {
                Pedido pedido = pedidoService.atualizarStatus(id, novoStatus);
                txtStatus.setText(pedido.getStatus());
                JOptionPane.showMessageDialog(this, "✅ Status atualizado para: " + novoStatus);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ ID do pedido inválido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao atualizar status: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ===================================================================
    // LIMPAR CAMPOS
    // ===================================================================
    private void limparCampos() {
        txtIdPedido.setText("");
        txtIdCliente.setText("");
        cbTipoPagamento.setSelectedIndex(0);
        txtStatus.setText("");
        txtValorTotal.setText("");
        txtObservacoes.setText("");
        modeloTabela.setRowCount(0);
    }
}