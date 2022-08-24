package com.kuniwake.julio.vendas.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfItemPedidoDTO {

    private String descicaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade;
}
