package com.kuniwake.julio.vendas.rest.resource;

import com.kuniwake.julio.vendas.domain.dto.InfItemPedidoDTO;
import com.kuniwake.julio.vendas.domain.dto.InfPedidoDTO;
import com.kuniwake.julio.vendas.domain.dto.PedidoDTO;
import com.kuniwake.julio.vendas.domain.dto.UpdateStatusPedidoDTO;
import com.kuniwake.julio.vendas.domain.entities.ItemPedido;
import com.kuniwake.julio.vendas.domain.entities.Pedido;
import com.kuniwake.julio.vendas.domain.enums.StatusPedido;
import com.kuniwake.julio.vendas.rest.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/pedidos")
public class PedidoResource {

    @Autowired
    private final PedidoService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer savePedido(@RequestBody @Valid PedidoDTO pedidoDTO){
        Pedido pedido = service.persitPedido(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    public InfPedidoDTO getByIdPedido(@PathVariable Integer id){
        return service.obterPedidoCompleto(id)
                .map(p -> convertPedido(p))
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado. "));
    }

    @PatchMapping("/{idPedido}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer idPedido, @RequestBody @Valid UpdateStatusPedidoDTO updateStatusPedidoDTO){
        String newStatus = updateStatusPedidoDTO.getNovoStatus();
        service.atualizarStatus(idPedido, StatusPedido.valueOf(newStatus));
    }

    private InfPedidoDTO convertPedido(Pedido pedido){
        return InfPedidoDTO.builder()
            .codigo(pedido.getId())
            .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) // Convertendo a data no formato brasileiro
            .cpf(pedido.getCliente().getCpf())
            .nomeCliente(pedido.getCliente().getNome())
            .total(pedido.getTotal())
            .status(pedido.getStatus().name())
            .itens(convertItemPedido(pedido.getItens()))
            .build();
    }

    private List<InfItemPedidoDTO> convertItemPedido(List<ItemPedido> itens){
        if (CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream() // Transformando List<ItemPedido> em List<InfItemPedidoDTO>
            .map(itemPedido -> InfItemPedidoDTO.builder()
                .descicaoProduto(itemPedido.getProduto().getDescricao())
                .precoUnitario(itemPedido.getProduto().getPreco())
                .quantidade(itemPedido.getQuantidade())
                .build()).collect(Collectors.toList());
    }



}
