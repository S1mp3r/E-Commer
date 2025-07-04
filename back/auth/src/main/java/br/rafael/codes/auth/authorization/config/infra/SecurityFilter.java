package br.rafael.codes.auth.authorization.config.infra;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.auth.AuthUtils;
import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenService;
import br.rafael.codes.auth.exceptions.DataNotFoundException;
import br.rafael.codes.auth.usuario.entity.Usuario;
import br.rafael.codes.auth.usuario.service.UsuarioService;
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
    private UsuarioService usuarioService;

    private static final List<String> PUBLIC_PATHS = List.of(
        "/auth/v1/login",
        "/auth/v1/register",
        "/auth/v1/logout"
    );

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if (PUBLIC_PATHS.contains(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        var sentToken = AuthUtils.recoverToken(request);
        if (sentToken == null) {
            sendUnauthorized(response ,HttpServletResponse.SC_UNAUTHORIZED, "Token ausente");
            return;
        }

        DecodedJWT tokenValidated;
        try {
            tokenValidated = tokenService.validateToken(sentToken);
        } catch (Exception e){
            sendUnauthorized(response ,HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
            return;
        }

        if(!checkForTrustedData(tokenValidated)) {
            sendUnauthorized(response ,HttpServletResponse.SC_UNAUTHORIZED, "Token expirado, deslogado ou inválido");
            return;
        }

        UserDetails user = usuarioService.loadUserByUsername(tokenValidated.getSubject());
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.setAttribute("authentication", authentication);
        filterChain.doFilter(request, response);
    }

    private boolean checkForTrustedData(DecodedJWT tokenValidated) {
        try {
            Usuario sessionUser = usuarioService.findUserByEmail(tokenValidated.getSubject());
            TokenJwt token = tokenService.findById(sessionUser.getId()).orElseThrow(() -> new DataNotFoundException("Token nao encontrado."));
            boolean expired = tokenValidated.getExpiresAt().before(Date.from(Instant.now()));

            if(token == null || expired || !token.getToken().equals(tokenValidated.getToken())) {
                tokenService.deleteToken(sessionUser.getId());
                return false;
            };
            return true;
        } catch (DataNotFoundException e) {
            return false;
        }
    }

    private void sendUnauthorized(HttpServletResponse response, int HttpCode, String message) throws IOException {
        response.setStatus(HttpCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

}
