package run.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.HibernateException;
import java.sql.CallableStatement;
import java.sql.Types;
import java.util.List;

//Se realiza login en BD, comparando SHA2 de la contraseña introducida con la de la BD,
//En la BD no se almacena contraseña en texto plano, se almacena el SHA2 de la contraseña.
public class Login {
    public static Boolean login(String email, String password, UserSession.Rol rol) {
        Integer islogin = 0;

        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            //Si es SUDO_ADMIN, se comprobara que el email y contraseña introducidos son correctos
            Integer finalRol = rolToInt(rol, email);
            islogin = session.doReturningWork(connection -> {
                try (CallableStatement function = connection.prepareCall(
                        "{ ? = call login(?, ?, ?) }")) {
                    function.registerOutParameter(1, Types.INTEGER);
                    function.setString(2, email);
                    function.setString(3, password);
                    function.setInt(4, finalRol);
                    function.execute();
                    return function.getInt(1);
                }
            });
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
        //Se crea instancia de usuario, no se guarda contraseña
        if (islogin == 1) {
            UserSession.createSession(email, rol);
        } else {
            UserSession.closeSession();
        }

        return islogin == 1;
    }

    private static Integer rolToInt(UserSession.Rol rol, String email) {
        if (email.equals("SUDO_ADMIN") || rol.equals(UserSession.Rol.ADMINISTRADOR)) {
            return 1;
        } else if (rol.equals(UserSession.Rol.PROFESOR)) {
            return 2;
        } else {
            return 3;
        }
    }

    //Crea contraseña en BD, se almacena el SHA2 de la contraseña
    public static void crearPassword(String email, String password, UserSession.Rol rol) {
        try (var session = HibernateUtils.getSessionFactory().openSession()) {
            Integer id = UserSession.getIdBy(email, rol);
            Integer finalRol = rolToInt(rol, email);
            session.doWork(connection -> {
                try (CallableStatement function = connection.prepareCall(
                        "{ call crear_pass(?, ?, ?) }")) {
                    function.setInt(1, id);
                    function.setInt(2, finalRol);
                    function.setString(3, password);
                    function.execute();
                }
            });
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    //Crear contraseñas o cambia SOLO si el usuario ha sido loggeado.
    public static void crearPasswordLogeado(UserSession user, String password) {
        if (UserSession.getInstance() != null) {
            try (var session = HibernateUtils.getSessionFactory().openSession()) {
                Integer id = user.getId();
                Integer finalRol = rolToInt(user.getRol(), user.getEmail());
                session.doWork(connection -> {
                    try (CallableStatement function = connection.prepareCall(
                            "{ call crear_pass(?, ?, ?) }")) {
                        function.setInt(1, id);
                        function.setInt(2, finalRol);
                        function.setString(3, password);
                        function.execute();
                    }
                });
            } catch (HibernateException e) {
                throw new HibernateException(e);
            }
        }
    }
}
