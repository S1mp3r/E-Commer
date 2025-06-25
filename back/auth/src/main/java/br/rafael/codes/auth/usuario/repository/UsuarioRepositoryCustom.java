package br.rafael.codes.auth.usuario.repository;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import br.rafael.codes.auth.usuario.entity.Usuario;

/**
 * Interface de Repositório de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
public interface UsuarioRepositoryCustom {
    
    /**
     * Busca um usuário pelo email.
     * 
     * @param email Email do usuário.
     * @return Usuário encontrado.
     */
    UserDetails findByEmail(String email);

    /**
     * Busca um usuário pelo email e senha.
     * 
     * @param email Email do usuário.
     * @param password Senha do usuário.
     * @return Usuário encontrado, se não encontrar retorna vazio.
     */
    Optional<Usuario> findDuplicatedData(String email, String password);
}
