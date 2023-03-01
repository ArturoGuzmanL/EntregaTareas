package run.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import run.entity.AlumnoEntity;
import run.entity.EmpresaEntity;
import run.entity.PracticaEntity;
import run.entity.ProfesorEntity;
import run.utils.HibernateUtils;

import java.util.ArrayList;

public class AlumnoDAO {

    //trae un alumno de la base de datos por su nombre y contraseña (para traer el de la sesión)
    public static AlumnoEntity traerAlumno(int id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        AlumnoEntity alumno = null;
        try {
            alumno = session.get(AlumnoEntity.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return alumno;
    }

    //trae todas las practicas de un alumno
    public static ArrayList<PracticaEntity> traerPracticasAlumno(AlumnoEntity alumno) {
        int id = alumno.getId();
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            var q = s.createQuery("from PracticaEntity where alumnoAsociado = :id", PracticaEntity.class);
            q.setParameter("id", id);
            return (ArrayList<PracticaEntity>) q.list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    //trae una practica por id
    public PracticaEntity traerPractica(int id) {
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            var q = s.createQuery("from PracticaEntity where id = :id", PracticaEntity.class);
            q.setParameter("id", id);
            return (PracticaEntity) q.uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }


    //añadir practica a un alumno
    public static void insertarPracticaAlumno(Object practica) {
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.persist(practica);
            t.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }

    }

    //modificar practica de un alumno
    public static void editarPracticaAlumno(PracticaEntity practica) {
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.update(practica);
            t.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    //borrar practica de un alumno
    public static void borrarPracticaAlumno(PracticaEntity practica) {
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();
            s.remove(practica);
            t.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }


    //saber cuantas horas de dual tiene un alumno
    public static int horasDualAlumno(AlumnoEntity alumno) {
        int id = alumno.getId();
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            var q = s.createQuery("select sum(totalHorasDia) from PracticaEntity where alumnoAsociado = :id and tipoPractica = 'Dual'");
            q.setParameter("id", id);
            var result = q.uniqueResult();
            var resultLong = (long) result;
            return (int) resultLong;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    //saber cuantas horas de FCT tiene un alumno
    public static int horasFCTAlumno(AlumnoEntity alumno) {
        int id = alumno.getId();
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            var q = s.createQuery("select sum(totalHorasDia) from PracticaEntity where alumnoAsociado = :id and tipoPractica = 'FCT'");
            q.setParameter("id", id);
            var result = q.uniqueResult();
            var resultLong = (long) result;
            return (int) resultLong;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }


    //trae el profesor asignado a un alumno
    public ProfesorEntity profesorAsignadoAlumno(AlumnoEntity alumno) {
        int id = alumno.getProfesorAsignadoId();
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            var q = s.createQuery("from ProfesorEntity where id = :id");
            q.setParameter("id", id);
            return (ProfesorEntity) q.uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    //trae empresa de practicas asignada a un alumno
    public EmpresaEntity empresaAsignadaAlumno(AlumnoEntity alumno) {
        int id = alumno.getEmpresaPracticasId();
        try (var s = HibernateUtils.getSessionFactory().openSession()) {
            var q = s.createQuery("from EmpresaEntity where id = :id");
            q.setParameter("id", id);
            return (EmpresaEntity) q.uniqueResult();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

}

