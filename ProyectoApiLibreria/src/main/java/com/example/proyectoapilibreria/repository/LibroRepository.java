package com.example.proyectoapilibreria.repository;

import com.example.proyectoapilibreria.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByCategoria(@Param("Categoria") String Categoria);

    List<Libro> findByAutor (@Param("Autor") String Autor);
}