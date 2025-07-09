package br.rafael.codes.ecommerce.usuario.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.rafael.codes.ecommerce.exceptions.DataNotFoundException;
import br.rafael.codes.ecommerce.location.entity.Location;
import br.rafael.codes.ecommerce.location.service.LocationService;
import br.rafael.codes.ecommerce.usuario.entity.Usuario;
import br.rafael.codes.ecommerce.usuario.model.UsuarioDTO;
import br.rafael.codes.ecommerce.usuario.model.UsuarioResumeDTO;
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
    private LocationService locationService;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private ModelMapper mapper;

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
        final UserDetails user = repository.findByEmail(username);
        if(user == null) {
            throw new UsernameNotFoundException("Usuário nao encontrado.");
        }
        return user;
    }

    @Override
    public Usuario createUser(UsuarioDTO entity) {
        final Usuario user = mapper.map(entity, Usuario.class);
        final Location location = locationService.create();

        location.setUser(user);
        user.setLocations(List.of(location));
        user.setCreatedAt(LocalDate.now());

        return repository.save(user);
    }

    @Override
    public Usuario updateUser(UsuarioResumeDTO entity) throws DataNotFoundException {
        final Usuario user = findUserByEmail(entity.getEmail());
        
        final Usuario updatedUser = mapper.map(entity, Usuario.class);
        updatedUser.setId(user.getId());
        updatedUser.setLocations(user.getLocations());
        updatedUser.setCreatedAt(user.getCreatedAt());

        return repository.save(updatedUser);
    }
}