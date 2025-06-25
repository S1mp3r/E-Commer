package br.rafael.codes.auth.usuario.service;

import java.util.List;

import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.exceptions.DuplicatedDataException;
import br.rafael.codes.auth.usuario.entity.Usuario;

/**
 * Interface de Serviço de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
public interface UsuarioService {
    
    /**
     * Registra um novo usuário no sistema.
     * 
     * @param auth Corpo de Autenticação.
     * @throws DuplicatedDataException 
     * @throws Exception 
     */
    void signUp(AuthenticationDTO auth) throws Exception;

    /**
     * Lista todos os usuários cadastrados no sistema.
     * 
     * @return uma lista de usuários cadastrados no sistema.
     */
    List<Usuario> findAll();
}
