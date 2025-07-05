package br.rafael.codes.auth.usuario.model;

import java.time.LocalDate;

import br.rafael.codes.auth.models.LocationDTO;
import lombok.Data;

@Data
public class UsuarioResumeDTO {
    
    private String email;

    private String firstName;
    private String lastName;
    private String cellPhoneNumber;
    private LocalDate birthDate;
    private String cpf;
    
    private LocationDTO location;

    private LocalDate createdAt;
    
}
