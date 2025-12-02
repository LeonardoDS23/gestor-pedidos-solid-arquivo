package com.tecdes.pedido.controller;


import java.util.List;


import com.tecdes.pedido.model.entity.Atendente;
import com.tecdes.pedido.model.entity.Gerente;
import com.tecdes.pedido.service.AtendenteService;
import com.tecdes.pedido.service.GerenteService;
import com.tecdes.pedido.repository.UsuarioRepositoryImpl;


public class GerenteController {
   
    private final GerenteService gerenteService;
    private final AtendenteService atendenteService; // Necessário para listar/gerenciar atendentes


    // Construtor para inicializar os Services
    public GerenteController() {
        UsuarioRepositoryImpl usuarioRepo = new UsuarioRepositoryImpl();
        this.gerenteService = new GerenteService(usuarioRepo);
        this.atendenteService = new AtendenteService(usuarioRepo);
    }


    // --- CRUD BÁSICO DO PRÓPRIO GERENTE ---


    public void salvarGerente(String login, String senha) {
        Gerente novoGerente = new Gerente(login, senha);
        gerenteService.cadastrarGerente(novoGerente);
    }


    public Gerente findById(Long id) {
        return gerenteService.buscarGerentePorId(id);
    }
   
    public List<Gerente> buscarTodosGerentes() {
        return gerenteService.buscarTodosGerentes();
    }
   
    // --- GESTÃO DE USUÁRIOS (RESPONSABILIDADE PRINCIPAL) ---
   
    public void cadastrarNovoAtendente(String login, String senha) {
        Atendente novoAtendente = new Atendente(login, senha);
        atendenteService.cadastrarAtendente(novoAtendente);
    }


    public void redefinirSenhaDeUsuario(Long idUsuario, String novaSenha) {
        gerenteService.redefinirSenhaUsuario(idUsuario, novaSenha);
    }


    public void excluirUsuario(Long idUsuario) {
        gerenteService.excluirUsuario(idUsuario);
    }
   
    public List<Atendente> listarTodosAtendentes() {
        return atendenteService.buscarTodosAtendentes();
    }
}
