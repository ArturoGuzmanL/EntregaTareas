public class Main {
    private static AlumnoDao almDAO = new AlumnoDao();

    public static void main(String[] args) {
        almDAO.cleanAll();
        llenarBdd();
        System.out.println("Listado: ");
        almDAO.listarTodo();
        System.out.println("****************************************");
        System.out.println("Listado de suspensos: ");
        almDAO.listarSuspensos();
        System.out.println("****************************************");
        almDAO.estadisticas();

    }

    public static void llenarBdd () {
        Alumno al1 = new Alumno();
        al1.setNombre("Juan");
        al1.setTelefono("123456789");
        al1.setEmail("Juan@mail.com");
        al1.setAd(10.0);
        al1.setDi(10.0);
        almDAO.insertarAlumno(al1);

        Alumno al2 = new Alumno();
        al2.setNombre("Pedrito");
        al2.setTelefono("456789321");
        al2.setEmail("Pedrito@mail.com");
        al2.setAd(5.10);
        al2.setDi(7.65);
        almDAO.insertarAlumno(al2);

        Alumno al3 = new Alumno();
        al3.setNombre("Gema");
        al3.setTelefono("765843091");
        al3.setEmail("Gema@mail.com");
        al3.setAd(4.50);
        al3.setDi(8.60);
        almDAO.insertarAlumno(al3);

        Alumno al4 = new Alumno();
        al4.setNombre("Laura");
        al4.setTelefono("894120953");
        al4.setEmail("Laura@mail.com");
        al4.setAd(7.60);
        al4.setDi(4.25);
        almDAO.insertarAlumno(al4);
    }
}
