package com.tecdes.pedido;

import com.tecdes.pedido.view.*;
import com.tecdes.pedido.service.*;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.repository.*;
import javax.swing.*;
import java.util.Scanner;

public class Main {
    
    private static PedidoService pedidoService;
    private static ProdutoService produtoService;
    private static ClienteService clienteService;
    private static ItemPedidoService itemPedidoService;
    private static AvaliacaoService avaliacaoService;
    private static Scanner scanner;
    private static JFrame menuFrame;

    public static void main(String[] args) {
        inicializarServicos();
        scanner = new Scanner(System.in);
        escolherModoInterface();
    }
    
    private static void inicializarServicos() {
        try {
            ProdutoRepository produtoRepo = new ProdutoRepositoryImpl();
            produtoService = new ProdutoService(produtoRepo);
            
            PedidoRepository pedidoRepo = new PedidoRepositoryImpl();
            pedidoService = new PedidoService(pedidoRepo, produtoService);
            
            ClienteRepository clienteRepo = new ClienteRepositoryImpl();
            clienteService = new ClienteService(clienteRepo);
            
            
            itemPedidoService = new ItemPedidoService(); 
            
            try {
                AvaliacaoRepository avaliacaoRepo = new AvaliacaoRepositoryImpl();
                avaliacaoService = new AvaliacaoService(avaliacaoRepo);
            } catch (Exception e) {
                System.out.println("⚠️  AvaliacaoService não disponível: " + e.getMessage());
                avaliacaoService = null;
            }
            
            System.out.println("✅ Serviços inicializados com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar serviços: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void escolherModoInterface() {
        System.out.println("\n=== SISTEMA GESTOR DE PEDIDOS ===");
        System.out.println("1. Interface Gráfica (Swing)");
        System.out.println("2. Interface Console (Terminal)");
        System.out.println("3. Sair");
        System.out.print("Escolha o modo de interface: ");
        
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    iniciarInterfaceGrafica();
                    break;
                case 2:
                    iniciarInterfaceConsole();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    fecharRecursos();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida!");
                    escolherModoInterface();
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            scanner.nextLine();
            escolherModoInterface();
        }
    }
    
    // =============================================
    // INTERFACE GRÁFICA (SWING)
    // =============================================
    private static void iniciarInterfaceGrafica() {
        SwingUtilities.invokeLater(() -> {
            criarMenuPrincipalGrafico();
        });
    }
    
    private static void criarMenuPrincipalGrafico() {
        menuFrame = new JFrame("Sistema Gestor de Pedidos");
        menuFrame.setSize(400, 550);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(null);
        
        JLabel titulo = new JLabel("SISTEMA GESTOR DE PEDIDOS");
        titulo.setBounds(80, 20, 250, 30);
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        menuFrame.add(titulo);
        
        int yPos = 80;
        int buttonHeight = 40;
        int buttonWidth = 300;
        int xPos = 50;
        int spacing = 50;
        
        // 1. Botão Atendente (Console)
        JButton btnAtendente = new JButton("👨‍💼 Menu Atendente (Console)");
        btnAtendente.setBounds(xPos, yPos, buttonWidth, buttonHeight);
        btnAtendente.addActionListener(e -> {
            menuFrame.setVisible(false);
            new AtendenteView().menuPrincipal(); // ✅ Esta View é console
            menuFrame.setVisible(true);
        });
        menuFrame.add(btnAtendente);
        yPos += spacing;
        
        // 2. Botão Pedidos (Swing)
        JButton btnPedidos = new JButton("📋 Gerenciar Pedidos");
        btnPedidos.setBounds(xPos, yPos, buttonWidth, buttonHeight);
        btnPedidos.addActionListener(e -> {
            menuFrame.setVisible(false);
            SwingUtilities.invokeLater(() -> {
                new PedidoView(pedidoService, produtoService);
                menuFrame.setVisible(true);
            });
        });
        menuFrame.add(btnPedidos);
        yPos += spacing;
        
        // 3. Botão Produtos (Swing)
        JButton btnProdutos = new JButton("📦 Gerenciar Produtos");
        btnProdutos.setBounds(xPos, yPos, buttonWidth, buttonHeight);
        btnProdutos.addActionListener(e -> {
            menuFrame.setVisible(false);
            SwingUtilities.invokeLater(() -> {
                new ProdutoView(produtoService);
                menuFrame.setVisible(true);
            });
        });
        menuFrame.add(btnProdutos);
        yPos += spacing;
        
        // 4. Botão Item Pedido (Swing)
        JButton btnItemPedido = new JButton("🛒 Gerenciar Itens do Pedido");
        btnItemPedido.setBounds(xPos, yPos, buttonWidth, buttonHeight);
        btnItemPedido.addActionListener(e -> {
            menuFrame.setVisible(false);
            SwingUtilities.invokeLater(() -> {
                new ItemPedidoView(itemPedidoService, pedidoService, produtoService);
                menuFrame.setVisible(true);
            });
        });
        menuFrame.add(btnItemPedido);
        yPos += spacing;
        
        // 5. Botão Clientes (Swing)
        JButton btnClientes = new JButton("👥 Gerenciar Clientes");
        btnClientes.setBounds(xPos, yPos, buttonWidth, buttonHeight);
        btnClientes.addActionListener(e -> {
            menuFrame.setVisible(false);
            SwingUtilities.invokeLater(() -> {
                new ClienteView(clienteService);
                menuFrame.setVisible(true);
            });
        });
        menuFrame.add(btnClientes);
        yPos += spacing;
        
        // 6. Botão Gerente (Console)
        JButton btnGerente = new JButton("👨‍💼 Menu Gerente (Console)");
        btnGerente.setBounds(xPos, yPos, buttonWidth, buttonHeight);
        btnGerente.addActionListener(e -> {
            menuFrame.setVisible(false);
            new GerenteView().menuPrincipal(); 
            menuFrame.setVisible(true);
        });
        menuFrame.add(btnGerente);
        yPos += spacing;
        
        // 7. Botão Avaliação (Swing - se tiver service)
        JButton btnAvaliacao = new JButton("⭐ Avaliar Pedido");
        btnAvaliacao.setBounds(xPos, yPos, buttonWidth, buttonHeight);
        btnAvaliacao.addActionListener(e -> {
            if (avaliacaoService != null) {
                menuFrame.setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    new AvaliacaoView(avaliacaoService); 
                    menuFrame.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(menuFrame,
                    "Serviço de avaliação não disponível!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        menuFrame.add(btnAvaliacao);
        
        // Botão Voltar
        JButton btnVoltar = new JButton("🔄 Voltar para Seleção de Interface");
        btnVoltar.setBounds(xPos, 420, buttonWidth, buttonHeight);
        btnVoltar.addActionListener(e -> {
            menuFrame.dispose();
            escolherModoInterface();
        });
        menuFrame.add(btnVoltar);
        
        // Botão Sair
        JButton btnSair = new JButton("❌ Sair do Sistema");
        btnSair.setBounds(xPos, 470, buttonWidth, buttonHeight);
        btnSair.addActionListener(e -> {
            fecharRecursos();
            System.exit(0);
        });
        menuFrame.add(btnSair);
        
        menuFrame.setVisible(true);
    }
    
    // =============================================
    // INTERFACE CONSOLE (TERMINAL)
    // =============================================
    private static void iniciarInterfaceConsole() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL (CONSOLE) ===");
            System.out.println("1. 🏪 Menu Atendente");
            System.out.println("2. 📋 Menu Pedidos (Console)");
            System.out.println("3. 📦 Menu Produtos (Console)");
            System.out.println("4. 👥 Menu Clientes (Console)");
            System.out.println("5. 👨‍💼 Menu Gerente");
            System.out.println("6. ⭐ Avaliar Pedido (Console)");
            System.out.println("7. 🧾 Comanda Virtual (Console)");
            System.out.println("8. 🔄 Voltar para Seleção de Interface");
            System.out.println("0. ❌ Sair");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 1:
                        new AtendenteView().menuPrincipal();
                        break;
                    case 2:
                        abrirMenuPedidosConsole();
                        break;
                    case 3:
                        abrirMenuProdutosConsole();
                        break;
                    case 4:
                        abrirMenuClientesConsole();
                        break;
                    case 5:
                        new GerenteView().menuPrincipal();
                        break;
                    case 6: 
                        System.out.println("Acesse a opção 6 no Menu Atendente");
                        new AtendenteView().menuPrincipal();
                        break;
                    case 7:
                        abrirComandaVirtualConsole();
                        break;
                    case 8:
                        escolherModoInterface();
                        return;
                    case 0:
                        System.out.println("Saindo...");
                        fecharRecursos();
                        System.exit(0);
                        break;
                    default:
                        System.err.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println("Erro: Entrada inválida!");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }
    
    // =============================================
    // MÉTODOS AUXILIARES CONSOLE
    // =============================================
    
    private static void abrirMenuPedidosConsole() {
        System.out.println("\n--- MENU PEDIDOS (CONSOLE) ---");
        System.out.println("Funcionalidades disponíveis:");
        System.out.println("1. Buscar pedido por ID");
        System.out.println("2. Ver pedidos recentes");
        System.out.println("3. Voltar");
        System.out.print("Escolha: ");
        
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    buscarPedidoConsole();
                    break;
                case 2:
                    verPedidosRecentesConsole();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            scanner.nextLine();
        }
    }
    
    private static void buscarPedidoConsole() {
        try {
            System.out.print("ID do Pedido: ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            Pedido pedido = pedidoService.buscarPedidoPorId(id);
            
            if (pedido != null) {
                System.out.println("\n" + "=".repeat(50));
                System.out.println("        DETALHES DO PEDIDO");
                System.out.println("=".repeat(50));
                System.out.println("🆔 ID: " + pedido.getIdPedido());
                System.out.println("👤 Cliente ID: " + pedido.getCliente().getIdCliente());
                System.out.println("📊 Status: " + pedido.getStatus());
                System.out.println("💳 Pagamento: " + pedido.getTipoPagamento());
                System.out.println("💰 Valor Total: R$ " + 
                    (pedido.getValorTotal() != null ? String.format("%.2f", pedido.getValorTotal()) : "0.00"));
                System.out.println("=".repeat(50));
            } else {
                System.out.println("❌ Pedido não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    private static void verPedidosRecentesConsole() {
        try {
            // Usando o AtendenteController para pedidos recentes
            com.tecdes.pedido.controller.AtendenteController controller = 
                new com.tecdes.pedido.controller.AtendenteController();
            var pedidos = controller.listarPedidosRecentes();
            
            if (pedidos.isEmpty()) {
                System.out.println("📭 Nenhum pedido registrado.");
                return;
            }
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("              PEDIDOS RECENTES");
            System.out.println("=".repeat(60));
            
            for (Pedido pedido : pedidos) {
                System.out.printf("🆔 #%d | 👤 Cliente: %d | 💰 R$ %.2f | 📊 %s\n",
                    pedido.getIdPedido(),
                    pedido.getCliente().getIdCliente(),
                    pedido.getValorTotal() != null ? pedido.getValorTotal() : 0.0,
                    pedido.getStatus());
            }
            
            System.out.println("=".repeat(60));
            System.out.println("📊 Total: " + pedidos.size() + " pedidos");
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    private static void abrirMenuProdutosConsole() {
        System.out.println("\n--- MENU PRODUTOS (CONSOLE) ---");
        System.out.println("1. Buscar produto por ID");
        System.out.println("2. Listar todos os produtos");
        System.out.println("3. Voltar");
        System.out.print("Escolha: ");
        
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    buscarProdutoConsole();
                    break;
                case 2:
                    listarProdutosConsole();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            scanner.nextLine();
        }
    }
    
    private static void buscarProdutoConsole() {
        try {
            System.out.print("ID do Produto: ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            var produto = produtoService.buscarPorId(id);
            
            if (produto != null) {
                System.out.println("\n" + "=".repeat(40));
                System.out.println("   DETALHES DO PRODUTO");
                System.out.println("=".repeat(40));
                System.out.println("🆔 ID: " + produto.getIdProduto());
                System.out.println("📦 Nome: " + produto.getNome());
                System.out.println("💰 Preço: R$ " + String.format("%.2f", produto.getPreco()));
                if (produto.getCategoria() != null && !produto.getCategoria().isEmpty()) {
                    System.out.println("🏷️  Categoria: " + produto.getCategoria());
                }
                System.out.println("=".repeat(40));
            } else {
                System.out.println("❌ Produto não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    private static void listarProdutosConsole() {
        try {
            var produtos = produtoService.buscarTodos();
            
            if (produtos.isEmpty()) {
                System.out.println("📭 Nenhum produto cadastrado.");
                return;
            }
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("           LISTA DE PRODUTOS");
            System.out.println("=".repeat(60));
            
            for (var produto : produtos) {
                System.out.printf("🆔 #%d | 📦 %-20s | 💰 R$ %-8.2f\n",
                    produto.getIdProduto(),
                    produto.getNome().length() > 20 ? produto.getNome().substring(0, 17) + "..." : produto.getNome(),
                    produto.getPreco());
            }
            
            System.out.println("=".repeat(60));
            System.out.println("📊 Total: " + produtos.size() + " produtos");
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    private static void abrirMenuClientesConsole() {
        System.out.println("\n--- MENU CLIENTES (CONSOLE) ---");
        System.out.println("1. Buscar cliente por ID");
        System.out.println("2. Listar todos os clientes");
        System.out.println("3. Voltar");
        System.out.print("Escolha: ");
        
        try {
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                case 1:
                    buscarClienteConsole();
                    break;
                case 2:
                    listarClientesConsole();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            scanner.nextLine();
        }
    }
    
    private static void buscarClienteConsole() {
        try {
            System.out.print("ID do Cliente: ");
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            var cliente = clienteService.buscarPorId(id);
            
            if (cliente != null) {
                System.out.println("\n" + "=".repeat(40));
                System.out.println("   DETALHES DO CLIENTE");
                System.out.println("=".repeat(40));
                System.out.println("🆔 ID: " + cliente.getIdCliente());
                System.out.println("👤 Nome: " + cliente.getNome());
                System.out.println("📞 Telefone: " + cliente.getFone());
                if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
                    System.out.println("📧 Email: " + cliente.getEmail());
                }
                System.out.println("=".repeat(40));
            } else {
                System.out.println("❌ Cliente não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    private static void listarClientesConsole() {
        try {
            var clientes = clienteService.buscarTodos();
            
            if (clientes.isEmpty()) {
                System.out.println("📭 Nenhum cliente cadastrado.");
                return;
            }
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("           LISTA DE CLIENTES");
            System.out.println("=".repeat(60));
            
            for (var cliente : clientes) {
                System.out.printf("🆔 #%d | 👤 %-20s | 📞 %-15s\n",
                    cliente.getIdCliente(),
                    cliente.getNome().length() > 20 ? cliente.getNome().substring(0, 17) + "..." : cliente.getNome(),
                    cliente.getFone() != null ? cliente.getFone() : "-");
            }
            
            System.out.println("=".repeat(60));
            System.out.println("📊 Total: " + clientes.size() + " clientes");
            
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    private static void abrirComandaVirtualConsole() {
        System.out.println("\n--- COMANDA VIRTUAL ---");
        try {
            System.out.print("ID do Pedido: ");
            Long idPedido = scanner.nextLong();
            scanner.nextLine();
            
            try {
                Pedido pedido = pedidoService.buscarPedidoPorId(idPedido);
                // ✅ USANDO A CLASSE CORRETA (não é View, é só método de exibição)
                new ComandaVirtualView().exibirComanda(pedido);
            } catch (Exception e) {
                System.err.println("❌ Erro ao buscar pedido: " + e.getMessage());
                System.out.println("Verifique se o ID do pedido existe.");
            }
            
        } catch (java.util.InputMismatchException e) {
            System.err.println("❌ Erro: ID deve ser um número válido.");
            scanner.nextLine();
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
    
    // Método para fechar recursos
    public static void fecharRecursos() {
        if (scanner != null) {
            scanner.close();
        }
        System.out.println("Recursos fechados com sucesso!");
    }
}