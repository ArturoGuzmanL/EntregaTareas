package API.repository;

import API.Model.Alumno;
import API.Model.Practica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import run.entity.AlumnoEntity;

import java.util.ArrayList;

public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {
}
