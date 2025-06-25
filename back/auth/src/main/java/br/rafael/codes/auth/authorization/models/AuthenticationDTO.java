package br.rafael.codes.auth.authorization.models;

import lombok.Data;

/**
 * Classe para recebimento das requisições de login de Autenticação.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
@Data
public class AuthenticationDTO {
    
    private String email;
    private String password;
    private String role;
}
