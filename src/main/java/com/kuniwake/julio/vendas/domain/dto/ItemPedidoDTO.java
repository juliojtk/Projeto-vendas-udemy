package com.kuniwake.julio.vendas.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemPedidoDTO {

    private Integer produtoId;
    private Integer quantidade;
}
