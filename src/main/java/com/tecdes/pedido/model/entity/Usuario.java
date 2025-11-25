package com.tecdes.pedido.model.entity;

public abstract class Usuario {

    private Long idUsuario;
    private String login;
    private String senha;
    
    public Usuario() {
    }

    public Usuario(String login, String senha) {
        setLogin(login);
        setSenha(senha);
    }
    
    public boolean logar(String senhaDigitada) {
        return this.senha != null && this.senha.equals(senhaDigitada);
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser vazio.");
        }
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.length() < 6) {
             throw new IllegalArgumentException("A senha deve ter no mínimo 6 caracteres.");
        }
        this.senha = senha;
    }
}