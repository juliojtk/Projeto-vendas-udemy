package com.kuniwake.julio.vendas.security.jwt;

import com.kuniwake.julio.vendas.domain.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario){
        long expString = Long.parseLong(expiracao);

        // Transformando LocalDateTime para objeto Date
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
        Date data = Date.from(instant);

        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setExpiration(data)
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenValido(String token){
        try {
            Claims claims = obterClaims(token);
            Date dataExpiracaoClaims = claims.getExpiration();

            // Convertendo objeto do tipo Date para LocalDateTime
            LocalDateTime localDateTime = dataExpiracaoClaims.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            // Verificando se a hora local, não é apos a data de expiração do token
            return !LocalDateTime.now().isAfter(localDateTime);

        }catch (Exception e){
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        return obterClaims(token).getSubject();
    }

//    public static void main(String[] args) {
//        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
//        JwtService jwtService = context.getBean(JwtService.class);
//
//        String token = jwtService.gerarToken(
//                Usuario.builder()
//                        .username("julio")
//                        .build()
//        );
//
//        System.out.println(token);
//
//        boolean tokenIsValid = jwtService.tokenValido(token);
//        System.out.println("O token esta valido: " + tokenIsValid);
//
//        System.out.println(jwtService.obterLoginUsuario(token));
//
//    }
}
