package br.rafael.codes.ecommerce.usuario.service;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.rafael.codes.ecommerce.ServiceTestConfigurations;
import br.rafael.codes.ecommerce.exceptions.DataNotFoundException;
import br.rafael.codes.ecommerce.location.entity.Location;
import br.rafael.codes.ecommerce.location.service.LocationService;
import br.rafael.codes.ecommerce.usuario.entity.UserRole;
import br.rafael.codes.ecommerce.usuario.entity.Usuario;
import br.rafael.codes.ecommerce.usuario.model.UsuarioDTO;
import br.rafael.codes.ecommerce.usuario.repository.UsuarioRepository;
import br.rafael.codes.ecommerce.usuario.service.impl.UsuarioServiceImpl;

/**
 * Classe responsável pelos Testes da Classe Serviço de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 01.07.2025
 */
@DisplayName("Testes unitários para UsuarioService")
public class UsuarioServiceTest extends ServiceTestConfigurations {
    
    private Usuario user;
    private Location location;
    private UsuarioDTO userDTO;

    @InjectMocks
    private UsuarioServiceImpl target;

    @Mock
    private LocationService locationServiceMock;

    @Mock
    private UsuarioRepository repositoryMock;

    @Mock
    private ModelMapper mapperMock;

    @BeforeEach
    void resetMocks() {
        reset(locationServiceMock, repositoryMock, mapperMock);
    }

    @BeforeEach
    void setUp() {
        user = mock(Usuario.class);
        userDTO = mock(UsuarioDTO.class);
        location = mock(Location.class);

        when(userDTO.getEmail()).thenReturn(email);
        when(userDTO.getPassword()).thenReturn(password);
        when(userDTO.getRole()).thenReturn(UserRole.USER.getRole());
        
    }

    @Nested
    @DisplayName("Testes para o findAll")
    class FindAllTests {

        @Test
        @DisplayName("Teste para o findAll quando vem um usuario ou mais na lista")
        void sucess_findUsers() {
            List<Usuario> expected = List.of(user);
            when(repositoryMock.findAll()).thenReturn(expected);

            List<Usuario> actual = target.findAll();

            verify(repositoryMock).findAll();
            assertEquals(expected, actual, "Deve retornar a lista de Usuários.");
            assertEquals(1, actual.size(), "A lista deve conter um ou mais Usuários.");
        }

        @Test
        @DisplayName("Teste para o findAll quando nao vem nada na lista")
        void sucess_empty_list() {
            when(repositoryMock.findAll()).thenReturn(List.of());

            List<Usuario> actual = target.findAll();

            verify(repositoryMock).findAll();
            assertEquals(List.of(), actual, "Deve retornar uma lista vazia.");
            assertEquals(0, actual.size(), "A lista deve estar vazia.");
        }
    }

    @Nested
    @DisplayName("Testes para o findUserByEmail")
    class FindUserByEmailTests {

        @Test
        @DisplayName("Teste para o findUserByEmail quando vem um usuario na lista")
        void sucess_findUser() throws DataNotFoundException {
            when(repositoryMock.findUserByEmail(anyString())).thenReturn(Optional.of(user));

            Usuario actual = target.findUserByEmail(anyString());

            verify(repositoryMock).findUserByEmail(anyString());
            assertEquals(user, actual, "Deve retornar o usuário.");
        }

        @Test
        @DisplayName("Teste para o findUserByEmail quando nao vem nada na lista")
        void fail_findUser() throws DataNotFoundException {
            when(repositoryMock.findUserByEmail(anyString())).thenReturn(Optional.empty());

            assertThrows(DataNotFoundException.class, () -> target.findUserByEmail(anyString()));
        }
    }
    
    @Nested
    @DisplayName("Testes para o loadUserByUsername")
    class LoadUserByUsernameTests {

        @Test
        @DisplayName("Teste para o loadUserByUsername quando vem um usuario na lista")
        void sucess_loadUser() throws Exception {
            when(repositoryMock.findByEmail(anyString())).thenReturn(user);

            UserDetails actual = target.loadUserByUsername(email);

            verify(repositoryMock).findByEmail(anyString());
            assertEquals(user, actual, "Deve retornar o usuário.");
        }

        @Test
        @DisplayName("Teste para o loadUserByUsername quando nao vem nada na lista")
        void fail_loadUser() throws Exception {
            when(repositoryMock.findByEmail(anyString())).thenReturn(null);

            assertThrows(UsernameNotFoundException.class, () -> target.loadUserByUsername(email));
        }
    }

    @Test
    @DisplayName("Teste para o createUser quando vem um usuario na lista")
    void sucess_createUser() throws Exception {
        when(mapperMock.map(any(), any())).thenReturn(user);
        when(locationServiceMock.create()).thenReturn(location);
        when(repositoryMock.save(any(Usuario.class))).thenReturn(user);

        Usuario actual = target.createUser(userDTO);

        verify(mapperMock).map(any(), any());
        assertEquals(user, actual, "Deve retornar o usuário.");
    }
}
