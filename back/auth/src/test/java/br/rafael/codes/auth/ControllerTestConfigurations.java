package br.rafael.codes.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.CsrfRequestPostProcessor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.rafael.codes.auth.authorization.config.infra.jwt.entity.TokenJwt;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenService;
import br.rafael.codes.auth.authorization.config.infra.jwt.service.TokenStorageService;
import br.rafael.codes.auth.authorization.models.AuthenticationDTO;
import br.rafael.codes.auth.authorization.service.AuthenticationService;
import br.rafael.codes.auth.usuario.entity.UserRole;
import br.rafael.codes.auth.usuario.entity.Usuario;
import br.rafael.codes.auth.usuario.repository.UsuarioRepository;
import br.rafael.codes.auth.usuario.service.UsuarioService;

/**
 * Classe de Configurações de Testes de Controller. 
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 02.07.2025
 */
@SuppressWarnings({"removal", "unused"})
@TestPropertySource(properties = {"EXPIRATION_TIME=999999", "JWT_SECRET_KEY=teste"})
public abstract class ControllerTestConfigurations {

    public AuthenticationDTO auth;
    public static final String tokenText = "token";
    
    private DecodedJWT decodedMock;
    private Usuario usuarioMock;
    private TokenJwt tokenMock;

    private static final List<String> PUBLIC_PATHS = List.of(
        "/auth/v1/login",
        "/auth/v1/register"
    );

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public AuthenticationService authenticationServiceMock;

    @MockBean
    public TokenService tokenServiceMock;
    
    @MockBean
    public TokenStorageService redisTkStorageServiceMock;

    @MockBean
    public UsuarioService usuarioServiceMock;

    @MockBean
    public UsuarioRepository usuarioRepositoryMock;

    @BeforeEach
    public void resetMocks() {
        reset(
            authenticationServiceMock, tokenServiceMock, 
            redisTkStorageServiceMock, usuarioServiceMock,
            usuarioRepositoryMock);
    }

    public void createValidRequest() throws Exception {
        createValidUser();
        decodedMock = mock(DecodedJWT.class);
        usuarioMock = mock(Usuario.class);
        tokenMock = mock(TokenJwt.class);

        usuarioMock.setRole(UserRole.USER.getRole());

        when(tokenServiceMock.validateToken(anyString())).thenReturn(decodedMock);
        when(decodedMock.getSubject()).thenReturn(auth.getEmail());
        when(usuarioServiceMock.findUserByEmail(anyString())).thenReturn(usuarioMock);
        when(usuarioMock.getId()).thenReturn(UUID.randomUUID());
        when(tokenServiceMock.findById(any())).thenReturn(Optional.of(tokenMock));
        when(decodedMock.getExpiresAt()).thenReturn(Date.from(Instant.now().plusSeconds(3600)));
        when(tokenMock.getToken()).thenReturn(tokenText);
        when(decodedMock.getToken()).thenReturn(tokenText);
        when(usuarioServiceMock.loadUserByUsername(anyString())).thenReturn(usuarioMock);
    }

    public UsernamePasswordAuthenticationToken createUsernamePassword() throws Exception {
        createValidUser();
        return new UsernamePasswordAuthenticationToken(auth.getEmail(), auth.getPassword());
    }

    private List<GrantedAuthority> createAuthorities() {
        List<String> permissionList = List.of("ROLE_USER", "ROLE_ADMIN");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities = AuthorityUtils.createAuthorityList(permissionList.toArray(new String[permissionList.size()]));
        return grantedAuthorities;
    }

    public void createValidUser() {
        auth = new AuthenticationDTO();
        auth.setEmail("teste@gmail.com");
        auth.setPassword("123456");
    }

    public String createToken() {
        return "Bearer " + tokenText;
    }

    public Object asObject(MvcResult result, Class<?> clazz) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(result.getResponse().getContentAsString(), mapper.getTypeFactory().constructSimpleType(clazz, null));
    }

    public static List<?> asListObject(final MvcResult result, Class<?> clazz)
        throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.readValue(result.getResponse().getContentAsString(),
				mapper.getTypeFactory().constructParametricType(List.class, clazz));
	}

    public ResponseEntity<?> asResponseEntity(MvcResult result) throws Exception {
        MockHttpServletResponse response = result.getResponse();
        HttpHeaders headers = new HttpHeaders();
        
        if (response.containsHeader("Authentication")) {
            headers.add("Authentication", response.getHeader("Authentication"));
        }
        if (response.containsHeader("SESSION")) {
            headers.add("SESSION", response.getHeader("SESSION"));
        }
        if (response.containsHeader("Location")) {
            headers.setLocation(new URI(response.getHeader("Location")));
        }
        
        return new ResponseEntity<>(response.getContentAsString(), headers, HttpStatus.valueOf(response.getStatus()));
    }

    public MvcResult runGet(String url, ResultMatcher httpStatus) throws Exception {
        return run(get(url), null, httpStatus, url);
    }

    public MvcResult runPost(String url, Object content, ResultMatcher httpStatus) throws Exception {
        return run(post(url), content, httpStatus, url);
    }

    public MvcResult runPatch(String url, Object content, ResultMatcher httpStatus) throws Exception {
        return run(patch(url), content, httpStatus, url);
    }

    public MvcResult runPut(String url, Object content, ResultMatcher httpStatus) throws Exception {
        return run(put(url), content, httpStatus, url);
    }

    public MvcResult runDelete(String url, ResultMatcher httpStatus) throws Exception {
        return run(delete(url), null, httpStatus, url);
    }

    public MvcResult runGet(String url) throws Exception {
        return run(get(url), null, status().isOk(), url);
    }

    public MvcResult runPost(String url, Object content) throws Exception {
        return run(post(url), content, status().isCreated(), url);
    }

    public MvcResult runPatch(String url, Object content) throws Exception {
        return run(patch(url), content, status().isOk(), url);
    }

    public MvcResult runPut(String url, Object content) throws Exception {
        return run(put(url), content, status().isOk(), url);
    }

    public MvcResult runDelete(String url) throws Exception {
        return run(delete(url), null, status().isNoContent(), url);
    }

    public MvcResult runGetBadRequest(String url) throws Exception {
        return run(get(url), null, status().isBadRequest(), url);
    }

    public MvcResult runPostBadRequest(String url, Object content) throws Exception {
        return run(post(url), content, status().isBadRequest(), url);
    }

    public MvcResult runPatchBadRequest(String url, Object content) throws Exception {
        return run(patch(url), content, status().isBadRequest(), url);
    }

    public MvcResult runPutBadRequest(String url, Object content) throws Exception {
        return run(put(url), content, status().isBadRequest(), url);
    }

    public MvcResult runDeleteBadRequest(String url) throws Exception {
        return run(delete(url), null, status().isBadRequest(), url);
    }
 
    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MockHttpServletRequestBuilder get(String url) {
        return MockMvcRequestBuilders.get(url);
    }

    private MockHttpServletRequestBuilder post(String url) {
        return MockMvcRequestBuilders.post(url);
    }

    private MockHttpServletRequestBuilder patch(String url) {
        return MockMvcRequestBuilders.patch(url);
    }

    private MockHttpServletRequestBuilder put(String url) {
        return MockMvcRequestBuilders.put(url);
    }

    private MockHttpServletRequestBuilder delete(String url) {
        return MockMvcRequestBuilders.delete(url);
    }

    public StatusResultMatchers status() {
        return MockMvcResultMatchers.status();
    }

    private CsrfRequestPostProcessor csrf() {
        return SecurityMockMvcRequestPostProcessors.csrf();
    }
    
    private MvcResult run(final MockHttpServletRequestBuilder builder, Object content, ResultMatcher resultMatcher, String url) throws Exception {
        MockHttpServletRequestBuilder builderToExec = builder;
		if (content != null) {
            builderToExec = builder
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(content))
                .with(csrf());
		}

        if(!PUBLIC_PATHS.contains(url))  {
            builderToExec = builderToExec.header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenText);
        }

        return mockMvc.perform(builderToExec)
            .andExpect(resultMatcher).andReturn();
	}
}