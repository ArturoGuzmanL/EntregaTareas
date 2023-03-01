package run;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import run.dao.AlumnoDAO;
import run.dao.ProfesorDAO;
import run.entity.AlumnoEntity;
import run.entity.EmpresaEntity;
import run.entity.ProfesorEntity;
import run.utils.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class DatosAlumnoController implements Initializable {


    @FXML
    private DialogPane dialogPaneDatosAlumno;
    @FXML
    private GridPane gridPaneDatosAlumno;
    @FXML
    private Separator separator11;
    @FXML
    private Separator separator111;
    @FXML
    private Separator separator1111;
    @FXML
    private Separator separator112;
    @FXML
    private Separator separator1121;
    @FXML
    private Region regionBorderDatos1;
    @FXML
    private Region regionBorderDatos4;
    @FXML
    private Region regionBorderDatos2;
    @FXML
    private Region regionBorderDatos3;
    @FXML
    private Text textHorasRestFCTDatosAlumno;
    @FXML
    private Text textHorasRealiFCTDatosAlumno;
    @FXML
    private Text textHorasRestResultFCTDatosAlumno;
    @FXML
    private Text textHorasRealiResultFCTDatosAlumno;
    @FXML
    private Text textHorasRealiResultDUALDatosAlumno;
    @FXML
    private Text textHorasRestResultDUALDatosAlumno;
    @FXML
    private Text textHorasRestDUALDatosAlumno;
    @FXML
    private Text textHorasRealiDUALDatosAlumno;
    @FXML
    private Text textInformacionTutor;
    @FXML
    private Text textTutorNombre;
    @FXML
    private Text textTutorNombreResult;
    @FXML
    private Text textTutorCorreo;
    @FXML
    private Text textTutorCorreoResult;
    @FXML
    private Text textInformacionEmpresa;
    @FXML
    private Text textCorreo;
    @FXML
    private Text textResponsable;
    @FXML
    private Text textNumTelf;
    @FXML
    private Text textCorreoResult;
    @FXML
    private Text textResponsableResult;
    @FXML
    private Text textNumTelfResult;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AlumnoDAO alumnoDAO = new AlumnoDAO();
        AlumnoEntity alumno = AlumnoDAO.traerAlumno(UserSession.getInstance().getId());

        int horasFCTrealizadas = alumnoDAO.horasFCTAlumno(alumno);
        int horasFCTrestantes = Integer.parseInt(alumno.getTotalHorasFct()) - horasFCTrealizadas;
        int horasDUALrealizadas = alumnoDAO.horasDualAlumno(alumno);
        int horasDUALrestantes = Integer.parseInt(alumno.getTotalHorasDual()) - horasDUALrealizadas;
        ProfesorEntity profesor = alumnoDAO.profesorAsignadoAlumno(alumno);
        EmpresaEntity empresa = alumnoDAO.empresaAsignadaAlumno(alumno);

        textHorasRealiResultFCTDatosAlumno.setText(String.valueOf(horasFCTrealizadas));
        textHorasRealiResultDUALDatosAlumno.setText(String.valueOf(horasDUALrealizadas));
        textHorasRestResultFCTDatosAlumno.setText(String.valueOf(horasFCTrestantes));
        textHorasRestResultDUALDatosAlumno.setText(String.valueOf(horasDUALrestantes));


        textTutorNombreResult.setText(profesor.getNombre());
        textTutorCorreoResult.setText(profesor.getCorreo());

        textCorreoResult.setText(empresa.getEmail());
        textResponsableResult.setText(empresa.getResponsableEmpresa());
        textNumTelfResult.setText(empresa.getTelefono());


    }
}
