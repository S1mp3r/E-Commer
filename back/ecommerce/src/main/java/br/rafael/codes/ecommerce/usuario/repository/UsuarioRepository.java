package br.rafael.codes.ecommerce.usuario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

import br.rafael.codes.ecommerce.usuario.entity.Usuario;

/**
 * Interface de Repositório de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 02.07.2025
 */
@Repository
public interface UsuarioRepository extends JpaRepositoryImplementation<Usuario, UUID>, UsuarioRepositoryCustom {

}