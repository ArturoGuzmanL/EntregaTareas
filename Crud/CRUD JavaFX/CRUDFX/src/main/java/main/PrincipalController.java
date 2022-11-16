package main;

import Extra.Toast;
import controller.Conexion;
import controller.PedidoDAO;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Pedido;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.PedidoDAO.listarAllPedidosOrdenados;
import static controller.PedidoDAO.listarPedidosFecha;

import java.sql.Connection;


public class PrincipalController implements Initializable {
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
    @FXML
    private BorderPane fondoPrincipal;
    @FXML
    private TableColumn idColumn;
    @FXML
    private GridPane textoTop;

    private static final Connection con = Conexion.getCon();
    private Stage stage;
    @FXML
    private ToolBar toolBarra;
    @FXML
    private Button addButton;
    @FXML
    private Button eliminarButton;
    @FXML
    private Button EditarButton;

    @FXML
    public void comandasButtonOnAction(ActionEvent event) {
        ArrayList<Pedido> pedidos = listarAllPedidosOrdenados();
        ObservableList<Pedido> pedido = FXCollections.observableArrayList(pedidos);
        tablaView.getItems().clear();
        tablaView.setItems(pedido);
    }

    @FXML
    public void addButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("/AddDialog-view.fxml"));
        DialogPane dialogPane = fxmlLoader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Añadir pedido");
        dialog.setResizable(false);

        ButtonBar buttonBar = (ButtonBar)dialog.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().get(0).setStyle("-fx-background-color: #5ac8ea; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1px;");
        buttonBar.getButtons().get(1).setStyle("-fx-background-color: #cc2323; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1px;");

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddDialogViewController controller = fxmlLoader.getController();
            Pedido addPedido = controller.procesarResultados();
            if (addPedido != null) {
                PedidoDAO.insertarPedido(addPedido);
                comandasButton.fire();
            }
        }
    }

    @FXML
    public void eliminarButtonOnAction() {
        Pedido pedido = (Pedido) tablaView.getSelectionModel().getSelectedItem();
        if (pedido != null) {
            int index = tablaView.getSelectionModel().getSelectedIndex();
            Object item = tablaView.getItems().get(index);
            TableColumn colId = (TableColumn) tablaView.getColumns().get(0);
            String id = colId.getCellObservableValue(item).getValue().toString();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar pedido");
            alert.setHeaderText("¿Estás seguro de que quieres eliminar el pedido?");
            alert.setContentText("El pedido se eliminará permanentemente.");
            alert.setResizable(false);

            DialogPane alerta = alert.getDialogPane();
            alerta.getStylesheets().add(PrincipalApplication.class.getResource("/PrincipalCSS.css").toExternalForm());
            alerta.getStyleClass().add("alertaDialog");

            ButtonBar buttonBar = (ButtonBar)alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().get(0).setStyle("-fx-background-color: #5ac8ea; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1px;");
            buttonBar.getButtons().get(1).setStyle("-fx-background-color: #cc2323; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1px;");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                PedidoDAO.eliminarPedido(pedido.getIdentificacion());
                comandasButton.fire();
                toastMessage(id, 3);
            }
        } else {
            toastMessage("", -1);
        }
    }

    @FXML
    public void editarButtonOnAction() {
        Pedido pedido = (Pedido) tablaView.getSelectionModel().getSelectedItem();
        if (pedido != null) {
            int index = tablaView.getSelectionModel().getSelectedIndex();
            Object item = tablaView.getItems().get(index);
            TableColumn colId = (TableColumn) tablaView.getColumns().get(0);
            String id = colId.getCellObservableValue(item).getValue().toString();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("/AddDialog-view.fxml"));
                DialogPane dialogPane = fxmlLoader.load();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);
                dialog.setTitle("Editar pedido");
                dialog.setResizable(false);

                ButtonBar buttonBar = (ButtonBar)dialog.getDialogPane().lookup(".button-bar");
                buttonBar.getButtons().get(0).setStyle("-fx-background-color: #5ac8ea; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1px;");
                buttonBar.getButtons().get(1).setStyle("-fx-background-color: #cc2323; -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 1px;");

                AddDialogViewController controller = fxmlLoader.getController();
                controller.cargarDatos(pedido);

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Pedido editPedido = controller.procesarResultados();
                    if (controller.isDiferente(pedido,editPedido)) {
                        editPedido.setIdentificacion(pedido.getIdentificacion());
                        PedidoDAO.editarPedido(editPedido);
                        comandasButton.fire();
                        toastMessage(id, 4);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            toastMessage("", -1);
        }
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("identificacion"));
        clienteColumn.setCellValueFactory(new PropertyValueFactory("cliente"));
        productosColumn.setCellValueFactory(new PropertyValueFactory("productos"));
        fechaColumn.setCellValueFactory(new PropertyValueFactory("fecha"));
        estadoColumn.setCellValueFactory(new PropertyValueFactory("estado"));
        tablaView.getStylesheets().add(this.getClass().getResource("/PrincipalCSS.css").toExternalForm());
        tablaView.setItems(pedido);

        tablaView.getSelectionModel().clearAndSelect(0);
    }

    /**
     private void TablaMousePressed() {
     tablaView.setOnMousePressed(e ->{
     if (e.getClickCount() == 1) {
     int index = tablaView.getSelectionModel().getSelectedIndex();
     tablaView.getSelectionModel().select(index);
     tablaView.getFocusModel().focus(index);
     }

     if (e.getClickCount() == 2 && e.isPrimaryButtonDown() ){
     int index = tablaView.getSelectionModel().getSelectedIndex();
     Object item = tablaView.getItems().get(index);
     TableColumn colId = (TableColumn) tablaView.getColumns().get(0);
     String id = colId.getCellObservableValue(item).getValue().toString();
     TableColumn colEstado = (TableColumn) tablaView.getColumns().get(4);
     String estado = colEstado.getCellObservableValue(item).getValue().toString();
     //update estado where id = id
     if (estado.equals("PENDIENTE")) {
     String sql_query = "UPDATE pedido SET estado = 'RECOGIDO' WHERE identificador = ?;";
     try (PreparedStatement pst = con.prepareStatement(sql_query)) {
     pst.setString(1, id);
     pst.executeUpdate();
     comandasButton.fire();
     toastMessage(id, 0);
     } catch (Exception ex) {
     throw new RuntimeException(ex);
     }
     }else {
     String sql_query = "UPDATE pedido SET estado = 'PENDIENTE' WHERE identificador = ?;";
     try (PreparedStatement pst = con.prepareStatement(sql_query)) {
     pst.setString(1, id);
     pst.executeUpdate();
     comandasButton.fire();
     toastMessage(id, 1);
     } catch (Exception ex) {
     throw new RuntimeException(ex);
     }
     }

     }
     });
     }
     */

    private void toastMessage(String id, int estado) {
        Stage window = new Stage();
        window.setX((stage.getX() + 50) + (stage.getWidth() / 2) - 150);
        window.setY(stage.getY() + stage.getHeight() / 2 + stage.getHeight() / 3);
        window.initStyle(StageStyle.TRANSPARENT);
        Text string = new Text();
        if (estado == 0) {
            string = new Text("El pedido " + id + " ha pasado a recogido");
        }else if (estado == 1) {
            string = new Text("El pedido " + id + " ha pasado a pendiente");
        }else if (estado == -1) {
            string = new Text("No has seleccionado ningún pedido");
        }else if (estado == 3) {
            string = new Text("El pedido " + id + " ha sido eliminado");
        }else if (estado == 4) {
            string = new Text("El pedido " + id + " ha sido editado");
        }
        string.setFill(Color.WHITE);
        string.setStyle("-fx-font-size: 17px;");
        string.setStrokeWidth(1.5);
        VBox layout = new VBox(10, string);
        layout.setPadding(new Insets(3));
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(100,100,100), new CornerRadii(3), Insets.EMPTY)));
        window.setScene(new Scene(layout, Color.TRANSPARENT));
        window.setAlwaysOnTop(true);
        window.setOpacity(0.95);
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, evt -> window.show(), new KeyValue(layout.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(200), new KeyValue(layout.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(800), new KeyValue(layout.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(1000), new KeyValue(layout.opacityProperty(), 0.2)));
        timeline.setOnFinished(evt -> window.close());
        timeline.play();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

