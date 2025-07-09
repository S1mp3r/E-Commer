package br.rafael.codes.ecommerce.user_preferences.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.rafael.codes.ecommerce.user_preferences.entity.UserPreferences;

/**
 * Interface de Repositório de Preferências de Estilo de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 09.07.2025
 */
public interface UserPreferencesRepository extends MongoRepository<UserPreferences, String> {
    
}
