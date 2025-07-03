package br.rafael.codes.ecommerce.location.model;

import java.time.LocalDate;

import lombok.Data;

/**
 * Classe de Model de Localização.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 03.07.2025
 */
@Data
public class LocationDTO {
    
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String cep;
    private String country;
    private String complement;
    
    private LocalDate createdAt;

}
