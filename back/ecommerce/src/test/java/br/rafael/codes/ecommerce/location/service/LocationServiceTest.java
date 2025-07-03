package br.rafael.codes.ecommerce.location.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import br.rafael.codes.ecommerce.ServiceTestConfigurations;
import br.rafael.codes.ecommerce.location.entity.Location;
import br.rafael.codes.ecommerce.location.service.impl.LocationServiceImpl;

/**
 * Classe responsável pelos Testes da Classe Serviço de Localização.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 03.07.2025
 */
@DisplayName("Testes unitários para LocationService")
public class LocationServiceTest extends ServiceTestConfigurations {
    
    @InjectMocks
    private LocationServiceImpl target;

    @Test
    @DisplayName("Teste para o create quando vem um localizacao na lista")
    void sucess_create() throws Exception {
        Location location = new Location();
        location.setCreatedAt(LocalDate.now());

        Location actual = target.create();

        assertEquals(location, actual, "Deve retornar a localização.");
        assertNotNull(actual, "Localização nao pode ser nula.");
    }
}
