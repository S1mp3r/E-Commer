package br.rafael.codes.ecommerce;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public abstract class ServiceTestConfigurations {

    final public static String email = "teste@exemplo.com";
    final public static String password = "123456";
    final public static UUID id = UUID.randomUUID();
    final public static String tokenText = "token";
    
    @BeforeEach
    private void resetMocks() {
    }

}
