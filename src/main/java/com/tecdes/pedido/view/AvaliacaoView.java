package com.tecdes.pedido.view;

import com.tecdes.pedido.controller.AvaliacaoController;
import com.tecdes.pedido.model.entity.Avaliacao;

import java.util.Scanner;

public class AvaliacaoView {

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        AvaliacaoController controller = new AvaliacaoController();

        try {
            System.out.println("--- REGISTRAR AVALIAÇÃO ---");
            
            System.out.print("ID do Pedido a ser avaliado: ");
            Long idPedido = scanner.nextLong(); 
            scanner.nextLine(); 

            System.out.print("Nota (1 a 5): ");
            int nota = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("Comentário: ");
            String comentario = scanner.nextLine();

            Avaliacao avaliacao = new Avaliacao(idPedido, nota, comentario);
            controller.avaliarPedido(avaliacao);

            System.out.println("\nAvaliação registrada com sucesso!");
            
        } catch (java.util.InputMismatchException e) {
            System.err.println("\nEntrada inválida. Certifique-se de digitar números para o ID e Nota.");
        } catch (Exception e) {
            System.err.println("\nOcorreu um erro: " + e.getMessage());
        }
    }
}