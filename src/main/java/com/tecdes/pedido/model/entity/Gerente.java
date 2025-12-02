package com.tecdes.pedido.model.entity;




public class Gerente extends Usuario {


    private Long idFuncionario;


    public Gerente() {
        super();
    }


    public Gerente(String login, String senha) {
        super(login, senha);
    }
   
    public void cadastrarUsuario(Usuario usuario) {
        System.out.println("Gerente cadastrando novo usuário: " + usuario.getLogin());
    }
   
    public void alterarUsuario(Usuario usuario) {
        System.out.println("Gerente alterando dados do usuário ID: " + usuario.getIdUsuario());
    }


    public void excluirUsuario(Long idUsuario) {
        System.out.println("Gerente excluindo usuário ID: " + idUsuario);
    }
   
    public void redefinirSenha(Usuario usuario, String novaSenha) {
        usuario.setSenha(novaSenha);
        System.out.println("Gerente redefiniu a senha do usuário: " + usuario.getLogin());
    }


    public Long getIdFuncionario() {
        return idFuncionario;
    }


    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
}
