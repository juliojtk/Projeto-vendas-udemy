package com.kuniwake.julio.vendas.rest.service.impl;

import com.kuniwake.julio.vendas.domain.entities.Usuario;
import com.kuniwake.julio.vendas.domain.repositories.UsuarioRepository;
import com.kuniwake.julio.vendas.exceptions.SenhaInvalidaException;
import com.kuniwake.julio.vendas.rest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Usuario persistUser(Usuario user) {
        return usuarioRepository.save(user);
    }

    public UserDetails autenticar(Usuario usuario){
        UserDetails user = loadUserByUsername(usuario.getUsername());
        boolean passwordEquals = encoder.matches(usuario.getPassword(), user.getPassword());

        if (passwordEquals){
            return user;
        }
        throw new SenhaInvalidaException();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base."));

        String[] roles = usuario.isAdmin() ? new String[]{"ADMIN", "USER"} : new String[]{"USER"};

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(roles)
                .build();
    }
}
