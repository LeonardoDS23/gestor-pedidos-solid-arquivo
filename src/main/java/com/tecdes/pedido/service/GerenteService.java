package com.tecdes.pedido.service;


import com.tecdes.pedido.model.entity.Gerente;
import com.tecdes.pedido.model.entity.Usuario;
import com.tecdes.pedido.repository.UsuarioRepository;
import java.util.List;
import java.util.stream.Collectors;


public class GerenteService {


    private final UsuarioRepository usuarioRepository;
   
    public GerenteService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    public Gerente cadastrarGerente(Gerente gerente) {
        if (usuarioRepository.findByLogin(gerente.getLogin()).isPresent()) {
            throw new RuntimeException("Login já em uso.");
        }
        usuarioRepository.save(gerente);
        return gerente;
    }


    public Gerente buscarGerentePorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gerente ID " + id + " não encontrado."));
       
        if (!(usuario instanceof Gerente)) {
            throw new RuntimeException("Usuário encontrado não é um Gerente.");
        }
        return (Gerente) usuario;
    }


    public List<Gerente> buscarTodosGerentes() {
        return usuarioRepository.findAllByType(Gerente.class).stream()
                .map(u -> (Gerente) u)
                .collect(Collectors.toList());
    }
   
    public void redefinirSenhaUsuario(Long idUsuario, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário ID " + idUsuario + " não encontrado."));
       
        // Regra: Gerente pode alterar a senha de qualquer usuário
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
    }
   
    public void excluirUsuario(Long idUsuario) {
        // Regra: Gerente pode excluir qualquer usuário (após validações de segurança)
        usuarioRepository.deleteById(idUsuario);
    }
}
