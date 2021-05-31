package Control.member;

import entity.Course;
import entity.Order;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Control.tool.*;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * this is a confirmation window
 * member can check the course information and pay for the live course
 * they can not pay if their balance is not enough
 * they can not schedule the course if the course is already scheduled
 */
public class User_OrderLivingCourse_Controller extends Application implements Initializable {
    private String name;
    private Course course;
    private String courseId;
    private String dayAfter;
    private int dayAf;
    private String filename1 = "course.txt";
    private String filename2 = "order.txt";
    @FXML
    private Label dateInfo;
    @FXML
    private Label priceInfo;
    @FXML
    private Button orderYes_btn;
    @FXML
    private Button orderNo_btn;


    public void showCourseInfo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        String nowDate = sdf.format(new Date());
        try{
            date = new SimpleDateFormat("yyyy-MM-dd").parse(nowDate);
        }catch(ParseException e){
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+dayAf);
        dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        String price = course.getPrice();
        dateInfo.setText(dayAfter);
        priceInfo.setText(price);
    }
    public void confirmOrder() throws IOException {
        Stage stage = (Stage)orderYes_btn.getScene().getWindow();
        String orderId = randomId.getRandomId();
        Order order = new Order(orderId, name, course.getCoachId(), course.getCourseId(), course.getLessonNo(), dayAfter, course.getPrice(), "payed");
        if(FileUtil.findLegalOrder(filename2, course.getCoachId(), dayAfter)) {
            String od = order.toString();
            if (RechargeRecord.payMoney(name, course.getPrice()) == -1) {
                new Alert(Alert.AlertType.NONE, "Insufficient balance, please recharge", new ButtonType[]{ButtonType.CLOSE}).show();
            } else {
                new Alert(Alert.AlertType.NONE, "Order Success", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            FileUtil.insert(od, filename2);
        }
        else{
            new Alert(Alert.AlertType.NONE, "This class has been reserved. Please choose another class", new ButtonType[]{ButtonType.CLOSE}).show();
        }
        stage.hide();
    }
    public void noConfirm(){
        Stage stage = (Stage)orderNo_btn.getScene().getWindow();
        stage.hide();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_orderLivingCourse.fxml"));
        stage.setTitle("Order Course");
        stage.setScene(new Scene(root, 800, 450));
        //tableView.getColumns().addAll(courseName,type,duration);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
    public void initOrderingData(String userName, String courseId, String dayAf) throws IOException {
        this.name = userName;
        this.courseId = courseId;
        this.course = FileUtil.findCourseInfo(filename1, courseId);
        this.dayAf = Integer.valueOf(dayAf);
        showCourseInfo();
    }
}
