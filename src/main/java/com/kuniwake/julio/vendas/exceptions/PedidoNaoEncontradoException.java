package com.kuniwake.julio.vendas.exceptions;

public class PedidoNaoEncontradoException extends RuntimeException {
    public PedidoNaoEncontradoException(){
        super("Pedido não encontrado.");
    }
}
