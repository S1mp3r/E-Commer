package br.rafael.codes.auth.authorization.config.infra;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Classe responsável pela configuração do CORS e Resources.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 24.06.2025
 */
@Configuration
public class AuthConfig implements WebMvcConfigurer {
    
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
