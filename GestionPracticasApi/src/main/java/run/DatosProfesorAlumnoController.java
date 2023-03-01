package run;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.hibernate.Session;
import org.hibernate.engine.spi.Status;
import org.hibernate.query.Query;
import run.dao.AlumnoDAO;
import run.entity.AlumnoEntity;
import run.entity.PracticaEntity;
import run.utils.HibernateUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DatosProfesorAlumnoController implements Initializable {

    AlumnoEntity alumno;
    @FXML
    private DialogPane dialogPaneDatosProfesor;
    @FXML
    private GridPane gridPane2DatosProfesor;
    @FXML
    private GridPane gridPaneDatosProfesor;
    @FXML
    private Text textHorasRestFCTDatosProfesor;
    @FXML
    private Text textHorasRealiFCTDatosProfesor;
    @FXML
    private Text textHorasRestResultFCTDatosProfesor;
    @FXML
    private Text textHorasRealiResultFCTDatosProfesor;
    @FXML
    private Separator separator11;
    @FXML
    private Separator separator11211;
    @FXML
    private Text textHorasRealiResultDUALDatosProfesor;
    @FXML
    private Text textHorasRestResultDUALDatosProfesor;
    @FXML
    private Text textHorasRestDUALDatosProfesor;
    @FXML
    private Text textHorasRealiDUALDatosProfesor;
    @FXML
    private Separator separator112;
    @FXML
    private Separator separator1121;
    @FXML
    private Text textNombreAlumnoDatosProfesor;
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

    public void initialize(URL location, ResourceBundle resources) {
    }


    public void setDatosAlumno(AlumnoEntity alumno) {
        this.alumno = alumno;
        int horasDualAlumno= AlumnoDAO.horasDualAlumno(alumno);
        textHorasRealiResultDUALDatosProfesor.setText(String.valueOf(horasDualAlumno));
        textHorasRestResultDUALDatosProfesor.setText(alumno.getTotalHorasDual());
        int horasFCTAlumno= AlumnoDAO.horasFCTAlumno(alumno);
        textHorasRealiResultFCTDatosProfesor.setText(String.valueOf(horasFCTAlumno));
        textHorasRestResultFCTDatosProfesor.setText(alumno.getTotalHorasFct());
        textNombreAlumnoDatosProfesor.setText(alumno.getNombre() + " " + alumno.getApellidos());

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("from PracticaEntity where alumnoAsociado = :id");
        query.setParameter("id", alumno.getId());
        ArrayList<PracticaEntity> practicas = new ArrayList();
        practicas.addAll(query.list());

        session.getTransaction().commit();
        session.close();

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
            return cell ;
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
            return cell ;
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
            return cell ;
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
            return cell ;
        });

        tablaView.setItems(practicasObserv);

        tablaView.getSelectionModel().clearAndSelect(0);
    }
}
