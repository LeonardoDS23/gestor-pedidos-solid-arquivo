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
    private static Scanner scanner;

    public static void main(String[] args) {
        // Inicializar serviços
        inicializarServicos();
        scanner = new Scanner(System.in);
        
        // Escolher entre Interface Gráfica ou Console
        escolherModoInterface();
    }
    
    private static void inicializarServicos() {
        try {
            ProdutoRepository produtoRepo = new ProdutoRepositoryImpl();
            produtoService = new ProdutoService(produtoRepo);
            
            PedidoRepository pedidoRepo = new PedidoRepositoryImpl();
            pedidoService = new PedidoService(pedidoRepo, produtoService);
            
            System.out.println("✅ Serviços inicializados com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro ao inicializar serviços: " + e.getMessage());
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
            // Criar menu principal gráfico
            criarMenuPrincipalGrafico();
        });
    }
    
    private static void criarMenuPrincipalGrafico() {
        JFrame menuFrame = new JFrame("Sistema Gestor de Pedidos");
        menuFrame.setSize(400, 500);
        menuFrame.setLocationRelativeTo(null);
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(null);
        
        JLabel titulo = new JLabel("SISTEMA GESTOR DE PEDIDOS");
        titulo.setBounds(80, 20, 250, 30);
        titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        menuFrame.add(titulo);
        
        // Botões para diferentes funcionalidades
        JButton btnPedidos = new JButton("📋 Gerenciar Pedidos");
        btnPedidos.setBounds(50, 80, 300, 40);
        btnPedidos.addActionListener(e -> {
            menuFrame.dispose();
            new PedidoView(pedidoService, produtoService);
        });
        menuFrame.add(btnPedidos);
        
        JButton btnProdutos = new JButton("📦 Gerenciar Produtos");
        btnProdutos.setBounds(50, 140, 300, 40);
        btnProdutos.addActionListener(e -> {
            menuFrame.dispose();
            new ProdutoView();
        });
        menuFrame.add(btnProdutos);
        
        JButton btnItemPedido = new JButton("🛒 Cadastrar Item Pedido");
        btnItemPedido.setBounds(50, 200, 300, 40);
        btnItemPedido.addActionListener(e -> {
            menuFrame.dispose();
            new ItemPedidoView();
        });
        menuFrame.add(btnItemPedido);
        
        JButton btnClientes = new JButton("👥 Gerenciar Clientes");
        btnClientes.setBounds(50, 260, 300, 40);
        btnClientes.addActionListener(e -> {
            menuFrame.dispose();
            abrirMenuClientesConsole(); // Clientes só tem console por enquanto
        });
        menuFrame.add(btnClientes);
        
        JButton btnVoltar = new JButton("🔄 Voltar ao Menu Principal");
        btnVoltar.setBounds(50, 320, 300, 40);
        btnVoltar.addActionListener(e -> {
            menuFrame.dispose();
            escolherModoInterface();
        });
        menuFrame.add(btnVoltar);
        
        JButton btnSair = new JButton("❌ Sair");
        btnSair.setBounds(50, 380, 300, 40);
        btnSair.addActionListener(e -> System.exit(0));
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
            System.out.println("2. 📋 Menu Pedidos (Interface Gráfica)");
            System.out.println("3. 📦 Menu Produtos (Interface Gráfica)");
            System.out.println("4. 👥 Menu Clientes");
            System.out.println("5. 👨‍💼 Menu Gerente");
            System.out.println("6. ⭐ Avaliar Pedido");
            System.out.println("7. 🧾 Comanda Virtual");
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
                        SwingUtilities.invokeLater(() -> new PedidoView(pedidoService, produtoService));
                        break;
                    case 3:
                        SwingUtilities.invokeLater(() -> new ProdutoView());
                        break;
                    case 4:
                        abrirMenuClientesConsole();
                        break;
                    case 5:
                        new GerenteView().menuPrincipal();
                        break;
                    case 6:
                        new AvaliacaoView().menu();
                        break;
                    case 7:
                        abrirComandaVirtualConsole();
                        break;
                    case 8:
                        escolherModoInterface();
                        return;
                    case 0:
                        System.out.println("Saindo...");
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
    // MÉTODOS AUXILIARES
    // =============================================
    
    private static void abrirMenuClientesConsole() {
        System.out.println("\n--- MENU CLIENTES ---");
        System.out.println("Abrindo interface de clientes...");
        new ClienteView().menuPrincipal();
    }
    
    private static void abrirComandaVirtualConsole() {
    System.out.println("\n--- COMANDA VIRTUAL ---");
    try {
        System.out.print("ID do Pedido: ");
        Long idPedido = scanner.nextLong();
        scanner.nextLine();
        
        // ✅ CORREÇÃO: Agora usando o idPedido para buscar o pedido
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
    
    // Método para fechar recursos
    public static void fecharRecursos() {
        if (scanner != null) {
            scanner.close();
        }
    }
}