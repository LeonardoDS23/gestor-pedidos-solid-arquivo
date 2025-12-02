package com.tecdes.pedido.view;

import com.tecdes.pedido.service.AvaliacaoService;
import com.tecdes.pedido.model.entity.Avaliacao;

import javax.swing.*;
import java.awt.*;

public class AvaliacaoView extends JFrame {

    private JTextField txtIdPedido;
    private JComboBox<Integer> cbNota;
    private JTextArea txtComentario;
    private JButton btnVoltar;

    private final AvaliacaoService avaliacaoService;

    public AvaliacaoView(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
        
        setTitle("⭐ Avaliar Pedido");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
        setVisible(true);
    }

    private void inicializarComponentes() {
        getContentPane().setLayout(null);
        
        // TÍTULO
        JLabel lblTitulo = new JLabel("AVALIAÇÃO DE PEDIDO");
        lblTitulo.setBounds(100, 10, 200, 30);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo);
        
        // PAINEL DE AVALIAÇÃO
        JPanel painelAvaliacao = new JPanel();
        painelAvaliacao.setLayout(null);
        painelAvaliacao.setBounds(20, 50, 350, 250);
        painelAvaliacao.setBorder(BorderFactory.createTitledBorder("Dados da Avaliação"));
        
        JLabel lblIdPedido = new JLabel("ID do Pedido:");
        lblIdPedido.setBounds(10, 30, 120, 25);
        painelAvaliacao.add(lblIdPedido);
        
        txtIdPedido = new JTextField();
        txtIdPedido.setBounds(140, 30, 180, 25);
        painelAvaliacao.add(txtIdPedido);
        
        JLabel lblNota = new JLabel("Nota (1-5):");
        lblNota.setBounds(10, 70, 120, 25);
        painelAvaliacao.add(lblNota);
        
        Integer[] notas = {1, 2, 3, 4, 5};
        cbNota = new JComboBox<>(notas);
        cbNota.setBounds(140, 70, 180, 25);
        cbNota.setSelectedIndex(4); // Nota 5 por padrão
        painelAvaliacao.add(cbNota);
        
        JLabel lblComentario = new JLabel("Comentário:");
        lblComentario.setBounds(10, 110, 120, 25);
        painelAvaliacao.add(lblComentario);
        
        txtComentario = new JTextArea();
        txtComentario.setLineWrap(true);
        JScrollPane scrollComentario = new JScrollPane(txtComentario);
        scrollComentario.setBounds(140, 110, 180, 100);
        painelAvaliacao.add(scrollComentario);
        
        add(painelAvaliacao);
        
        // BOTÕES
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBounds(20, 310, 350, 80);
        
        JButton btnSalvar = new JButton("⭐ Registrar Avaliação");
        btnSalvar.addActionListener(e -> registrarAvaliacao());
        painelBotoes.add(btnSalvar);
        
        JButton btnLimpar = new JButton("🧹 Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        painelBotoes.add(btnLimpar);
        
        btnVoltar = new JButton("⬅️ Voltar");
        btnVoltar.addActionListener(e -> this.dispose());
        painelBotoes.add(btnVoltar);
        
        add(painelBotoes);
    }
    
    private void registrarAvaliacao() {
        try {
            // Validações básicas
            if (txtIdPedido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "ID do Pedido é obrigatório!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Long idPedido = Long.parseLong(txtIdPedido.getText().trim());
            Integer nota = (Integer) cbNota.getSelectedItem();
            String comentario = txtComentario.getText().trim();
            
            // Validação da nota
            if (nota == null || nota < 1 || nota > 5) {
                JOptionPane.showMessageDialog(this, 
                    "Nota deve ser entre 1 e 5!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Criar objeto Avaliacao usando o construtor correto
            // Baseado no seu código original: new Avaliacao(idPedido, nota, comentario)
            Avaliacao avaliacao = new Avaliacao();
            
            // Configurar os campos (ajuste conforme sua classe Avaliacao)
            avaliacao.setIdPedido(idPedido);
            avaliacao.setNota(nota);
            avaliacao.setComentario(comentario);
            
            // Registrar usando o método CORRETO do service
            Avaliacao avaliacaoRegistrada = avaliacaoService.registrarAvaliacao(avaliacao);
            
            // Mensagem de sucesso
            if (avaliacaoRegistrada != null) {
                JOptionPane.showMessageDialog(this, 
                    "✅ Avaliação registrada com sucesso!\n\n" +
                    "📋 Detalhes:\n" +
                    "   📝 ID do Pedido: " + idPedido + "\n" +
                    "   ⭐ Nota: " + nota + "/5\n" +
                    (comentario.isEmpty() ? "" : "   💬 Comentário: " + comentario + "\n") +
                    "   🆔 ID da Avaliação: " + (avaliacaoRegistrada.getIdAvaliacao() != null ? 
                        avaliacaoRegistrada.getIdAvaliacao() : "Gerado"),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "❌ Não foi possível registrar a avaliação.", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "❌ ID do Pedido deve ser um número válido!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, 
                "❌ " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this, 
                "❌ " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "❌ Erro ao registrar avaliação: " + e.getMessage(), 
                "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para debug
        }
    }
    
    private void limparCampos() {
        txtIdPedido.setText("");
        cbNota.setSelectedIndex(4); // Volta para nota 5
        txtComentario.setText("");
    }
}