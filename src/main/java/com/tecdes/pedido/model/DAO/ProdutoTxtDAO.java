package com.tecdes.pedido.model.DAO;


import java.io.*;
import java.util.ArrayList;
import java.util.List;


import com.tecdes.pedido.model.entity.Produto;


public class ProdutoTxtDAO {


    private final String caminho = "data/produtos.txt";


    // SALVAR
    public void salvar(Produto p) {


        try {
            FileWriter fw = new FileWriter(caminho, true);
            BufferedWriter bw = new BufferedWriter(fw);


            bw.write(p.toTxt());
            bw.newLine();


            bw.close();
            fw.close();


        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar produto: " + e.getMessage());
        }
    }


    // LER TODOS
    public List<Produto> listar() {
        List<Produto> lista = new ArrayList<>();


        try {
            File file = new File(caminho);
            if (!file.exists()) {
                return lista; // Arquivo vazio ainda
            }


            FileReader fr = new FileReader(caminho);
            BufferedReader br = new BufferedReader(fr);


            String linha;
            while ((linha = br.readLine()) != null) {
                lista.add(Produto.fromTxt(linha));
            }


            br.close();
            fr.close();


        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler produtos: " + e.getMessage());
        }


        return lista;
    }
}
