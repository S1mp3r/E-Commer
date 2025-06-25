package br.rafael.codes.auth.authorization.config.infra;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.rafael.codes.auth.authorization.config.infra.jwt.TokenService;
import br.rafael.codes.auth.usuario.service.impl.UsuarioServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Classe responsável pela configuração de segurança da API.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
@Component
public class SecurityFilter extends OncePerRequestFilter{

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    private static final List<String> PUBLIC_PATHS = List.of(
        "/v1/auth/login",
        "/v1/auth/register"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (PUBLIC_PATHS.contains(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        var token = recoverToken(request);

        var subject = tokenService.validateToken(token);
        UserDetails user = usuarioService.loadUserByUsername(subject);

        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.setAttribute("authentication", authentication);
    }
    
    private String recoverToken(HttpServletRequest request) {
        if(request.getHeader("Authorization") == null) {
            return null;
        }
        return request.getHeader("Authorization").replace("Bearer ", "");
    }
}
