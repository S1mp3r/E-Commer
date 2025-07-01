package br.rafael.codes.auth;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public abstract class BaseServiceTest {

    final public String email = "teste@exemplo.com";
    final public String senha = "123456";
    final public UUID id = UUID.randomUUID();
    final public String tokenText = "token";
    
    @BeforeEach
    private void resetMocks() {
    }

}
