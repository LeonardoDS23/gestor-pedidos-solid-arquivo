package com.tecdes.pedido.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableModel;

import com.tecdes.pedido.controller.ClienteController;
import com.tecdes.pedido.model.entity.Cliente;

public class ClienteView extends JFrame{
    
    private final ClienteController controller = new ClienteController();

    private JTextField txtNome;
    private JTextField txtFone;
    private JButton btnSalvar;

public ClienteView() {
    super("Cadastro de Pedidos - Lanchonete App");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));

    setupCadastroPainel();

    setupListagemPainel();

    pack();
    setLocationRelativeTo(null);
  }

  private void setupCadastroPainel() {
    JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

    txtNome = new JTextField(20);
    txtFone = new JTextField(13);
    btnSalvar = new JButton("Salvar Pedido");

    panel.add(new JLabel("Nome:"));
    panel.add(txtNome);
    panel.add(new JLabel("Telefone (13 dígitos):"));
    panel.add(txtFone);
    panel.add(new JLabel());
    panel.add(btnSalvar);

    add(panel, BorderLayout.NORTH);

    btnSalvar.addActionListener(e -> salvarPedido());
  }

  private void setupListagemPainel() {
    DefaultTableColumnModel tableModel = new DefaultTableColumnModel();
    JTable tblClientes = new JTable((TableModel) tableModel);
    JScrollPane scrollPane = new JScrollPane(tblClientes);

    add(new JLabel("Pedidos Cadastrados: "), BorderLayout.CENTER);
    add(scrollPane, BorderLayout.SOUTH);

    carregarTabelaClientes();
  }

  private void salvarPedido(){
    String nome = txtNome.getText();
    String fone = txtFone.getText();

    try {
      controller.salvar(nome, fone);
      JOptionPane.showMessageDialog(this, "Pedido salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

      txtNome.getText();
      txtFone.getText();

      carregarTabelaClientes();

    } catch (IllegalArgumentException ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro de Validação", JOptionPane.WARNING_MESSAGE );
    } catch (RuntimeException ex) {
      JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getCause().getMessage(), "Erro de Persistência", JOptionPane.ERROR_MESSAGE);
      ex.printStackTrace();
    }
  }

  private void carregarTabelaClientes() {
    Object tableModel;
        ((Object) tableModel).setRowCount(0);

    try {
      List<Cliente> clientes = controller.listarTodos();
      for (Cliente c: clientes) {
        DefaultTableColumnModel.addRow(new Object [] {c.getIdCliente(), c.getNome(), c.getFone()});
      }
    } catch (RuntimeException ex) {
      JOptionPane.showMessageDialog(this, "Erro ao carregar lista: " + ex.getCause(). getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
    }
  }
}
