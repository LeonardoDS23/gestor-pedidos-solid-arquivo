package com.tecdes.pedido.model.entity;

import java.util.List;

public class Atendente extends Usuario {

    private Long idFuncionario;

    public Atendente() {
        super();
    }
    
    public Atendente(String login, String senha) {
        super(login, senha);
    }
    
    public Produto buscarProduto(Long idProduto) {
        System.out.println("Atendente buscando Produto ID: " + idProduto);
        return null;
    }
    
    public Pedido buscarPedido(Long idPedido) {
        System.out.println("Atendente buscando Pedido ID: " + idPedido);
        return null;
    }
    
    public Cliente buscarCliente(Long idCliente) {
        System.out.println("Atendente buscando Cliente ID: " + idCliente);
        return null;
    }
    
    public Pedido realizarVenda(Pedido novoPedido) {
        System.out.println("Atendente registrando nova venda.");
        return novoPedido;
    }
    
    public void gerarComanda(Pedido pedido) {
        System.out.println("Atendente gerando comanda para Pedido ID: " + pedido.getIdPedido());
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}