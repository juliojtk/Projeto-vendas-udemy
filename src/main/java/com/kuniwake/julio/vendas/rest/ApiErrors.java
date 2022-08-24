package com.kuniwake.julio.vendas.rest;

import lombok.Getter;

import java.util.List;

public class ApiErrors {
    @Getter
    private List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String msgErro){
        this.errors = List.of(msgErro);
    }
}
