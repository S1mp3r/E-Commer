package br.rafael.codes.auth.authorization.controller;

import org.springframework.web.bind.annotation.RestController;

import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.authorization.service.AuthenticationService;
import br.rafael.codes.auth.exceptions.DataNotFoundException;

import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * Classe de Controle de Autenticação.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationService service;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO auth) throws DataNotFoundException, AuthenticationException {
        logger.info("Login: {}", auth);
        try {
            var userNamePassword = new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword());
            String token = service.authenticate(userNamePassword);
            URI uri = URI.create("/auth/v1/login");
            logger.info("Login efetuado com sucesso.");

            return ResponseEntity.created(uri).body(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválido(a).");
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthenticationDTO auth) throws Exception {
        logger.info("Register: {}", auth);
        service.signUp(auth);
        URI uri = URI.create("/auth/v1/register");
        logger.info("Usuário cadastrado com sucesso.");
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader ("Authorization") String token) throws Exception {
        logger.info("Logout: {}", token);
        service.logout(token);
        logger.info("Logout efetuado com sucesso.");
        return ResponseEntity.noContent().build();
    }

}
