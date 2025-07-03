package br.rafael.codes.ecommerce.location.entity;

import java.time.LocalDate;
import java.util.UUID;

import br.rafael.codes.ecommerce.usuario.entity.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe de Entidade de Localização.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 03.07.2025
 */
@Data
@Entity
@Table(name = "LOCALIZACOES")
@EqualsAndHashCode(of = "id")
public class Location {
    
    @Id
    @GeneratedValue(
        strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(name = "RUA")
    private String street;

    @Column(name = "NUMERO")
    private String number;

    @Column(name = "BAIRRO")
    private String neighborhood;

    @Column(name = "CIDADE")
    private String city;

    @Column(name = "ESTADO")
    private String state;

    @Column(name = "CEP")
    private String cep;

    @Column(name = "PAIS")
    private String country;

    @Column(name = "COMPLEMENTO")
    private String complement;

    @Column(name = "DT_CADASTRO")
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private Usuario user;

}
