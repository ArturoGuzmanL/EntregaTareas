package fill_db;

import run.dao.AlumnoDAO;
import run.dao.ProfesorDAO;
import run.entity.AlumnoEntity;
import run.entity.EmpresaEntity;
import run.entity.PracticaEntity;
import run.entity.ProfesorEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class FillDB {
    public static void main(String[] args) {
        String[] nombres = {"Pepe", "Juan", "Benito", "Alvaro", "Jose", "Francisco", "Aaron", "Antonio", "Jorge", "Miguel", "Rafa", "Maite", "Adolfo", "Sam", "Alberto", "Adrian", "Alejandro", "Alberto", "Roberto", "Reyes"};
        String[] apellidos = {"Alvarez", "Rutatan", "Arguelles", "Aguilar", "Lanzas", "Balbuena", "Jimenez", "Rodriguez", "Privado", "Bernard", "Beethoven", "Nopor", "Juanillo", "Sout", "Ramon", "Joseju", "Vinance", "Santos", "Escohotado", "Caja"};
        Integer[] telefonos = {66578394, 66576892, 36485964, 43589459, 32134560, 32234896, 47586726, 56473495, 12345332, 65788909, 64779670, 56117523, 78562590, 91223684, 78541255, 65774894, 45532177, 77594885, 22355475, 99878566};
        ArrayList<String> dni = new ArrayList<>();
        for (Integer tlf : telefonos) {
            // Numero entre 1 y 27
            int i = (int) Math.round(Math.random() * 27 + 1) + 97;
            String carac = Character.toString(i);

            dni.add(tlf + "" + carac.toUpperCase());
        }
        String fct = "FCT";
        String dual = "DUAL";
        // Numero entre 1 y 100
        String observacion_generica = "Aqui va una observacion ...";

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(nombres.length);
        System.out.println(apellidos.length);
        System.out.println(telefonos.length);


        // Insertar 10 Profesores
        ArrayList<ProfesorEntity> prof_list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProfesorEntity prof = new ProfesorEntity();
            int val = (int) Math.round(Math.random() * 19);
            int val2 = (int) Math.round(Math.random() * 19);
            prof = ProfesorDAO.crearInsertarProfesor(nombres[val], apellidos[val2], "1234", nombres[val] + "i" + "@mail.com");
            prof_list.add(prof);
        }

        // Insertar 10 Empresas
        ArrayList<EmpresaEntity> emp_list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int val = (int) Math.round(Math.random() * 19);
            int val2 = (int) Math.round(Math.random() * 19);
            EmpresaEntity emp =
                    ProfesorDAO.crearInsertarEmpresa(nombres[val] + "S.A", telefonos[val] * 10, nombres[val] + "S.A" + "i" + "@mail.com", nombres[val] + " " + apellidos[val2], observacion_generica);
            emp_list.add(emp);
        }

        // Insertar 10 alumnos por Profesor
        ArrayList<AlumnoEntity> al_list = new ArrayList<>();
        for (ProfesorEntity pro :
                prof_list) {
            for (int i = 0; i < 10; i++) {
                int val = (int) Math.round(Math.random() * 19);
                int val2 = (int) Math.round(Math.random() * 19);
                int val_emp = (int) Math.round(Math.random() * 9);
                Integer total_horas = (int) Math.round(Math.random() * 50 + 1) * 10;
                AlumnoEntity al = ProfesorDAO.crearInsertarAlumno(pro, emp_list.get(val_emp), nombres[val], apellidos[val2], "1234", dni.get(val), randomDate(), nombres[val] + "i" + "@mail.com", telefonos[val] * 10, total_horas, total_horas, observacion_generica);
                al_list.add(al);
            }
        }

        // Insertar 3 Actividades por Alumno
        for (AlumnoEntity al : al_list) {
            for (int j = 0; j < 3; j++) {
                PracticaEntity practica = new PracticaEntity();
                practica.setAlumnoByAlumnoAsociado(al);
                practica.setFecha(randomDate());
                if (j % 2 == 0) {
                    practica.setTipoPractica(fct);
                    practica.setTotalHorasDia(8);
                } else {
                    practica.setTipoPractica(dual);
                    practica.setTotalHorasDia(8);
                }

                practica.setActividad("Rellenar base de Datos");
                practica.setObservaciones(observacion_generica);
                AlumnoDAO.insertarPracticaAlumno(practica);
            }
        }
    }

    private static int randomDay() {

        return (int) (Math.random() * 29 + 1);

    }


    private static int randomMonth() {

        return (int) (Math.random() * 12 + 1);

    }


    private static int randomYear() {

        return (int) (Math.random() * 50 + 1960);

    }


    private static Date randomDate() {

        return convertFromJAVADateToSQLDate(new GregorianCalendar(randomYear(), randomMonth() - 1, randomDay()).getTime());

    }

    public static java.sql.Date convertFromJAVADateToSQLDate(
            java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new Date(javaDate.getTime());
        }
        return sqlDate;
    }
}
