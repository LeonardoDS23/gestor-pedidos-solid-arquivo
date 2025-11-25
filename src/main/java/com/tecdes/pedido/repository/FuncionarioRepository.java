package com.tecdes.pedido.repository;

import com.tecdes.pedido.model.entity.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioRepository {

    private List<Funcionario> funcionarios = new ArrayList<>();

    public void salvar(Funcionario funcionario){
        funcionarios.add(funcionario);
    }

    public List<Funcionario> listar(){
        return funcionarios;
    }
}
