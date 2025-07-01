package br.rafael.codes.auth.usuario.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.exceptions.DataNotFoundException;
import br.rafael.codes.auth.exceptions.DuplicatedDataException;
import br.rafael.codes.auth.exceptions.UnauthorizedUserException;
import br.rafael.codes.auth.usuario.entity.UserRole;
import br.rafael.codes.auth.usuario.entity.Usuario;
import br.rafael.codes.auth.usuario.repository.UsuarioRepository;
import br.rafael.codes.auth.usuario.service.UsuarioService;

/**
 * Classe de Serviço de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
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
    @Transactional
    public void signUp(AuthenticationDTO auth) throws Exception {

        final Usuario usuario = new Usuario();
        usuario.setEmail(auth.getEmail());
        usuario.setPassword(
            new BCryptPasswordEncoder().encode(auth.getPassword())
            );
        usuario.setRole(UserRole.USER.getRole());

        final Optional<Usuario> usuarioRepetido = repository.findDuplicatedData(auth.getEmail(), auth.getPassword());

        if (usuarioRepetido.isPresent()) {
            throw new DuplicatedDataException("Usuário ja cadastrado.");
        }
        if(usuario.getRole().contains(UserRole.ADMIN.getRole())) {
            throw new UnauthorizedUserException("Usuário não autorizado.");
        }

        repository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }
}