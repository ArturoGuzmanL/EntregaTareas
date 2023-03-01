package run.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "profesor", schema = "gestion_empresa")
public class ProfesorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Basic
    @Column(name = "apellido", nullable = false, length = 255)
    private String apellido;

    @Basic
    @Transient
    @Column(name = "contraseña", nullable = false, length = 255, updatable = false)
    private String contraseña;

    @Basic
    @Column(name = "correo", nullable = false, length = 255)
    private String correo;

    @OneToMany
    private Collection<AlumnoEntity> alumnosById;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // String

    public String getIdString() {
        return id + "";
    }

    public String getFullNombre() {
        return nombre + " " + apellido + "";
    }

    public String getCorreoString() {
        return correo + "";
    }

    public String getAlumnosProfesor() {
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
        ProfesorEntity that = (ProfesorEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(apellido, that.apellido) && Objects.equals(contraseña, that.contraseña) && Objects.equals(correo, that.correo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, contraseña, correo);
    }

    public Collection<AlumnoEntity> getAlumnosById() {
        return alumnosById;
    }

    public void setAlumnosById(Collection<AlumnoEntity> alumnosById) {
        this.alumnosById = alumnosById;
    }
}
