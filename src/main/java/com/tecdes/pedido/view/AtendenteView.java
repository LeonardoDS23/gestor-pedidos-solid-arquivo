package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.AtendenteController;
import com.tecdes.pedido.model.entity.Atendente;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.model.entity.Pedido;

import java.util.Scanner;

public class AtendenteView {

    private final AtendenteController controller = new AtendenteController();
    private final Scanner scanner = new Scanner(System.in);

    public void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n--- MENU ATENDENTE ---");
            System.out.println("1. Iniciar Nova Venda/Pedido");
            System.out.println("2. Buscar Produto por ID");
            System.out.println("3. Listar Produtos (Implementação futura)");
            System.out.println("9. Gerenciar minha conta (CRUD Básico)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
                
                switch (opcao) {
                    case 1: iniciarVenda(); break;
                    case 2: buscarProduto(); break;
                    case 3: System.out.println("Funcionalidade em desenvolvimento..."); break;
                    case 9: menuGerenciamentoProprio(); break;
                    case 0: System.out.println("Saindo do Menu Atendente..."); break;
                    default: System.err.println("Opção inválida.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }
    
    private void iniciarVenda() {
        System.out.println("\n--- INICIAR NOVA VENDA (PEDIDO) ---");
        System.out.println("ATENÇÃO: A lógica de itens do pedido é complexa para console.");
        System.out.println("Simulando a finalização de um Pedido de teste.");
        
        try {
            // OBS: Esta é uma SIMULAÇÃO simplificada. A lógica completa estaria no Controller/Service.
            Pedido pedidoTeste = new Pedido(); 
            // setCliente, setItens, etc., seriam feitos aqui com base na entrada do usuário
            
            Pedido pedidoFinalizado = controller.iniciarNovaVenda(pedidoTeste);
            System.out.println("Venda/Pedido finalizado com sucesso!");
            System.out.printf("ID Pedido: %d | Valor Total: %.2f\n", 
                              pedidoFinalizado.getId(), pedidoFinalizado.getValorTotal());
            
        } catch (RuntimeException e) {
            System.err.println("ERRO ao finalizar pedido: " + e.getMessage());
        }
    }
    
    private void buscarProduto() {
        System.out.println("\n--- BUSCAR PRODUTO ---");
        System.out.print("ID do Produto: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            
            Produto produto = controller.buscarProdutoPorId(id);
            System.out.println("\n--- DETALHES DO PRODUTO ---");
            System.out.println("ID: " + produto.getIdProduto());
            System.out.println("Nome: " + produto.getNome());
            System.out.println("Preço: " + produto.getPreco());
            System.out.println("---------------------------");
            
        } catch (RuntimeException e) {
            System.err.println("ERRO: Produto não encontrado.");
        } catch (java.util.InputMismatchException e) {
            System.err.println("ERRO: ID deve ser um número.");
            scanner.nextLine();
        }
    }
    
    // --- GERENCIAMENTO DE CONTA ---

    private void menuGerenciamentoProprio() {
        System.out.println("\n--- GERENCIAR MINHA CONTA ---");
        System.out.println("1. Deletar minha conta (APENAS TESTE)");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            int subOpcao = scanner.nextInt();
            scanner.nextLine();
            
            if (subOpcao == 1) {
                System.out.print("Confirme seu ID para exclusão (NÃO FAÇA ISSO EM PRODUÇÃO!): ");
                Long id = scanner.nextLong();
                scanner.nextLine();
                controller.delete(id);
                System.out.println("Conta ID " + id + " excluída. Você foi desconectado.");
            }
        } catch (Exception e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
}