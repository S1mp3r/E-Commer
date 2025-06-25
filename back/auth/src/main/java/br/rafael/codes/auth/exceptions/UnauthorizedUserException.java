package br.rafael.codes.auth.exceptions;

/**
 * Exceção de Usuário Não Autorizado.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
public class UnauthorizedUserException extends Exception {

    public UnauthorizedUserException() {}

    public UnauthorizedUserException(String message) {
        super(message);
    }

    public UnauthorizedUserException(String message, Throwable cause) {
        super(message, cause);
    }
}