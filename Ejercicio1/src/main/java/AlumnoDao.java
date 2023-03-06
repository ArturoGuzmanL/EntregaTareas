
import javax.persistence.TypedQuery;
import java.util.ArrayList;

public class AlumnoDao {


    public void insertarAlumno(Alumno alumno) {
        var em = ObjectDBUtil.getETM().createEntityManager();
        em.getTransaction().begin();
        em.persist(alumno);
        em.getTransaction().commit();
        em.close();
    }

    public void listarTodo() {
        ArrayList<Alumno> listado_alumnos;
        var em = ObjectDBUtil.getETM().createEntityManager();
        TypedQuery<Alumno> q = em.createQuery("select al from Alumno al", Alumno.class);
        listado_alumnos = (ArrayList<Alumno>) q.getResultList();
        em.close();
        if (!listado_alumnos.isEmpty()) {

            mostrarLista(listado_alumnos);
        } else {
            System.out.println("No hay alumnos");
        }
    }

    public void listarSuspensos() {
        var em = ObjectDBUtil.getETM().createEntityManager();
        ArrayList<Alumno> listado;
        TypedQuery<Alumno> q = em.createQuery("SELECT al from Alumno al where al.ad<5.0 OR al.di<5.0 ", Alumno.class);
        listado = (ArrayList<Alumno>) q.getResultList();
        em.close();
        mostrarLista(listado);
    }

    public void estadisticas() {
        ArrayList<Alumno> listado_alumnos;
        var em = ObjectDBUtil.getETM().createEntityManager();
        TypedQuery<Alumno> q = em.createQuery("select al from Alumno al", Alumno.class);
        listado_alumnos = (ArrayList<Alumno>) q.getResultList();
        em.close();
        var total_AD = 0.0;
        var total_DI = 0.0;
        var total_alumnos = listado_alumnos.size();

        for (Alumno al : listado_alumnos) {
            total_AD += al.getAd();
            total_DI += al.getDi();
        }
        em = ObjectDBUtil.getETM().createEntityManager();
        ArrayList<Alumno> listado_aprobados_ad;
        TypedQuery<Alumno> qu = em.createQuery("SELECT al from Alumno al where al.ad>=5.0", Alumno.class);
        listado_aprobados_ad = (ArrayList<Alumno>) qu.getResultList();
        em.close();

        em = ObjectDBUtil.getETM().createEntityManager();
        ArrayList<Alumno> listado_aprobados_id;
        TypedQuery<Alumno> que = em.createQuery("SELECT al from Alumno al where al.di>=5.0 ", Alumno.class);
        listado_aprobados_id = (ArrayList<Alumno>) que.getResultList();
        em.close();

        System.out.println("Media AD: " + total_AD / total_alumnos);
        Double ratio_ad = (((double) listado_aprobados_ad.size() / (double) total_alumnos));
        System.out.println("Porcentaje de aprobados en AD: " + ratio_ad * 100 + "%");
        System.out.println("Media DI: " + total_DI / total_alumnos);
        Double ratio_id = ((double) listado_aprobados_id.size() / (double) total_alumnos);
        System.out.println("Porcentaje de aprobados en DI: " + ratio_id * 100 + "%");

    }

    public void mostrarLista(ArrayList<Alumno> listado_alumnos) {
        for (Alumno al : listado_alumnos) {
            System.out.println(al.toString());
        }
    }

    public void cleanAll() {
        var em = ObjectDBUtil.getETM().createEntityManager();
        TypedQuery<Alumno> q = em.createQuery("delete from Alumno p", Alumno.class);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction();
        em.getTransaction().commit();
        em.close();
    }
}
