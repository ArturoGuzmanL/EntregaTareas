import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ObjectDBUtil {
    private static EntityManagerFactory ETM;

    static {
        try {

            ETM = Persistence.createEntityManagerFactory("alumnos.odb");
            System.out.println("Conexión realizada");
        } catch (Exception ex) {
            System.out.println("Hubo error al iniciar el entityManager");
            System.out.println(ex);
        }
    }

    public static EntityManagerFactory getETM() {
        return ETM;
    }
}
