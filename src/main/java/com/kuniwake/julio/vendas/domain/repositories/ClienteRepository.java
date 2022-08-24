package com.kuniwake.julio.vendas.domain.repositories;

import com.kuniwake.julio.vendas.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("select c from Cliente c where c.id = :id")
    Optional<Cliente> getByClienteID(@Param("id") Integer id);

    // ********** São Equivalentes **********
    List<Cliente> findByNomeLike(String nome);

    @Query(value = "select * from cliente c where c.nome like '%:nome%' ", nativeQuery = true)
    List<Cliente> EncontrarPorNomeSql( @Param("nome") String nome );
    // ****************************************************************************************************

    // ********** Outras Consultas Basicas **********
    List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);
    Cliente findOneByNome(String nome);
    boolean existsByNome(String nome);
    // ****************************************************************************************************

    // ********** Consultas com relacionamentos de outras entidades **********
    // Buscando todos os pedidos do Cliente
    @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);

    // ********** Exemplo quando não for uma consulta **********
    @Modifying
    // Para Dizer que não é uma consulta.
    void deleteByNome(String nome);

}
