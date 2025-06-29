package br.rafael.codes.auth.exceptions;

/**
 * Exceção de Dados Nao Encontrados.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 29.06.2025
 */
public class DataNotFoundException extends Exception {

    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}