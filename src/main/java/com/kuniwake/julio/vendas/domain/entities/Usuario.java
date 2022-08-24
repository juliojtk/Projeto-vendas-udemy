package com.kuniwake.julio.vendas.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotEmpty(message = "{campo.user-name.obrigatorio}")
    private String username;

    @Column
    @NotEmpty(message = "{campo.password.obrigatorio}")
    private String password;

    @Column
    private boolean admin;
}
