package br.rafael.codes.auth.usuario.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import br.rafael.codes.auth.AuthUtils;
import br.rafael.codes.auth.usuario.entity.Usuario;
import br.rafael.codes.auth.usuario.repository.UsuarioRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

/**
 * Classe de Implementação da Classe de Repositório de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails findByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        
        String query = "select u from Usuario u ";
        String where = "where 1=1 ";

        if(!AuthUtils.isEmptyString(email)) {
            params.put("email", email);
            where += "and u.email = :email ";
        }

        TypedQuery<UserDetails> user = em.createQuery(query + where, UserDetails.class);
        
        params.forEach(user::setParameter);

        return user.getSingleResult();
    }

    @Override
    public Optional<Usuario> findDuplicatedData(String email, String password) {
        Map<String, Object> params = new HashMap<>();
        
        String query = "select u from Usuario u ";
        String where = "where 1=1 ";

        if(!AuthUtils.isEmptyString(email)) {
            params.put("email", email);
            where += "and u.email = :email ";
        }
        if(!AuthUtils.isEmptyString(password)) {
            params.put("password", password);
            where += "and u.password = :password ";
        }

        where += " and u.role = 'USER'";
        
        TypedQuery<Usuario> user = em.createQuery(query + where, Usuario.class);
        
        params.forEach(user::setParameter);

        try {
            return Optional.ofNullable(user.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Usuario> findUserByEmail(String email) {
        Map<String, Object> params = new HashMap<>();
        
        String query = "select u from Usuario u ";
        String where = "where 1=1 ";

        if(!AuthUtils.isEmptyString(email)) {
            params.put("email", email);
            where += "and u.email = :email ";
        }

        TypedQuery<Usuario> user = em.createQuery(query + where, Usuario.class);
        
        params.forEach(user::setParameter);

        try {
            return Optional.ofNullable(user.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
