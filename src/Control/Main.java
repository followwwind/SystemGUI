package Control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Boundary/login.fxml"));
        primaryStage.setTitle("Gym System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

}
