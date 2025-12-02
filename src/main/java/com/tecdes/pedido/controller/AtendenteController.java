package com.tecdes.pedido.controller;


import java.util.List;
import java.util.stream.Collectors;


import com.tecdes.pedido.model.entity.Atendente;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.service.AtendenteService;
import com.tecdes.pedido.service.PedidoService;
import com.tecdes.pedido.service.ProdutoService;
import com.tecdes.pedido.service.ClienteService;
import com.tecdes.pedido.repository.UsuarioRepositoryImpl;
import com.tecdes.pedido.repository.PedidoRepositoryImpl;
import com.tecdes.pedido.repository.ProdutoRepositoryImpl;
import com.tecdes.pedido.repository.ClienteRepositoryImpl;


public class AtendenteController {
   
    private final AtendenteService atendenteService;
    private final PedidoService pedidoService;
    private final ProdutoService produtoService;
    private final ClienteService clienteService;


    // Construtor para inicializar os Services
    public AtendenteController() {
        // Inicialização do Repositório de Usuário para o Service do Atendente
        UsuarioRepositoryImpl usuarioRepo = new UsuarioRepositoryImpl();
        this.atendenteService = new AtendenteService(usuarioRepo);
       
        // Inicialização do ProdutoService
        ProdutoRepositoryImpl produtoRepo = new ProdutoRepositoryImpl();
        this.produtoService = new ProdutoService(produtoRepo);
       
        // Inicialização do ClienteService
        ClienteRepositoryImpl clienteRepo = new ClienteRepositoryImpl();
        this.clienteService = new ClienteService(clienteRepo);
       
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
   
    public List<Produto> listarTodosProdutos() {
        return produtoService.buscarTodos();
    }


    public Pedido iniciarNovaVenda(Pedido pedido) {
        return pedidoService.finalizarPedido(pedido);
    }
   
    // ✅ CORREÇÃO: Mude de buscarPorId() para buscarClientePorId()
    public Cliente buscarClientePorId(Long idCliente) {
        return clienteService.buscarClientePorId(idCliente); // ← NOME CORRETO
    }
   
    public List<Cliente> listarTodosClientes() {
        return clienteService.buscarTodos();
    }
   
    public Pedido buscarPedidoPorId(Long idPedido) {
        return pedidoService.buscarPedidoPorId(idPedido);
    }
   
    public List<Pedido> listarPedidosRecentes() {
        try {
            List<Pedido> todosPedidos = pedidoService.buscarTodos();
            if (todosPedidos == null || todosPedidos.isEmpty()) {
                return List.of(); // Retorna lista vazia
            }
           
            // Ordena por ID (mais recentes primeiro) e pega os últimos 10
            return todosPedidos.stream()
                .sorted((p1, p2) -> Long.compare(p2.getIdPedido(), p1.getIdPedido())) // Ordem decrescente
                .limit(10)
                .collect(Collectors.toList());
               
        } catch (Exception e) {
            System.err.println("Erro ao listar pedidos recentes: " + e.getMessage());
            return List.of(); // Retorna lista vazia em caso de erro
        }
    }
   
    // ✅ CORRIGIDO: Método para alterar senha do próprio atendente
    public boolean alterarMinhaSenha(Long idAtendente, String senhaAtual, String novaSenha) {
        try {
            // Buscar o atendente
            Atendente atendente = atendenteService.buscarAtendentePorId(idAtendente);
            if (atendente == null) {
                System.err.println("Atendente não encontrado: ID " + idAtendente);
                return false;
            }
           
            // Verificar se a senha atual está correta
            if (!atendente.logar(senhaAtual)) {
                System.err.println("Senha atual incorreta para atendente ID " + idAtendente);
                return false;
            }
           
            // Validar nova senha
            if (novaSenha == null || novaSenha.length() < 6) {
                System.err.println("Nova senha deve ter no mínimo 6 caracteres");
                return false;
            }
           
            // ✅ AGORA FUNCIONA: Alterar a senha e salvar no banco
            atendente.setSenha(novaSenha);
            atendenteService.atualizarAtendente(atendente);
           
            System.out.println("✅ Senha alterada com sucesso para atendente ID " + idAtendente);
            return true;
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao alterar senha: " + e.getMessage());
            return false;
        }
    }
   
    // Método para excluir própria conta
    public boolean excluirMinhaConta(Long id) {
        try {
            // Verificar se o atendente existe
            Atendente atendente = atendenteService.buscarAtendentePorId(id);
            if (atendente == null) {
                System.err.println("❌ Atendente não encontrado: ID " + id);
                return false;
            }
           
            System.out.println("🗑️  Excluindo conta do atendente: " + atendente.getLogin() + " (ID: " + id + ")");
           
            // Excluir usando o service
            atendenteService.excluirAtendente(id);
           
            System.out.println("✅ Conta excluída com sucesso.");
            return true;
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao excluir conta: " + e.getMessage());
            return false;
        }
    }
   
    // --- MÉTODOS ADICIONAIS (MELHORIAS) ---
   
    // ✅ AGORA FUNCIONAL: Método para atualizar dados do atendente
    public boolean atualizarAtendente(Atendente atendente) {
        try {
            System.out.println("📝 Atualizando dados do atendente ID " + atendente.getIdUsuario());
           
            // ✅ AGORA USA O MÉTODO DO SERVICE
            Atendente atualizado = atendenteService.atualizarAtendente(atendente);
           
            if (atualizado != null) {
                System.out.println("✅ Atendente atualizado com sucesso!");
                System.out.println("   Login atual: " + atualizado.getLogin());
                return true;
            }
           
            return false;
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao atualizar atendente: " + e.getMessage());
            return false;
        }
    }
   
    public List<Pedido> buscarPedidosPorStatus(String status) {
        try {
            List<Pedido> todosPedidos = pedidoService.buscarTodos();
            if (todosPedidos == null || todosPedidos.isEmpty()) {
                return List.of();
            }
           
            return todosPedidos.stream()
                .filter(pedido -> pedido.getStatus() != null &&
                                  pedido.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
               
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar pedidos por status: " + e.getMessage());
            return List.of();
        }
    }
   
    public Long contarTotalPedidos() {
        try {
            List<Pedido> todosPedidos = pedidoService.buscarTodos();
            return todosPedidos != null ? (long) todosPedidos.size() : 0L;
        } catch (Exception e) {
            System.err.println("❌ Erro ao contar pedidos: " + e.getMessage());
            return 0L;
        }
    }
   
    public Double calcularValorTotalVendas() {
        try {
            List<Pedido> todosPedidos = pedidoService.buscarTodos();
            if (todosPedidos == null || todosPedidos.isEmpty()) {
                return 0.0;
            }
           
            return todosPedidos.stream()
                .filter(pedido -> {
                    try {
                        return pedido.getProdutos() != null &&
                               !pedido.getProdutos().isEmpty() &&
                               pedido.getValorTotal() > 0.0;
                    } catch (Exception e) {
                        return false;
                    }
                })
                .mapToDouble(Pedido::getValorTotal)
                .sum();
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao calcular valor total: " + e.getMessage());
            return 0.0;
        }
    }
   
    // Método para verificar disponibilidade de login
    public boolean verificarLoginDisponivel(String login) {
        try {
            if (login == null || login.trim().isEmpty()) {
                return false;
            }
           
            // Em um sistema real, você poderia adicionar no AtendenteService:
            // return atendenteService.verificarLoginDisponivel(login);
           
            // Por enquanto, simulação básica
            String[] loginsReservados = {"admin", "gerente", "atendente_master"};
            for (String reservado : loginsReservados) {
                if (reservado.equalsIgnoreCase(login.trim())) {
                    return false;
                }
            }
           
            return true;
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao verificar login: " + e.getMessage());
            return false;
        }
    }
   
    // Método para obter estatísticas do atendente
    public String obterEstatisticasAtendente(Long idAtendente) {
        try {
            Atendente atendente = atendenteService.buscarAtendentePorId(idAtendente);
            if (atendente == null) {
                return "Atendente não encontrado.";
            }
           
            long totalPedidos = contarTotalPedidos();
            double totalVendas = calcularValorTotalVendas();
           
            return String.format(
                "📊 Estatísticas do Atendente:\n" +
                "   👤 Nome: %s\n" +
                "   🆔 ID: %d\n" +
                "   📅 Cadastro: %s\n" +
                "   📦 Total Pedidos: %d\n" +
                "   💰 Total Vendas: R$ %.2f",
                atendente.getLogin(),
                atendente.getIdUsuario(),
                atendente.getDataCadastroFormatada(),
                totalPedidos,
                totalVendas
            );
           
        } catch (Exception e) {
            return "❌ Erro ao obter estatísticas: " + e.getMessage();
        }
    }
   
    // ✅ NOVO: Método para buscar atendente por login
    public Atendente buscarAtendentePorLogin(String login) {
        try {
            List<Atendente> todos = atendenteService.buscarTodosAtendentes();
            return todos.stream()
                .filter(a -> login.equals(a.getLogin()))
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            System.err.println("Erro ao buscar atendente por login: " + e.getMessage());
            return null;
        }
    }
}


