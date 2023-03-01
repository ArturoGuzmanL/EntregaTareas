package API.repository;

import API.Model.Practica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface PracticaRepository extends JpaRepository<Practica, Integer> {
    ArrayList<Practica> findByAlumnoAsociado(@Param("alumno_asociado") Integer alumno_asociado);
}
