package com.ejercicio2.Repository;

import com.ejercicio2.Model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno,Long> {

}
