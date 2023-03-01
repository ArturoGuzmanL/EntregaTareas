package fill_db;

import static run.utils.Login.crearPassword;
import static run.utils.UserSession.Rol.ALUMNO;
import static run.utils.UserSession.Rol.PROFESOR;

public class CrearPasswordDB {
    public static void main(String[] args) {
        crearPassword("Adriani32@mail.com", "1234", PROFESOR);
        crearPassword("Antonioi@mail.com", "1234", ALUMNO);
    }
}
