package br.rafael.codes.auth;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe de Utilitários de Autenticação.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
public class AuthUtils {
    
    private AuthUtils() {}

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
     * @param request Requisição.
     * @return Token da requisição, se houver, null caso contrário.
     */
    public static String recoverToken(HttpServletRequest request) {
        if(request.getHeader("Authorization") == null) {
            return null;
        }
        return request.getHeader("Authorization").replace("Bearer ", "");
    }

    /**
     * Recupera o token do cabeçalho de autorização da requisição.
     * 
     * @param token O Token com o prefixo.
     * @return Token da requisição, se houver, null caso contrário.
     */
    public static String recoverToken(String token) {
        return token.replace("Bearer ", "");
    }
}
