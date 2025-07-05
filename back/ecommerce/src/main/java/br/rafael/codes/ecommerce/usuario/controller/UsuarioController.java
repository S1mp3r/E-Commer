package br.rafael.codes.ecommerce.usuario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.rafael.codes.ecommerce.exceptions.DataNotFoundException;
import br.rafael.codes.ecommerce.usuario.model.UsuarioDTO;
import br.rafael.codes.ecommerce.usuario.model.UsuarioResumeDTO;
import br.rafael.codes.ecommerce.usuario.service.UsuarioService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Classe de Controle de Usuários.
 * 
 * @author Rafael Carneiro - <rafaelcarneiro1313@gmail.com>
 * @since 03.07.2025
 */
@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public UsuarioResumeDTO findUser(@RequestParam String email) throws DataNotFoundException {
        logger.info("Finding user: {}", email);
        return mapper.map(service.findUserByEmail(email), UsuarioResumeDTO.class);
    }

    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createUser(@RequestBody UsuarioDTO entity) {
        logger.info("Creating user: {}", entity);
        service.createUser(entity);
        logger.info("Creation completed.");
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateUser(@RequestBody UsuarioResumeDTO entity) throws DataNotFoundException {
        logger.info("Updating user: {}", entity);
        service.updateUser(entity);
        logger.info("Update completed.");
    }
    
}