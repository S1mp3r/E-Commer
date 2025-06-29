package br.rafael.codes.auth.authorization.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.exceptions.DataNotFoundException;
import br.rafael.codes.auth.exceptions.DuplicatedDataException;

/**
 * Interface de Serviço de Autenticação.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
public interface AuthenticationService {
    
    /**
     * Persisti um usuário não logado em banco.
     * 
     * @param auth Corpo de Autenticação.
     * @throws DuplicatedDataException 
     * @throws Exception 
     */
    void signUp(AuthenticationDTO auth) throws Exception;

    /**
     * Autentica um usuário e gera um token de autenticação.
     * 
     * @param userToSignUp Corpo de Autenticação.
     * @return Token de Autenticação.
     * @throws DataNotFoundException 
     */
    String authenticate(UsernamePasswordAuthenticationToken userToSignUp) throws DataNotFoundException;
}
