package API.Controller;

import API.Model.Alumno;
import API.Model.Practica;
import API.repository.AlumnoRepository;
import API.repository.PracticaRepository;
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
import run.dao.AlumnoDAO;
import run.dao.ProfesorDAO;
import run.entity.AlumnoEntity;
import run.entity.PracticaEntity;
import run.model.Actividad;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumno")
public class AlumnoController {
    @Autowired
    AlumnoRepository alumnoRepository;
    @Autowired
    PracticaRepository practicaRepository;

    @GetMapping("/actividades/id={id}")
    public ResponseEntity<ArrayList<Practica>> getById(@PathVariable Integer id) {
        ResponseEntity<ArrayList<Practica>> response;
        ArrayList<Practica> practicas = new ArrayList<>();
        Optional<Alumno> alumno = alumnoRepository.findById(id);
        if (alumno.isPresent()) {
            practicas = practicaRepository.findByAlumnoAsociado(id);
            for (Practica practica : practicas) {
                practica.setAlumnoByAlumnoAsociado(null);
            }
            response = new ResponseEntity<>(practicas, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/info/id={id}")
    public ResponseEntity<Alumno> getInfo(@PathVariable Integer id) {
        ResponseEntity<Alumno> response;

        Optional<Alumno> alumno = alumnoRepository.findById(id);
        if (alumno.isPresent()) {
            response = new ResponseEntity<>(alumno.get(), HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @GetMapping("/info/actividad/id={id}")
    public ResponseEntity<Practica> getInfoActividad(@PathVariable Integer id) {
        ResponseEntity<Practica> response;

        Optional<Practica> actividad = practicaRepository.findById(id);
        if (actividad.isPresent()) {
            actividad.get().setAlumnoByAlumnoAsociado(null);
            response = new ResponseEntity<>(actividad.get(), HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping("/delete/actividad/id={id}")
    public ResponseEntity<Practica> deletePractica(@PathVariable Integer id) {
        ResponseEntity<Practica> response;
        Optional<Practica> practica = practicaRepository.findById(id);

        if (practica.isPresent()) {
            practicaRepository.deleteById(id);
            response = new ResponseEntity<>(practica.get(), HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/edit/actividad/id={id}")
    public ResponseEntity<Practica> editPractica(@PathVariable Integer id, @RequestBody Practica practicaT) {
        ResponseEntity<Practica> response;
        Optional<Practica> practica = practicaRepository.findById(id);

        if (practica.isPresent()) {
            practica.get().setAlumnoAsociado(practicaT.getAlumnoAsociado());
            practica.get().setFecha(practicaT.getFecha());
            practica.get().setActividad(practicaT.getActividad());
            practica.get().setTipoPractica(practicaT.getTipoPractica());
            practica.get().setTotalHorasDia(practicaT.getTotalHorasDia());
            practica.get().setObservaciones(practicaT.getObservaciones());
            practicaRepository.save(practica.get());
            response = new ResponseEntity<>(practica.get(), HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

}
