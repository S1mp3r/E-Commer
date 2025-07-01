package br.rafael.codes.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("Test")
public abstract class BaseServiceTest {
    
    @BeforeEach
    private void resetMocks() {
    }

}
