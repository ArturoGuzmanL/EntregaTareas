package run;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import run.dao.AlumnoDAO;
import run.dao.ProfesorDAO;
import run.entity.PracticaEntity;

import java.io.IOException;
//TODO Main
public class MainApplication extends Application {



    @Override
    public void start(Stage stage) throws IOException {
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

    public static void main(String[] args) {
        launch();
    }
}