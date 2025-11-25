package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.AvaliacaoController;
import com.tecdes.pedido.model.entity.Avaliacao;

import java.util.Scanner;

public class AvaliacaoView {

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        AvaliacaoController controller = new AvaliacaoController();

        System.out.print("Nota (1 a 5): ");
        int nota = scanner.nextInt();

        scanner.nextLine();
        System.out.print("Comentário: ");
        String comentario = scanner.nextLine();

        Avaliacao avaliacao = new Avaliacao(1, nota, comentario);
        controller.avaliarPedido(avaliacao);

        System.out.println("Avaliação registrada!");
    }
}
