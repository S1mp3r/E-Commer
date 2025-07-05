package br.rafael.codes.auth;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.rafael.codes.auth.authorization.models.AuthenticationDTO;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public abstract class ServiceTestConfigurations {

    final public String email = "teste@exemplo.com";
    final public String senha = "123456";
    final public UUID id = UUID.randomUUID();
    final public String tokenText = "token";
    public AuthenticationDTO auth;
    
    @BeforeEach
    private void resetMocks() {
    }

    public UsernamePasswordAuthenticationToken createUsernamePassword() throws Exception {
        createValidUser();
        return new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword());
    }

    public void createValidUser() {
        auth = new AuthenticationDTO();
        auth.setEmail("teste@gmail.com");
        auth.setPassword("123456");
    }

}
