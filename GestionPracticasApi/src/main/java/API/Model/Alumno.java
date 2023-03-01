package API.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;

@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "alumno", schema = "gestion_empresa")
public class Alumno {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    @Basic
    @Column(name = "apellidos", nullable = false, length = - 1)
    private String apellidos;
    @Basic
    @Transient
    @Column(name = "contraseña", nullable = false, length = 255, updatable = false)
    private String contraseña;
    @Basic
    @Column(name = "dni", nullable = false, length = 9)
    private String dni;
    @Basic
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;
    @Basic
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Basic
    @Column(name = "telefono", nullable = false)
    private int telefono;
    @Basic
    @Column(name = "empresa_practicas", nullable = false)
    private int empresaPracticas;
    @Basic
    @Column(name = "profesor_asignado", nullable = false)
    private int profesorAsignado;
    @Basic
    @Column(name = "total_horas_dual", nullable = true)
    private Integer totalHorasDual;
    @Basic
    @Column(name = "total_horas_fct", nullable = false)
    private int totalHorasFct;
    @Basic
    @Column(name = "observaciones", nullable = true, length = - 1)
    private String observaciones;

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getNombre () {
        return nombre;
    }

    public void setNombre (String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos () {
        return apellidos;
    }

    public void setApellidos (String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContraseña () {
        return contraseña;
    }

    public void setContraseña (String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDni () {
        return dni;
    }

    public void setDni (String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento () {
        return fechaNacimiento;
    }

    public void setFechaNacimiento (Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public int getTelefono () {
        return telefono;
    }

    public void setTelefono (int telefono) {
        this.telefono = telefono;
    }

    public int getEmpresaPracticas () {
        return empresaPracticas;
    }

    public void setEmpresaPracticas (int empresaPracticas) {
        this.empresaPracticas = empresaPracticas;
    }

    public int getProfesorAsignado () {
        return profesorAsignado;
    }

    public void setProfesorAsignado (int profesorAsignado) {
        this.profesorAsignado = profesorAsignado;
    }

    public Integer getTotalHorasDual () {
        return totalHorasDual;
    }

    public void setTotalHorasDual (Integer totalHorasDual) {
        this.totalHorasDual = totalHorasDual;
    }

    public int getTotalHorasFct () {
        return totalHorasFct;
    }

    public void setTotalHorasFct (int totalHorasFct) {
        this.totalHorasFct = totalHorasFct;
    }

    public String getObservaciones () {
        return observaciones;
    }

    public void setObservaciones (String observaciones) {
        this.observaciones = observaciones;
    }

    public Alumno (String nombre, String apellidos, String contraseña, String dni, Date fechaNacimiento, String email, int telefono, int empresaPracticas, int profesorAsignado, Integer totalHorasDual, int totalHorasFct, String observaciones) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.email = email;
        this.telefono = telefono;
        this.empresaPracticas = empresaPracticas;
        this.profesorAsignado = profesorAsignado;
        this.totalHorasDual = totalHorasDual;
        this.totalHorasFct = totalHorasFct;
        this.observaciones = observaciones;
    }

    public Alumno () {

    }
}