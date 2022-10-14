package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.text.Normalizer;
import java.util.*;

public class Main {
    static final String[] header = {"PALABRA", "VECES REPETIDAS"};

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("""
                                Introduzca el nombre del archivo con el que quiere trabajar.
                                El archivo debe de estar dentro de la carpeta input.
                                No especifique la extensión, y cuide las mayusculas y minusculas.
                                """);

        System.out.println();
        String texto = sc.nextLine();
        String textocontxt = texto+".txt";
        limpiarPantalla(5);

        uploadToCSV(leeryNormalizarTexto("src\\input\\"+textocontxt), "src\\output\\"+texto+"_histograma.csv");
    }

    static LinkedHashMap<String, Integer> leeryNormalizarTexto(String path) {
        LinkedHashMap<String, Integer> listaPalabras;

        try (var br = new BufferedReader(new FileReader(path))) {

            ArrayList<String> all_text = new ArrayList<>();
            ArrayList<String> all_text_mod = new ArrayList<>();
            String value;
            listaPalabras = new LinkedHashMap<>();
            StringBuilder final_text = new StringBuilder();
            while (br.ready()) {
                value = br.readLine();
                if (value != null) {
                    final_text.append(value).append("\n");
                    all_text.addAll(List.of(value.split(" ")));
                }
            }
            limpiarPantalla(1);
            System.out.println("*************");
            System.out.println("El texto es: ");
            System.out.println("*************");
            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.println(final_text);
            System.out.println("-----------------------------------------------------------------------------------------------------");
            limpiarPantalla(10);

            String normalized_element;
            for (String element : all_text) {
                normalized_element = normalizar(element);
                normalized_element = eliminarSimbolos(normalized_element);
                if (normalized_element.length() >= 3 && permitido(normalized_element)) {
                    all_text_mod.add(normalized_element);
                }
            }

            // Orden alfabetico de los elementos
            Collections.sort(all_text_mod);

            // Permite guardar las palabras ordenadas alfabeticamente

            for (String k : all_text_mod) {
                listaPalabras.put(k, 0);
            }

            // Creamos un HasSet a partir de las palabras que nos aparecen en la Lista Normalizada y guardada como nosotros deseamos
            Set<String> diccionario_elementos = new HashSet<>(all_text_mod);

            for (String s : diccionario_elementos) {
                // (Calculo de el numero de veces que aparece en el listado, elemento s)
                int veces = Collections.frequency(all_text_mod, s);
                // Guardamos la informacion en el HasMap() ordenado -> En esta variable se encuentran los datos que exportaremos a un archivo CSV
                listaPalabras.put(s, veces);
            }
            System.out.println("---------");
            System.out.println("Listado: ");
            System.out.println();
            listaPalabras.forEach((k, v) -> System.out.println(k + " -> " + v));
            System.out.println("---------");
            limpiarPantalla(14);


            System.out.println("<<----------------------------------------------------->>");
            System.out.println("    Numero de Palabras diferentes: " + listaPalabras.size());
            System.out.println("<<----------------------------------------------------->>");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listaPalabras;
    }

    static public String normalizar(String text) {

        String normalized_element = Normalizer.normalize(text, Normalizer.Form.NFKD);
        normalized_element = normalized_element.replaceAll("\\p{InCombiningDiacriticalMarks}", "").toLowerCase();
        return normalized_element;
    }

    static public String eliminarSimbolos(String text) {
        String word = text;
        word = word.replaceAll("\\p{Punct}", "");
        word = word.replaceAll("[?¡«»]", "");
        word = word.replaceAll("[—+/*]", "");
        word = word.replaceAll("[0-9]", "");
        return word;
    }

    static public Boolean permitido(String text) {
        String[] non_util_char = {"con", "por", "los", "las", "que", "del"};
        boolean permitido = true;
        for (String carac : non_util_char) {
            if (text.equals(carac)) {
                permitido = false;
                break;
            }
        }
        return permitido;
    }

    static public void uploadToCSV(LinkedHashMap<String, Integer> element, String path) {
        try (var out = new FileWriter(path); var printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(header))) {
            element.forEach((k, v) -> {
                try {
                    printer.printRecord(k, v);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void limpiarPantalla(Integer max) {
        for (int i = 0; i < max; i++) {
            System.out.println(".");

        }

    }
}