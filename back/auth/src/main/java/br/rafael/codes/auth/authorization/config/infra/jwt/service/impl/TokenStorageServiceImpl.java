package br.rafael.codes.auth.authorization.config.infra.jwt.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.authorization.config.infra.jwt.repository.TokenJwtRepository;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenStorageService;
import br.rafael.codes.auth.usuario.entity.Usuario;
import jakarta.transaction.Transactional;

/**
 * Classe responsável pela configuração de segurança da API.
 */
@Service
public class TokenStorageServiceImpl implements TokenStorageService {

    @Autowired
    private TokenJwtRepository repository;

    @Override
    @Cacheable(value = "tokens_jwt", unless = "#result == null")
    public List<TokenJwt> findAll() {
        return repository.findAll();
    }

    @Override
    // For some reason it's giving me null values every time I try to use it, even with token existing...
    // @Cacheable(value = "tokens_jwt", key = "#id", unless = "#result == null")
    public Optional<TokenJwt> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public boolean exists(UUID id) {
        return repository.existsById(id);
    }

    @Override
    @CachePut(value = "tokens_jwt", key = "#user.id")
    @Transactional
    public void createToken(Usuario user, DecodedJWT tokenDecoded, String token) {
        final TokenJwt tokenEntity = new TokenJwt();
        tokenEntity.setUsuario(user);
        tokenEntity.setToken(token);
        tokenEntity.setCriadoEm(Instant.now());
        tokenEntity.setExpiraEm(tokenDecoded.getExpiresAt().toInstant());

        repository.save(tokenEntity);
    }

    @Override
    @CacheEvict(value = "tokens_jwt", key = "#id", allEntries = true)
    public void deleteToken(UUID id) {
        repository.deleteById(id);
    }
}
