package br.rafael.codes.ecommerce.usuario.model;

import java.time.LocalDate;

import br.rafael.codes.ecommerce.location.model.LocationDTO;
import lombok.Data;

/**
 * Classe de Model de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 03.07.2025
 */
@Data
public class UsuarioDTO {
    
    private String email;
    private String password;
    private String role;

    private String firstName;
    private String lastName;
    private String cellPhoneNumber;
    private LocalDate birthDate;
    private String cpf;
    
    private LocationDTO location;

    private LocalDate createdAt;

    private boolean active;

}
