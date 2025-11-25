package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Atendente;
import com.tecdes.pedido.model.entity.Usuario;
import com.tecdes.pedido.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;

public class AtendenteService {

    private final UsuarioRepository usuarioRepository;
    
    public AtendenteService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Atendente cadastrarAtendente(Atendente atendente) {
        // Validação de login único seria feita aqui antes de salvar
        if (usuarioRepository.findByLogin(atendente.getLogin()).isPresent()) {
            throw new RuntimeException("Login já em uso.");
        }
        usuarioRepository.save(atendente);
        return atendente;
    }

    public Atendente buscarAtendentePorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendente ID " + id + " não encontrado."));
        
        if (!(usuario instanceof Atendente)) {
            throw new RuntimeException("Usuário encontrado não é um Atendente.");
        }
        return (Atendente) usuario;
    }

    public List<Atendente> buscarTodosAtendentes() {
        return usuarioRepository.findAllByType(Atendente.class).stream()
                .map(u -> (Atendente) u)
                .collect(Collectors.toList());
    }
    
    public void excluirAtendente(Long id) {
        // Validação de exclusão:
        buscarAtendentePorId(id);
        usuarioRepository.deleteById(id);
    }
}