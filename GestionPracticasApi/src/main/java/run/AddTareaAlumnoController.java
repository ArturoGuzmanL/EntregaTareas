package run;

import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import run.dao.AlumnoDAO;
import run.entity.AlumnoEntity;
import run.entity.PracticaEntity;
import run.utils.UserSession;

public class AddTareaAlumnoController implements Initializable {

    @FXML
    private DialogPane dialogPaneAddTarea;
    @FXML
    private BorderPane borderPaneAddTarea;
    @FXML
    private GridPane gridPaneAddTarea;
    @FXML
    private DatePicker datePickerAddTarea;
    @FXML
    private Text textActividadAddTarea;
    @FXML
    private Text textObservAddTarea;
    @FXML
    private TextField textFieldActividadAddTarea;
    @FXML
    private TextField textFieldObservAddTarea;
    @FXML
    private Text textFechaAddTarea;
    @FXML
    private Text textHorasAddTarea;
    @FXML
    private TextField textFieldHorasAddTarea;
    @FXML
    private ComboBox comboBoxAddTarea;
    @FXML
    private Text textTipoAddTarea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxAddTarea.getItems().addAll("DUAL", "FCT");
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        datePickerAddTarea.setValue(date.toLocalDate());
    }

    public int insertarTarea(AlumnoEntity alumno) {
        //el if es para que no se pueda añadir una tarea sin haber rellenado todos los campos
        if (datePickerAddTarea == null || textFieldActividadAddTarea.getText().isEmpty() ||
                textFieldObservAddTarea.getText().isEmpty() || textFieldHorasAddTarea.getText().isEmpty() ||
                comboBoxAddTarea.getValue() == null) {
            return -1;
        } else {
            PracticaEntity practica = new PracticaEntity();
            practica.setFecha(Date.valueOf(datePickerAddTarea.getValue()));
            practica.setActividad(textFieldActividadAddTarea.getText());
            practica.setAlumnoByAlumnoAsociado(alumno);
            practica.setObservaciones(textFieldObservAddTarea.getText());
            practica.setTotalHorasDia(Integer.parseInt(textFieldHorasAddTarea.getText()));
            practica.setTipoPractica(comboBoxAddTarea.getValue().toString());

            AlumnoDAO.insertarPracticaAlumno(practica);

            return 1;
        }
    }


    public void editarPractica(PracticaEntity practicaEntity) {

        textFieldActividadAddTarea.setText(practicaEntity.getActividad());
        textFieldObservAddTarea.setText(practicaEntity.getObservaciones());
        textFieldHorasAddTarea.setText(String.valueOf(practicaEntity.getHoras()));
        if (practicaEntity.getTipo().equals("DUAL")) {
            comboBoxAddTarea.setValue("DUAL");
        } else {
            comboBoxAddTarea.setValue("FCT");
        }
        datePickerAddTarea.setValue(practicaEntity.getFecha().toLocalDate());

    }

    public void updatePractica(PracticaEntity practicaEntity) {
        practicaEntity.setActividad(textFieldActividadAddTarea.getText());
        practicaEntity.setObservaciones(textFieldObservAddTarea.getText());
        practicaEntity.setTotalHorasDia(Integer.valueOf(textFieldHorasAddTarea.getText()));
        practicaEntity.setTipoPractica(comboBoxAddTarea.getValue().toString());

        try {
            AlumnoDAO.editarPracticaAlumno(practicaEntity);
        }catch (Exception e){
            System.out.println("Problema al editar la Practica");
        }
    }
}
