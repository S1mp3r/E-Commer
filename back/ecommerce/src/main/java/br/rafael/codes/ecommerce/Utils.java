package br.rafael.codes.ecommerce;

import jakarta.servlet.http.HttpServletRequest;

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

    /**
     * Recupera o token do cabeçalho de autorização da requisição.
     * 
     * @param request A requisição.
     * @return Token da requisição, se houver, null caso contrário.
     */
    public static String recoverToken(HttpServletRequest request) {
        if(request.getHeader("Authorization") == null) {
            return null;
        }
        return request.getHeader("Authorization").replace("Bearer ", "");
    }

    /**
     * Recupera o token sem o prefixo.
     * 
     * @param token O token.
     * @return Token sem o prefixo.
     */
    public static String recoverToke(String token) {
        if(token == null) {
            return null;
        }
        return token.replace("Bearer ", "");
    }
}
