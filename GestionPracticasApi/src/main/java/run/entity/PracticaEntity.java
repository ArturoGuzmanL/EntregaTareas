package run.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "practica", schema = "gestion_empresa")
public class PracticaEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public int id;
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
    @Column(name = "observaciones", nullable = true, length = -1)
    public String observaciones;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_asociado")
    public AlumnoEntity alumnoByAlumnoAsociado;

    public PracticaEntity(int alumnoAsociado, Date fecha, String tipoPractica, Integer totalHorasDia, String actividad, String observaciones) {
        this.alumnoAsociado = alumnoAsociado;
        this.fecha = fecha;
        this.tipoPractica = tipoPractica;
        this.totalHorasDia = totalHorasDia;
        this.actividad = actividad;
        this.observaciones = observaciones;
    }
    public PracticaEntity() {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlumnoAsociado() {
        return alumnoAsociado;
    }

    public void setAlumnoAsociado(Integer alumnoAsociado) {
        this.alumnoAsociado = alumnoAsociado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipoPractica;
    }

    public void setTipoPractica(String tipoPractica) {
        this.tipoPractica = tipoPractica;
    }

    public Integer getTotalHorasDia() {
        return totalHorasDia;
    }
    public String getHoras() {
        return totalHorasDia.toString();
    }

    public void setTotalHorasDia(Integer totalHorasDia) {
        this.totalHorasDia = totalHorasDia;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PracticaEntity that = (PracticaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(alumnoAsociado, that.alumnoAsociado) && Objects.equals(fecha, that.fecha) && Objects.equals(tipoPractica, that.tipoPractica) && Objects.equals(totalHorasDia, that.totalHorasDia) && Objects.equals(actividad, that.actividad) && Objects.equals(observaciones, that.observaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alumnoAsociado, fecha, tipoPractica, totalHorasDia, actividad, observaciones);
    }

    public AlumnoEntity getAlumnoByAlumnoAsociado() {
        return alumnoByAlumnoAsociado;
    }

    public void setAlumnoByAlumnoAsociado(AlumnoEntity alumnoByAlumnoAsociado) {
        this.alumnoByAlumnoAsociado = alumnoByAlumnoAsociado;
    }
    @Override
    public String toString() {
        return "PracticaEntity{" +
                "id=" + id +
                ", alumnoAsociado=" + alumnoAsociado +
                ", fecha=" + fecha +
                ", tipoPractica='" + tipoPractica + '\'' +
                ", totalHorasDia=" + totalHorasDia +
                ", actividad='" + actividad + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", alumnoByAlumnoAsociado=" + alumnoByAlumnoAsociado +
                '}';
    }
}
