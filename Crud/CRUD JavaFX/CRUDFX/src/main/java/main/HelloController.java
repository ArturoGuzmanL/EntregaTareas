package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Pedido;
import model.Producto;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import static controller.PedidoDAO.listarAllPedidosOrdenados;
import static controller.PedidoDAO.listarPedidosFecha;

public class HelloController implements Initializable {
    @FXML
    private TableView tablaView;
    @FXML
    private TableColumn clienteColumn;
    @FXML
    private TableColumn productosColumn;
    @FXML
    private TableColumn fechaColumn;
    @FXML
    private TableColumn estadoColumn;
    @FXML
    private Text comandasText;
    @FXML
    private Text fechaText;
    @FXML
    private Button comandasButton;

    public void comandasButtonOnAction(ActionEvent event) {
        ArrayList<Pedido> pedidos = listarAllPedidosOrdenados();
        ObservableList<Pedido> pedido = FXCollections.observableArrayList(pedidos);
        tablaView.getItems().clear();
        tablaView.setItems(pedido);
        tablaView.refresh();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        String fecha = date.toString();
        String[] fechaMod = fecha.split("-");
        fechaText.setText("Fecha: " + fechaMod[2] + "/" + fechaMod[1] + "/" + fechaMod[0]);

        ArrayList<Pedido> pedidos = listarPedidosFecha(date, date);
        ObservableList<Pedido> pedido = FXCollections.observableArrayList(pedidos);
        clienteColumn.setCellValueFactory(new PropertyValueFactory("cliente"));
        productosColumn.setCellValueFactory(new PropertyValueFactory("productos"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory("fecha"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory("estado"));
        tablaView.getStylesheets().add(this.getClass().getResource("/Estilo.css").toExternalForm());
        tablaView.setItems(pedido);
    }
}
