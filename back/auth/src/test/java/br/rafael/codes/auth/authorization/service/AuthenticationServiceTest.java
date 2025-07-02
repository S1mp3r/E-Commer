package br.rafael.codes.auth.authorization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;


import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.auth.BaseServiceTest;
import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenService;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenStorageService;
import br.rafael.codes.auth.authorization.service.impl.AuthenticationServiceImpl;
import br.rafael.codes.auth.usuario.entity.Usuario;
import br.rafael.codes.auth.usuario.service.UsuarioService;

/**
 * Classe responsável pelos Testes da Classe Serviço de Autenticação.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 01.07.2025
 */
@DisplayName("Testes Unitários para o AuthenticationService")
public class AuthenticationServiceTest extends BaseServiceTest {

    private Authentication auth;
    private Usuario usuario;
    private TokenJwt token;
    private DecodedJWT decoded;

    @InjectMocks
    private AuthenticationServiceImpl target;

    @Mock
    private AuthenticationManager authenticationManagerMock;

    @Mock
    private UsuarioService usuarioServiceMock;

    @Mock
    private TokenService tokenServiceMock;

    @Mock
    private TokenStorageService tokenStorageServiceMock;

    @BeforeEach
    void resetMocks() {
        reset(authenticationManagerMock, usuarioServiceMock, tokenServiceMock, tokenStorageServiceMock);
    }
    
    @BeforeEach
    void setUp() {
        auth = mock(Authentication.class);
        usuario = mock(Usuario.class);
        token = mock(TokenJwt.class);
        decoded = mock(DecodedJWT.class);
        
        when(auth.getPrincipal()).thenReturn(usuario);
        when(usuario.getId()).thenReturn(id);
        when(token.getExpiraEm()).thenReturn(Instant.now().plusSeconds(3600));
        when(token.getToken()).thenReturn(tokenText);
        when(decoded.getSubject()).thenReturn(email);
    }

    
    @Test
    @DisplayName("Teste para o signUp caso o usuário seja cadastrado com sucesso")
    void sucess_signUp() throws Exception {
        target.signUp(any());

        verify(usuarioServiceMock).signUp(any());
    }

    @Nested
    @DisplayName("Teste para o authenticate")
    class Authenticate {
        @Test
        @DisplayName("Teste para o authenticate caso o usuário seja autenticado com sucesso")
        void sucess_authenticate() throws Exception {
            when(authenticationManagerMock.authenticate(any())).thenReturn(auth);
            when(tokenStorageServiceMock.findById(any())).thenReturn(Optional.of(token));

            final String actual = target.authenticate(any());

            assertNotNull(actual, "Token nao pode ser nulo.");
            assertEquals(tokenText, actual, "O token retornado eh o salvo em banco.");
        }

        @Test
        @DisplayName("Teste para o authenticate caso o usuário nao seja autenticado")
        void sucess_authenticate_token_not_found() throws Exception {
            when(authenticationManagerMock.authenticate(any())).thenReturn(auth);
            when(tokenStorageServiceMock.findById(any())).thenReturn(Optional.empty());
            when(tokenServiceMock.createToken(any())).thenReturn(tokenText);
        
            final String actual = target.authenticate(any());

            assertNotNull(actual, "Token nao pode ser nulo.");
            assertEquals(tokenText, actual, "O token gerado eh o mesmo do token esperado.");
        }
    }

    @Nested
    @DisplayName("Teste para o logout")
    class Logout {

        @Test
        @DisplayName("Teste para o logout caso o usuário seja deslogado com sucesso")
        void sucess_logout() throws Exception {
            when(tokenServiceMock.validateToken(anyString())).thenReturn(decoded);
            when(usuarioServiceMock.findUserByEmail(anyString())).thenReturn(usuario);

            target.logout(tokenText);

            verify(tokenServiceMock).validateToken(anyString());
            verify(usuarioServiceMock).findUserByEmail(anyString());
            verify(tokenServiceMock).deleteToken(any());
        }
    }
}
