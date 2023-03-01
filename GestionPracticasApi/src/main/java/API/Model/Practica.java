package API.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.entity.AlumnoEntity;
import run.entity.PracticaEntity;

import java.sql.Date;

@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "practica", schema = "gestion_empresa")
public class Practica {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "alumno_asociado", nullable = false, updatable = false, insertable = false)
    public int alumnoAsociado;
    @Basic
    @Column(name = "fecha", nullable = false)
    public Date fecha;
    @Basic
    @Column(name = "tipo_practica", nullable = false)
    public String tipoPractica;
    @Basic
    @Column(name = "total_horas_dia", nullable = false)
    public Integer totalHorasDia;
    @Basic
    @Column(name = "actividad", nullable = false)
    public String actividad;
    @Basic
    @Column(name = "observaciones", nullable = true, length = - 1)
    public String observaciones;
    @OneToOne(targetEntity = Alumno.class)
    @JoinColumn(name = "alumno_asociado")
    public Alumno alumnoByAlumnoAsociado;


    public Practica (int alumnoAsociado, Date fecha, String tipoPractica, Integer totalHorasDia, String actividad, String observaciones) {
        this.alumnoAsociado = alumnoAsociado;
        this.fecha = fecha;
        this.tipoPractica = tipoPractica;
        this.totalHorasDia = totalHorasDia;
        this.actividad = actividad;
        this.observaciones = observaciones;
    }


    public Practica() {
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public int getAlumnoAsociado () {
        return alumnoAsociado;
    }

    public void setAlumnoAsociado (int alumnoAsociado) {
        this.alumnoAsociado = alumnoAsociado;
    }

    public Date getFecha () {
        return fecha;
    }

    public void setFecha (Date fecha) {
        this.fecha = fecha;
    }

    public String getTipoPractica () {
        return tipoPractica;
    }

    public void setTipoPractica (String tipoPractica) {
        this.tipoPractica = tipoPractica;
    }

    public Integer getTotalHorasDia () {
        return totalHorasDia;
    }

    public void setTotalHorasDia (Integer totalHorasDia) {
        this.totalHorasDia = totalHorasDia;
    }

    public String getActividad () {
        return actividad;
    }

    public void setActividad (String actividad) {
        this.actividad = actividad;
    }

    public String getObservaciones () {
        return observaciones;
    }

    public void setObservaciones (String observaciones) {
        this.observaciones = observaciones;
    }

    public Alumno getAlumnoByAlumnoAsociado () {
        return alumnoByAlumnoAsociado;
    }

    public void setAlumnoByAlumnoAsociado (Alumno alumnoByAlumnoAsociado) {
        this.alumnoByAlumnoAsociado = alumnoByAlumnoAsociado;
    }
}