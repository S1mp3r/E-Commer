package br.rafael.codes.auth.authorization.config.infra.jwt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.auth0.jwt.interfaces.DecodedJWT;

import br.rafael.codes.auth.BaseServiceTest;
import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.authorization.config.infra.jwt.repository.TokenJwtRepository;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.impl.TokenStorageServiceImpl;
import br.rafael.codes.auth.usuario.entity.Usuario;

/**
 * Classe responsável pelos Testes da configuração de segurança da API.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 01.07.2025
 */
@DisplayName("Testes unitários para TokenStorageService")
public class TokenStorageServiceTest extends BaseServiceTest {

    private DecodedJWT decoded;
    private TokenJwt token;

    @InjectMocks
    private TokenStorageServiceImpl target;

    @Mock
    private TokenJwtRepository repositoryMock;

    @BeforeEach
    void resetMocks() {
        reset(repositoryMock);
    }

    @BeforeEach
    void setup() {
        decoded = mock(DecodedJWT.class);
        token = mock(TokenJwt.class);

        when(decoded.getExpiresAt()).thenReturn(Date.from(Instant.now()));
    }

    @Nested
    @DisplayName("Testes para findAll")
    class FindAll {

        @Test
        @DisplayName("Testa para findAll, deve listar todos os tokens existentes no banco")
        void findAll() {
            List<TokenJwt> expected = List.of(mock(TokenJwt.class));

            when(repositoryMock.findAll()).thenReturn(expected);

            List<?> actual = target.findAll();

            assertEquals(expected, actual, "Deve retornar a lista de tokens");
            assertEquals(1, actual.size(), "A lista deve conter um ou mais tokens.");
            assertNotNull(actual, "A lista nao pode estar vazia.");
        }

        @Test
        @DisplayName("Testa para findAll, deve retornar uma lista vazia quando nenhuma token existir")
        void findAllEmpty() {
            when(repositoryMock.findAll()).thenReturn(List.of());

            List<?> actual = target.findAll();

            assertEquals(List.of(), actual, "Deve retornar uma lista vazia.");
            assertEquals(0, actual.size(), "A lista deve estar vazia.");
        }
    }

    @Nested
    @DisplayName("Testes para findById")
    class FindById {

        @Test
        @DisplayName("Testa para findById, deve retornar o token correspondente ao id informado")
        void findById() {
            when(repositoryMock.findById(any())).thenReturn(Optional.of(token));
            
            Optional<TokenJwt> actual = target.findById(id);

            assertEquals(Optional.of(token), actual, "Deve retornar o token correspondente ao id informado.");
        }

        @Test
        @DisplayName("Testa para findById, deve retornar uma Optional vazia quando o token nao existir")
        void findByIdEmpty() {
            when(repositoryMock.findById(any())).thenReturn(Optional.empty());

            Optional<TokenJwt> actual = target.findById(id);

            assertEquals(Optional.empty(), actual, "Deve retornar um Optional vazia.");
        }
    }

    @Nested
    @DisplayName("Testes para exists")
    class Exists {

        @Test
        @DisplayName("Testa para exists, deve retornar true quando o token existir")
        void exists() {
            when(repositoryMock.existsById(any())).thenReturn(true);

            boolean actual = target.exists(id);

            assertEquals(true, actual, "Deve retornar true quando o token existir.");
        }

        @Test
        @DisplayName("Testa para exists, deve retornar false quando o token nao existir")
        void notExists() {
            when(repositoryMock.existsById(any())).thenReturn(false);

            boolean actual = target.exists(id);

            assertEquals(false, actual, "Deve retornar false quando o token nao existir.");
        }
    }

    @Test
    @DisplayName("Deve salvar um token na base de dados")
    void sucess() {
        target.createToken(new Usuario(), decoded, tokenText);

        verify(repositoryMock).save(any(TokenJwt.class));
    }

    @Test
    @DisplayName("Deve deletar um token na base de dados")
    void sucess_deleteToken() {
        target.deleteToken(id);

        verify(repositoryMock).deleteById(id);
    }
}