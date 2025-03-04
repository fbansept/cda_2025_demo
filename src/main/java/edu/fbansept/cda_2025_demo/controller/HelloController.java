package edu.fbansept.cda_2025_demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping({"/", "/hello"})
    public String sayHello() {
        return "<h1>Le serveur marche mais il n'y a rien a voir ici</h1>";
    }

}
