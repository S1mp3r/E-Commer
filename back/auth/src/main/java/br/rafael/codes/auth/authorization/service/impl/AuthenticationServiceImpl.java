package br.rafael.codes.auth.authorization.service.impl;

import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenService;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenStorageService;
import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.authorization.service.AuthenticationService;
import br.rafael.codes.auth.exceptions.DataNotFoundException;
import br.rafael.codes.auth.usuario.entity.Usuario;
import br.rafael.codes.auth.usuario.model.UsuarioDTO;
import br.rafael.codes.auth.usuario.service.UsuarioService;

/**
 * Classe de Implementação da Classe Serviço de Autenticação.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenStorageService tokenStorageService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper mapper;

    @Value("${api.url}")
    private String url;

    @Override
    @Transactional
    public void signUp(AuthenticationDTO auth) throws Exception {
        final Usuario registredUser = usuarioService.signUp(auth);

        sentUser(registredUser);
    }

    @Override
    @Transactional
    public String authenticate(UsernamePasswordAuthenticationToken userToSignUp) throws DataNotFoundException, AuthenticationException {
        var authorized = authenticationManager.authenticate(userToSignUp);

        Usuario user = (Usuario) authorized.getPrincipal();
        TokenJwt token = tokenStorageService.findById(user.getId()).orElse(null);
    
        if(token == null || token.getExpiraEm().isBefore(Instant.now())) {
            tokenService.deleteToken(user.getId());
            return tokenService.createToken((Usuario) authorized.getPrincipal());
        }
        return token.getToken();
    }

    @Override
    @Transactional
    public void logout(String token) throws Exception {
        DecodedJWT tokenValidated = tokenService.validateToken(token);

        Usuario sessionUser = usuarioService.findUserByEmail(tokenValidated.getSubject());
        tokenService.deleteToken(sessionUser.getId());
    }

    @Async
    private void sentUser(Usuario user) {
        UsuarioDTO userDTO = mapper.map(user, UsuarioDTO.class);
        restTemplate.postForObject(url + "/api/v1/usuario", userDTO, UsuarioDTO.class);
    }
}
