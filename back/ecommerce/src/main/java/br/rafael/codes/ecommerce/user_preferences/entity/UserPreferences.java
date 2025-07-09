package br.rafael.codes.ecommerce.user_preferences.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Classe de Entidade de Preferências de Estilo de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 09.07.2025 
 */
@Data
@Document(collection = "user_preferences")
public class UserPreferences {

    @Id
    private String userId;

    private String bgColor;
    private String btnColor;

    // getters e setters
}