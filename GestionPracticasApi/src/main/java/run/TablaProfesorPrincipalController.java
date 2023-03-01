package run;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javafx.util.Duration;
import org.hibernate.Session;
import run.dao.AlumnoDAO;
import run.dao.ProfesorDAO;
import run.entity.AlumnoEntity;
import run.entity.EmpresaEntity;
import run.entity.PracticaEntity;
import run.model.Actividad;
import run.utils.HibernateUtils;
import org.hibernate.engine.spi.Status;
import run.utils.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static run.AddAlumnoController.*;
import static run.dao.ProfesorDAO.*;

public class TablaProfesorPrincipalController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    Parent root;
    Stage stage;

    @FXML
    private ButtonBar regionButtonBar;
    @FXML
    private ImageView imageViewMinimize;
    @FXML
    private ImageView imageViewMaxi;
    @FXML
    private ImageView imageViewClose;
    @FXML
    private TabPane tabPaneProfesor;
    @FXML
    private Tab tabAlumnoProfesor;
    @FXML
    private BorderPane fondoPrincipal;
    @FXML
    private TableView tablaView; //Tabla Alumnos
    @FXML
    private TableColumn dniProfesorColumn;
    @FXML
    private TableColumn nombreProfesorColumn;
    @FXML
    private TableColumn correoProfesorColumn;
    @FXML
    private TableColumn telefonoProfesorColumn;
    @FXML
    private TableColumn empresaAsigProfesorColumn;
    @FXML
    private GridPane textoTop;
    @FXML
    private Text fechaTextProfesor;
    @FXML
    private ToolBar toolbarMain;
    @FXML
    private Button addButtonProfesor;
    @FXML
    private Button eliminarButtonProfesor;
    @FXML
    private Button editarButtonProfesor;
    @FXML
    private Tab tabEmpresaProfesor;
    @FXML
    private BorderPane fondoPrincipal1;
    @FXML
    private TableView tablaView1; //Tabla Empresas
    @FXML
    private TableColumn nombreEmpresaColumn;
    @FXML
    private TableColumn responsableEmpresaColumn;
    @FXML
    private TableColumn tipoColumn1;
    @FXML
    private GridPane textoTop1;
    @FXML
    private Text fechaTextEmpresa;
    @FXML
    private ToolBar toolbarMain1;
    @FXML
    private Button addButtonEmpresa;
    @FXML
    private Region RegionBorder2;
    @FXML
    private Region RegionBorder1;
    @FXML
    private Region RegionBorder3;
    @FXML
    private Region RegionBorder4;
    @FXML
    private TableColumn observacionesEmpresaColumn;
    @FXML
    private TableColumn correoEmpresaColumn;
    @FXML
    private TableColumn telefonoEmpresaColumn;
    @FXML
    private ButtonBar profesorButtonBar;
    @FXML
    private Button cerrarSesionButtonProfesor;
    @FXML
    private ButtonBar empresaButtonBar;
    @FXML
    private Button cerrarSesionButtonEmpresa;
    @FXML
    private Button datosAlumnoButtonProfesor;
    @FXML
    private Button editarButtonEmpresa;

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

    @FXML
    public void addButtonProfesorOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/run/AddAlumno-view.fxml"));
        DialogPane dialogPane = fxmlLoader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Añadir Alumno");
        dialog.setResizable(false);

        ButtonBar buttonBar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().get(0).setStyle("-fx-background-color: #151928; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");
        buttonBar.getButtons().get(1).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddAlumnoController controller = fxmlLoader.getController();
            controller.procesarInsertarAlumno();
            toastMessage(1);
            refresh();
        } else {
            toastMessage(-1);
        }
    }

    @FXML
    public void eliminarButtonProfesorOnAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("¿Estás seguro de que quieres eliminar este alumno?");
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
            AlumnoEntity alumno = (AlumnoEntity) tablaView.getSelectionModel().getSelectedItem();
            ArrayList<PracticaEntity> practicas_alumno = AlumnoDAO.traerPracticasAlumno(alumno);
            for (Integer i = 0; i < practicas_alumno.size(); i++) {
                AlumnoDAO.borrarPracticaAlumno(practicas_alumno.get(i));
            }
            ProfesorDAO.deleteAlumno(alumno);
            toastMessage(0);
            refresh();
        } else {
            toastMessage(-1);
        }
    }

    @FXML
    public void editarButtonProfesorOnAction(ActionEvent actionEvent) throws IOException {

//TODO editar Alumno
        if (hayAlumnoSeleccionado()) {

            AlumnoEntity alumno = (AlumnoEntity) tablaView.getSelectionModel().getSelectedItem();

            if (alumno != null) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/run/AddAlumno-view.fxml"));
                DialogPane dialogPane = fxmlLoader.load();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.setDialogPane(dialogPane);
                dialog.setResizable(false);

                ButtonBar buttonBar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
                buttonBar.getButtons().get(0).setStyle("-fx-background-color: #151928; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");
                buttonBar.getButtons().get(1).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");

                AddAlumnoController addAlumnoController = fxmlLoader.getController();
                addAlumnoController.verEditarAlumno(alumno);

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    addAlumnoController.editarAlumno(alumno);
                    toastMessage(2);

                    refresh();
                }
            }

        } else {
            toastMessage(-1);
        }
    }

    @FXML
    public void addButtonEmpresaOnAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/run/AddEmpresa-view.fxml"));
        DialogPane dialogPane = fxmlLoader.load();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setDialogPane(dialogPane);
        dialog.setTitle("Añadir Empresa");
        dialog.setResizable(false);

        ButtonBar buttonBar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().get(0).setStyle("-fx-background-color: #151928; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");
        buttonBar.getButtons().get(1).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            AddEmpresaController controller = fxmlLoader.getController();
            controller.procesarInsertarEmpresa();
            toastMessage(4);

            refresh();
        } else {
            toastMessage(-1);
        }
    }

    @FXML
    public void editarButtonEmpresaOnAction(ActionEvent actionEvent) throws IOException {
        if (hayEmpresaSeleccionada()) {

            EmpresaEntity emp = (EmpresaEntity) tablaView1.getSelectionModel().getSelectedItem();

            if (emp != null) {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/run/AddEmpresa-view.fxml"));
                DialogPane dialogPane = fxmlLoader.load();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.setDialogPane(dialogPane);
                dialog.setResizable(false);

                ButtonBar buttonBar = (ButtonBar) dialog.getDialogPane().lookup(".button-bar");
                buttonBar.getButtons().get(0).setStyle("-fx-background-color: #151928; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");
                buttonBar.getButtons().get(1).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");

                AddEmpresaController addEmpresaController = fxmlLoader.getController();
                addEmpresaController.verEditarEmpresa(emp);

                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    addEmpresaController.editarEmpresa(emp);
                    toastMessage(5);
                    refresh();
                }
            }

        } else {
            toastMessage(-1);
        }
    }

    @FXML
    public void cerrarSesionEmpresaOnAction(ActionEvent actionEvent) throws IOException {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        regionButtonBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        regionButtonBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);

        String fecha = date.toString();
        String[] fechaMod = fecha.split("-");
        fechaTextProfesor.setText("Fecha: " + fechaMod[2] + "/" + fechaMod[1] + "/" + fechaMod[0]);
        fechaTextEmpresa.setText("Fecha: " + fechaMod[2] + "/" + fechaMod[1] + "/" + fechaMod[0]);

        //Cargar de Alumnos en tableView
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        ArrayList<AlumnoEntity> alumnosAsociados = new ArrayList<>();
        alumnosAsociados.addAll(listarAllAlumnos(UserSession.getInstance().getId()));
        ObservableList<AlumnoEntity> observableListaAlumnos = FXCollections.observableArrayList(alumnosAsociados);

        ArrayList<EmpresaEntity> empresas = new ArrayList<>();
        empresas.addAll(listarAllEmpresas());
        ObservableList<EmpresaEntity> observableEmpresas = FXCollections.observableArrayList(empresas);


        dniProfesorColumn.setCellValueFactory(new PropertyValueFactory<>("dni"));
        dniProfesorColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(dniProfesorColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        nombreProfesorColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreProfesorColumn.setCellFactory(tc -> {
            TableCell<AlumnoEntity, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(nombreProfesorColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        correoProfesorColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        correoProfesorColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(correoProfesorColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        telefonoProfesorColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        telefonoProfesorColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(telefonoProfesorColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        empresaAsigProfesorColumn.setCellValueFactory(new PropertyValueFactory<>("empresaPracticas"));
        empresaAsigProfesorColumn.setCellFactory(tc -> {

            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(empresaAsigProfesorColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        nombreEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        nombreEmpresaColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(nombreEmpresaColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        responsableEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("ResponsableEmpresa"));
        responsableEmpresaColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(responsableEmpresaColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        observacionesEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        observacionesEmpresaColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(observacionesEmpresaColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        correoEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        correoEmpresaColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(correoEmpresaColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        telefonoEmpresaColumn.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        telefonoEmpresaColumn.setCellFactory(tc -> {
            TableCell<Status, String> cell = new TableCell<>();
            Text text = new Text();
            text.setFill(Color.WHITE);
            text.setTextAlignment(TextAlignment.CENTER);
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(telefonoEmpresaColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        tablaView.getItems().clear();
        tablaView.setItems(observableListaAlumnos);
        tablaView.getSelectionModel().clearAndSelect(0);

        tablaView1.getItems().clear();
        tablaView1.setItems(observableEmpresas);
        tablaView1.getSelectionModel().clearAndSelect(0);
    }


    public void setRoot(Parent root, Stage stage) {
        this.root = root;
        this.stage = stage;
    }

    public boolean hayEntidadSeleccionada() {
        return tablaView.getSelectionModel().getSelectedItem() != null;
    }

    private void toastMessage(int estado) {
        Stage window = new Stage();
        window.setX((stage.getX() + 50) + (stage.getWidth() / 2) - 200);
        window.setY(stage.getY() + stage.getHeight() / 2 + stage.getHeight() / 3);
        window.initStyle(StageStyle.TRANSPARENT);
        Text string = new Text();
        if (estado == 0) {
            string = new Text("El Alumno ha sido eliminado");
        } else if (estado == 1) {
            string = new Text("El Alumno ha sido añadido");
        } else if (estado == 2) {
            string = new Text("El Alumno ha sido editado");
        } else if (estado == 3) {
            string = new Text("La Empresa ha sido eliminada");
        } else if (estado == 4) {
            string = new Text("La Empresa ha sido añadida");
        } else if (estado == 5) {
            string = new Text("La Empresa ha sido Editada");
        } else if (estado == -1) {
            string = new Text("No se han producido cambios");
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


    @FXML
    public void datosAlumnoButtonProfesorOnAction(ActionEvent actionEvent) {
        if (hayEntidadSeleccionada()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/run/DatosProfesorAlumno-view.fxml"));
                DialogPane dialogPane = loader.load();
                DatosProfesorAlumnoController controller = loader.getController();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);
                dialog.initStyle(StageStyle.UNDECORATED);
                dialog.getDialogPane().setPrefSize(950, 700);
                dialog.setResizable(false);

                ButtonBar buttonBar = (ButtonBar)dialog.getDialogPane().lookup(".button-bar");
                buttonBar.getButtons().get(0).setStyle("-fx-background-color: #3C58FA; -fx-text-fill: white; -fx-border-color: black; -fx-border-width: 1px;");

                AlumnoEntity alumno = (AlumnoEntity) tablaView.getSelectionModel().getSelectedItem();

                if (alumno != null) {
                    controller.setDatosAlumno(alumno);
                }

                dialog.showAndWait();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    //para saber si hay un alumno seleccionada en la tabla
    public boolean hayAlumnoSeleccionado() {
        return tablaView.getSelectionModel().getSelectedItem() != null;
    }

    //para saber si hay una empresa seleccionada en la tabla
    public boolean hayEmpresaSeleccionada() {
        return tablaView1.getSelectionModel().getSelectedItem() != null;
    }

    public void refresh() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        ArrayList<AlumnoEntity> alumnosAsociados = new ArrayList<>();
        alumnosAsociados.addAll(listarAllAlumnos(UserSession.getInstance().getId()));
        ObservableList<AlumnoEntity> observableListaAlumnos = FXCollections.observableArrayList(alumnosAsociados);

        ArrayList<EmpresaEntity> empresas = new ArrayList<>();
        empresas.addAll(listarAllEmpresas());
        ObservableList<EmpresaEntity> observableEmpresas = FXCollections.observableArrayList(empresas);

        tablaView.getItems().clear();
        tablaView.setItems(observableListaAlumnos);
        tablaView.getSelectionModel().clearAndSelect(0);
        tablaView.refresh();
        tablaView1.getItems().clear();
        tablaView1.setItems(observableEmpresas);
        tablaView1.getSelectionModel().clearAndSelect(0);
        tablaView1.refresh();
    }
}
