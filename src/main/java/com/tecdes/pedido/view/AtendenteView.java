package com.tecdes.pedido.view;


import com.tecdes.pedido.controller.AtendenteController;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.model.entity.Atendente;


import java.util.Scanner;
import java.util.List;


public class AtendenteView {


    private final AtendenteController controller = new AtendenteController();
    private final Scanner scanner = new Scanner(System.in);
   
    // Para simular o atendente logado (em um sistema real, isso viria de autenticação)
    private Long atendenteLogadoId = 1L; // ID simulado


    public void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("        MENU ATENDENTE");
            System.out.println("=".repeat(40));
            System.out.println("1. 🛒 Iniciar Nova Venda/Pedido");
            System.out.println("2. 🔍 Buscar Produto por ID");
            System.out.println("3. 📋 Listar Todos Produtos");
            System.out.println("4. 👥 Buscar Cliente por ID");
            System.out.println("5. 📊 Ver Pedidos Recentes");
            System.out.println("6. ⭐ Avaliar Pedido (Console)");
            System.out.println("7. 🧾 Gerar Comanda Virtual");
            System.out.println("8. 👤 Gerenciar Minha Conta");
            System.out.println("0. ↩️ Voltar ao Menu Principal");
            System.out.println("=".repeat(40));
            System.out.print("Escolha uma opção: ");


            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
               
                switch (opcao) {
                    case 1: iniciarVenda(); break;
                    case 2: buscarProduto(); break;
                    case 3: listarProdutos(); break;
                    case 4: buscarCliente(); break;
                    case 5: verPedidosRecentes(); break;
                    case 6: avaliarPedidoConsole(); break;
                    case 7: gerarComandaConsole(); break;
                    case 8: menuGerenciamentoProprio(); break;
                    case 0:
                        System.out.println("Voltando ao Menu Principal...");
                        return;
                    default:
                        System.err.println("❌ Opção inválida. Tente novamente.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("❌ Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            } catch (Exception e) {
                System.err.println("❌ Erro inesperado: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }
   
    private void iniciarVenda() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         INICIAR NOVA VENDA");
        System.out.println("=".repeat(50));
       
        try {
            // Solicitar dados do cliente
            System.out.print("ID do Cliente (ou 0 para cliente não cadastrado): ");
            Long idCliente = scanner.nextLong();
            scanner.nextLine();
           
            if (idCliente == 0) {
                System.out.println("⚠️  Cliente não cadastrado. Venda será registrada como 'CLIENTE AVULSO'.");
                idCliente = 0L;
            } else {
                // Verificar se cliente existe
                Cliente cliente = controller.buscarClientePorId(idCliente);
                if (cliente == null) {
                    System.out.println("❌ Cliente não encontrado. Use ID 0 para cliente avulso.");
                    return;
                }
                System.out.println("✅ Cliente: " + cliente.getNome());
            }
           
            // Criar pedido básico
            Pedido pedido = new Pedido();
            Cliente cliente = new Cliente();
            cliente.setIdCliente(idCliente);
            pedido.setCliente(cliente);
           
            // Solicitar tipo de pagamento
            System.out.println("\n📝 TIPOS DE PAGAMENTO:");
            System.out.println("1. 💳 Cartão de Crédito");
            System.out.println("2. 💳 Cartão de Débito");
            System.out.println("3. 💵 Dinheiro");
            System.out.println("4. 📱 PIX");
            System.out.print("Escolha a forma de pagamento (1-4): ");
           
            int formaPagamento = scanner.nextInt();
            scanner.nextLine();
           
            String tipoPagamento = "";
            switch (formaPagamento) {
                case 1: tipoPagamento = "CARTAO_CREDITO"; break;
                case 2: tipoPagamento = "CARTAO_DEBITO"; break;
                case 3: tipoPagamento = "DINHEIRO"; break;
                case 4: tipoPagamento = "PIX"; break;
                default:
                    System.out.println("⚠️  Opção inválida. Usando DINHEIRO como padrão.");
                    tipoPagamento = "DINHEIRO";
            }
           
            pedido.setTipoPagamento(tipoPagamento);
           
            System.out.println("\n⚠️  ATENÇÃO: Para adicionar itens ao pedido, use a interface gráfica.");
            System.out.println("   Acesse: Menu Principal → Gerenciar Pedidos");
           
            System.out.print("\nDeseja continuar para a interface gráfica? (S/N): ");
            String resposta = scanner.nextLine().toUpperCase();
           
            if (resposta.equals("S")) {
                System.out.println("✅ Pedido inicial criado! Acesse a interface gráfica para adicionar itens.");
                System.out.println("📋 ID do Pedido será gerado automaticamente.");
            } else {
                System.out.println("❌ Operação cancelada.");
            }
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao iniciar venda: " + e.getMessage());
        }
    }
   
    private void buscarProduto() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         BUSCAR PRODUTO");
        System.out.println("=".repeat(50));
        System.out.print("ID do Produto: ");
       
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
           
            Produto produto = controller.buscarProdutoPorId(id);
           
            if (produto != null) {
                System.out.println("\n" + "-".repeat(50));
                System.out.println("        DETALHES DO PRODUTO");
                System.out.println("-".repeat(50));
                System.out.println("🆔 ID: " + produto.getIdProduto());
                System.out.println("📦 Nome: " + produto.getNome());
                System.out.println("💰 Preço: R$ " + String.format("%.2f", produto.getPreco()));
                if (produto.getCategoria() != null) {
                    System.out.println("🏷️  Categoria: " + produto.getCategoria());
                }
                if (produto.getDescricao() != null && !produto.getDescricao().isEmpty()) {
                    System.out.println("📝 Descrição: " + produto.getDescricao());
                }
                System.out.println("-".repeat(50));
            } else {
                System.out.println("❌ Produto não encontrado!");
            }
           
        } catch (java.util.InputMismatchException e) {
            System.err.println("❌ ERRO: ID deve ser um número.");
            scanner.nextLine();
        } catch (Exception e) {
            System.err.println("❌ ERRO: " + e.getMessage());
        }
    }
   
    private void listarProdutos() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("            LISTA DE PRODUTOS");
        System.out.println("=".repeat(60));
       
        try {
            List<Produto> produtos = controller.listarTodosProdutos();
           
            if (produtos == null || produtos.isEmpty()) {
                System.out.println("📭 Nenhum produto cadastrado.");
                return;
            }
           
            System.out.printf("%-5s %-25s %-12s %-15s\n", "ID", "NOME", "PREÇO", "CATEGORIA");
            System.out.println("-".repeat(60));
           
            for (Produto produto : produtos) {
                System.out.printf("%-5d %-25s R$ %-9.2f %-15s\n",
                    produto.getIdProduto(),
                    produto.getNome().length() > 25 ? produto.getNome().substring(0, 22) + "..." : produto.getNome(),
                    produto.getPreco(),
                    produto.getCategoria() != null ? produto.getCategoria() : "-");
            }
           
            System.out.println("-".repeat(60));
            System.out.println("📊 Total de produtos: " + produtos.size());
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao listar produtos: " + e.getMessage());
        }
    }
   
    private void buscarCliente() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("          BUSCAR CLIENTE");
        System.out.println("=".repeat(50));
        System.out.print("ID do Cliente: ");
       
        try {
            Long id = scanner.nextLong();
            scanner.nextLine();
           
            Cliente cliente = controller.buscarClientePorId(id);
           
            if (cliente != null) {
                System.out.println("\n" + "-".repeat(50));
                System.out.println("       DETALHES DO CLIENTE");
                System.out.println("-".repeat(50));
                System.out.println("🆔 ID: " + cliente.getIdCliente());
                System.out.println("👤 Nome: " + cliente.getNome());
                System.out.println("📞 Telefone: " + cliente.getFone());
                System.out.println("-".repeat(50));
            } else {
                System.out.println("❌ Cliente não encontrado!");
            }
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar cliente: " + e.getMessage());
        }
    }
   
    private void verPedidosRecentes() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("                PEDIDOS RECENTES");
        System.out.println("=".repeat(70));
       
        try {
            List<Pedido> pedidos = controller.listarPedidosRecentes();
           
            if (pedidos == null || pedidos.isEmpty()) {
                System.out.println("📭 Nenhum pedido registrado recentemente.");
                return;
            }
           
            System.out.printf("%-8s %-10s %-20s %-12s %-10s\n",
                "ID", "CLIENTE", "STATUS", "VALOR", "PAGAMENTO");
            System.out.println("-".repeat(70));
           
            for (Pedido pedido : pedidos) {
                String nomeCliente = "AVULSO";
                if (pedido.getCliente() != null && pedido.getCliente().getIdCliente() != 0L) {
                    Cliente cliente = controller.buscarClientePorId(pedido.getCliente().getIdCliente());
                    if (cliente != null && cliente.getNome() != null) {
                        nomeCliente = cliente.getNome().length() > 18 ?
                            cliente.getNome().substring(0, 15) + "..." :
                            cliente.getNome();
                    }
                }
               
                 Double valorTotal = pedido.getValorTotal();
            double valorExibicao = (valorTotal != null) ? valorTotal : 0.0;
           
            System.out.printf("%-8d %-10s %-20s R$ %-8.2f %-10s\n",
                pedido.getIdPedido(),
                nomeCliente,
                pedido.getStatus() != null ? pedido.getStatus() : "PENDENTE",
                valorExibicao,
                pedido.getTipoPagamento() != null ? pedido.getTipoPagamento() : "NÃO INFORMADO");
            }
           
            System.out.println("-".repeat(70));
            System.out.println("📊 Total de pedidos: " + pedidos.size());
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao listar pedidos: " + e.getMessage());
        }
    }
   
    private void avaliarPedidoConsole() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         AVALIAR PEDIDO");
        System.out.println("=".repeat(50));
       
        try {
            System.out.print("ID do Pedido a ser avaliado: ");
            Long idPedido = scanner.nextLong();
            scanner.nextLine();
           
            System.out.print("Nota (1 a 5): ");
            int nota = scanner.nextInt();
            scanner.nextLine();
           
            if (nota < 1 || nota > 5) {
                System.err.println("❌ Nota deve ser entre 1 e 5!");
                return;
            }
           
            System.out.print("Comentário (opcional): ");
            String comentario = scanner.nextLine();
           
            System.out.println("\n✅ Avaliação registrada localmente!");
            System.out.println("📝 ID Pedido: " + idPedido);
            System.out.println("⭐ Nota: " + nota + "/5");
            if (!comentario.isEmpty()) {
                System.out.println("💬 Comentário: " + comentario);
            }
            System.out.println("ℹ️  Use a opção 'Avaliar Pedido' no menu principal para salvar no sistema.");
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao avaliar pedido: " + e.getMessage());
        }
    }
   
    private void gerarComandaConsole() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        COMANDA VIRTUAL");
        System.out.println("=".repeat(50));
       
        try {
            System.out.print("ID do Pedido: ");
            Long idPedido = scanner.nextLong();
            scanner.nextLine();
           
            Pedido pedido = controller.buscarPedidoPorId(idPedido);
           
            if (pedido != null) {
                System.out.println("\n" + "*".repeat(50));
                System.out.println("          COMANDA #" + idPedido);
                System.out.println("*".repeat(50));
               
                String nomeCliente = "CLIENTE AVULSO";
                if (pedido.getCliente() != null && pedido.getCliente().getIdCliente() != 0L) {
                    Cliente cliente = controller.buscarClientePorId(pedido.getCliente().getIdCliente());
                    if (cliente != null && cliente.getNome() != null) {
                        nomeCliente = cliente.getNome();
                    }
                }
               
                System.out.println("👤 Cliente: " + nomeCliente);
                System.out.println("📊 Status: " + (pedido.getStatus() != null ? pedido.getStatus() : "PENDENTE"));
                System.out.println("💳 Pagamento: " + (pedido.getTipoPagamento() != null ? pedido.getTipoPagamento() : "NÃO INFORMADO"));
                System.out.println("-".repeat(50));
               
                if (pedido.getProdutos() != null && !pedido.getProdutos().isEmpty()) {
                    System.out.println("🛒 ITENS:");
                    double total = 0;
                    int itemNum = 1;
                   
                    for (var item : pedido.getProdutos()) {
                        double subtotal = item.getQuantidade() * item.getPrecoUnitario();
                        total += subtotal;
                       
                        System.out.printf("%d. %s\n", itemNum, item.getProduto().getNome());
                        System.out.printf("   %d x R$ %.2f = R$ %.2f\n",
                            item.getQuantidade(), item.getPrecoUnitario(), subtotal);
                        itemNum++;
                    }
                   
                    System.out.println("-".repeat(50));
                    System.out.printf("💰 TOTAL: R$ %.2f\n", total);
                } else {
                    System.out.println("📭 Nenhum item no pedido.");
                }
               
                System.out.println("*".repeat(50));
                System.out.println("📅 Gerado em: " + new java.util.Date());
               
            } else {
                System.out.println("❌ Pedido não encontrado!");
            }
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao gerar comanda: " + e.getMessage());
        }
    }
   
    // --- GERENCIAMENTO DE CONTA ---


    private void menuGerenciamentoProprio() {
        int opcao;
        do {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("      GERENCIAR MINHA CONTA");
            System.out.println("=".repeat(40));
            System.out.println("1. 👁️  Ver Meus Dados");
            System.out.println("2. 🔐 Alterar Minha Senha");
            System.out.println("3. 🗑️  Excluir Minha Conta (TESTE)");
            System.out.println("0. ↩️  Voltar");
            System.out.println("=".repeat(40));
            System.out.print("Escolha uma opção: ");


            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
               
                switch (opcao) {
                    case 1: verMeusDados(); break;
                    case 2: alterarSenha(); break;
                    case 3: excluirMinhaConta(); break;
                    case 0:
                        System.out.println("Voltando...");
                        return;
                    default:
                        System.err.println("❌ Opção inválida.");
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println("❌ Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            } catch (Exception e) {
                System.err.println("❌ ERRO: " + e.getMessage());
                opcao = -1;
            }
        } while (opcao != 0);
    }
   
    private void verMeusDados() {
    System.out.println("\n" + "=".repeat(40));
    System.out.println("        MEUS DADOS - ATENDENTE");
    System.out.println("=".repeat(40));
   
    try {
        // Buscar dados do atendente logado
        Atendente atendente = controller.findById(atendenteLogadoId);
       
        if (atendente != null) {
            System.out.println("👤 Tipo: Atendente");
            System.out.println("🆔 ID Usuário: " + atendente.getIdUsuario());
            System.out.println("👨‍💼 Login: " + atendente.getLogin());
           
            // Mostrar ID do funcionário se existir
            if (atendente.getIdFuncionario() != null) {
                System.out.println("🏢 ID Funcionário: " + atendente.getIdFuncionario());
            }
           
            // ✅ AGORA FUNCIONA: Mostrar data de cadastro
            if (atendente.getDataCadastro() != null) {
                // Formatar a data para exibição amigável
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy 'às' HH:mm:ss");
                String dataFormatada = sdf.format(atendente.getDataCadastro());
                System.out.println("📅 Data de Cadastro: " + dataFormatada);
               
                // Calcular há quanto tempo está cadastrado (opcional)
                long diferenca = System.currentTimeMillis() - atendente.getDataCadastro().getTime();
                long dias = diferenca / (1000 * 60 * 60 * 24);
                if (dias > 0) {
                    System.out.println("⏳ Cadastrado há: " + dias + " dia" + (dias > 1 ? "s" : ""));
                }
            } else {
                System.out.println("📅 Data de Cadastro: Não informada");
            }
           
            System.out.println("\n🔧 Funcionalidades Disponíveis:");
            System.out.println("   • Buscar Produtos por ID");
            System.out.println("   • Buscar Pedidos por ID");
            System.out.println("   • Buscar Clientes por ID");
            System.out.println("   • Registrar Novas Vendas");
            System.out.println("   • Gerar Comandas");
           
        } else {
            System.out.println("⚠️  Não foi possível obter seus dados do sistema.");
            System.out.println("\n📋 Dados Simulados:");
            System.out.println("👤 Tipo: Atendente");
            System.out.println("🆔 ID: " + atendenteLogadoId);
            System.out.println("👨‍💼 Login: atendente_simulado");
            System.out.println("📅 Data Cadastro: " + new java.util.Date());
        }
       
    } catch (Exception e) {
        System.err.println("❌ Erro ao obter dados: " + e.getMessage());
        // Dados simulados em caso de erro
        System.out.println("👤 Tipo: Atendente");
        System.out.println("🆔 ID: " + atendenteLogadoId + " (simulado)");
        System.out.println("👨‍💼 Login: atendente_" + atendenteLogadoId);
        System.out.println("📅 Data Cadastro: " + new java.util.Date());
    }
   
    System.out.println("=".repeat(40));
    System.out.println("ℹ️  Para alterar dados, consulte o gerente.");
}
   
    private void alterarSenha() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("       ALTERAR SENHA");
        System.out.println("=".repeat(40));
       
        try {
            System.out.print("Senha atual: ");
            String senhaAtual = scanner.nextLine();
           
            System.out.print("Nova senha: ");
            String novaSenha = scanner.nextLine();
           
            System.out.print("Confirmar nova senha: ");
            String confirmarSenha = scanner.nextLine();
           
            if (!novaSenha.equals(confirmarSenha)) {
                System.err.println("❌ As senhas não coincidem!");
                return;
            }
           
            if (novaSenha.length() < 4) {
                System.err.println("❌ A senha deve ter pelo menos 4 caracteres!");
                return;
            }
           
            // Tentar alterar a senha
            boolean sucesso = controller.alterarMinhaSenha(atendenteLogadoId, senhaAtual, novaSenha);
           
            if (sucesso) {
                System.out.println("✅ Senha alterada com sucesso!");
                System.out.println("⚠️  Você será desconectado na próxima sessão.");
            } else {
                System.err.println("❌ Não foi possível alterar a senha. Verifique a senha atual.");
            }
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao alterar senha: " + e.getMessage());
        }
    }
   
    private void excluirMinhaConta() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("  EXCLUIR MINHA CONTA (APENAS PARA TESTES)");
        System.out.println("=".repeat(50));
        System.out.println("⚠️  ⚠️  ⚠️  ATENÇÃO! ⚠️  ⚠️  ⚠️");
        System.out.println("Esta ação é IRREVERSÍVEL!");
        System.out.println("Todos os seus dados serão perdidos.");
        System.out.println("Não use esta função em produção!");
        System.out.println("=".repeat(50));
       
        System.out.print("\nDigite 'CONFIRMAR' para prosseguir: ");
        String confirmacao = scanner.nextLine();
       
        if (!confirmacao.equals("CONFIRMAR")) {
            System.out.println("✅ Operação cancelada.");
            return;
        }
       
        try {
            System.out.print("Confirme seu ID para exclusão (" + atendenteLogadoId + "): ");
            Long id = scanner.nextLong();
            scanner.nextLine();
           
            if (!id.equals(atendenteLogadoId)) {
                System.err.println("❌ ID incorreto!");
                return;
            }
           
            System.out.print("Digite 'EXCLUIR DEFINITIVAMENTE' para confirmação final: ");
            String confirmacaoFinal = scanner.nextLine();
           
            if (confirmacaoFinal.equals("EXCLUIR DEFINITIVAMENTE")) {
                boolean sucesso = controller.excluirMinhaConta(id);
               
                if (sucesso) {
                    System.out.println("✅ Conta excluída com sucesso.");
                    System.out.println("🚪 Encerrando programa...");
                    System.exit(0);
                } else {
                    System.err.println("❌ Não foi possível excluir a conta.");
                }
            } else {
                System.out.println("✅ Exclusão cancelada.");
            }
           
        } catch (Exception e) {
            System.err.println("❌ Erro ao excluir conta: " + e.getMessage());
        }
    }
}

