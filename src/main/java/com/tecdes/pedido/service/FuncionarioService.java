package com.tecdes.pedido.service;

import com.tecdes.pedido.model.entity.Funcionario;
import com.tecdes.pedido.repository.FuncionarioRepository;

public class FuncionarioService {

    private FuncionarioRepository repository = new FuncionarioRepository();

    public void cadastrar(Funcionario funcionario){
        repository.salvar(funcionario);
    }

    public void listarFuncionarios(){
        repository.listar().forEach(f -> 
            System.out.println(f.getId() + " - " + f.getNome() + " - " + f.getCargo())
        );
    }
}
