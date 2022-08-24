package com.kuniwake.julio.vendas.domain.repositories;

import com.kuniwake.julio.vendas.domain.entities.Cliente;
import com.kuniwake.julio.vendas.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // Consulta para carregar ps pedidos do Cliente
    List<Pedido> findByCliente(Cliente cliente);

    // Trazer os itens do Pedido
    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id = :id")
    Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
