package com.tecdes.pedido.view;

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
    private JButton btnVoltar;

    private final ProdutoService service;

    // ✅ CORREÇÃO: Construtor recebe ProdutoService como parâmetro
    public ProdutoView(ProdutoService produtoService) {
        this.service = produtoService;
        
        inicializarComponentes();
        configurarEventos();
        
        // Carregar produtos automaticamente ao abrir
        listarTodos();
        setVisible(true);

    }

    private void inicializarComponentes() {
        setTitle("📦 Gerenciamento de Produtos");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // ---------- TÍTULO ----------
        JLabel lblTitulo = new JLabel("GERENCIAMENTO DE PRODUTOS");
        lblTitulo.setBounds(300, 10, 300, 30);
        lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);

        // ---------- CAMPOS DE ENTRADA ----------
        JPanel painelCampos = new JPanel();
        painelCampos.setLayout(null);
        painelCampos.setBounds(20, 50, 300, 300);
        painelCampos.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));

        JLabel lblId = new JLabel("ID (para buscar):");
        lblId.setBounds(10, 30, 120, 25);
        painelCampos.add(lblId);

        txtId = new JTextField();
        txtId.setBounds(140, 30, 140, 25);
        painelCampos.add(txtId);

        JLabel lblNome = new JLabel("Nome*:");
        lblNome.setBounds(10, 70, 120, 25);
        painelCampos.add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(140, 70, 140, 25);
        painelCampos.add(txtNome);

        JLabel lblPreco = new JLabel("Preço*:");
        lblPreco.setBounds(10, 110, 120, 25);
        painelCampos.add(lblPreco);

        txtPreco = new JTextField();
        txtPreco.setBounds(140, 110, 140, 25);
        painelCampos.add(txtPreco);

        JLabel lblCategoria = new JLabel("Categoria:");
        lblCategoria.setBounds(10, 150, 120, 25);
        painelCampos.add(lblCategoria);

        txtCategoria = new JTextField();
        txtCategoria.setBounds(140, 150, 140, 25);
        painelCampos.add(txtCategoria);

        JLabel lblDescricao = new JLabel("Descrição:");
        lblDescricao.setBounds(10, 190, 120, 25);
        painelCampos.add(lblDescricao);

        txtDescricao = new JTextArea();
        txtDescricao.setLineWrap(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescricao);
        scrollDesc.setBounds(140, 190, 140, 80);
        painelCampos.add(scrollDesc);

        add(painelCampos);

        // ---------- BOTÕES DE AÇÃO ----------
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(null);
        painelBotoes.setBounds(20, 360, 300, 130);
        painelBotoes.setBorder(BorderFactory.createTitledBorder("Ações"));

        JButton btnSalvar = new JButton("💾 Salvar Novo");
        btnSalvar.setBounds(10, 20, 130, 30);
        painelBotoes.add(btnSalvar);

        JButton btnAtualizar = new JButton("✏️ Atualizar");
        btnAtualizar.setBounds(150, 20, 130, 30);
        painelBotoes.add(btnAtualizar);

        JButton btnExcluir = new JButton("🗑️ Excluir");
        btnExcluir.setBounds(10, 60, 130, 30);
        painelBotoes.add(btnExcluir);

        JButton btnBuscar = new JButton("🔍 Buscar por ID");
        btnBuscar.setBounds(150, 60, 130, 30);
        painelBotoes.add(btnBuscar);

        JButton btnListar = new JButton("📋 Listar Todos");
        btnListar.setBounds(10, 100, 130, 30);
        painelBotoes.add(btnListar);

        JButton btnLimpar = new JButton("🧹 Limpar");
        btnLimpar.setBounds(150, 100, 130, 30);
        painelBotoes.add(btnLimpar);

        add(painelBotoes);

        // ---------- TABELA DE PRODUTOS ----------
        JPanel painelTabela = new JPanel();
        painelTabela.setLayout(null);
        painelTabela.setBounds(350, 50, 520, 400);
        painelTabela.setBorder(BorderFactory.createTitledBorder("Produtos Cadastrados"));

        String[] colunas = {"ID", "Nome", "Preço", "Categoria", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável diretamente
            }
        };
        
        tabela = new JTable(modeloTabela);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                preencherCamposComSelecionado();
            }
        });

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBounds(10, 20, 500, 370);
        painelTabela.add(scrollTabela);

        add(painelTabela);

        // ---------- BOTÃO VOLTAR ----------
        btnVoltar = new JButton("⬅️ Voltar ao Menu Principal");
        btnVoltar.setBounds(350, 460, 200, 35);
        add(btnVoltar);

        // ---------- BOTÃO SAIR ----------
        JButton btnSair = new JButton("❌ Sair");
        btnSair.setBounds(670, 460, 100, 35);
        add(btnSair);
    }

    private void configurarEventos() {
        // Buscar o botão "Voltar" pelo tipo ou adicionar ação diretamente
        for (java.awt.Component comp : getContentPane().getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                if (btn.getText().contains("Voltar")) {
                    btn.addActionListener(e -> {
                        this.dispose(); // Fecha esta janela
                        // O menu principal será mostrado automaticamente
                    });
                }
                if (btn.getText().contains("Sair")) {
                    btn.addActionListener(e -> System.exit(0));
                }
            }
        }

        // Adicionar eventos aos botões de ação
        JPanel painelBotoes = (JPanel) getContentPane().getComponent(2);
        for (java.awt.Component comp : painelBotoes.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                String texto = btn.getText();
                
                if (texto.contains("Salvar")) {
                    btn.addActionListener(e -> salvarProduto());
                } else if (texto.contains("Atualizar")) {
                    btn.addActionListener(e -> atualizarProduto());
                } else if (texto.contains("Excluir")) {
                    btn.addActionListener(e -> excluirProduto());
                } else if (texto.contains("Buscar")) {
                    btn.addActionListener(e -> buscarProduto());
                } else if (texto.contains("Listar")) {
                    btn.addActionListener(e -> listarTodos());
                } else if (texto.contains("Limpar")) {
                    btn.addActionListener(e -> limparCampos());
                }
            }
        }
    }

    // --------------- MÉTODOS DE AÇÃO ---------------

    private void salvarProduto() {
        try {
            // Validação dos campos obrigatórios
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (txtPreco.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preço é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nome = txtNome.getText().trim();
            double preco = Double.parseDouble(txtPreco.getText().trim());
            String categoria = txtCategoria.getText().trim();
            String descricao = txtDescricao.getText().trim();

            service.salvarProduto(nome, preco, categoria, descricao);

            JOptionPane.showMessageDialog(this, "✅ Produto salvo com sucesso!");
            listarTodos();
            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ Preço deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao salvar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarProduto() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID é necessário para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long id = Long.parseLong(txtId.getText().trim());
            String nome = txtNome.getText().trim();
            double preco = Double.parseDouble(txtPreco.getText().trim());
            String categoria = txtCategoria.getText().trim();
            String descricao = txtDescricao.getText().trim();

            service.atualizarProduto(id, nome, preco, categoria, descricao);

            JOptionPane.showMessageDialog(this, "✅ Produto atualizado com sucesso!");
            listarTodos();
            limparCampos();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ ID e Preço devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao atualizar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirProduto() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o ID do produto para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long id = Long.parseLong(txtId.getText().trim());
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Tem certeza que deseja excluir o produto ID " + id + "?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                service.deletarProduto(id);
                JOptionPane.showMessageDialog(this, "✅ Produto excluído com sucesso!");
                listarTodos();
                limparCampos();
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao excluir produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProduto() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o ID do produto para buscar!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long id = Long.parseLong(txtId.getText().trim());
            Produto p = service.buscarPorId(id);

            if (p != null) {
                txtNome.setText(p.getNome());
                txtPreco.setText(String.valueOf(p.getPreco()));
                txtCategoria.setText(p.getCategoria());
                txtDescricao.setText(p.getDescricao());
                JOptionPane.showMessageDialog(this, "✅ Produto encontrado!");
                
                // Selecionar na tabela
                for (int i = 0; i < tabela.getRowCount(); i++) {
                    if (tabela.getValueAt(i, 0).equals(id)) {
                        tabela.setRowSelectionInterval(i, i);
                        tabela.scrollRectToVisible(tabela.getCellRect(i, 0, true));
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "❌ Produto não encontrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "❌ ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao buscar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listarTodos() {
        try {
            modeloTabela.setRowCount(0);
            List<Produto> lista = service.buscarTodos();

            if (lista.isEmpty()) {
                modeloTabela.addRow(new Object[]{"-", "Nenhum produto cadastrado", "-", "-", "-"});
            } else {
                for (Produto p : lista) {
                    modeloTabela.addRow(new Object[]{
                        p.getIdProduto(),
                        p.getNome(),
                        String.format("R$ %.2f", p.getPreco()),
                        p.getCategoria(),
                        p.getDescricao()
                    });
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao carregar produtos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void preencherCamposComSelecionado() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada >= 0) {
            try {
                Object idObj = tabela.getValueAt(linhaSelecionada, 0);
                if (idObj instanceof Long || (idObj instanceof String && !((String) idObj).equals("-"))) {
                    Long id = Long.parseLong(idObj.toString());
                    Produto p = service.buscarPorId(id);
                    
                    if (p != null) {
                        txtId.setText(String.valueOf(p.getIdProduto()));
                        txtNome.setText(p.getNome());
                        txtPreco.setText(String.valueOf(p.getPreco()));
                        txtCategoria.setText(p.getCategoria());
                        txtDescricao.setText(p.getDescricao());
                    }
                }
            } catch (Exception ex) {
                // Ignora erros ao clicar em linha vazia
            }
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtPreco.setText("");
        txtCategoria.setText("");
        txtDescricao.setText("");
        tabela.clearSelection();
    }

    public static void main(String[] args) {
        // Para teste direto
        SwingUtilities.invokeLater(() -> {
            // Em produção, isso viria da Main
            com.tecdes.pedido.repository.ProdutoRepository repo = 
                new com.tecdes.pedido.repository.ProdutoRepositoryImpl();
            ProdutoService service = new ProdutoService(repo);
            new ProdutoView(service);
        });
    }
}