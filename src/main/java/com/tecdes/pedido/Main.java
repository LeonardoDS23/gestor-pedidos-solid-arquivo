package com.tecdes.pedido;


import com.tecdes.pedido.view.*;
import com.tecdes.pedido.service.*;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.repository.*;
import javax.swing.*;
import java.util.Scanner;
import java.util.List;


public class Main {
   
    private static PedidoService pedidoService;
    private static ProdutoService produtoService;
    private static ClienteService clienteService;
    private static ItemPedidoService itemPedidoService;
    private static AvaliacaoService avaliacaoService;
    private static Scanner scanner;
    private static JFrame menuFrame;


    public static void main(String[] args) {
        System.out.println("🚀 INICIANDO SISTEMA GESTOR DE PEDIDOS");
        System.out.println("======================================");
       
        // Teste rápido do sistema
        testarSistemaBasico();
       
        inicializarServicos();
        scanner = new Scanner(System.in);
        escolherModoInterface();
    }
   
    private static void testarSistemaBasico() {
        System.out.println("\n🔍 TESTE DO SISTEMA BÁSICO:");
       
        // Testa Swing
        System.out.print("1. Testando Swing... ");
        try {
            SwingUtilities.invokeAndWait(() -> {
                JFrame teste = new JFrame("Teste");
                teste.setSize(1, 1);
                teste.dispose();
            });
            System.out.println("✅ OK");
        } catch (Exception e) {
            System.err.println("❌ FALHOU: " + e.getMessage());
        }
       
        // Testa ProdutoRepository
        System.out.print("2. Testando ProdutoRepository... ");
        try {
            ProdutoRepositoryImpl repo = new ProdutoRepositoryImpl();
            System.out.println("✅ OK - " + repo.findAll().size() + " produtos");
        } catch (Exception e) {
            System.err.println("❌ FALHOU: " + e.getMessage());
        }
    }
   
    private static void inicializarServicos() {
        System.out.println("\n🔄 INICIALIZANDO SERVIÇOS:");
       
        try {
            System.out.print("1. ProdutoService... ");
            ProdutoRepository produtoRepo = new ProdutoRepositoryImpl();
            produtoService = new ProdutoService(produtoRepo);
            System.out.println("✅ OK");
           
            System.out.print("2. PedidoService... ");
            PedidoRepository pedidoRepo = new PedidoRepositoryImpl();
            pedidoService = new PedidoService(pedidoRepo, produtoService);
            System.out.println("✅ OK");
           
            System.out.print("3. ClienteService... ");
            ClienteRepository clienteRepo = new ClienteRepositoryImpl();
            clienteService = new ClienteService(clienteRepo);
            System.out.println("✅ OK");
           
            System.out.print("4. ItemPedidoService... ");
            itemPedidoService = new ItemPedidoService();
            System.out.println("✅ OK");
           
            System.out.print("5. AvaliacaoService... ");
            try {
                AvaliacaoRepository avaliacaoRepo = new AvaliacaoRepositoryImpl();
                avaliacaoService = new AvaliacaoService(avaliacaoRepo);
                System.out.println("✅ OK");
            } catch (Exception e) {
                System.out.println("⚠️  INDISPONÍVEL: " + e.getMessage());
                avaliacaoService = null;
            }
           
            System.out.println("\n🎉 TODOS OS SERVIÇOS INICIALIZADOS!");
           
            // Testa funcionalidade básica
            testarFuncionalidadeServicos();
           
        } catch (Exception e) {
            System.err.println("\n❌ ERRO CRÍTICO ao inicializar serviços: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
   
    private static void testarFuncionalidadeServicos() {
        System.out.println("\n🧪 TESTANDO FUNCIONALIDADE:");
       
        try {
            // Testa ProdutoService
            List<com.tecdes.pedido.model.entity.Produto> produtos = produtoService.buscarTodos();
            System.out.println("📦 Produtos: " + produtos.size() + " encontrados");
           
            // Testa ClienteService  
            List<com.tecdes.pedido.model.entity.Cliente> clientes = clienteService.buscarTodos();
            System.out.println("👥 Clientes: " + clientes.size() + " encontrados");
           
        } catch (Exception e) {
            System.err.println("⚠️  Teste falhou: " + e.getMessage());
        }
    }
   
    private static void escolherModoInterface() {
        System.out.println("\n" + repetir("=", 50));
        System.out.println("       SISTEMA GESTOR DE PEDIDOS");
        System.out.println(repetir("=", 50));
        System.out.println("1. Interface Gráfica (Swing)");
        System.out.println("2. Interface Console (Terminal)");
        System.out.println("3. Sair");
        System.out.print("\nEscolha o modo de interface: ");
       
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
                    System.out.println("👋 Saindo...");
                    fecharRecursos();
                    System.exit(0);
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
                    escolherModoInterface();
            }
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
            scanner.nextLine();
            escolherModoInterface();
        }
    }
   
    // =============================================
    // INTERFACE GRÁFICA (SWING)
    // =============================================
    private static void iniciarInterfaceGrafica() {
        System.out.println("\n🖥️  Iniciando interface gráfica...");
        SwingUtilities.invokeLater(() -> {
            criarMenuPrincipalGrafico();
        });
    }
   
    private static void criarMenuPrincipalGrafico() {
        System.out.println("🎨 Criando menu gráfico...");
       
        menuFrame = new JFrame("Sistema Gestor de Pedidos");
        menuFrame.setSize(400, 600);
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
        JButton btnAtendente = criarBotao("👨‍💼 Menu Atendente (Console)", xPos, yPos, buttonWidth, buttonHeight);
        btnAtendente.addActionListener(e -> {
            System.out.println("📞 Abrindo Menu Atendente...");
            menuFrame.setVisible(false);
            new AtendenteView().menuPrincipal();
            menuFrame.setVisible(true);
        });
        menuFrame.add(btnAtendente);
        yPos += spacing;
       
        // 2. Botão Pedidos (Swing)
        JButton btnPedidos = criarBotao("📋 Gerenciar Pedidos", xPos, yPos, buttonWidth, buttonHeight);
        btnPedidos.addActionListener(e -> {
            System.out.println("📋 Abrindo PedidoView...");
            abrirJanelaComSeguranca(() -> new PedidoView(pedidoService, produtoService));
        });
        menuFrame.add(btnPedidos);
        yPos += spacing;
       
        // 3. Botão Produtos (Swing) - COM DIAGNÓSTICO
        JButton btnProdutos = criarBotao("📦 Gerenciar Produtos", xPos, yPos, buttonWidth, buttonHeight);
        btnProdutos.addActionListener(e -> {
            System.out.println("\n🎯 CLIQUE NO BOTÃO PRODUTOS DETECTADO!");
            System.out.println("🔍 Estado atual:");
            System.out.println("   - produtoService: " + (produtoService != null ? "OK" : "NULL"));
            System.out.println("   - Thread: " + Thread.currentThread().getName());
           
            if (produtoService == null) {
                JOptionPane.showMessageDialog(menuFrame,
                    "ERRO: Serviço de produtos não disponível!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
           
            // Testa o serviço antes de abrir
            try {
                List<com.tecdes.pedido.model.entity.Produto> produtos = produtoService.buscarTodos();
                System.out.println("   - Produtos no BD: " + produtos.size());
            } catch (Exception ex) {
                System.err.println("   - ERRO ao testar serviço: " + ex.getMessage());
            }
           
            abrirJanelaComSeguranca(() -> new ProdutoView(produtoService));
        });
        menuFrame.add(btnProdutos);
        yPos += spacing;
       
        // 4. Botão Item Pedido (Swing)
        JButton btnItemPedido = criarBotao("🛒 Gerenciar Itens do Pedido", xPos, yPos, buttonWidth, buttonHeight);
        btnItemPedido.addActionListener(e -> {
            System.out.println("🛒 Abrindo ItemPedidoView...");
            abrirJanelaComSeguranca(() -> new ItemPedidoView(itemPedidoService, pedidoService, produtoService));
        });
        menuFrame.add(btnItemPedido);
        yPos += spacing;
       
        // 5. Botão Clientes (Swing)
        JButton btnClientes = criarBotao("👥 Gerenciar Clientes", xPos, yPos, buttonWidth, buttonHeight);
        btnClientes.addActionListener(e -> {
            System.out.println("👥 Abrindo ClienteView...");
            abrirJanelaComSeguranca(() -> new ClienteView(clienteService));
        });
        menuFrame.add(btnClientes);
        yPos += spacing;
       
        // 6. Botão Gerente (Console)
        JButton btnGerente = criarBotao("👔 Menu Gerente (Console)", xPos, yPos, buttonWidth, buttonHeight);
        btnGerente.addActionListener(e -> {
            System.out.println("👔 Abrindo Menu Gerente...");
            menuFrame.setVisible(false);
            new GerenteView().menuPrincipal();
            menuFrame.setVisible(true);
        });
        menuFrame.add(btnGerente);
        yPos += spacing;
       
        // 7. Botão Avaliação (Swing)
        JButton btnAvaliacao = criarBotao("⭐ Avaliar Pedido", xPos, yPos, buttonWidth, buttonHeight);
        btnAvaliacao.addActionListener(e -> {
            if (avaliacaoService != null) {
                System.out.println("⭐ Abrindo AvaliacaoView...");
                abrirJanelaComSeguranca(() -> new AvaliacaoView(avaliacaoService));
            } else {
                JOptionPane.showMessageDialog(menuFrame,
                    "Serviço de avaliação não disponível!",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        menuFrame.add(btnAvaliacao);
       
        // Botão Voltar
        JButton btnVoltar = criarBotao("🔄 Voltar para Seleção", xPos, 450, buttonWidth, buttonHeight);
        btnVoltar.addActionListener(e -> {
            menuFrame.dispose();
            escolherModoInterface();
        });
        menuFrame.add(btnVoltar);
       
        // Botão Sair
        JButton btnSair = criarBotao("❌ Sair do Sistema", xPos, 505, buttonWidth, buttonHeight);
        btnSair.addActionListener(e -> {
            fecharRecursos();
            System.exit(0);
        });
        menuFrame.add(btnSair);
       
        menuFrame.setVisible(true);
        System.out.println("✅ Menu gráfico criado e visível!");
    }
   
    // =============================================
    // MÉTODOS AUXILIARES SWING
    // =============================================
   
    private static JButton criarBotao(String texto, int x, int y, int width, int height) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, width, height);
        return botao;
    }
   
  private static void abrirJanelaComSeguranca(java.util.concurrent.Callable<JFrame> criadorJanela) {
    menuFrame.setVisible(false);
   
    SwingUtilities.invokeLater(() -> {
        try {
            JFrame novaJanela = criadorJanela.call();
            System.out.println("✅ Janela criada com sucesso: " + novaJanela.getTitle());
           
            // Centralizar e mostrar a nova janela
            novaJanela.setLocationRelativeTo(null);
            novaJanela.setVisible(true);
           
            // Quando fechar, volta ao menu
            novaJanela.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    System.out.println("↩️  Voltando ao menu principal...");
                    menuFrame.setVisible(true);
                }
               
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.out.println("⚠️  Janela sendo fechada...");
                }
            });
           
            // Fechamento correto
            novaJanela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           
        } catch (Exception ex) {
            System.err.println("❌ ERRO ao criar janela: " + ex.getMessage());
            ex.printStackTrace();
           
            JOptionPane.showMessageDialog(null,
                "Erro ao abrir janela:\n" + ex.getClass().getSimpleName() + "\n" + ex.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
           
            menuFrame.setVisible(true);
        }
    });
}

   
    // =============================================
    // INTERFACE CONSOLE (TERMINAL)
    // =============================================
    private static void iniciarInterfaceConsole() {
        int opcao;
        do {
            System.out.println("\n" + repetir("=", 50));
            System.out.println("       MENU PRINCIPAL (CONSOLE)");
            System.out.println(repetir("=", 50));
            System.out.println("1. 🏪 Menu Atendente");
            System.out.println("2. 📋 Menu Pedidos");
            System.out.println("3. 📦 Menu Produtos");
            System.out.println("4. 👥 Menu Clientes");
            System.out.println("5. 👔 Menu Gerente");
            System.out.println("6. 🧾 Comanda Virtual");
            System.out.println("7. 🔄 Voltar para Seleção de Interface");
            System.out.println("0. ❌ Sair");
            System.out.print("\nEscolha uma opção: ");
           
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
                        abrirComandaVirtualConsole();
                        break;
                    case 7:
                        escolherModoInterface();
                        return;
                    case 0:
                        System.out.println("👋 Saindo...");
                        fecharRecursos();
                        System.exit(0);
                        break;
                    default:
                        System.err.println("❌ Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println("❌ Erro: Entrada inválida!");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);
    }
   
    // =============================================
    // MÉTODOS AUXILIARES CONSOLE
    // =============================================
   
    private static void abrirMenuPedidosConsole() {
        System.out.println("\n" + repetir("-", 40));
        System.out.println("       MENU PEDIDOS");
        System.out.println(repetir("-", 40));
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
                    System.out.println("❌ Opção inválida!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
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
                System.out.println("\n" + repetir("=", 50));
                System.out.println("        DETALHES DO PEDIDO");
                System.out.println(repetir("=", 50));
                System.out.println("🆔 ID: " + pedido.getIdPedido());
                System.out.println("👤 Cliente ID: " + pedido.getCliente().getIdCliente());
                System.out.println("📊 Status: " + pedido.getStatus());
                System.out.println("💳 Pagamento: " + pedido.getTipoPagamento());
                System.out.println("💰 Valor Total: R$ " +
                    (pedido.getValorTotal() != null ? String.format("%.2f", pedido.getValorTotal()) : "0.00"));
                System.out.println(repetir("=", 50));
            } else {
                System.out.println("❌ Pedido não encontrado!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
   
    private static void verPedidosRecentesConsole() {
        try {
            com.tecdes.pedido.controller.AtendenteController controller =
                new com.tecdes.pedido.controller.AtendenteController();
            var pedidos = controller.listarPedidosRecentes();
           
            if (pedidos.isEmpty()) {
                System.out.println("📭 Nenhum pedido registrado.");
                return;
            }
           
            System.out.println("\n" + repetir("=", 60));
            System.out.println("              PEDIDOS RECENTES");
            System.out.println(repetir("=", 60));
           
            for (Pedido pedido : pedidos) {
                System.out.printf("🆔 #%d | 👤 Cliente: %d | 💰 R$ %.2f | 📊 %s\n",
                    pedido.getIdPedido(),
                    pedido.getCliente().getIdCliente(),
                    pedido.getValorTotal() != null ? pedido.getValorTotal() : 0.0,
                    pedido.getStatus());
            }
           
            System.out.println(repetir("=", 60));
            System.out.println("📊 Total: " + pedidos.size() + " pedidos");
           
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
   
    private static void abrirMenuProdutosConsole() {
        System.out.println("\n" + repetir("-", 40));
        System.out.println("       MENU PRODUTOS");
        System.out.println(repetir("-", 40));
        System.out.println("1. Buscar produto por ID");
        System.out.println("2. Listar todos os produtos");
        System.out.println("3. Adicionar produto (teste)");
        System.out.println("4. Voltar");
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
                    adicionarProdutoTeste();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Opção inválida!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
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
                System.out.println("\n" + repetir("=", 40));
                System.out.println("   DETALHES DO PRODUTO");
                System.out.println(repetir("=", 40));
                System.out.println("🆔 ID: " + produto.getIdProduto());
                System.out.println("📦 Nome: " + produto.getNome());
                System.out.println("💰 Preço: R$ " + String.format("%.2f", produto.getPreco()));
                if (produto.getCategoria() != null && !produto.getCategoria().isEmpty()) {
                    System.out.println("🏷️  Categoria: " + produto.getCategoria());
                }
                System.out.println(repetir("=", 40));
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
           
            System.out.println("\n" + repetir("=", 60));
            System.out.println("           LISTA DE PRODUTOS");
            System.out.println(repetir("=", 60));
           
            for (var produto : produtos) {
                System.out.printf("🆔 #%d | 📦 %-20s | 💰 R$ %-8.2f\n",
                    produto.getIdProduto(),
                    produto.getNome().length() > 20 ? produto.getNome().substring(0, 17) + "..." : produto.getNome(),
                    produto.getPreco());
            }
           
            System.out.println(repetir("=", 60));
            System.out.println("📊 Total: " + produtos.size() + " produtos");
           
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
   
    private static void adicionarProdutoTeste() {
        try {
            System.out.println("\n➕ ADICIONAR PRODUTO DE TESTE");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Preço: ");
            double preco = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Categoria: ");
            String categoria = scanner.nextLine();
            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();
           
            var novo = produtoService.salvarProduto(nome, preco, categoria, descricao);
            System.out.println("✅ Produto adicionado: ID " + novo.getIdProduto());
           
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
   
    private static void abrirMenuClientesConsole() {
        System.out.println("\n" + repetir("-", 40));
        System.out.println("       MENU CLIENTES");
        System.out.println(repetir("-", 40));
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
                    System.out.println("❌ Opção inválida!");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
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
                System.out.println("\n" + repetir("=", 40));
                System.out.println("   DETALHES DO CLIENTE");
                System.out.println(repetir("=", 40));
                System.out.println("🆔 ID: " + cliente.getIdCliente());
                System.out.println("👤 Nome: " + cliente.getNome());
                System.out.println("📞 Telefone: " + cliente.getFone());
                if (cliente.getEmail() != null && !cliente.getEmail().isEmpty()) {
                    System.out.println("📧 Email: " + cliente.getEmail());
                }
                System.out.println(repetir("=", 40));
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
           
            System.out.println("\n" + repetir("=", 60));
            System.out.println("           LISTA DE CLIENTES");
            System.out.println(repetir("=", 60));
           
            for (var cliente : clientes) {
                System.out.printf("🆔 #%d | 👤 %-20s | 📞 %-15s\n",
                    cliente.getIdCliente(),
                    cliente.getNome().length() > 20 ? cliente.getNome().substring(0, 17) + "..." : cliente.getNome(),
                    cliente.getFone() != null ? cliente.getFone() : "-");
            }
           
            System.out.println(repetir("=", 60));
            System.out.println("📊 Total: " + clientes.size() + " clientes");
           
        } catch (Exception e) {
            System.err.println("❌ Erro: " + e.getMessage());
        }
    }
   
    private static void abrirComandaVirtualConsole() {
        System.out.println("\n" + repetir("-", 40));
        System.out.println("       COMANDA VIRTUAL");
        System.out.println(repetir("-", 40));
        try {
            System.out.print("ID do Pedido: ");
            Long idPedido = scanner.nextLong();
            scanner.nextLine();
           
            try {
                Pedido pedido = pedidoService.buscarPedidoPorId(idPedido);
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
   
    // =============================================
    // MÉTODOS UTILITÁRIOS
    // =============================================
   
    private static String repetir(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
   
    private static void fecharRecursos() {
        if (scanner != null) {
            scanner.close();
        }
        System.out.println("🔧 Recursos fechados com sucesso!");
    }
}


aaaaaaaa