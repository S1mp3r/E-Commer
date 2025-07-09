package br.rafael.codes.ecommerce.user_preferences.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rafael.codes.ecommerce.user_preferences.entity.UserPreferences;
import br.rafael.codes.ecommerce.user_preferences.repository.UserPreferencesRepository;
import br.rafael.codes.ecommerce.user_preferences.service.UserPreferencesService;
import br.rafael.codes.ecommerce.usuario.controller.UsuarioController;

/**
 * Classe de Implementação da Classe Serviço de Preferências de Estilo de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 09.07.2025
 */
@Service
public class UserPreferencesServiceImpl implements UserPreferencesService {
    
    Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UserPreferencesRepository repository;

    @Override
    public Optional<UserPreferences> getByUserId(String userId) {
        logger.info("Getting preferences for user: {}", userId);
        return repository.findById(userId);
    }

    @Override
    public UserPreferences saveOrUpdate(String userId, UserPreferences prefs) {
        logger.info("Saving preferences for user: {}", userId);
        prefs.setUserId(userId);
        return repository.save(prefs);
    }
}
