package br.rafael.codes.ecommerce.usuario.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.rafael.codes.ecommerce.exceptions.DataNotFoundException;
import br.rafael.codes.ecommerce.usuario.entity.Usuario;

/**
 * Interface de Serviço de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 202.07.2025
 */
public interface UsuarioService {

    /**
     * Lista todos os usuários cadastrados no sistema.
     * 
     * @return uma lista de usuários cadastrados no sistema.
     */
    List<Usuario> findAll();

    /**
     * Busca um usuário pelo email.
     * 
     * @param email Email do usuário.
     * @return Usuário encontrado.
     * @throws DataNotFoundException 
     */
    Usuario findUserByEmail(String email) throws DataNotFoundException;

    /**
     * Busca um usuário pelo username.
     * 
     * @param username Username do usuário.
     * @return Usuário encontrado.
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
