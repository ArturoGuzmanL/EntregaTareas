package run.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "empresa", schema = "gestion_empresa")
public class EmpresaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;
    @Basic
    @Column(name = "telefono", nullable = false)
    private int telefono;
    @Basic
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Basic
    @Column(name = "responsable_empresa", nullable = false, length = 255)
    private String responsableEmpresa;
    @Basic
    @Column(name = "observaciones", nullable = true, length = -1)
    private String observaciones;

    @OneToMany
    protected Collection<AlumnoEntity> alumnosById;

    public EmpresaEntity(String nombre, Integer telefono, String email, String responsableEmpresa, String observaciones) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.responsableEmpresa = responsableEmpresa;
        this.observaciones = observaciones;
    }

    public EmpresaEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono + "";
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponsableEmpresa() {
        return responsableEmpresa;
    }

    public void setResponsableEmpresa(String responsableEmpresa) {
        this.responsableEmpresa = responsableEmpresa;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getAlumnosEmpresa() {
        String mostrar = "";
        ArrayList<AlumnoEntity> alumno_entities = (ArrayList<AlumnoEntity>) alumnosById;
        for (AlumnoEntity k : alumno_entities) {
            String tmp = " || " + k.getId() + " " + k.getNombre() + " " + k.getApellidos() + " " + k.getDni() + " " + k.getEmail() + " \n";
            mostrar += tmp;
        }
        return mostrar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmpresaEntity that = (EmpresaEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(telefono, that.telefono) && Objects.equals(email, that.email) && Objects.equals(responsableEmpresa, that.responsableEmpresa) && Objects.equals(observaciones, that.observaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, telefono, email, responsableEmpresa, observaciones);
    }

    public Collection<AlumnoEntity> getAlumnosById() {
        return alumnosById;
    }

    public void setAlumnosById(Collection<AlumnoEntity> alumnosById) {
        this.alumnosById = alumnosById;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
