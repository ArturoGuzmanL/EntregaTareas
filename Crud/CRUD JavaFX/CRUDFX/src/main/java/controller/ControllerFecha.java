package controller;

import java.util.ArrayList;

import static view.View.*;

import model.Fecha;

/**
 * Clase de apoyo para el manejo de la lectura de fechas por teclado
 */
public class ControllerFecha {
    public static Fecha leerFecha() {
        ArrayList<String> meses = new ArrayList<>();
        System.out.println("Indiqueme el dia que desea consultar");
        cleanDot(2);
        System.out.println("Tenga en cuenta que si ingresa de manera incorrecta los parámetros. Ej: '30-02'");
        Integer dia = leerInt();
        clean(2);
        System.out.println("A continuacion puede ver los meses del año");
        meses.add("Enero");
        meses.add("Febrero");
        meses.add("Marzo");
        meses.add("Abril");
        meses.add("Mayo");
        meses.add("Junio");
        meses.add("Julio");
        meses.add("Agosto");
        meses.add("Septiembre");
        meses.add("Octubre");
        meses.add("Noviembre");
        meses.add("Diciembre");
        final Integer[] cont = {1};
        meses.forEach(k -> {
            System.out.print(cont[0] + ". ");
            System.out.println(k);
            cont[0]++;
        });
        cleanDot(2);
        System.out.println("Indique el mes");
        Integer mes_int = leerInt();
        String mes = meses.get(mes_int - 1);

        return new Fecha(dia, mes);
    }
}
