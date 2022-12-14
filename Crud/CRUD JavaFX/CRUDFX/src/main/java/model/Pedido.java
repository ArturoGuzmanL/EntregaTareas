package model;

import javafx.scene.control.Button;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import static controller.PedidoDAO.obtenerAllIdentificacion;
import static view.View.clean;
import static view.View.cleanDot;

/**
 * Objeto que guarda la comanda de un usuario del programa.
 */
public class Pedido {

    // Valor de Identificacion de un Pedido. Sirve para guardar la informacion dentro de la base de datos en una misma tabla
    private Integer identificacion;

    // Fecha en la que se realizó la comanda
    private Date fecha;

    // Nombre asociado al pedido
    private String cliente;

    // Estados Posibles: PENDIENTE y RECOGIDO
    private String estado;

    //Listado de los Productos elegidos
    private HashMap<Producto, Integer> productos;

    public Pedido() {
    }
    public Pedido(Date fecha, String cliente, String estado, HashMap<Producto, Integer> productos) {
        this.fecha = fecha;
        this.cliente = cliente;
        this.estado = estado;
        this.productos = productos;
        try {
            ArrayList<Integer> identificaciones = obtenerAllIdentificacion();
            Integer max;

            if (!identificaciones.isEmpty()) {
                max = Collections.max(identificaciones);
            } else {
                max = 0;
            }
            this.identificacion = max + 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado.toUpperCase();
    }

    public HashMap<Producto, Integer> getHashProductos() {
        return productos;
    }

    public String getProductos() {
        StringBuilder s = new StringBuilder();
        ArrayList<String> lista = new ArrayList<>();

        for (int i = 0; i < productos.size(); i++) {
            lista.add(productos.keySet().toArray()[i] + " x " + productos.values().toArray()[i] + "\n");
        }
        lista.sort(String::compareTo);

        String finalS = "";
        for (int i = 0; i < lista.size(); i++) {
            finalS += lista.get(i);
        }
        return finalS;
    }

    public void setProductos(HashMap<Producto, Integer> producto) {
        this.productos = producto;
    }

    @Override
    public String toString() {
        return "fecha -> " + fecha +
                ", cliente -> " + cliente +
                ", estado -> '" + estado + '\'' +
                ", productos: {" + productos +
                '}';
    }

    /**
     * Vista mas sencilla de interpretar por el usuario
     *
     * @return String con la Identificacion, el nombre del pedido y la fecha
     */
    public String infoView() {
        clean(1);
        System.out.println(">>-------------------------->>");
        System.out.println("Identificacion del pedido --> " + identificacion + " :>");
        System.out.println("----------------------------------------------------------------------");
        productos.forEach((k, v) -> {
            System.out.println(k.cartaView() + " º | º Cantidad: " + v);
        });

        return "\n Cliente: " + cliente + "\n || Fecha: " + fecha + "\n"+"----------------------------------------------------------------------";
    }

    /**
     * Vista que simula un recibo emitido tras la efectuacion del pedido. Tambien calcula el precio total del pedido a partir de la cantidad deseada
     */
    public void recibo() {
        AtomicReference<Float> precio_total = new AtomicReference<>((float) 0);
        cleanDot(3);
        System.out.println("**********************************");
        System.out.println("Nombre: " + cliente);
        System.out.println("Fecha: " + fecha);
        System.out.println();
        System.out.println("Productos: ");
        productos.forEach((k, v) -> {
            if (v > 0) {
                System.out.println(k.cartaView() + " -- Uds: " + v);
                float precio = k.getPrecio() * v;
                precio_total.updateAndGet(v1 -> (v1 + precio));
            }
        });
        System.out.println();
        System.out.println("Precio Final a Pagar: " + precio_total + "€");
        System.out.println("**********************************");
        cleanDot(3);
    }
}
