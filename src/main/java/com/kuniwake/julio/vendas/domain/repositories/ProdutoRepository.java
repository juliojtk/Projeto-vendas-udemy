package com.kuniwake.julio.vendas.domain.repositories;

import com.kuniwake.julio.vendas.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
