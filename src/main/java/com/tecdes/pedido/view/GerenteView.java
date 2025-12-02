package com.tecdes.pedido.view;


import com.tecdes.pedido.controller.GerenteController;
import com.tecdes.pedido.model.entity.Atendente;


import java.util.List;
import java.util.Scanner;


public class GerenteView {


    private final GerenteController controller = new GerenteController();
    private final Scanner scanner = new Scanner(System.in);


    public void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n--- MENU GERENTE ---");
            System.out.println("1. Cadastrar Novo Atendente");
            System.out.println("2. Listar Todos Atendentes");
            System.out.println("3. Redefinir Senha de Usuário");
            System.out.println("4. Excluir Usuário (Atendente/Gerente)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");


            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
               
                switch (opcao) {
                    case 1: cadastrarAtendente(); break;
                    case 2: listarAtendentes(); break;
                    case 3: redefinirSenha(); break;
                    case 4: excluirUsuario(); break;
                    case 0: System.out.println("Saindo do Menu Gerente..."); break;
                    default: System.err.println("Opção inválida.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }


    private void cadastrarAtendente() {
        System.out.println("\n--- CADASTRAR NOVO ATENDENTE ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
       
        try {
            controller.cadastrarNovoAtendente(login, senha);
            System.out.println("Atendente cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
   
    private void listarAtendentes() {
        System.out.println("\n--- LISTA DE ATENDENTES ---");
        List<Atendente> atendentes = controller.listarTodosAtendentes();
        if (atendentes.isEmpty()) {
            System.out.println("Nenhum atendente cadastrado.");
            return;
        }
        for (Atendente a : atendentes) {
            System.out.printf("ID: %d | Login: %s\n", a.getIdUsuario(), a.getLogin());
        }
        System.out.println("---------------------------");
    }
   
    private void redefinirSenha() {
        System.out.println("\n--- REDEFINIR SENHA ---");
        System.out.print("ID do Usuário para redefinir: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
            System.out.print("Nova Senha: ");
            String novaSenha = scanner.nextLine();
           
            controller.redefinirSenhaDeUsuario(id, novaSenha);
            System.out.println("Senha redefinida com sucesso para o usuário ID " + id + ".");
           
        } catch (java.util.InputMismatchException e) {
            System.err.println("ERRO: ID deve ser um número.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }


    private void excluirUsuario() {
        System.out.println("\n--- EXCLUIR USUÁRIO ---");
        System.out.print("ID do Usuário para exclusão: ");
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
           
            controller.excluirUsuario(id);
            System.out.println("Usuário ID " + id + " excluído com sucesso.");
           
        } catch (java.util.InputMismatchException e) {
            System.err.println("ERRO: ID deve ser um número.");
            scanner.nextLine();
        } catch (RuntimeException e) {
            System.err.println("ERRO: " + e.getMessage());
        }
    }
}

