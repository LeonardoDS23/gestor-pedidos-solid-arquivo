package com.tecdes.pedido;

import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.ProdutoRepository;
import com.tecdes.pedido.service.ProdutoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Sistema para ProdutoService e ProdutoRepository")
public class ProdutoServiceTest {

    private ProdutoService service;
    private ProdutoRepository repositoryFake;

    // -----------------------------------------------------------------------
    // FAKE REPOSITORY COMPLETO
    // -----------------------------------------------------------------------
    class ProdutoRepositoryFake implements ProdutoRepository {

        private Map<Long, Produto> banco = new HashMap<>();
        private long idSequencia = 1;

        @Override
        public Produto save(Produto produto) {
            if (produto.getIdProduto() == null) {
                produto.setIdProduto(idSequencia++);
            }
            banco.put(produto.getIdProduto(), produto);
            return produto;
        }

        @Override
        public Optional<Produto> findById(Long id) {
            return Optional.ofNullable(banco.get(id));
        }

        @Override
        public List<Produto> findAll() {
            return new ArrayList<>(banco.values());
        }

        @Override
        public void delete(Long id) {
            banco.remove(id);
        }

        @Override
        public List<Produto> findByCategoria(String categoria) {
            List<Produto> list = new ArrayList<>();
            for (Produto p : banco.values()) {
                if (p.getCategoria().equalsIgnoreCase(categoria)) {
                    list.add(p);
                }
            }
            return list;
        }

        @Override
        public List<Produto> findByNomeContaining(String nomeParcial) {
            List<Produto> list = new ArrayList<>();
            for (Produto p : banco.values()) {
                if (p.getNome().toLowerCase().contains(nomeParcial.toLowerCase())) {
                    list.add(p);
                }
            }
            return list;
        }
    }

    // -----------------------------------------------------------------------
    // SETUP DO TESTE
    // -----------------------------------------------------------------------
    @BeforeEach
    void setup() {
        repositoryFake = new ProdutoRepositoryFake();
        service = new ProdutoService(repositoryFake);

        // Produto inicial da lanchonete
        service.salvarProduto("Refrigerante Cola", 8.00, "Bebida", "Lata 350ml");
    }

    // -----------------------------------------------------------------------
    // 1 ● TESTE DE SUCESSO COMPLETO
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("Deve criar, buscar e atualizar um produto com sucesso")
    void deveExecutarFluxoPrincipalComSucesso() {

        Produto novo = service.salvarProduto(
                "Hambúrguer", 18.5, "Lanche", "Pão, carne e queijo"
        );

        Produto atualizado = service.atualizarProduto(
                novo.getIdProduto(),
                "Hambúrguer Duplo", 26.0, "Lanche", "Duas carnes e queijo"
        );

        Produto encontrado = service.buscarPorId(atualizado.getIdProduto());

        assertNotNull(encontrado);
        assertEquals("Hambúrguer Duplo", encontrado.getNome());
        assertEquals(26.0, encontrado.getPreco());
    }

    // -----------------------------------------------------------------------
    // 2 ● TESTES DE REGRA DE NEGÓCIO
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("Não deve permitir salvar produto com nome vazio ou preço inválido")
    void naoDeveSalvarDadosInvalidos() {

        assertThrows(IllegalArgumentException.class, () -> {
            service.salvarProduto("", 10.0, "Bebida", "Sem nome");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            service.salvarProduto("Batata Frita", 0, "Lanche", "Porção");
        });
    }

    // -----------------------------------------------------------------------
    // 3 ● TESTE DE LIMITE
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("Deve aceitar valores mínimos válidos para produto")
    void deveAceitarValoresMinimosValidos() {

        Produto p = service.salvarProduto(
                "Suco Natural", 0.01, "Bebida", "Suco de fruta"
        );

        assertNotNull(p.getIdProduto());
        assertEquals(0.01, p.getPreco());
    }

    // -----------------------------------------------------------------------
    // 4 ● TESTE DELETE
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("Deve excluir um produto com sucesso")
    void deveExcluirProduto() {

        Produto produto = service.buscarTodos().get(0);
        Long id = produto.getIdProduto();

        service.deletarProduto(id);

        assertThrows(RuntimeException.class, () -> service.buscarPorId(id));
    }

    // -----------------------------------------------------------------------
    // 5 ● BUSCA INEXISTENTE
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("Deve falhar ao buscar produto inexistente")
    void deveFalharAoBuscarInexistente() {

        assertThrows(RuntimeException.class, () -> service.buscarPorId(999L));
    }

    // -----------------------------------------------------------------------
    // 6 ● REPOSITORY: SAVE
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("Repositorio: Deve salvar produto e gerar ID")
    void repositoryDeveSalvarProduto() {

        Produto produto = new Produto();
        produto.setNome("Milkshake");
        produto.setPreco(14.0);
        produto.setCategoria("Sobremesa");
        produto.setDescricao("Milkshake de chocolate");

        Produto salvo = repositoryFake.save(produto);

        assertNotNull(salvo.getIdProduto());
        assertEquals("Milkshake", salvo.getNome());
    }

    // -----------------------------------------------------------------------
    // 7 ● REPOSITORY: FIND BY ID
    // -----------------------------------------------------------------------
    @Test
    @DisplayName("Repositorio: Deve buscar produto salvo pelo ID")
    void repositoryDeveBuscarPorId() {

        Produto produto = new Produto(null, "Cachorro-Quente", 12.0, "Lanche", "Pão, salsicha e molho");
        Produto salvo = repositoryFake.save(produto);

        Optional<Produto> encontrado = repositoryFake.findById(salvo.getIdProduto());

        assertTrue(encontrado.isPresent());
        assertEquals("Cachorro-Quente", encontrado.get().getNome());
    }
}
