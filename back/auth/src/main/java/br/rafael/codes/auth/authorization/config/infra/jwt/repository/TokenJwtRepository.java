package br.rafael.codes.auth.authorization.config.infra.jwt.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;

/**
 * Interface de Repositório de Tokens JWT.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 26.06.2025
 */
@Repository
public interface TokenJwtRepository extends JpaRepository<TokenJwt, UUID> {

}
