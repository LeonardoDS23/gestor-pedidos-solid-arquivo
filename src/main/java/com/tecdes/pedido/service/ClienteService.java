package com.tecdes.pedido.service;


import com.tecdes.pedido.model.entity.Cliente;
import com.tecdes.pedido.repository.ClienteRepository;
import java.util.List;


public class ClienteService {


    private final ClienteRepository clienteRepository;


    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    // ✅ MÉTODO EXISTENTE
    public Cliente cadastrarCliente(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente é obrigatório.");
        }
        return clienteRepository.save(cliente);
    }
   
    // ✅ NOVO: Método conveniente para salvar com parâmetros simples
    public Cliente salvarCliente(String nome, String telefone, String email) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setFone(telefone);
        cliente.setEmail(email);
        return cadastrarCliente(cliente);
    }


    // ✅ MÉTODO EXISTENTE
    public Cliente buscarClientePorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente ID " + id + " não encontrado."));
    }
   
    // ✅ NOVO: Alias para compatibilidade
    public Cliente buscarPorId(Long id) {
        return buscarClientePorId(id);
    }


    public List<Cliente> buscarTodosClientes() {
        return clienteRepository.findAll();
    }
   
    public List<Cliente> buscarTodos() {
        return buscarTodosClientes();
    }
   
    // ✅ MÉTODO EXISTENTE (recebe objeto Cliente)
    public Cliente atualizarCliente(Long id, Cliente dadosNovos) {
        Cliente clienteExistente = buscarClientePorId(id);
       
        clienteExistente.setNome(dadosNovos.getNome());
        clienteExistente.setFone(dadosNovos.getFone());
        clienteExistente.setEmail(dadosNovos.getEmail()); // ✅ ADICIONAR EMAIL
       
        return clienteRepository.save(clienteExistente);
    }
   
    // ✅ NOVO: Método para atualizar com parâmetros simples
    public Cliente atualizarCliente(Long id, String nome, String telefone, String email) {
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome(nome);
        clienteAtualizado.setFone(telefone);
        clienteAtualizado.setEmail(email);
        return atualizarCliente(id, clienteAtualizado);
    }


    // ✅ MÉTODO EXISTENTE
    public void excluirCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente ID " + id + " não pode ser excluído, pois não existe.");
        }
        clienteRepository.deleteById(id);
    }
}
