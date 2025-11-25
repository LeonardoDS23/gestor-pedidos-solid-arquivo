package com.tecdes.pedido.controller;

import com.tecdes.pedido.model.entity.Funcionario;
import com.tecdes.pedido.service.FuncionarioService;

public class FuncionarioController {

    private FuncionarioService service = new FuncionarioService();

    public void cadastrar(Funcionario funcionario){
        service.cadastrar(funcionario);
    }

    public void listar(){
        service.listarFuncionarios();
    }
}
