package run;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import run.utils.Login;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static run.utils.UserSession.Rol.ALUMNO;


public class LoginAlumnoMainController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    Parent root;
    Stage stage;

    @FXML
    private AnchorPane mainAnchorLogin;
    @FXML
    private Label labelLog;
    @FXML
    private Label labelIn;
    @FXML
    private Button buttonProfesorLogin;
    @FXML
    private Label labelProfesor;
    @FXML
    private TextField textFieldMail;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private ImageView imageViewGmail;
    @FXML
    private ImageView imageViewPassword;
    @FXML
    private Button buttonIniciarSesion;
    @FXML
    private ImageView imageViewClose;
    @FXML
    private Region regionDragMouse;
    @FXML
    private ImageView imageViewMinimize;

    @FXML
    public void imageViewCloseOnAction() {
        System.exit(0);
    }

    @FXML
    public void imageViewMinimizeOnAction() {
        stage.setIconified(true);
    }

    @FXML
    private Label txtErrorText;
    @FXML
    public void buttonProfesorLoginOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/run/LoginProfesorMain-view.fxml"));
        root = fxmlLoader.load();
        LoginProfesorMainController controller = fxmlLoader.getController();
        controller.setRoot(root, stage);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 650, 420));
        stage.show();
    }

    @FXML
    public void buttonIniciarSesionOnAction(ActionEvent event) throws IOException {
        if (loginAlumnoAction()) {
            txtErrorText.setVisible(false);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/run/TablaAlumnoPrincipal-view.fxml"));
            root = fxmlLoader.load();
            TablaAlumnoPrincipalController controller = fxmlLoader.getController();
            controller.setRoot(root, stage);
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 520));
            stage.setResizable(true);
            stage.setMinHeight(520);
            stage.setMinWidth(700);
            stage.centerOnScreen();
            ResizeHelper.addResizeListener(stage);
            stage.show();
            toastMessage(0);
        } else {
           txtErrorText.setVisible(true);
           textFieldMail.setText("");
           textFieldPassword.setText("");
        }
    }

    @Deprecated
    public void initialize (URL url, ResourceBundle resourceBundle) {
        txtErrorText.setVisible(false);

        regionDragMouse.setOnMousePressed(event -> {

            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        regionDragMouse.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        mainAnchorLogin.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                buttonIniciarSesion.fire();
            }
        });

    }

    public void setRoot(Parent root, Stage stage) {
        this.root = root;
        this.stage = stage;
    }

    public Boolean loginAlumnoAction() {
        //Rol es 3 para alumno
        var result = Login.login(textFieldMail.getText(), textFieldPassword.getText(), ALUMNO);
        return result;
    }



    private void toastMessage(int estado) {
        Stage window = new Stage();
        window.setX((stage.getX() + 50) + (stage.getWidth() / 2) - 200);
        window.setY(stage.getY() + stage.getHeight() / 2 + stage.getHeight() / 3);
        window.initStyle(StageStyle.TRANSPARENT);
        Text string = new Text();
        if (estado == 0) {
            string = new Text("Sesión iniciada correctamente");
            string.setFill(Color.WHITE);
            string.setStrokeWidth(1.5);
        }else if (estado == 1) {
            string = new Text("Usuario o contraseña incorrectos");
            string.setFill(Color.RED);
            string.setStrokeWidth(2.5);
        }
        string.setStyle("-fx-font-size: 17px;");
        VBox layout = new VBox(10, string);
        layout.setPadding(new Insets(3));
        if (estado == 0) {
            layout.setBackground(new Background(new BackgroundFill(Color.rgb(100, 100, 100), new CornerRadii(3), Insets.EMPTY)));
        }else if (estado == 1) {
            layout.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), new CornerRadii(3), Insets.EMPTY)));
        }
        window.setScene(new Scene(layout, Color.TRANSPARENT));
        window.setAlwaysOnTop(true);
        window.setOpacity(0.95);
        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, evt -> window.show(), new KeyValue(layout.opacityProperty(), 0)),
                new KeyFrame(Duration.millis(400), new KeyValue(layout.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(1000), new KeyValue(layout.opacityProperty(), 1.0)),
                new KeyFrame(Duration.millis(1200), new KeyValue(layout.opacityProperty(), 0.2)));
        timeline.setOnFinished(evt -> window.close());
        timeline.play();
    }


}