package com.example.proyectoapilibreria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProyectoApiLibreriaApplication {

    public static void main (String[] args) {
        SpringApplication.run(ProyectoApiLibreriaApplication.class, args);
    }

}
