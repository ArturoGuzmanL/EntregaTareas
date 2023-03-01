package run.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import run.entity.AlumnoEntity;
import run.entity.EmpresaEntity;
import run.entity.PracticaEntity;
import run.entity.ProfesorEntity;
import run.utils.HibernateUtils;
import run.utils.UserSession;

import java.sql.Date;
import java.util.ArrayList;

public class ProfesorDAO {

    // METODOS DEL SUDO

    /**
     * Permite crear un profesor e insertarlo en la Base de Datos. ESTO ES UN METODO DE SUDO
     *
     * @param nombre     Nombre del Profesor
     * @param apellido   Apellido del Profesor
     * @param contraseña Contraseña de acceso del Profesor
     * @param correo     Correo de asociado al Profesor
     * @return El profesor creado que ha sido insertado
     */
    public static ProfesorEntity crearInsertarProfesor(String nombre, String apellido, String contraseña, String correo) {
        ProfesorEntity profesorPrueba = new ProfesorEntity();
        Integer id_profesor;
        profesorPrueba.setNombre(nombre);
        profesorPrueba.setApellido(apellido);
//        profesorPrueba.setContraseña(contraseña);
        profesorPrueba.setCorreo(correo);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.persist(profesorPrueba);
            tst.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String sql_query = "select max(profesor.id) FROM ProfesorEntity profesor";
            id_profesor = session.createQuery(sql_query, Integer.class).uniqueResult();
            profesorPrueba.setId(id_profesor);

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return profesorPrueba;
    }

    /**
     * Devuelve el Alumno guardado en la Base de Datos con el Id indicado
     *
     * @param id Id del Alumno que se desea obtener
     * @return Alumno con la Id indicada
     */
    public static ProfesorEntity infoProfesor(Integer id) {
        ProfesorEntity profesor = new ProfesorEntity();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            profesor = session.get(ProfesorEntity.class, id);

        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        return profesor;
    }


    //-------------------------------------------------------

    /**
     * Devuelve el Alumno guardado en la Base de Datos con el Id indicado
     *
     * @param id Id del Alumno que se desea obtener
     * @return Alumno con la Id indicada
     */
    public static AlumnoEntity infoAlumno(Integer id) {
        AlumnoEntity alumno = new AlumnoEntity();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            alumno = session.get(AlumnoEntity.class, id);

        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        return alumno;
    }

    /**
     * Devuelve la Empresa guardada en la Base de Datos con el Id indicado
     *
     * @param id Id de la Empresa que se desea obtener
     * @return Empresa con la Id indicada
     */
    public static EmpresaEntity infoEmpresa(Integer id) {
        EmpresaEntity empresa = new EmpresaEntity();
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            empresa = session.get(EmpresaEntity.class, id);

        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        return empresa;
    }

    /**
     * Crea un Alumno Nuevo y lo inserta en la Base de Datos(Tabla:Alumno)
     *
     * @param profesor        Profesor que ha creado el Alumno
     * @param empresa         Empresa asignada al Alumno para realizar las practicas
     * @param nombre          Nombre del Alumno
     * @param apellidos       Apellidos del Alumno
     * @param contraseña      Contraseña de acceso del Alumno a la plataforma
     * @param dni             DNI del Alumno
     * @param fechaNacimiento Fecha de Nacimiento del Alumno
     * @param email           Email del Alumno
     * @param telefono        Telefono del Alumno
     * @param totalHorasDual  Total de horas que el Alumno debe cursar en la Formacion Dual
     * @param totalHorasFct   Total de horas que el Alumno debe cursar en la Formacion en Centros de Trabajo
     * @param observaciones   Anotaciones Acerca del Alumno
     * @return El Id del Alumno creado
     */
    public static AlumnoEntity crearInsertarAlumno(ProfesorEntity profesor, EmpresaEntity empresa, String nombre, String apellidos, String contraseña, String dni, Date fechaNacimiento, String email, int telefono, Integer totalHorasDual, int totalHorasFct, String observaciones) {
        Integer id_alumno_creado;
        AlumnoEntity alumno_nuevo = new AlumnoEntity(nombre, apellidos, contraseña, dni, fechaNacimiento, email, telefono, empresa.getId(), profesor.getId(), totalHorasDual, totalHorasFct, observaciones);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.persist(alumno_nuevo);
            tst.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String sql_query = "select max(alumno.id) FROM AlumnoEntity alumno";
            id_alumno_creado = session.createQuery(sql_query, Integer.class).uniqueResult();
            alumno_nuevo.setId(id_alumno_creado);

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return alumno_nuevo;
    }

    /**
     * Crea e Inserta una Empresa nueva en la Base de Datos
     *
     * @param nombre             Nombre de la Empresa
     * @param telefono           Telefono de la Empresa
     * @param email              Email de la Empresa
     * @param responsableEmpresa Nombre de la Persona responsable de la Empresa
     * @param observaciones      Observaciones acerca de la Empresa
     * @return El Id de la Empresa creada
     */
    public static EmpresaEntity crearInsertarEmpresa(String nombre, Integer telefono, String email, String responsableEmpresa, String observaciones) {
        Integer id_empresa;
        EmpresaEntity empresa = new EmpresaEntity(nombre, telefono, email, responsableEmpresa, observaciones);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.persist(empresa);
            tst.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            String sql_query = "select max(empresa.id) FROM EmpresaEntity empresa";
            id_empresa = session.createQuery(sql_query, Integer.class).uniqueResult();
            empresa.setId(id_empresa);

        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return empresa;
    }

    /**
     * Elimina un Alumno de la Base de Datos
     *
     * @param alumno El alumno que se desea eliminar
     */
    public static void deleteAlumno(AlumnoEntity alumno) {
        boolean eliminado = false;


        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.remove(alumno);
            tst.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

//    /**
//     * Elimina una Empresa de la Base de Datos
//     *
//     * @param empresa La empresa que se desea eliminar
//     */
//    public static void deleteEmpresa(EmpresaEntity empresa) {
//        boolean eliminado = false;
//
//        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
//            Transaction tst = session.beginTransaction();
//            session.remove(empresa);
//            tst.commit();
//        } catch (HibernateException e) {
//            throw new HibernateException(e);
//        }
//    }

    /**
     * Permite sobreescribir un Alumno guardado en la Base de datos.
     * A tener en cuenta:  No se puede editar el 'ProfesorAsignado', ni 'TotalHorasDual', ni tampoco 'TotalHorasFCT'
     *
     * @param alumno_actualizado Alumno con la informacion actualizada. La que se desea insertar
     */
    public static void modificarAlumno(AlumnoEntity alumno_actualizado) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.update(alumno_actualizado);
            tst.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    /**
     * Permite sobreescribir una empresa guardada en la Base de datos
     *
     * @param empresa_actualizada Empresa con la informacion actualizada. La que se desea insertar
     */
    public static void modificarEmpresa(EmpresaEntity empresa_actualizada) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.update(empresa_actualizada);
            tst.commit();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    /**
     * Forma directa de Actualizar un Alumno existente asignandole una empresa.
     * A la hora de crear un alumno tambien se le puede asignar una empresa.
     * Tambien mediante el metodo 'actualizarAlumno()'
     *
     * @param alumno  Alumno que se desea actualizar
     * @param empresa Empresa que se desea asignar al Alumno
     */
    public static void asignarEmpresaAlumno(AlumnoEntity alumno, EmpresaEntity empresa) {
        alumno.setEmpresaPracticas(empresa.getId());
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Transaction tst = session.beginTransaction();
            session.update(alumno);
            tst.commit();

        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    /**
     * Lista todos los Alumnos asociados a un Profesor
     *
     * @param id Id del Profesor del que se desean listar los alumnos
     * @return Listado de los Alumnos del profesor con el 'Id' indicado
     */
    public static ArrayList<AlumnoEntity> listarAllAlumnos(Integer id) {
        ArrayList<AlumnoEntity> listado_alumnos = new ArrayList<>();
        String hql_query = "";
        if (UserSession.getInstance().getRol() == UserSession.Rol.ADMINISTRADOR) {
            hql_query = "SELECT alumno from AlumnoEntity alumno";
        } else {
            hql_query = "SELECT alumno from AlumnoEntity alumno WHERE profesorAsignado=:i";
        }
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<AlumnoEntity> query = session.createQuery(hql_query, AlumnoEntity.class);
            query.setParameter("i", id);
            listado_alumnos = (ArrayList<AlumnoEntity>) query.list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        return listado_alumnos;
    }

    /**
     * Lista todas las practicas de un Alumno
     *
     * @param id Id del Alumno del que se desean Obtener las practicas
     * @return Listado con todas las Practicas de un Alumno
     */
    public static ArrayList<PracticaEntity> listarAllPracticas(Integer id) {
        ArrayList<PracticaEntity> practicas = new ArrayList<>();
        String hql_query = "select practica from PracticaEntity practica where alumnoAsociado=:alum";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<PracticaEntity> query = session.createQuery(hql_query, PracticaEntity.class);
            query.setParameter("alum", id);
            practicas = (ArrayList<PracticaEntity>) query.list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        return practicas;
    }

    /**
     * Lista todas las Empresas existentes
     *
     * @return Listado con todas las Empresas que hay registradas
     */
    public static ArrayList<EmpresaEntity> listarAllEmpresas() {
        ArrayList<EmpresaEntity> empresas = new ArrayList<>();
        String hql_query = "select em from EmpresaEntity em";
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Query<EmpresaEntity> query = session.createQuery(hql_query, EmpresaEntity.class);
            empresas = (ArrayList<EmpresaEntity>) query.list();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        return empresas;
    }

    /**
     * Permite obtener el total de Horas que un Alumno ha dedicado a cada una de las practicas
     *
     * @param id            Id del Alumno cuyo total de horas en un tipo de practica se desea consultar
     * @param tipo_practica <ul>
     *                      <li>FCT</li>
     *                      <li>DUAL</li>
     *                      </ul>
     * @return Total de horas dedicadas a la Practica. En caso de que el tipo de practica no exista, devolvera un -1
     */
    public static Integer totalHorasAlumno(Integer id, String tipo_practica) {
        Integer total_horas = 0;
        String practica = tipo_practica.toLowerCase();
        String hql_query = "select sum(totalHorasDia) from PracticaEntity where alumnoAsociado=:id and tipoPractica=:param";
        if (practica.equals("fct") || practica.equals("dual")) {
            try (Session session = HibernateUtils.getSessionFactory().openSession()) {
                Query<Integer> query = session.createQuery(hql_query, Integer.class);
                query.setParameter("id", id);
                query.setParameter("param", practica);
                total_horas = query.getSingleResult();
            } catch (HibernateException e) {
                throw new HibernateException(e);
            }
            return total_horas;
        } else {
            return -1;
        }
    }

    /**
     * Devuelve la sumatoria de las horas dedicadas de un Alumno a las 'FCT' mas las horas dedicadas a 'DUAL'
     *
     * @param id Id del Alumno cuyo total de horas en practicas se desea consultar
     * @return Sumatoria de las horas en 'FCT' mas las horas en 'DUAL'
     */
    public static Integer sumatoriaTotalPracticas(Integer id) {
        return totalHorasAlumno(id, "fct") + totalHorasAlumno(id, "dual");
    }

}
