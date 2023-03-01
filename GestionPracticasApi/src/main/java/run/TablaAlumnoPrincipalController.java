package run;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;
import org.hibernate.engine.spi.Status;
import org.hibernate.query.Query;
import run.dao.AlumnoDAO;
import run.dao.ProfesorDAO;
import run.entity.AlumnoEntity;
import run.entity.PracticaEntity;
import javafx.util.Duration;
import run.utils.HibernateUtils;
import run.utils.UserSession;

import static run.dao.ProfesorDAO.listarAllAlumnos;
import static run.dao.ProfesorDAO.listarAllPracticas;

public class TablaAlumnoPrincipalController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    Parent root;
    Stage stage;

    @FXML
    private ToolBar toolbarMain;
    @FXML
    private Button addButton;
    @FXML
    private Button eliminarButton;
    @FXML
    private Button editarButton;
    @FXML
    private Button datosButton;
    @FXML
    private BorderPane fondoPrincipal;
    @FXML
    private TableView tablaView;
    @FXML
    private TableColumn fechaColumn;
    @FXML
    private TableColumn actividadColumn;
    @FXML
    private TableColumn observacionesColumn;
    @FXML
    private TableColumn horasColumn;
    @FXML
    private TableColumn tipoColumn;
    @FXML
    private GridPane textoTop;
    @FXML
    private Text nombreAlumnoText;
    @FXML
    private Text fechaText;
    @FXML
    private ImageView imageViewClose;
    @FXML
    private ImageView imageViewMinimize;
    @FXML
    private ImageView imageViewMaxi;
    @FXML
    private ButtonBar regionButtonBar;
    @FXML
    private ButtonBar cerrarSesionButtonBar;
    @FXML
    private Button cerrarSesionButton;
    @FXML
    private Region RegionBorder2;
    @FXML
    private Region RegionBorder1;
    @FXML
    private Region RegionBorder3;
    @FXML
    private Region RegionBorder4;

    private AlumnoEntity actual_user = null;

    @FXML
    public void addButtonOnAction(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/run/AddTareaAlumno-view.fxml"));
        DialogPane dialogPane = fxmlLoader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Añadir actividad");
        dialog.setResizable(false);

        ButtonBar buttonBar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().get(0).setStyle("-fx-background-color: #151928; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");
        buttonBar.getButtons().get(1).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddTareaAlumnoController controller = fxmlLoader.getController();
            int resultado = controller.insertarTarea(actual_user);

            if (resultado == 1) {
                toastMessage(0);
            } else if (resultado == -1) {
                toastMessage(-2);
            }
            // Recargar Lista
            ArrayList<PracticaEntity> practicas = new ArrayList<>();
            practicas.addAll(listarAllPracticas(actual_user.getId()));
            ObservableList<PracticaEntity> observableListaAlumnos = FXCollections.observableArrayList(practicas);
            tablaView.getItems().clear();
            tablaView.setItems(observableListaAlumnos);
            tablaView.getSelectionModel().clearAndSelect(0);

        }
    }

    @FXML
    public void eliminarButtonOnAction(ActionEvent actionEvent) {

        if (hayTareaSeleccionada()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("¿Estás seguro de que quieres eliminar esta tarea?");
            alert.setContentText("Esta acción no se puede deshacer.");
            alert.setResizable(false);
            alert.setGraphic(null);

            alert.initStyle(StageStyle.UNDECORATED);
            DialogPane alerta = alert.getDialogPane();
            alerta.getStylesheets().add(TablaAlumnoPrincipalController.class.getResource("/run/PrincipalCSS.css").toExternalForm());
            alerta.getStyleClass().add("alertaDialog");

            ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
            buttonBar.getButtons().get(0).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");
            buttonBar.getButtons().get(1).setStyle("-fx-background-color: #151928; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                PracticaEntity practicaEntity = (PracticaEntity) tablaView.getSelectionModel().getSelectedItem();
                AlumnoDAO alumnoDAO = new AlumnoDAO();
                alumnoDAO.borrarPracticaAlumno(practicaEntity);
                toastMessage(1);

                // Recargar Lista
                ArrayList<PracticaEntity> practicas = new ArrayList<>();
                practicas.addAll(listarAllPracticas(actual_user.getId()));
                ObservableList<PracticaEntity> observableListaAlumnos = FXCollections.observableArrayList(practicas);
                tablaView.getItems().clear();
                tablaView.setItems(observableListaAlumnos);
                tablaView.getSelectionModel().clearAndSelect(0);
            }
        } else {
            toastMessage(-1);
        }
    }

    @FXML
    public void editarButtonOnAction(ActionEvent actionEvent) throws IOException {

        if (hayTareaSeleccionada()) {

            PracticaEntity practicaEntity = (PracticaEntity) tablaView.getSelectionModel().getSelectedItem();

            if (practicaEntity != null) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/run/AddTareaAlumno-view.fxml"));
                DialogPane dialogPane = fxmlLoader.load();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.setDialogPane(dialogPane);
                dialog.setResizable(false);

                ButtonBar buttonBar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
                buttonBar.getButtons().get(0).setStyle("-fx-background-color: #151928; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");
                buttonBar.getButtons().get(1).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");

                AddTareaAlumnoController addTareaAlumnoController = fxmlLoader.getController();
                addTareaAlumnoController.editarPractica(practicaEntity);

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    addTareaAlumnoController.updatePractica(practicaEntity);
                    toastMessage(2);
                    // Recargar Lista
                    ArrayList<PracticaEntity> practicas = new ArrayList<>();
                    practicas.addAll(listarAllPracticas(actual_user.getId()));
                    ObservableList<PracticaEntity> observableListaAlumnos = FXCollections.observableArrayList(practicas);
                    tablaView.getItems().clear();
                    tablaView.setItems(observableListaAlumnos);
                    tablaView.getSelectionModel().clearAndSelect(0);
                }
            }

        } else {
            toastMessage(-1);
        }
    }

    @FXML
    public void datosButtonOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TablaAlumnoPrincipalController.class.getResource("/run/DatosAlumno-view.fxml"));
        DialogPane dialogPane = fxmlLoader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.getDialogPane().setPrefSize(950, 700);
        dialog.setTitle("Datos de la empresa");
        dialog.setResizable(false);


        ButtonBar buttonBar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().get(0).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");

        DatosAlumnoController controller = fxmlLoader.getController();
        Optional<ButtonType> result = dialog.showAndWait();
    }

    @FXML
    public void imageViewCloseOnAction() {
        System.exit(0);
    }

    @FXML
    public void imageViewMinimizeOnAction() {
        stage.setIconified(true);
    }

    @FXML
    public void imageViewMaxiOnAction(Event event) {
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        regionButtonBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        regionButtonBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        actual_user = ProfesorDAO.infoAlumno(2);
        ArrayList<PracticaEntity> practicas = AlumnoDAO.traerPracticasAlumno(actual_user);

        ObservableList<PracticaEntity> practicasObserv = FXCollections.observableArrayList(practicas);

        fechaColumn.setCellValueFactory(new PropertyValueFactory("fecha"));
        actividadColumn.setCellValueFactory(new PropertyValueFactory("actividad"));
        actividadColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setAlignment(Pos.CENTER);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(actividadColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        observacionesColumn.setCellValueFactory(new PropertyValueFactory("observaciones"));
        observacionesColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setAlignment(Pos.CENTER);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(observacionesColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        horasColumn.setCellValueFactory(new PropertyValueFactory("horas"));
        horasColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setAlignment(Pos.CENTER);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(horasColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        tipoColumn.setCellValueFactory(new PropertyValueFactory("tipo"));
        tipoColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(tipoColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        tablaView.setItems(practicasObserv);

        tablaView.getSelectionModel().clearAndSelect(0);


        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        String fecha = date.toString();
        String[] fechaMod = fecha.split("-");
        fechaText.setText("Fecha: " + fechaMod[2] + "/" + fechaMod[1] + "/" + fechaMod[0]);
        nombreAlumnoText.setText(actual_user.getNombre() + " " + actual_user.getApellidos());
    }

    @FXML
    public void cerrarSesionButtonOnAction(ActionEvent actionEvent) throws IOException {

        this.stage.close();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/run/LoginAlumnoMain-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 650, 420);
        stage.initStyle(StageStyle.TRANSPARENT);
        LoginAlumnoMainController controller = fxmlLoader.getController();
        controller.setRoot(root, stage);
        stage.setResizable(false);
        String css = this.getClass().getResource("/run/PrincipalCSS.css").toExternalForm();
        stage.setScene(scene);
        stage.show();

    }


    public void setRoot(Parent root, Stage stage) {
        this.root = root;
        this.stage = stage;
    }

    //para saber si hay una tarea seleccionada en la tabla
    public boolean hayTareaSeleccionada() {
        return tablaView.getSelectionModel().getSelectedItem() != null;
    }

    //mensaje tipo pop up que aparece en la parte inferior de la pantalla
    private void toastMessage(int estado) {
        Stage window = new Stage();
        window.setX((stage.getX() + 50) + (stage.getWidth() / 2) - 200);
        window.setY(stage.getY() + stage.getHeight() / 2 + stage.getHeight() / 3);
        window.initStyle(StageStyle.TRANSPARENT);
        Text string = new Text();
        if (estado == 0) {
            string = new Text("Tarea añadida correctamente");
        } else if (estado == 1) {
            string = new Text("Tarea eliminada correctamente");
        } else if (estado == 2) {
            string = new Text("Tarea editada correctamente");
        } else if (estado == -1) {
            string = new Text("Error al actualizar los Datos");
        }
        string.setFill(Color.WHITE);
        string.setStyle("-fx-font-size: 17px;");
        string.setStrokeWidth(1.5);
        VBox layout = new VBox(10, string);
        layout.setPadding(new Insets(3));
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), new CornerRadii(3), Insets.EMPTY)));
        window.setScene(new Scene(layout, Color.TRANSPARENT));
        window.setAlwaysOnTop(true);
        window.setOpacity(0.95);
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, evt -> window.show(), new KeyValue(layout.opacityProperty(), 0)), new KeyFrame(Duration.millis(200), new KeyValue(layout.opacityProperty(), 1.0)), new KeyFrame(Duration.millis(800), new KeyValue(layout.opacityProperty(), 1.0)), new KeyFrame(Duration.millis(1000), new KeyValue(layout.opacityProperty(), 0.2)));
        timeline.setOnFinished(evt -> window.close());
        timeline.play();
    }
}
