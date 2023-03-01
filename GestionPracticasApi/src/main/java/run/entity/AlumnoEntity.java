package run.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import run.dao.ProfesorDAO;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "alumno", schema = "gestion_empresa")
public class AlumnoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    @Basic
    @Column(name = "apellidos", nullable = false, length = -1)
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
    @Column(name = "observaciones", nullable = true, length = -1)
    private String observaciones;

    public AlumnoEntity(String nombre, String apellidos, String contraseña, String dni, Date fechaNacimiento, String email, int telefono, int empresaPracticas, int profesorAsignado, Integer totalHorasDual, int totalHorasFct, String observaciones) {
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

    public AlumnoEntity() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono + "";
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEmpresaPracticas() {
        return ProfesorDAO.infoEmpresa(empresaPracticas) + "";
    }

    public EmpresaEntity getEmpresaPracticasOBJ() {
        return ProfesorDAO.infoEmpresa(empresaPracticas);
    }

    //este metodo es para que me devuelva el id de la empresa
    public int getEmpresaPracticasId() {
        return empresaPracticas;
    }

    public void setEmpresaPracticas(Integer empresaPracticas) {
        this.empresaPracticas = empresaPracticas;
    }

    public String getProfesorAsignadoOBJ() {
        ProfesorEntity em = ProfesorDAO.infoProfesor(profesorAsignado);
        return em.getNombre();
    }
    //este metodo es por si solo quieres el id del profesor
    public int getProfesorAsignadoId() {
        return profesorAsignado;
    }

    public Integer getProfesorAsignadoInt() {
        return profesorAsignado;
    }

    public void setProfesorAsignado(Integer profesorAsignado) {
        this.profesorAsignado = profesorAsignado;
    }

    public String getTotalHorasDual() {
        return totalHorasDual + "";
    }

    public void setTotalHorasDual(Integer totalHorasDual) {
        this.totalHorasDual = totalHorasDual;
    }

    public String getTotalHorasFct() {
        return totalHorasFct + "";
    }

    public void setTotalHorasFct(Integer totalHorasFct) {
        this.totalHorasFct = totalHorasFct;
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
        AlumnoEntity that = (AlumnoEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(apellidos, that.apellidos) && Objects.equals(contraseña, that.contraseña) && Objects.equals(dni, that.dni) && Objects.equals(fechaNacimiento, that.fechaNacimiento) && Objects.equals(email, that.email) && Objects.equals(telefono, that.telefono) && Objects.equals(empresaPracticas, that.empresaPracticas) && Objects.equals(profesorAsignado, that.profesorAsignado) && Objects.equals(totalHorasDual, that.totalHorasDual) && Objects.equals(totalHorasFct, that.totalHorasFct) && Objects.equals(observaciones, that.observaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellidos, contraseña, dni, fechaNacimiento, email, telefono, empresaPracticas, profesorAsignado, totalHorasDual, totalHorasFct, observaciones);
    }

    @Override
    public String toString() {
        return "AlumnoEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", dni='" + dni + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", email='" + email + '\'' +
                ", telefono=" + telefono +
                ", empresaPracticas=" + empresaPracticas +
                ", profesorAsignado=" + profesorAsignado +
                ", totalHorasDual=" + totalHorasDual +
                ", totalHorasFct=" + totalHorasFct +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
