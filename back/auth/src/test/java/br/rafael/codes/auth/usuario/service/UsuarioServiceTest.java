package br.rafael.codes.auth.usuario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;

import br.rafael.codes.auth.ServiceTestConfigurations;
import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.exceptions.DataNotFoundException;
import br.rafael.codes.auth.exceptions.DuplicatedDataException;
import br.rafael.codes.auth.usuario.entity.Usuario;
import br.rafael.codes.auth.usuario.repository.UsuarioRepository;
import br.rafael.codes.auth.usuario.service.impl.UsuarioServiceImpl;

/**
 * Classe responsável pelos Testes da Classe Serviço de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 01.07.2025
 */
@DisplayName("Testes Unitario para UsuarioService")
public class UsuarioServiceTest extends ServiceTestConfigurations {

    private AuthenticationDTO auth;

    @InjectMocks
    private UsuarioServiceImpl target;

    @Mock
    private UsuarioRepository repositoryMock;

    @BeforeEach
    void resetMocks() {
        reset(repositoryMock);
    }

    @BeforeEach
    void setUp() {
        auth = mock(AuthenticationDTO.class);
        when(auth.getEmail()).thenReturn(email);
        when(auth.getPassword()).thenReturn(senha);
    }

    @Nested
    @DisplayName("Testes para o findAll")
    class FindAll {

        @Test
        @DisplayName("Teste para o findAll quando vem algo na lista")
        void sucess_findUsers() {
            when(repositoryMock.findAll()).thenReturn(List.of(mock(Usuario.class)));

            List<?> actual = target.findAll();

            assertNotNull(actual, "Lista de Usuários nao pode ser nula.");
            assertNotEquals(0, actual.size(), "Lista de Usuários nao pode ser vazia.");
        }

        @Test
        @DisplayName("Teste para o findAll quando nao vem nada na lista")
        void sucess_empty_list() {
            when(repositoryMock.findAll()).thenReturn(List.of());

            List<Usuario> actual = target.findAll();

            assertNotNull(actual, "Lista de Usuários tem que estar nula.");
            assertEquals(0, actual.size(), "Lista de Usuários deve ter o seu tamanho zerado.");
        }
    }

    @Nested
    @DisplayName("Testes para o findUserByEmail")
    class FindUserByEmail {

        @Test
        @DisplayName("Teste para o findUserByEmail quando vem algo na lista")
        void sucess_findUser() throws DataNotFoundException {
            when(repositoryMock.findUserByEmail(anyString())).thenReturn(Optional.of(mock(Usuario.class)));

            Usuario actual = target.findUserByEmail(email);

            assertNotNull(actual, "Usuário nao pode ser nulo.");
        }

        @Test
        @DisplayName("Teste para o findUserByEmail quando nao vem nada na lista")
        void fail_findUser() {
            when(repositoryMock.findUserByEmail(anyString())).thenReturn(Optional.empty());

            assertThrows(DataNotFoundException.class, () -> target.findUserByEmail(email));
        }
    }

    @Nested
    @DisplayName("Testes para o signUp")
    class SignUp {

        @Test
        @DisplayName("Teste para o signUp caso o usuário seja cadastrado com sucesso")
        void sucess_signUp() throws Exception {
            when(repositoryMock.findDuplicatedData(anyString(), anyString())).thenReturn(Optional.empty());
            when(repositoryMock.save(any(Usuario.class))).thenReturn(mock(Usuario.class));

            target.signUp(auth);

            assertEquals(0, repositoryMock.findAll().size(), "Usuário nao existe em banco, logo pode ser cadastrado.");
        }

        @Test
        @DisplayName("Teste para o signUp caso o usuário nao seja cadastrado por conta de dados duplicados")
        void fail_signUp() throws Exception {
            when(repositoryMock.findDuplicatedData(anyString(), anyString())).thenReturn(Optional.of(mock(Usuario.class)));

            assertThrows(DuplicatedDataException.class, () -> target.signUp(auth), "Usuário nao pode ser cadastrado pois ja existe em banco.");
        }
    }

    @Test
    @DisplayName("Teste para o loadUserByUsername quando vem algo na lista")
    void sucess_loadUser() throws Exception {
        when(repositoryMock.findByEmail(anyString())).thenReturn(mock(UserDetails.class));

        UserDetails actual = target.loadUserByUsername(email);

        assertNotNull(actual, "Usuário nao pode ser nulo.");
    }
}
