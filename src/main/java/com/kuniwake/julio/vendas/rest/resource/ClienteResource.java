package com.kuniwake.julio.vendas.rest.resource;

import com.kuniwake.julio.vendas.domain.entities.Cliente;
import com.kuniwake.julio.vendas.domain.repositories.ClienteRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cliente")
@Api("Api de Clientes")
public class ClienteResource {

    @Autowired
    private final ClienteRepository clienteRepository;

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salva um novo cliente.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public Cliente saveCliente(@RequestBody @Valid Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualiza um cliente.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cliente atualizado"),
            @ApiResponse(code = 400, message = "Cliente não encontrado para o ID informado.")
    })
    public void updateCliente(@PathVariable Integer id, @RequestBody @Valid Cliente cliente){
        clienteRepository
            .findById(id)
            .map(clienteExistente -> {
                cliente.setId(clienteExistente.getId());
                clienteRepository.save(cliente);
                return clienteExistente;
            }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não Encontrado"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Deletar cliente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente deletado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public void deleteCliente(@PathVariable Integer id){
        clienteRepository
            .findById(id)
            .map(cliente -> {
                clienteRepository.delete(cliente);
                return cliente;
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public Cliente getByIdCliente(@PathVariable @ApiParam("Id do Cliente") Integer id){
        return clienteRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @GetMapping()
    @ApiOperation("Obter detalhes de um cliente utilizando filtro.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente encontrado"),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public List<Cliente> findFiltroCliente(Cliente filtro){
        ExampleMatcher matcher  = ExampleMatcher
            .matching()
            .withIgnoreCase() // Ignorando caixa alta ou não
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING); // buscando pela String que conten no filtro

        Example<Cliente> example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }

}
