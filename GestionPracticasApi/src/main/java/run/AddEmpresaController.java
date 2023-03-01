package run;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import run.dao.ProfesorDAO;
import run.entity.AlumnoEntity;
import run.entity.EmpresaEntity;
import run.entity.ProfesorEntity;
import run.utils.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmpresaController implements Initializable {


    @FXML
    private TextField emailInput;

    @FXML
    private TextField nombreInput;

    @FXML
    private TextArea observacionesInput;

    @FXML
    private TextField responsableInput;

    @FXML
    private TextField telefonoInput;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public boolean procesarInsertarEmpresa() {
        boolean insertado = false;
        try {
            ProfesorDAO.crearInsertarEmpresa(nombreInput.getText(), Integer.valueOf(telefonoInput.getText()),emailInput.getText(), responsableInput.getText(), observacionesInput.getText());
            insertado = true;
        } catch (Exception e) {
            System.out.println("La empresa no se ha insertado correctamente");
            insertado = false;
        }
        return insertado;
    }

    public void verEditarEmpresa(EmpresaEntity emp) {

        nombreInput.setText(emp.getNombre());
        emailInput.setText(emp.getEmail());
        responsableInput.setText(emp.getResponsableEmpresa());
        observacionesInput.setText(emp.getObservaciones());
        telefonoInput.setText(emp.getTelefono());
    }

    public void editarEmpresa(EmpresaEntity emp) {

        emp.setNombre(nombreInput.getText());
        emp.setEmail(emailInput.getText());
        emp.setResponsableEmpresa(responsableInput.getText());
        emp.setObservaciones(observacionesInput.getText());
        emp.setTelefono(Integer.parseInt(telefonoInput.getText()));

        try {
            ProfesorDAO.modificarEmpresa(emp);
        } catch (Exception e) {
            System.out.println("Error al editar un Alumno");
        }
    }
}
