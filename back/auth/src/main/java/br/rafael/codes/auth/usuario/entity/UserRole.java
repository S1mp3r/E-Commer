package br.rafael.codes.auth.usuario.entity;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Enum de Roles dos Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
public enum UserRole {

    ADMIN("ADMIN"),
    USER("USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static List<SimpleGrantedAuthority> getUnhatorizedRoles() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}