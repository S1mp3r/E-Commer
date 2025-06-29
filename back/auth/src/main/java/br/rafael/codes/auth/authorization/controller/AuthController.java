package br.rafael.codes.auth.authorization.controller;

import org.springframework.web.bind.annotation.RestController;

import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.authorization.service.AuthenticationService;
import br.rafael.codes.auth.exceptions.DataNotFoundException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Classe de Controle de Autenticação.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationService service;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> login(@RequestBody AuthenticationDTO auth) throws DataNotFoundException {
        try {
            var userNamePassword = new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword());
            String token = service.authenticate(userNamePassword);
            URI uri = URI.create("/v1/auth/login");

            return ResponseEntity.created(uri).body(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-mail ou senha inválido(a).");
        }
    }
    
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> register(@RequestBody AuthenticationDTO auth) throws Exception {
        service.signUp(auth);

        URI uri = URI.create("/v1/auth/login");
        return ResponseEntity.created(uri).build();
    }

}
