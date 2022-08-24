package com.kuniwake.julio.vendas.domain.repositories;

import com.kuniwake.julio.vendas.domain.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String userName);

}
