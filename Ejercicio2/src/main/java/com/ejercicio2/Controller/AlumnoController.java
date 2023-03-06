package com.ejercicio2.Controller;


import com.ejercicio2.Model.Alumno;
import com.ejercicio2.Repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/")
public class AlumnoController {

    @Autowired
    AlumnoRepository alumnoRepository;

    @GetMapping("/alumnado")
    public ResponseEntity<List<Alumno>> list() {
        ResponseEntity<List<Alumno>> respuesta;
        List<Alumno> alumnos = alumnoRepository.findAll();
        if (!alumnos.isEmpty()) {
            respuesta = new ResponseEntity<>(alumnos, HttpStatus.OK);
        }else {
            respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return respuesta;
    }

    @GetMapping("/alumnado/{id}")
    public ResponseEntity<Alumno> getAlumno(@PathVariable Long id) {
        ResponseEntity<Alumno> respuesta;
        Optional<Alumno> alumno = alumnoRepository.findById(id);
        if (alumno.isPresent()) {
            respuesta = new ResponseEntity<>(alumno.get(), HttpStatus.OK);
        }else {
            respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return respuesta;
    }

    @GetMapping("alumnado/suspensos/{modulo}")
    public ResponseEntity<List<Alumno>> getAlumnoSuspenso(@PathVariable String modulo) {
        ResponseEntity<List<Alumno>> respuesta;
        List<Alumno> lista = alumnoRepository.findAll();
        List<Alumno> suspensos = new ArrayList<>();

        for (Alumno alumn : lista) {
            if (modulo.equals("ad")) {
                if (alumn.getAd()<5.00) {
                    suspensos.add(alumn);
                }
            }else {
                if (alumn.getDi()<5.00) {
                    suspensos.add(alumn);
                }
            }
        }

        return respuesta = new ResponseEntity<>(suspensos, HttpStatus.OK);
    }


}
