package com.kuniwake.julio.vendas.rest.service.impl;

import com.kuniwake.julio.vendas.domain.dto.ItemPedidoDTO;
import com.kuniwake.julio.vendas.domain.dto.PedidoDTO;
import com.kuniwake.julio.vendas.domain.entities.Cliente;
import com.kuniwake.julio.vendas.domain.entities.ItemPedido;
import com.kuniwake.julio.vendas.domain.entities.Pedido;
import com.kuniwake.julio.vendas.domain.entities.Produto;
import com.kuniwake.julio.vendas.domain.enums.StatusPedido;
import com.kuniwake.julio.vendas.domain.repositories.ClienteRepository;
import com.kuniwake.julio.vendas.domain.repositories.ItemPedidoRepository;
import com.kuniwake.julio.vendas.domain.repositories.PedidoRepository;
import com.kuniwake.julio.vendas.domain.repositories.ProdutoRepository;
import com.kuniwake.julio.vendas.exceptions.ManagerException;
import com.kuniwake.julio.vendas.exceptions.PedidoNaoEncontradoException;
import com.kuniwake.julio.vendas.rest.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private final PedidoRepository pedidoRepository;

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final ProdutoRepository produtoRepository;

    @Autowired
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido persitPedido(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getClienteId();
        Cliente cliente = clienteRepository
                .findById(idCliente).orElseThrow(() -> new ManagerException("Codigo de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, pedidoDTO.getItens());
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer idPedido) {
        return pedidoRepository.findByIdFetchItens(idPedido);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer idPedido, StatusPedido newStatus) {
        pedidoRepository.findById(idPedido)
            .map(pedido -> {
                pedido.setStatus(newStatus);
                return pedidoRepository.save(pedido);
            }).orElseThrow(() -> new PedidoNaoEncontradoException());
    }

    // Metodo auxiliar para converter os itens em ItemPedido
    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if (itens.isEmpty()){
            throw new ManagerException("Não é possivel realizar um pedido sem itens.");
        }
        return itens.stream() // Transformando List<ItemPedidoDTO> em List<ItemPedido>
            .map( itemPedidoDTO -> {
                Integer idProduto = itemPedidoDTO.getProdutoId();
                Produto produto = produtoRepository.findById(idProduto)
                        .orElseThrow(() -> new ManagerException("Código de produto inválido : " + idProduto));

                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setQuantidade(itemPedidoDTO.getQuantidade());
                itemPedido.setPedido(pedido);
                itemPedido.setProduto(produto);
                return itemPedido;

            }).collect(Collectors.toList());
    }
}
