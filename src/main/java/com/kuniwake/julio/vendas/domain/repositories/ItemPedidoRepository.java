package com.kuniwake.julio.vendas.domain.repositories;

import com.kuniwake.julio.vendas.domain.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
