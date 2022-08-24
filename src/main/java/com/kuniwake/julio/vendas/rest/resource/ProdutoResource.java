package com.kuniwake.julio.vendas.rest.resource;

import com.kuniwake.julio.vendas.domain.entities.Produto;
import com.kuniwake.julio.vendas.domain.repositories.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/produto")
public class ProdutoResource {

    @Autowired
    private final ProdutoRepository produtoRepository;

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto saveProduto(@RequestBody @Valid Produto produto){
        return produtoRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateProduto(@PathVariable Integer id, @RequestBody @Valid Produto produto){
        produtoRepository
            .findById(id)
            .map(prodEncontrado -> {
               produto.setId(prodEncontrado.getId());
               produtoRepository.save(produto);
               return produto;
            }).orElseThrow(( ) -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduto(@PathVariable Integer id){
        produtoRepository
            .findById(id)
            .map(p -> {
                produtoRepository.delete(p);
                return Void.TYPE;
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Produto getByIdProduto(@PathVariable Integer id){
        return  produtoRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping()
    public List<Produto> findFiltroProduto(Produto filtro){
        ExampleMatcher matcher  = ExampleMatcher
                .matching()
                .withIgnoreCase() // Ignorando caixa alta ou não
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // buscando pela String que conten no filtro

        Example<Produto> example = Example.of(filtro, matcher);
        return produtoRepository.findAll(example);
    }

}
