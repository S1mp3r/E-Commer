package br.rafael.codes.ecommerce.usuario.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.rafael.codes.ecommerce.exceptions.DataNotFoundException;
import br.rafael.codes.ecommerce.usuario.entity.Usuario;
import br.rafael.codes.ecommerce.usuario.repository.UsuarioRepository;
import br.rafael.codes.ecommerce.usuario.service.UsuarioService;

/**
 * Classe de Serviço de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 02.07.2025
 */
@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Override
    @Cacheable(value = "usuarios", key = "#email", unless = "#result == null")
    public Usuario findUserByEmail(String email) throws DataNotFoundException {
        return repository.findUserByEmail(email).orElseThrow(() -> 
            new DataNotFoundException("Usuário nao encontrado."));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }
}