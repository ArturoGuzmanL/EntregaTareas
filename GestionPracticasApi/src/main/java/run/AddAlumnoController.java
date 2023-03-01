package run;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import run.dao.ProfesorDAO;
import run.entity.AlumnoEntity;
import run.entity.EmpresaEntity;
import run.entity.ProfesorEntity;
import run.utils.UserSession;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddAlumnoController implements Initializable {

    @FXML
    private TextField apellidos;

    @FXML
    private TextField dni;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<String> empresaComboBox;

    @FXML
    private DatePicker fechaNacimiento;

    @FXML
    private TextField horasFCT;

    @FXML
    private TextField nombre;

    @FXML
    private TextField telefono;

    @FXML
    private TextField horasDUAL;


    @FXML
    private TextArea textObservaciones;

    private ArrayList<EmpresaEntity> listado_empresas = new ArrayList<>();
    @FXML
    private DialogPane dialogPaneAddTarea;
    @FXML
    private BorderPane borderPaneAddTarea;
    @FXML
    private GridPane gridPaneAddTarea;
    @FXML
    private Text textActividadAddTarea;
    @FXML
    private Text textObservAddTarea;
    @FXML
    private Text textFechaAddTarea;
    @FXML
    private Text textHorasAddTarea;
    @FXML
    private Text textActividadAddTarea1;
    @FXML
    private Text horasFCTText;
    @FXML
    private Text horasDUALTxt;
    @FXML
    private Text textTipoAddTarea2;
    @FXML
    private Text textTipoAddTarea11;
    @FXML
    private Text textActividadAddTarea3;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listado_empresas = ProfesorDAO.listarAllEmpresas();
        listado_empresas.forEach(k -> {
            empresaComboBox.getItems().add(k.getNombre());
        });
    }

    public void procesarInsertarAlumno() {
        boolean insertado = false;
        ProfesorEntity profesor_generico = ProfesorDAO.infoProfesor(UserSession.getInstance().getId());
        String empresa_seleccionada = empresaComboBox.getValue();
        EmpresaEntity emp = new EmpresaEntity();
        for (EmpresaEntity empresa : listado_empresas) {
            if (empresa.getNombre().equals(empresa_seleccionada)) {
                emp = empresa;
                break;
            }
        }
        try {
            ProfesorDAO.crearInsertarAlumno(profesor_generico, emp, nombre.getText(), apellidos.getText(), "", dni.getText(), java.sql.Date.valueOf(fechaNacimiento.getValue()), email.getText(), Integer.parseInt(telefono.getText()), Integer.parseInt(horasDUAL.getText()), Integer.parseInt(horasFCT.getText()), textObservaciones.getText());
        } catch (Exception e) {
            System.out.println("El alumno no se ha insertado correctamente");
        }
    }

    public void verEditarAlumno(AlumnoEntity alumno_seleccionado) {

        nombre.setText(alumno_seleccionado.getNombre());
        apellidos.setText(alumno_seleccionado.getApellidos());
        dni.setText(alumno_seleccionado.getDni());
        fechaNacimiento.setValue(alumno_seleccionado.getFechaNacimiento().toLocalDate());
        email.setText(alumno_seleccionado.getEmail());
        telefono.setText(alumno_seleccionado.getTelefono());
        empresaComboBox.setValue(alumno_seleccionado.getEmpresaPracticasOBJ().getNombre());
        horasDUAL.setText(alumno_seleccionado.getTotalHorasDual());
        horasFCT.setText(alumno_seleccionado.getTotalHorasFct());
        textObservaciones.setText(alumno_seleccionado.getObservaciones());
    }

    public void editarAlumno(AlumnoEntity alumno_seleccionado) {

        alumno_seleccionado.setNombre(nombre.getText());
        alumno_seleccionado.setApellidos(apellidos.getText());
        alumno_seleccionado.setDni(dni.getText());
        alumno_seleccionado.setFechaNacimiento(java.sql.Date.valueOf(fechaNacimiento.getValue()));

        String empresa_seleccionada = empresaComboBox.getValue();
        EmpresaEntity emp = new EmpresaEntity();
        for (EmpresaEntity empresa : listado_empresas) {
            if (empresa.getNombre().equals(empresa_seleccionada)) {
                emp = empresa;
                break;
            }
        }
        ProfesorDAO.asignarEmpresaAlumno(alumno_seleccionado,emp);
        alumno_seleccionado.setEmail(email.getText());
        alumno_seleccionado.setProfesorAsignado(alumno_seleccionado.getProfesorAsignadoInt());
        alumno_seleccionado.setTelefono(Integer.parseInt(telefono.getText()));
        alumno_seleccionado.setTotalHorasDual(Integer.parseInt(horasDUAL.getText()));
        alumno_seleccionado.setTotalHorasFct(Integer.parseInt(horasFCT.getText()));
        alumno_seleccionado.setObservaciones(textObservaciones.getText());
        try {
            ProfesorDAO.modificarAlumno(alumno_seleccionado);
        } catch (Exception e) {
            System.out.println("Error al editar un Alumno");
        }
    }
}
