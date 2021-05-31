package Control.member;

import entity.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Control.tool.*;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * member can check the available live course and order the course
 * they will pay for the course if they order
 */
public class User_LivingCourse_Controller extends Application implements Initializable {
    @FXML
    private TableView<Course> coachInfo;
    @FXML
    private TableColumn<employee, String> coachName, coursePrice;
    @FXML
    private ComboBox<String> lessonPick, preDay;
    @FXML
    private Button searchLesson;
    private String name;
    private String filename = "coachLesson.txt";
    private String filename2 = "order.txt";
    private static List<coachLesson> coachLessonList;

    public void initCombobox(){
        ObservableList<String> option =
                FXCollections.observableArrayList(
                        "07:00 ~ 09:00",
                        "09:00 ~ 11:00",
                        "13:00 ~ 15:00",
                        "15:00 ~ 17:00",
                        "19:00 ~ 21:00"

                );
        lessonPick.setItems(option);
    }
    public void initCombobox2(){
        ObservableList<String> option =
                FXCollections.observableArrayList(
                        "Tomorrow",
                        "The day after tomorrow",
                        "Three days later",
                        "Four days later",
                        "Five days later",
                        "Six days later",
                        "Seven days later"
                );
        preDay.setItems(option);
    }

    public static String stringJudge(String str){
        if(str.equals("07:00 ~ 09:00"))
            return "1";
        else if(str.equals("09:00 ~ 11:00"))
            return "2";
        else if(str.equals("13:00 ~ 15:00"))
            return "3";
        else if(str.equals("15:00 ~ 17:00"))
            return  "4";
        else if(str.equals("19:00 ~ 21:00"))
            return "5";
        else if(str.equals("Tomorrow"))
            return  "1";
        else if(str.equals("The day after tomorrow"))
            return "2";
        else if(str.equals("Three days later"))
            return  "3";
        else if(str.equals("Four days later"))
            return "4";
        else if(str.equals("Five days later"))
            return "5";
        else if(str.equals("Six days later"))
            return "6";
        else
            return "7";
    }

    /**
     * member search available live course by choosing the time and date
     * @throws IOException
     */
    public void searchCoa() throws IOException {
        String lessonNo = stringJudge(lessonPick.getValue());
        String dayAf = stringJudge(preDay.getValue());
        int int_dayAf = Integer.valueOf(dayAf);
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
        c.set(Calendar.DATE,day+int_dayAf);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        coachName.setCellValueFactory(new PropertyValueFactory<>("coachName"));
        coursePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        coachLessonList = UserRecord.findCoachInfo(filename);
        ObservableList<Course> data = FXCollections.observableArrayList();
        for (int i = 0; i < coachLessonList.size(); i++) {
            coachLesson coachLesson = coachLessonList.get(i);
            String no = coachLesson.getCoaCourse().getLessonNo();
            if (no.equals(lessonNo)) {
                if(FileUtil.findLegalOrder(filename2, coachLesson.getCoaCourse().getCoachId(), dayAfter)){
                    data.add(coachLesson.getCoaCourse());
                }

            }
        }
        coachInfo.setItems(data);
    }

    /**
     * member can schedule the live course by clicking "Order" button
     */
    public void orderCourse(){
        ObservableList<Course> data=coachInfo.getSelectionModel().getSelectedItems();
        String dayAf = stringJudge(preDay.getValue());
        System.out.println(dayAf);
        for(Course course : data){
            try{
                String courseId = course.getCourseId();
                System.out.println(courseId);
                showOrderCoursePage(name, courseId, dayAf);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_livingCourse.fxml"));
        stage.setTitle("Living Course");
        stage.setScene(new Scene(root, 800, 450));
        //tableView.getColumns().addAll(courseName,type,duration);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        initCombobox();
        initCombobox2();
    }

    public void initLivingData(String userName){
        this.name = userName;
    }

    /**
     * pass data to the alert window for live course schedule
     * @param userName
     * @param courseId
     * @param dayAf
     * @return
     * @throws IOException
     */
    public Stage showOrderCoursePage(String userName, String courseId, String dayAf) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_orderLivingCourse.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        User_OrderLivingCourse_Controller controller =
                loader.<User_OrderLivingCourse_Controller>getController();
        loader.<User_LivingCourse_Controller>getController();
        controller.initOrderingData(userName, courseId, dayAf);
        stage.show();
        return stage;
    }
}
