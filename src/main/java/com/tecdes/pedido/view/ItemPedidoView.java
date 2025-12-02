package com.tecdes.pedido.view;


import javax.swing.*;
import java.awt.*;


import com.tecdes.pedido.service.ItemPedidoService;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.service.ProdutoService;
import com.tecdes.pedido.model.entity.ItemPedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.model.entity.Pedido;


public class ItemPedidoView extends JFrame {


    private JTextField txtPedidoId, txtProdutoId, txtQtd, txtPreco;
    private JTextArea txtObs;
    private JButton btnVoltar, btnSair;


    private final ItemPedidoService itemPedidoService;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;


    public ItemPedidoView(ItemPedidoService itemPedidoService, PedidoService pedidoService, ProdutoService produtoService) {
        this.itemPedidoService = itemPedidoService;
        this.pedidoService = pedidoService;
        this.produtoService = produtoService;
       
        setTitle("🛒 Gerenciar Itens de Pedido");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
       
        inicializarComponentes();
        setVisible(true);
    }


    private void inicializarComponentes() {
        // TÍTULO
        JLabel lblTitulo = new JLabel("GERENCIAMENTO DE ITENS DO PEDIDO");
        lblTitulo.setBounds(100, 10, 300, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);


        // PAINEL DE DADOS
        JPanel painelDados = new JPanel();
        painelDados.setLayout(null);
        painelDados.setBounds(20, 50, 450, 250);
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados do Item"));


        JLabel lblPedidoId = new JLabel("ID do Pedido:");
        lblPedidoId.setBounds(10, 30, 120, 25);
        painelDados.add(lblPedidoId);


        txtPedidoId = new JTextField();
        txtPedidoId.setBounds(140, 30, 150, 25);
        painelDados.add(txtPedidoId);


        JLabel lblProdutoId = new JLabel("ID do Produto:");
        lblProdutoId.setBounds(10, 70, 120, 25);
        painelDados.add(lblProdutoId);


        txtProdutoId = new JTextField();
        txtProdutoId.setBounds(140, 70, 150, 25);
        painelDados.add(txtProdutoId);


        JLabel lblQtd = new JLabel("Quantidade:");
        lblQtd.setBounds(10, 110, 120, 25);
        painelDados.add(lblQtd);


        txtQtd = new JTextField();
        txtQtd.setBounds(140, 110, 150, 25);
        txtQtd.setText("1");
        painelDados.add(txtQtd);


        JLabel lblPreco = new JLabel("Preço Unitário:");
        lblPreco.setBounds(10, 150, 120, 25);
        painelDados.add(lblPreco);


        txtPreco = new JTextField();
        txtPreco.setBounds(140, 150, 150, 25);
        painelDados.add(txtPreco);


        JLabel lblObs = new JLabel("Observações:");
        lblObs.setBounds(10, 190, 120, 25);
        painelDados.add(lblObs);


        txtObs = new JTextArea();
        txtObs.setLineWrap(true);
        JScrollPane scrollObs = new JScrollPane(txtObs);
        scrollObs.setBounds(140, 190, 150, 50);
        painelDados.add(scrollObs);


        add(painelDados);


        // BOTÕES DE AÇÃO
        JButton btnBuscarProduto = new JButton("🔍 Buscar Produto");
        btnBuscarProduto.setBounds(300, 70, 140, 25);
        btnBuscarProduto.addActionListener(e -> buscarProduto());
        painelDados.add(btnBuscarProduto);


        JButton btnCalcular = new JButton("💰 Calcular Valor");
        btnCalcular.setBounds(300, 110, 140, 25);
        btnCalcular.addActionListener(e -> calcularValor());
        painelDados.add(btnCalcular);


        // BOTÕES PRINCIPAIS
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBounds(20, 310, 450, 100);


        JButton btnSalvar = new JButton("💾 Salvar Item");
        btnSalvar.addActionListener(e -> salvarItem());
        painelBotoes.add(btnSalvar);


        JButton btnLimpar = new JButton("🧹 Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        painelBotoes.add(btnLimpar);


        btnVoltar = new JButton("⬅️ Voltar");
        btnVoltar.addActionListener(e -> this.dispose());
        painelBotoes.add(btnVoltar);


        btnSair = new JButton("❌ Sair");
        btnSair.addActionListener(e -> System.exit(0));
        painelBotoes.add(btnSair);


        add(painelBotoes);
    }


    private void buscarProduto() {
        try {
            if (txtProdutoId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Digite o ID do produto primeiro!");
                return;
            }
           
            Long idProduto = Long.parseLong(txtProdutoId.getText().trim());
            Produto produto = produtoService.buscarPorId(idProduto);
           
            if (produto != null) {
                txtPreco.setText(String.valueOf(produto.getPreco()));
                JOptionPane.showMessageDialog(this,
                    "Produto encontrado!\n" +
                    "Nome: " + produto.getNome() + "\n" +
                    "Preço: R$ " + produto.getPreco(),
                    "Informação do Produto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
           
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID do produto deve ser um número!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void calcularValor() {
        try {
            if (txtQtd.getText().trim().isEmpty() || txtPreco.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha quantidade e preço!");
                return;
            }
           
            int qtd = Integer.parseInt(txtQtd.getText().trim());
            double preco = Double.parseDouble(txtPreco.getText().trim());
            double total = qtd * preco;
           
            JOptionPane.showMessageDialog(this,
                "Cálculo do Item:\n" +
                "Quantidade: " + qtd + "\n" +
                "Preço Unitário: R$ " + String.format("%.2f", preco) + "\n" +
                "Total do Item: R$ " + String.format("%.2f", total),
                "Cálculo", JOptionPane.INFORMATION_MESSAGE);
           
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantidade e preço devem ser números!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void salvarItem() {
        try {
            // Validações
            if (txtPedidoId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID do Pedido é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            if (txtProdutoId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID do Produto é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            if (txtQtd.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Quantidade é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            Long pedidoId = Long.parseLong(txtPedidoId.getText().trim());
            Long produtoId = Long.parseLong(txtProdutoId.getText().trim());
            int quantidade = Integer.parseInt(txtQtd.getText().trim());
            double precoUnitario = txtPreco.getText().trim().isEmpty() ?
                produtoService.buscarPorId(produtoId).getPreco() :
                Double.parseDouble(txtPreco.getText().trim());
           
            // Verificar se pedido existe
            Pedido pedido = pedidoService.buscarPedidoPorId(pedidoId);
            if (pedido == null) {
                JOptionPane.showMessageDialog(this, "Pedido não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            // Criar item
            ItemPedido item = new ItemPedido();
           
            Produto produto = new Produto();
            produto.setIdProduto(produtoId);
            item.setProduto(produto);
           
            item.setQuantidade(quantidade);
            item.setPrecoUnitario(precoUnitario);
            // item.setObservacoes(txtObs.getText().trim()); // Remover se não existir na classe
           
            // Salvar via service
            itemPedidoService.adicionarItemAoPedido(pedidoId, item);
           
            JOptionPane.showMessageDialog(this,
                "✅ Item adicionado ao pedido #" + pedidoId + " com sucesso!\n" +
                "Produto: " + produtoService.buscarPorId(produtoId).getNome() + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Valor Total: R$ " + String.format("%.2f", quantidade * precoUnitario),
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
           
            limparCampos();
           
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "IDs e quantidades devem ser números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Erro ao salvar item: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void limparCampos() {
        txtPedidoId.setText("");
        txtProdutoId.setText("");
        txtQtd.setText("1");
        txtPreco.setText("");
        txtObs.setText("");
    }
}

