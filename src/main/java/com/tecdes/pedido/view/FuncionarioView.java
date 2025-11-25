package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.FuncionarioController;
import com.tecdes.pedido.model.entity.Funcionario;

import java.util.Scanner;

public class FuncionarioView {

    private FuncionarioController controller = new FuncionarioController();

    public void menu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("1 - Cadastrar Funcionário");
        System.out.println("2 - Listar Funcionários");
        int op = scanner.nextInt();

        if(op == 1){
            System.out.print("Nome: ");
            scanner.nextLine();
            String nome = scanner.nextLine();

            System.out.print("Cargo: ");
            String cargo = scanner.nextLine();

            controller.cadastrar(new Funcionario(1, nome, cargo));
        } else if(op == 2){
            controller.listar();
        }
    }
}
