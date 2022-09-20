package com.softcode.primeiroexemplo.repository;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.softcode.primeiroexemplo.model.Produto;

@Repository
public class ProdutoRepositoryOld {
    
    private List<Produto> produtos = new ArrayList<>(); 
    private Integer ultimoId = 0;

    public List<Produto> obterTodos(){
        return produtos;
    }

    public Optional<Produto> obterPorId(Integer id){
        return produtos.stream().filter(produto -> produto.getId() == id).findFirst();
    }

    public Produto adcionar(Produto produto){
        ultimoId++;
        produto.setId(ultimoId);
        produtos.add(produto);
        return produto;
    }

    public void deletar(Integer id){
        produtos.removeIf(produto -> produto.getId() == id);
    }
    
    public Produto atualizar(Produto produto, Integer id){
        Optional<Produto> produtoEncontrado = obterPorId(id);
        
        if(produtoEncontrado != null){
            throw new InputMismatchException("Produto não encontrado");
        }
        else{
            deletar(id);
            produto.setId(id);
            produtos.add(produto);
    
            return produto;
        }
        }
        
}
