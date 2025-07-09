package br.rafael.codes.ecommerce.usuario.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.rafael.codes.ecommerce.location.entity.Location;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Classe de Entidade de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 02.07.2025
 */
@Entity
@Table(name = "USUARIOS")
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "locations")
public class Usuario implements UserDetails{
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SENHA")
    private String password;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "NOME")
    private String firstName;

    @Column(name = "SOBRENOME")
    private String lastName;

    @Column(name = "CELULAR")
    private String cellPhoneNumber;

    @Column(name = "DATA_NASCIMENTO")
    private LocalDate birthDate;

    @Column(name = "CPF")
    private String cpf;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)    
    private List<Location> locations;

    @Column(name = "ATIVO")
    private boolean active;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;

    @Column(name = "DELETED_AT")
    private LocalDate deletedAt;
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == UserRole.ADMIN.getRole()) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

}
