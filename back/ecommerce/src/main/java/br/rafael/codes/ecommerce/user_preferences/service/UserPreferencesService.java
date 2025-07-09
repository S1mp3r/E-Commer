package br.rafael.codes.ecommerce.user_preferences.service;

import java.util.Optional;

import br.rafael.codes.ecommerce.user_preferences.entity.UserPreferences;

/**
 * Interface de Serviço de Preferências de Estilo de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 09.07.2025
 */
public interface UserPreferencesService {

    /**
     * Retorna as preferências de estilo de um usuário.
     * 
     * @param userId Id do usuário.
     * @return Preferências de estilo.
     */
    Optional<UserPreferences> getByUserId(String userId);

    /**
     * Salva ou atualiza as preferências de estilo de um usuário.
     * 
     * @param userId Id do usuário.
     * @param prefs Preferências de estilo.
     * @return Preferências de estilo.
     */
    UserPreferences saveOrUpdate(String userId, UserPreferences prefs);
    
}
