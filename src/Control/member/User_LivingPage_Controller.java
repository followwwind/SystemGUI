package Control.member;

import entity.Order;
import entity.employee;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Control.tool.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * member can watch their scheduled live courses
 */
public class User_LivingPage_Controller extends Application implements Initializable {
    @FXML
    private Label coachName,timeLive;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_livingPage.fxml"));
        stage.setTitle("User Living Course");
        stage.setScene(new Scene(root, 850, 600));

        //tableView.getColumns().addAll(courseName,type,duration);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
    public void initData(Order order) throws IOException {
        String time=User_LivingCourse_Controller.stringJudge(order.getLessonNo());
        String dateTime=order.getLessonDate()+" "+time;
        timeLive.setText(dateTime);
        employee emp= UserRecord.getEmployeeInfo(order.getCoachId());
        String empName=emp.getEmployeeName();
        coachName.setText(empName);
    }
}
