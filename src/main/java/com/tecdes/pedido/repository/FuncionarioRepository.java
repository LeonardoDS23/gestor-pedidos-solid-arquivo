package com.tecdes.pedido.repository;


import com.tecdes.pedido.model.entity.Gerente;


import java.util.ArrayList;
import java.util.List;


public class FuncionarioRepository {


    private List<Gerente> funcionarios = new ArrayList<>();


    public void salvar(Gerente funcionario){
        funcionarios.add(funcionario);
    }


    public List<Gerente> listar(){
        return funcionarios;
    }
}


