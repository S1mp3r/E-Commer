package br.rafael.codes.auth.authorization.config.infra.jwt.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.usuario.entity.Usuario;

/**
 * Classe responsável pela configuração de segurança da API.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 *  @since 24.06.2025
 */
@Component
public class TokenService {
    
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Integer expiration;
    private String issuer = "auth-ecommerce";

    @Autowired
    private TokenStorageService redisTkStorageService;

    @Autowired
    private ApplicationContext context;

    /**
     * Gera um token para o usuário.
     * 
     * @param user Usuário a ter seu token gerado.
     * @return Token valido gerado.
     */
    public String generateKey(Usuario user) {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            var token = JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getEmail())
                .withExpiresAt(generateExpirationDate())
                .sign(algorithm)
            ;

            redisTkStorageService.createToken(user, getDecodedToken(token), token);

            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    /**
     * Valida o token enviado pelo usuário.
     * 
     * @param token Token enviado pelo usuário.
     * @return Token valido.
     */
    public DecodedJWT validateToken(String token) {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while validating token", e);
        }
    }

    public void deleteToken(UUID id) {
        context.getBean(TokenStorageService.class).deleteToken(id);
    }

    public Optional<TokenJwt> findById(UUID id) {
        return context.getBean(TokenStorageService.class).findById(id);
    }
    
    public boolean exists(UUID id) {
        return context.getBean(TokenStorageService.class).exists(id);
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusMinutes(expiration / 60000).toInstant(ZoneOffset.of("-03:00"));
    }

    private DecodedJWT getDecodedToken(String token) {
        return validateToken(token);
    }
}
