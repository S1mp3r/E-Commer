package br.rafael.codes.ecommerce.config.service;


import java.io.IOException;
import java.sql.Date;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.ecommerce.Utils;
import br.rafael.codes.ecommerce.config.service.jwt.TokenService;
import br.rafael.codes.ecommerce.usuario.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Classe responsável pela configuração de segurança da API.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 02.07.2025
 */
@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    private TokenService tokenService;

    @Autowired
    @Lazy
    private UsuarioService usuarioService;

    private static final String PUBLIC_PATH = "/api/v1/usuario/create";

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (PUBLIC_PATH.equals(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        var sentToken = Utils.recoverToken(request);
        if (sentToken == null) {
            sendUnauthorized(response, HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
            return;
        }

        DecodedJWT tokenValidated;
        try {
            tokenValidated = tokenService.validateToken(sentToken);
        } catch (Exception e){
            sendUnauthorized(response ,HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return;
        }

        if(tokenValidated == null || tokenValidated.getExpiresAt().before(Date.from(Instant.now()))){
            sendUnauthorized(response ,HttpServletResponse.SC_UNAUTHORIZED, "Token expirado, deslogado ou inválido");
            return;
        }

        UserDetails user = usuarioService.loadUserByUsername(tokenValidated.getSubject());
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.setAttribute("authentication", authentication);
        filterChain.doFilter(request, response);
    }

    private void sendUnauthorized(HttpServletResponse response, int HttpCode, String message) throws IOException {
        response.setStatus(HttpCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

}
