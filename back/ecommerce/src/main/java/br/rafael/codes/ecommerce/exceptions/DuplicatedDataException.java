package br.rafael.codes.ecommerce.exceptions;

/**
 * Exceção de Dados Duplicados.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 02.07.2025
 */
public class DuplicatedDataException extends Exception {

    public DuplicatedDataException() {}

    public DuplicatedDataException(String message) {
        super(message);
    }

    public DuplicatedDataException(String message, Throwable cause) {
        super(message, cause);
    }
}