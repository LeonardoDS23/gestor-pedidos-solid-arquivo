package com.tecdes.pedido.view;


import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.ItemPedido;


public class ComandaVirtualView {


    public void exibirComanda(Pedido pedido) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("                   COMANDA VIRTUAL");
        System.out.println("=".repeat(50));
       
        System.out.printf("ID do Pedido: %d\n", pedido.getIdPedido());
        System.out.printf("Cliente ID: %d\n", pedido.getCliente().getIdCliente());
        System.out.printf("Status: %s\n", pedido.getStatus());
        System.out.printf("Tipo de Pagamento: %s\n", pedido.getTipoPagamento());
       
        System.out.println("-".repeat(50));
        System.out.println("ITENS DO PEDIDO:");
        System.out.println("-".repeat(50));
       
        if (pedido.getProdutos() != null && !pedido.getProdutos().isEmpty()) {
            int contador = 1;
            double total = 0;
           
            for (ItemPedido item : pedido.getProdutos()) {
                double subtotal = item.getQuantidade() * item.getPrecoUnitario();
                total += subtotal;
               
                System.out.printf("%d. %s\n", contador, item.getProduto().getNome());
                System.out.printf("   Quantidade: %d x R$ %.2f = R$ %.2f\n",
                    item.getQuantidade(), item.getPrecoUnitario(), subtotal);
                contador++;
            }
           
            System.out.println("-".repeat(50));
            System.out.printf("VALOR TOTAL: R$ %.2f\n", total);
        } else {
            System.out.println("Nenhum item no pedido.");
        }
       
        System.out.println("=".repeat(50));
        System.out.println("Comanda gerada em: " + new java.util.Date());
        System.out.println("=".repeat(50));
    }
}

