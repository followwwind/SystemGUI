package Control.management;

import entity.recordLesson;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * recorded course video can be watched here
 */
public class VideoPalyer_Controller extends Application  implements Initializable {
    @FXML
    private Label courseName,type;
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/VideoPlayer.fxml"));
        stage.setTitle("Media Player");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public void initData(recordLesson lesson){
        courseName.setText(lesson.getReLessName());
        type.setText(lesson.getType());
    }
}
