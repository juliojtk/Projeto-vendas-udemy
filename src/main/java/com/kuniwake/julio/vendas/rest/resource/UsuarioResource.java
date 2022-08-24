package com.kuniwake.julio.vendas.rest.resource;

import com.kuniwake.julio.vendas.domain.dto.CredenciaisDTO;
import com.kuniwake.julio.vendas.domain.dto.TokenDTO;
import com.kuniwake.julio.vendas.domain.entities.Usuario;
import com.kuniwake.julio.vendas.exceptions.SenhaInvalidaException;
import com.kuniwake.julio.vendas.rest.service.impl.UsuarioServiceImpl;
import com.kuniwake.julio.vendas.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;


    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credencias){
        try {
            Usuario usuario = Usuario.builder()
                    .username(credencias.getLogin())
                    .password(credencias.getPassword())
                    .build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getUsername(), token);
        }catch (UsernameNotFoundException | SenhaInvalidaException ex){
            throw new ResponseStatusException(UNAUTHORIZED, ex.getMessage());
        }
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public Usuario saveUser(@RequestBody @Valid Usuario usuario){
        String passwordCrypto = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(passwordCrypto);

        return usuarioService.persistUser(usuario);
    }

}
