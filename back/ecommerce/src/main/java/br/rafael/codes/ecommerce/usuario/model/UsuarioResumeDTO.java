package br.rafael.codes.ecommerce.usuario.model;

import java.time.LocalDate;
import java.util.List;

import br.rafael.codes.ecommerce.location.model.LocationDTO;
import lombok.Data;

@Data
public class UsuarioResumeDTO {
    
    private String email;

    private String firstName;
    private String lastName;
    private String cellPhoneNumber;
    private LocalDate birthDate;
    private String cpf;
    
    private List<LocationDTO> locations;

}
