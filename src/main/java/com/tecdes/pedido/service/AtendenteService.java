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
   
    // ✅ NOVO MÉTODO ADICIONADO: Atualizar atendente
    public Atendente atualizarAtendente(Atendente atendenteAtualizado) {
        try {
            // Verificar se o atendente existe
            Atendente atendenteExistente = buscarAtendentePorId(atendenteAtualizado.getIdUsuario());
           
            // Atualizar os campos necessários
            if (atendenteAtualizado.getLogin() != null && !atendenteAtualizado.getLogin().isEmpty()) {
                // Verificar se o novo login não está em uso por outro usuário
                usuarioRepository.findByLogin(atendenteAtualizado.getLogin())
                    .ifPresent(outroUsuario -> {
                        if (!outroUsuario.getIdUsuario().equals(atendenteAtualizado.getIdUsuario())) {
                            throw new RuntimeException("Login já está em uso por outro usuário.");
                        }
                    });
                atendenteExistente.setLogin(atendenteAtualizado.getLogin());
            }
           
            if (atendenteAtualizado.getSenha() != null && !atendenteAtualizado.getSenha().isEmpty()) {
                atendenteExistente.setSenha(atendenteAtualizado.getSenha());
            }
           
            // Atualizar outros campos específicos do Atendente
            if (atendenteAtualizado instanceof Atendente) {
                Atendente atendenteNovo = (Atendente) atendenteAtualizado;
                if (atendenteNovo.getIdFuncionario() != null) {
                    ((Atendente) atendenteExistente).setIdFuncionario(atendenteNovo.getIdFuncionario());
                }
                if (atendenteNovo.getDataCadastro() != null) {
                    ((Atendente) atendenteExistente).setDataCadastro(atendenteNovo.getDataCadastro());
                }
            }
           
            // Salvar as alterações
            return (Atendente) usuarioRepository.save(atendenteExistente);
           
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar atendente: " + e.getMessage(), e);
        }
    }
}
