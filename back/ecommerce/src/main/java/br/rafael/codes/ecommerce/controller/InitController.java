package br.rafael.codes.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/init")
public class InitController {
    
    @GetMapping
    public String init() {
        return "OK";
    }
}
