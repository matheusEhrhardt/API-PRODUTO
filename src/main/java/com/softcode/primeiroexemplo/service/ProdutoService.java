package com.softcode.primeiroexemplo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.softcode.primeiroexemplo.model.Produto;
import com.softcode.primeiroexemplo.model.exception.ResourseNotFoundException;
import com.softcode.primeiroexemplo.repository.ProdutoRepository;
import com.softcode.primeiroexemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository repository;

    public List<ProdutoDTO> obterTodos(){
        List<Produto> produtos =  repository.findAll();

        return produtos.stream().map(produto -> new ModelMapper().map(produto, ProdutoDTO.class)).collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> obterPorId(Integer id){

        Optional<Produto> produtoOptional = repository.findById(id);

        if (produtoOptional.get() == null){
            throw new ResourseNotFoundException("Produto com ID: "+ id + " não encontrado");
        }
        
        ProdutoDTO dto = new ModelMapper().map(produtoOptional.get(), ProdutoDTO.class); 

        return Optional.of(dto);

    }

    public ProdutoDTO adcionar(ProdutoDTO produtoDTO){
        produtoDTO.setId(null);
        Produto produto = new ModelMapper().map(produtoDTO, Produto.class);
        produto = repository.save(produto);
        produtoDTO.setId(produto.getId());
        return produtoDTO;
    }

    public void deletar(Integer id){
        Optional<Produto> produto = repository.findById(id);

        if (produto.get() == null){
            throw new ResourseNotFoundException("Produto com ID: "+ id + " não encontrado");
        }

        repository.deleteById(id);  
    }

    public ProdutoDTO atualizar(ProdutoDTO produtoDTO, Integer id){
        Optional<Produto> model = repository.findById(id);

        if (model.get() == null){
            throw new ResourseNotFoundException("Produto com ID: "+ id + " não encontrado");
        }
        produtoDTO.setId(id);
        Produto produto = new ModelMapper().map(produtoDTO, Produto.class);
        repository.save(produto);
        return produtoDTO;
    }
}
