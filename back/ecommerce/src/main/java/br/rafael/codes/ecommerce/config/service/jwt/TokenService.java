package br.rafael.codes.ecommerce.config.service.jwt;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * Classe responsável pela configuração de segurança da API.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 *  @since 02.07.2025
 */
@Component
public class TokenService {
    
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Integer expiration;
    private String issuer = "auth-ecommerce";


    /**
     * Cria um token para o usuário.
     * 
     * @return Token valido gerado.
     */
    public String generateKey() {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT.create()
            .withIssuer(issuer)
            .withExpiresAt(generateExpirationDate())
            .sign(algorithm);
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

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusMinutes(expiration / 60000).toInstant(ZoneOffset.of("-03:00"));
    }
}
