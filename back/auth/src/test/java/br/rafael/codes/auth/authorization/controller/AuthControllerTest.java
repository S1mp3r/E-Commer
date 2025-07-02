package br.rafael.codes.auth.authorization.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;

import br.rafael.codes.auth.ControllerTestConfigurations;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@DisplayName("Teste Unitário para AuthController")
public class AuthControllerTest extends ControllerTestConfigurations {

    private static final String url = "/auth/v1";

    @BeforeEach
    public void setUp() {
        createValidUser();
    }

    @Nested
    @DisplayName("Teste para o login")
    class Login {
        @Test
        @DisplayName("Teste para o login caso o usuário seja logado com sucesso")
        void sucess_login() throws Exception {
            when(authenticationServiceMock.authenticate(any())).thenReturn(tokenText);

            ResponseEntity<?> actual = asResponseEntity(runPost(url + "/login", auth));

            assertEquals(HttpStatus.CREATED.value(), actual.getStatusCode().value(), 
                "Deve retornar o status 201, usuario logado com sucesso.");
            assertEquals(tokenText, actual.getBody(), "Deve retornar o token gerado e salvo em banco do Usuario.");

        }

        @Test
        @DisplayName("Teste para o login caso o corpo da requisicao esteja vazio")
        void fail_login() throws Exception {
            when(authenticationServiceMock.authenticate(any())).thenReturn(tokenText);

            ResponseEntity<?> actual = asResponseEntity(runPost(url + "/login", null, status().isBadRequest()));

            assertEquals(HttpStatus.BAD_REQUEST.value(), actual.getStatusCode().value(), 
                "Deve retornar o status 400, corpo da requisicao nao pode ser nulo.");

        }
        
        @Test
        @DisplayName("Teste para o login caso o email ou senha esteja invalido")
        void fail_login_email_or_password() throws Exception {
            when(authenticationServiceMock.authenticate(any())).thenThrow(new BadCredentialsException("Credenciais inválidas"));

            ResponseEntity<?> actual = asResponseEntity(runPost(url + "/login", auth, status().isUnauthorized()));

            assertEquals(HttpStatus.UNAUTHORIZED.value(), actual.getStatusCode().value(), 
                "Deve retornar o status 401, email ou senha invalido.");
        }
    }

    @Test
    @DisplayName("Teste para o register caso o usuário seja cadastrado com sucesso")
    void sucess_signUp() throws Exception {
        ResponseEntity<?> actual = asResponseEntity(runPost(url + "/register", auth));

        verify(authenticationServiceMock).signUp(auth);
        assertEquals(HttpStatus.CREATED.value(), actual.getStatusCode().value(), 
            "Deve retornar o status 201, usuario cadastrado com sucesso.");
    }

    @Test
    @DisplayName
    ("Teste para o logout caso o usuário seja deslogado com sucesso")
    void sucess_logout() throws Exception {
        createValidRequest();

        ResponseEntity<?> actual = asResponseEntity(runDelete(url + "/logout"));

        verify(authenticationServiceMock).logout(createToken());
        assertEquals(HttpStatus.NO_CONTENT.value(), actual.getStatusCode().value(), 
            "Deve retornar o status 204, usuario deslogado com sucesso.");
    }
}
