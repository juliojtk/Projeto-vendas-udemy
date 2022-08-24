package com.kuniwake.julio.vendas.domain.entities;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @ManyToOne // Muitos ItemPedido para um Pedido
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
    @ManyToOne // Muitos ItemPedido para um Produto
    @JoinColumn(name = "produto_id")
    private Produto produto;
    @Column(name = "quantidade")
    private Integer quantidade;

}
