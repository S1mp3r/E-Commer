package br.rafael.codes.auth.authorization.service.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenService;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenStorageService;
import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.authorization.service.AuthenticationService;
import br.rafael.codes.auth.exceptions.DataNotFoundException;
import br.rafael.codes.auth.usuario.entity.Usuario;
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

    @Override
    public void signUp(AuthenticationDTO auth) throws Exception {
        usuarioService.signUp(auth);
    }

    @Override
    public String authenticate(UsernamePasswordAuthenticationToken userToSignUp) throws DataNotFoundException {
        var authorized = authenticationManager.authenticate(userToSignUp);

        Usuario user = (Usuario) authorized.getPrincipal();
        TokenJwt token = tokenStorageService.findById(user.getId()).orElse(null);
    
        if(token == null || token.getExpiraEm().isBefore(Instant.now())) {
            tokenService.deleteToken(user.getId());
            return tokenService.generateKey((Usuario) authorized.getPrincipal());
        }
        return token.getToken();
    }
}
