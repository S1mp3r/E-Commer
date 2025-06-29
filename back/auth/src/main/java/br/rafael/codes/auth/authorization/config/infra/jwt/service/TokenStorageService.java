package br.rafael.codes.auth.authorization.config.infra.jwt.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.usuario.entity.Usuario;

/**
 * Interface responsável pela configuração de segurança da API.
 */
public interface TokenStorageService {

    /**
     * Persisti um token para o usuário.
     * 
     * @param user Usuário a ter seu token persistido.
     * @param tokenDecoded Token valido persistido.
     * @param token O Token recebido pela requisicao.
     */
    public void createToken(Usuario user, DecodedJWT tokenDecoded, String token);

    /**
     * Remove o token do Redis apos o logout ou expirado.
     * 
     * @param id Identificador Unico Universal do usuario da sessao.
     */
    public void deleteToken(UUID id);

    /**
     * Verifica se o token existe no Redis.
     * 
     * @param id Identificador Unico Universal do usuario da sessao.
     * @return Token valido, se nao existir retorna vazio.
     */
    public Optional<TokenJwt> findById(UUID id);

    /**
     * Verifica se o token existe no Redis.
     * @param id Identificador Unico Universal do usuario da sessao.
     * @return True se houver, false se nao houver.
     */
    public boolean exists(UUID id);

    /**
     * Busca todos os tokens do Redis.
     * @return Todos os tokens do Redis.
     */
    public List<TokenJwt> findAll();

}