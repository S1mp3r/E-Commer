package br.rafael.codes.ecommerce.user_preferences.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.ecommerce.Utils;
import br.rafael.codes.ecommerce.config.service.jwt.TokenService;
import br.rafael.codes.ecommerce.exceptions.DataNotFoundException;
import br.rafael.codes.ecommerce.user_preferences.entity.UserPreferences;
import br.rafael.codes.ecommerce.user_preferences.service.UserPreferencesService;
import br.rafael.codes.ecommerce.usuario.controller.UsuarioController;
import br.rafael.codes.ecommerce.usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/v1/preferences")
public class UserPreferencesController {

    Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UserPreferencesService service;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> getPreferences(@RequestHeader("Authorization") String token) throws DataNotFoundException {
        logger.info("Getting preferences for user: {}", token);
        String userId = extractUserIdFromToken(token);

        final UserPreferences prefs = service.getByUserId(userId).orElse(new UserPreferences());
        prefs.setUserId(null);
        logger.info("Preferences for user: {}", prefs);

        return ResponseEntity.ok().body(prefs);
    }

    @PostMapping
    public ResponseEntity<?> updatePreferences(@RequestHeader("Authorization") String token, @RequestBody UserPreferences preferences) throws DataNotFoundException {
        logger.info("Updating preferences for user: {}", token);
        String userId = extractUserIdFromToken(token);

        final UserPreferences prefs = service.saveOrUpdate(userId, preferences);
        prefs.setUserId(null);
        logger.info("Preferences updated: {}", prefs);
        
        final URI uri = URI.create("/api/v1/preferences");

        return ResponseEntity.created(uri).body(prefs);
    }

    private String extractUserIdFromToken(String token) throws DataNotFoundException {
        final DecodedJWT tokenValidated = tokenService.validateToken(Utils.recoverToke(token));
        
        return usuarioService.findUserByEmail(tokenValidated.getSubject()).getId().toString();
    }
}