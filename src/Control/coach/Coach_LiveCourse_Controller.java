package Control.coach;

import entity.Order;
import entity.user;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Control.tool.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * coach can deliver the live course using this page
 */
public class Coach_LiveCourse_Controller extends Application implements Initializable {
    @FXML
    private Label timeLive,memberName;
    @FXML
    private TextField feedback;

    private Order orderPage;
    private String time;
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/coach_liveCourse.fxml"));
        stage.setTitle("Live Course Page");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public void initData(Order order) throws IOException {
        this.orderPage=order;
        String str=orderPage.getLessonNo();
        if(str.equals("1"))
            this.time="07:00 ~ 09:00";
        else if(str.equals("2"))
            this.time="09:00 ~ 11:00";
        else if(str.equals("3"))
            this.time="13:00 ~ 15:00";
        else if(str.equals("4"))
            this.time="15:00 ~ 17:00";
        else if(str.equals("5"))
            this.time="19:00 ~ 21:00";
        String dateTime=orderPage.getLessonDate()+" "+time;
        timeLive.setText(dateTime);
        user use= UserRecord.getUserInfo(orderPage.getUserId());
        memberName.setText(use.getUserName());
    }
}
