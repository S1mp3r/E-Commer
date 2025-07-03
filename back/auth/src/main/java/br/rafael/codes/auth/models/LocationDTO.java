package br.rafael.codes.auth.models;

import java.time.LocalDate;

import lombok.Data;

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
