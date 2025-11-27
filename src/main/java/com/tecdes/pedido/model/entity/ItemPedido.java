package com.tecdes.pedido.model.entity;

public class ItemPedido {

    private Long idItem;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;
    private String observacoes;

    public Long getIdItem() {
        return idItem;
    }

    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public double calcularTotal() {
        return this.precoUnitario * this.quantidade;
    }
}
// package com.tecdes.pedido.model.entity;

// public class ItemPedido {

//     private int idItem;
//     private Produto produto;
//     private int quantidade;
//     private double precoUnitario;
//     private String observacoes;

//     public ItemPedido() {}

//     public ItemPedido(int idItem, Produto produto, int quantidade, double precoUnitario, String observacoes) {
//         this.idItem = idItem;
//         this.produto = produto;
//         this.quantidade = quantidade;
//         this.precoUnitario = precoUnitario;
//         this.observacoes = observacoes;
//     }

//     public int getIdItem(){ 
//         return idItem; }

//     public void setIdItem(int idItem){ 
//         this.idItem = idItem; }

//     public Produto getProduto(){
//         return produto; }

//     public void setProduto(Produto produto){
//         this.produto = produto; }

//     public int getQuantidade(){
//         return quantidade; }

//     public void setQuantidade(int quantidade){
//         this.quantidade = quantidade; }

//     public double getPrecoUnitario(){
//         return precoUnitario; }

//     public void setPrecoUnitario(double precoUnitario){
//         this.precoUnitario = precoUnitario; }

//     public String getObservacoes(){
//         return observacoes; }

//     public void setObservacoes(String observacoes){
//         this.observacoes = observacoes; }

//     public double getSubtotal() {
//         return quantidade * precoUnitario;
//     }
//     //metodo para calcular o total do item
    
// }
