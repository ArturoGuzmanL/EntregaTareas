package run.utils;

import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.HibernateException;

import java.sql.CallableStatement;

import static run.utils.UserSession.Rol.*;

public class UserSession {
    //Rol es un enum, enteros asignados a Variables constantes: https://jarroba.com/enum-enumerados-en-java-con-ejemplos/
    public enum Rol {
        ADMINISTRADOR,
        PROFESOR,
        ALUMNO
    }

    private static UserSession instance = null;
    private String email;
    private Rol rol;
    private Integer id;

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRol(Integer rol) {
        switch (rol) {
            case 1:
                this.rol = ADMINISTRADOR;
                break;
            case 2:
                this.rol = Rol.PROFESOR;
                break;
            case 3:
                this.rol = Rol.ALUMNO;
                break;
        }
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public static void closeSession() {
        UserSession session = UserSession.getInstance();
        session.setEmail(null);
        session.setRol(Rol.ALUMNO);
        instance = null;
    }

    public static void createSession(String email, Rol rol) {
        UserSession session = UserSession.getInstance();
        session.setEmail(email);
        session.setRol(rol);
        session.setId(getIdBy(email, rol));
    }

    public static Integer getIdBy(String email, Rol rol) {
        var session = HibernateUtils.getSessionFactory().openSession();
        if (rol == ADMINISTRADOR || email.equals("SUDO_ADMIN")) {
            return 1;
        } else if (rol == PROFESOR) {
            var query = session.createQuery("select id from ProfesorEntity where correo = :email");
            query.setParameter("email", email);
            return (int) query.uniqueResult();
        } else { //ALUMNO
            var query = session.createQuery("select id from AlumnoEntity where email = :email");
            query.setParameter("email", email);
            return (int) query.uniqueResult();
        }
    }
}
