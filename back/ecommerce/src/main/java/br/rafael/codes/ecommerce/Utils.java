package br.rafael.codes.ecommerce;

/**
 * Classe de Utilitários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
public class Utils {
    
    private Utils() {}

    /**
     * Verifica se uma String está vazia.
     * 
     * @param str A String.
     * @return true se a String estiver vazia, false caso contrário.
     */
    public static Boolean isEmptyString(String str) { return str == null || str.isEmpty(); }
}
