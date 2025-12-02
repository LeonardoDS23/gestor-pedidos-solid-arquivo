package com.tecdes.pedido.view;


import com.tecdes.pedido.service.ClienteService;
import com.tecdes.pedido.model.entity.Cliente;


import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ClienteView extends JFrame {


    private JTextField txtId, txtNome, txtTelefone, txtEmail;
    private JTable tabelaClientes;
    private javax.swing.table.DefaultTableModel modeloTabela;
    private JButton btnVoltar;


    private final ClienteService clienteService;


    public ClienteView(ClienteService clienteService) {
        this.clienteService = clienteService;
       
        setTitle("👥 Gerenciamento de Clientes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       
        inicializarComponentes();
        carregarClientes();
        setVisible(true);
    }


    private void inicializarComponentes() {
        getContentPane().setLayout(null);
       
        // TÍTULO
        JLabel lblTitulo = new JLabel("GERENCIAMENTO DE CLIENTES");
        lblTitulo.setBounds(250, 10, 300, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);
       
        // PAINEL DE DADOS
        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setBounds(20, 50, 350, 250);
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
       
        JLabel lblId = new JLabel("ID (buscar):");
        lblId.setBounds(10, 30, 120, 25);
        painelDados.add(lblId);
       
        txtId = new JTextField();
        txtId.setBounds(140, 30, 180, 25);
        painelDados.add(txtId);
       
        JLabel lblNome = new JLabel("Nome*:");
        lblNome.setBounds(10, 70, 120, 25);
        painelDados.add(lblNome);
       
        txtNome = new JTextField();
        txtNome.setBounds(140, 70, 180, 25);
        painelDados.add(txtNome);
       
        JLabel lblTelefone = new JLabel("Telefone*:");
        lblTelefone.setBounds(10, 110, 120, 25);
        painelDados.add(lblTelefone);
       
        txtTelefone = new JTextField();
        txtTelefone.setBounds(140, 110, 180, 25);
        painelDados.add(txtTelefone);
       
        JLabel lblEmail = new JLabel("E-mail:");
        lblEmail.setBounds(10, 150, 120, 25);
        painelDados.add(lblEmail);
       
        txtEmail = new JTextField();
        txtEmail.setBounds(140, 150, 180, 25);
        painelDados.add(txtEmail);
       
        add(painelDados);
       
        // BOTÕES DE AÇÃO
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new GridLayout(2, 3, 10, 10));
        painelBotoes.setBounds(20, 310, 350, 120);
        painelBotoes.setBorder(BorderFactory.createTitledBorder("Ações"));
       
        JButton btnSalvar = new JButton("💾 Salvar");
        btnSalvar.addActionListener(e -> salvarCliente());
        painelBotoes.add(btnSalvar);
       
        JButton btnAtualizar = new JButton("✏️ Atualizar");
        btnAtualizar.addActionListener(e -> atualizarCliente());
        painelBotoes.add(btnAtualizar);
       
        JButton btnExcluir = new JButton("🗑️ Excluir");
        btnExcluir.addActionListener(e -> excluirCliente());
        painelBotoes.add(btnExcluir);
       
        JButton btnBuscar = new JButton("🔍 Buscar");
        btnBuscar.addActionListener(e -> buscarCliente());
        painelBotoes.add(btnBuscar);
       
        JButton btnListar = new JButton("📋 Listar");
        btnListar.addActionListener(e -> carregarClientes());
        painelBotoes.add(btnListar);
       
        JButton btnLimpar = new JButton("🧹 Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        painelBotoes.add(btnLimpar);
       
        add(painelBotoes);
       
        // TABELA DE CLIENTES
        JPanel painelTabela = new JPanel();
        painelTabela.setLayout(null);
        painelTabela.setBounds(400, 50, 360, 400);
        painelTabela.setBorder(BorderFactory.createTitledBorder("Clientes Cadastrados"));
       
        String[] colunas = {"ID", "Nome", "Telefone", "E-mail"};
        modeloTabela = new javax.swing.table.DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
       
        tabelaClientes = new JTable(modeloTabela);
        tabelaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaClientes.getSelectedRow() != -1) {
                preencherCamposComSelecionado();
            }
        });
       
        JScrollPane scrollTabela = new JScrollPane(tabelaClientes);
        scrollTabela.setBounds(10, 20, 340, 370);
        painelTabela.add(scrollTabela);
       
        add(painelTabela);
       
        // BOTÃO VOLTAR
        btnVoltar = new JButton("⬅️ Voltar ao Menu Principal");
        btnVoltar.setBounds(20, 440, 200, 35);
        btnVoltar.addActionListener(e -> this.dispose());
        add(btnVoltar);
    }
   
    private void salvarCliente() {
        try {
            if (txtNome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            if (txtTelefone.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Telefone é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            // ✅ AGORA FUNCIONA COM O NOVO MÉTODO
            clienteService.salvarCliente(
                txtNome.getText().trim(),
                txtTelefone.getText().trim(),
                txtEmail.getText().trim()
            );
           
            JOptionPane.showMessageDialog(this, "✅ Cliente salvo com sucesso!");
            carregarClientes();
            limparCampos();
           
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao salvar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    private void buscarCliente() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o ID do cliente!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
           
            Long id = Long.parseLong(txtId.getText().trim());
           
            // ✅ AGORA FUNCIONA COM O NOVO MÉTODO buscarPorId()
            Cliente cliente = clienteService.buscarPorId(id);
           
            if (cliente != null) {
                txtNome.setText(cliente.getNome());
                txtTelefone.setText(cliente.getFone());
               
                // ✅ AGORA TEMOS getEmail()
                txtEmail.setText(cliente.getEmail() != null ? cliente.getEmail() : "");
               
                // Selecionar na tabela
                for (int i = 0; i < tabelaClientes.getRowCount(); i++) {
                    if (tabelaClientes.getValueAt(i, 0).equals(id)) {
                        tabelaClientes.setRowSelectionInterval(i, i);
                        break;
                    }
                }
               
                JOptionPane.showMessageDialog(this, "✅ Cliente encontrado!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Cliente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
           
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao buscar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    private void atualizarCliente() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID é necessário para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
           
            Long id = Long.parseLong(txtId.getText().trim());
            String nome = txtNome.getText().trim();
            String telefone = txtTelefone.getText().trim();
            String email = txtEmail.getText().trim();
           
            if (nome.isEmpty() || telefone.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e telefone são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            // ✅ AGORA FUNCIONA COM O NOVO MÉTODO
            clienteService.atualizarCliente(id, nome, telefone, email);
           
            JOptionPane.showMessageDialog(this, "✅ Cliente atualizado com sucesso!");
            carregarClientes();
            limparCampos();
           
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao atualizar cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    private void excluirCliente() {
        try {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o ID do cliente para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
           
            Long id = Long.parseLong(txtId.getText().trim());
           
            int confirm = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o cliente ID " + id + "?",
                "Confirmação de Exclusão",
                JOptionPane.YES_NO_OPTION);
           
            if (confirm == JOptionPane.YES_OPTION) {
                // ✅ MÉTODO JÁ EXISTE
                clienteService.excluirCliente(id);
                JOptionPane.showMessageDialog(this, "✅ Cliente excluído com sucesso!");
                carregarClientes();
                limparCampos();
            }
           
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao excluir cliente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    private void carregarClientes() {
        try {
            modeloTabela.setRowCount(0);
            List<Cliente> clientes = clienteService.buscarTodos();
           
            if (clientes.isEmpty()) {
                modeloTabela.addRow(new Object[]{"-", "Nenhum cliente cadastrado", "-", "-"});
            } else {
                for (Cliente cliente : clientes) {
                    modeloTabela.addRow(new Object[]{
                        cliente.getIdCliente(),
                        cliente.getNome(),
                        cliente.getFone(),
                        cliente.getEmail() != null ? cliente.getEmail() : ""
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao carregar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    private void preencherCamposComSelecionado() {
        int linhaSelecionada = tabelaClientes.getSelectedRow();
        if (linhaSelecionada >= 0) {
            try {
                Object idObj = tabelaClientes.getValueAt(linhaSelecionada, 0);
                if (idObj instanceof Long || (idObj instanceof String && !((String) idObj).equals("-"))) {
                    Long id = Long.parseLong(idObj.toString());
                   
                    // ✅ AGORA FUNCIONA
                    Cliente cliente = clienteService.buscarPorId(id);
                   
                    if (cliente != null) {
                        txtId.setText(String.valueOf(cliente.getIdCliente()));
                        txtNome.setText(cliente.getNome());
                        txtTelefone.setText(cliente.getFone());
                        txtEmail.setText(cliente.getEmail() != null ? cliente.getEmail() : "");
                    }
                }
            } catch (Exception e) {
                // Ignora erros ao clicar em linha vazia
            }
        }
    }
   
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        tabelaClientes.clearSelection();
    }
}

