package com.tecdes.pedido.controller;

import java.util.List;

import com.tecdes.pedido.model.entity.Atendente;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.service.AtendenteService;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.service.ProdutoService;
import com.tecdes.pedido.repository.UsuarioRepositoryImpl;
// Importamos as implementações dos repositórios necessários
import com.tecdes.pedido.repository.PedidoRepositoryImpl; 
import com.tecdes.pedido.repository.ProdutoRepositoryImpl; 

public class AtendenteController {
    
    private final AtendenteService atendenteService;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;

    // Construtor para inicializar os Services
    public AtendenteController() {
        // Inicialização do Repositório de Usuário para o Service do Atendente
        UsuarioRepositoryImpl usuarioRepo = new UsuarioRepositoryImpl();
        this.atendenteService = new AtendenteService(usuarioRepo);
        
        // Inicialização do ProdutoService (necessário para buscar itens)
        ProdutoRepositoryImpl produtoRepo = new ProdutoRepositoryImpl();
        this.produtoService = new ProdutoService(produtoRepo);
        
        // Inicialização do PedidoService (requer ProdutoService)
        PedidoRepositoryImpl pedidoRepo = new PedidoRepositoryImpl();
        this.pedidoService = new PedidoService(pedidoRepo, this.produtoService);
    }

    // --- CRUD BÁSICO DO PRÓPRIO ATENDENTE ---

    public void salvar(String login, String senha) {
        Atendente novoAtendente = new Atendente(login, senha);
        atendenteService.cadastrarAtendente(novoAtendente);
    }

    public Atendente findById(Long id) {
        return atendenteService.buscarAtendentePorId(id);
    }
    
    public List<Atendente> buscarTodos() {
        return atendenteService.buscarTodosAtendentes();
    }
    
    public void delete(Long id) {
        atendenteService.excluirAtendente(id);
    }
    
    // --- FUNCIONALIDADES DE OPERAÇÃO ---
    
    public Produto buscarProdutoPorId(Long idProduto) {
        return produtoService.buscarPorId(idProduto);
    }

    public Pedido iniciarNovaVenda(Pedido pedido) {
        // O método no Service lida com a lógica de calcular totais, validar estoque e salvar
        return pedidoService.finalizarPedido(pedido);
    }
}