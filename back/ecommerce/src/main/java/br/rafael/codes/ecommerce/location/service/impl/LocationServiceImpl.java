package br.rafael.codes.ecommerce.location.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import br.rafael.codes.ecommerce.location.entity.Location;
import br.rafael.codes.ecommerce.location.service.LocationService;

/**
 * Classe de Implementação da Classe Serviço de Localização.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 03.07.2025
 */
@Service
public class LocationServiceImpl implements LocationService {

    @Override
    public Location create() {
        final Location location = new Location();
        location.setCreatedAt(LocalDate.now());
        
        return location;
    }
    
}
