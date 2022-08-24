package com.kuniwake.julio.vendas.rest.service;

import com.kuniwake.julio.vendas.domain.dto.PedidoDTO;
import com.kuniwake.julio.vendas.domain.entities.Pedido;
import com.kuniwake.julio.vendas.domain.enums.StatusPedido;

import java.util.Optional;

public interface PedidoService {
    Pedido persitPedido(PedidoDTO pedidoDTO);
    Optional<Pedido> obterPedidoCompleto(Integer idPedido);
    void atualizarStatus(Integer idPedido, StatusPedido status);
}
