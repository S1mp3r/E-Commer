package br.rafael.codes.ecommerce.usuario.controller;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import br.rafael.codes.ecommerce.ControllerTestConfigurations;
import br.rafael.codes.ecommerce.usuario.entity.UserRole;
import br.rafael.codes.ecommerce.usuario.model.UsuarioDTO;

/**
 * Teste Unitário para UsuarioController
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 03.07.2025
 */
@WebMvcTest(controllers = UsuarioController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@DisplayName("Teste Unitário para UsuarioController")
public class UsuarioControllerTest extends ControllerTestConfigurations {

    private UsuarioDTO user;

    @BeforeEach
    public void setUp() {
        user = new UsuarioDTO();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(UserRole.USER.getRole());
    }

    @Test
    @DisplayName("Teste para o register caso o usuário seja cadastrado com sucesso")
    void sucess_signUp() throws Exception {
        createValidRequest();

        runPost(url + "/usuario", user, status().isCreated());

        verify(usuarioServiceMock).createUser(user);
    }
}
