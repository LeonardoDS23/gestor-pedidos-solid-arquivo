package com.tecdes.pedido.model.entity;

import java.util.Date;

public class Atendente extends Usuario {

    private Long idFuncionario;
    private Date dataCadastro; // ✅ Adicionar campo para data de cadastro

    public Atendente() {
        super();
        this.dataCadastro = new Date(); // Data atual automaticamente
    }
    
    public Atendente(String login, String senha) {
        super(login, senha);
        this.dataCadastro = new Date(); // Data atual automaticamente
    }
    
    // ✅ GETTER para dataCadastro
    public Date getDataCadastro() {
        return dataCadastro;
    }
    
    // ✅ SETTER para dataCadastro
    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    
    // ✅ Método para formatar a data (opcional, mas útil)
    public String getDataCadastroFormatada() {
        if (dataCadastro != null) {
            return new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dataCadastro);
        }
        return "Não informada";
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