package com.softcode.primeiroexemplo.view.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softcode.primeiroexemplo.service.ProdutoService;
import com.softcode.primeiroexemplo.shared.ProdutoDTO;
import com.softcode.primeiroexemplo.view.model.ProdutoRequest;
import com.softcode.primeiroexemplo.view.model.ProdutoResponse;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;
    
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> obterTodos(){

        List<ProdutoDTO> produtos = service.obterTodos();
        List<ProdutoResponse> resposta = produtos.stream()
        .map(produtoDto -> new ModelMapper().map(produtoDto, ProdutoResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(resposta,HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoResponse>> obterPorId(@PathVariable Integer id){
       
        Optional<ProdutoDTO> produtoDto = service.obterPorId(id);
        ProdutoResponse produtoResponse = new ModelMapper().map(produtoDto.get(), ProdutoResponse.class);      
        return new ResponseEntity<>(Optional.of(produtoResponse), HttpStatus.OK);
    } 

    @PostMapping
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoReq){
        ModelMapper mapper =  new ModelMapper();
        ProdutoDTO dto = mapper.map(produtoReq, ProdutoDTO.class);
        dto = service.adcionar(dto);
        ProdutoResponse produtoResp = mapper.map(dto, ProdutoResponse.class);
        return new ResponseEntity<>(produtoResp,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id){
        
        service.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@RequestBody ProdutoRequest produtoReq, @PathVariable Integer id){
       
        ModelMapper mapper =  new ModelMapper();
        ProdutoDTO dto = mapper.map(produtoReq, ProdutoDTO.class);
        service.atualizar(dto,id);
        return new ResponseEntity<>(mapper.map(produtoReq, ProdutoResponse.class), HttpStatus.OK);

    }

}
