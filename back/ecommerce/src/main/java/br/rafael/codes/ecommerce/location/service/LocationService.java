package br.rafael.codes.ecommerce.location.service;

import br.rafael.codes.ecommerce.location.entity.Location;

/**
 * Interface de Serviço de Endereço.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 03.07.2025
 */
public interface LocationService {
    
    /**
     * Cria um novo endereço para um usuário.
     * 
     * @return Endereço criado.
     */
    Location create();
}
