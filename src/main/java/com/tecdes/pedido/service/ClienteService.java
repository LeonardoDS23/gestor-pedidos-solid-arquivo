package com.tecdes.pedido.service;

import java.util.List;

import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.model.entity.Pedido;
import com.tecdes.pedido.model.entity.Produto;
import com.tecdes.pedido.repository.ClienteRepository;
import com.tecdes.pedido.repository.ClienteRepositorylmpl;
import com.tecdes.pedido.repository.PedidoRepository;
import com.tecdes.pedido.repository.PedidoRepositoryImpl;

public class ClienteService {

    private final ClienteRepository repository = new ClienteRepositorylmpl();

    // --- Criar ---
    public void salvarCliente(String nome, String fone) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo nome é obrigatório.");
        }
        if (fone == null) {
            throw new IllegalArgumentException("O campo telefone é obrigatório.");
        }

        Cliente cliente = new Cliente(nome, fone);
        repository.salvar(cliente);
    }

    // --- Listar todos ---
    public List<Cliente> buscarTodos() {
        return repository.buscarTodos();
    }

    // --- Buscar por ID ---
    public Cliente buscarPorId(int id) {
        Cliente cliente = repository.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }
        return cliente;
    }

    // --- Atualizar ---
    public void atualizarCliente(int id, String nome, String fone) {
        Cliente clienteExistente = repository.buscarPorId(id);
        if (clienteExistente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para atualização. ID: " + id);
        }

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo nome é obrigatório.");
        }
        if (fone == null) {
            throw new IllegalArgumentException("O campo telefone é obrigatório.");
        }

        clienteExistente.setNome(nome);
        clienteExistente.setFone(fone);

        repository.atualizar(clienteExistente);
    }

    // --- Deletar ---
    public void deletarCliente(int id) {
        Cliente cliente = repository.buscarPorId(id);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado para exclusão. ID: " + id);
        }
        repository.deletar(id);
    }
}
