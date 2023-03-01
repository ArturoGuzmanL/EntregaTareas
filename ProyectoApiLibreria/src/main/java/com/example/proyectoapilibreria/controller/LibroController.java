package com.example.proyectoapilibreria.controller;

import com.example.proyectoapilibreria.model.Libro;
import com.example.proyectoapilibreria.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/libro")
public class LibroController {
    @Autowired
    LibroRepository libroRepository;

    @GetMapping("/all")
    public List<Libro> list() {
        List<Libro> libros = libroRepository.findAll();
        for (Libro libro : libros) {
            libro.setAutor(null);
            libro.setCategoria(null);
            libro.setEdicion(null);
        }
        return libros;
    }
    @GetMapping("/ISBN={ISBN}")
    public ResponseEntity<Libro> getById(@PathVariable Long ISBN) {
        ResponseEntity<Libro> libro;
        if (libroRepository.existsById(ISBN)) {
            Optional<Libro> lib = libroRepository.findById(ISBN);
            libro = new ResponseEntity<>(lib.get(), HttpStatus.OK);
        } else {
            libro = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return libro;
    }

    @GetMapping("/categoria={categoria}")
    public ResponseEntity<List<Libro>> getByCategoria(@PathVariable String categoria) {
        ResponseEntity<List<Libro>> libro;
        List<Libro> respuesta =libroRepository.findByCategoria(categoria);

        if (Objects.nonNull(respuesta) && !respuesta.isEmpty()) {
            libro = new ResponseEntity<>(respuesta, HttpStatus.OK);
        }else {
            libro = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return libro;
    }

    @GetMapping("/autor={autor}")
    public ResponseEntity<List<Libro>> getByAutor(@PathVariable String autor) {
        ResponseEntity<List<Libro>> libro;
        List<Libro> respuesta = libroRepository.findByAutor(autor);

        if (Objects.nonNull(respuesta) && !respuesta.isEmpty()) {
            libro = new ResponseEntity<>(respuesta, HttpStatus.OK);
        }else {
            libro = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return libro;
    }

    @PostMapping("/create/libro")
    public ResponseEntity<Libro> postCrear(@RequestBody Libro libro) {
        ResponseEntity<Libro> libroResponse;
        if (libro.noneNull()) {
            libroRepository.save(libro);
            libroResponse = new ResponseEntity<>(libro, HttpStatus.OK);
        }else {
            libroResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return libroResponse;
    }

    @PostMapping("/delete/libro={ISBN}")
    public ResponseEntity<Libro> postEliminar(@PathVariable Long ISBN) {
        ResponseEntity<Libro> libroResponse;
        Optional<Libro> libro = libroRepository.findById(ISBN);

        if (libro.isPresent()) {
            libroRepository.delete(libro.get());
            libroResponse = new ResponseEntity<>(libro.get(), HttpStatus.OK);
        }else {
            libroResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return libroResponse;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Libro> put(@PathVariable Long id, @RequestBody Libro input) {
        ResponseEntity<Libro> salida;
        if (libroRepository.existsById(id)) {
            libroRepository.save(input);
            salida = new ResponseEntity<Libro>(input, HttpStatus.OK);
        } else {
            salida = new ResponseEntity<Libro>(HttpStatus.NOT_FOUND);
        }
        return salida;
    }
}