package Control.member;

import entity.MyRecordLesson;
import entity.recordLesson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import Control.management.VideoPalyer_Controller;
import Control.tool.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * this page contains all the recorded courses
 * member can search and add the recorded courses according to their member state
 * they can search courses by key word and type
 * and they can add the recorded courses into their course list
 */
public class User_CoursesInfo_Controller extends Application implements Initializable {

    @FXML
    public ChoiceBox SearchByType;
    @FXML
    public TextField SearchCourse;
    @FXML
    public TableView<recordLesson> courseTable=new TableView<>();
    @FXML
    public TableColumn<recordLesson,String> course_id,course_name,description,course_type,duration;
    @FXML
    public Button search_course,addCourse;


    String filename="LessonInfo.txt";
    String myFile="mylesson.txt";
    String username;

    String newtype = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void initData(String userName){
        this.username=userName;
        ObservableList<recordLesson> data = FXCollections.observableArrayList();
        List<recordLesson> record;
        try {
            record = ReCourseRecord.searchAll(username,filename);
            for(int i=0;i<record.size();i++){
                recordLesson re=record.get(i);
                data.add(re);
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        course_id.setCellValueFactory(new PropertyValueFactory<>("reLessId"));
        course_name.setCellValueFactory(new PropertyValueFactory<>("reLessName"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        course_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        courseTable.setItems(data);
        SearchByType.setItems(FXCollections.observableArrayList("all", "yoga", "lose weight", "muscle building"));
        SearchByType.getSelectionModel().select(0);
        final String[] string1 = new String[] { "all", "yoga", "lose weight", "muscle building" };
        SearchByType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                newtype = string1[newValue.intValue()];
            }
        });
        courseTable.setRowFactory( tv -> {
            TableRow<recordLesson> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    recordLesson rowData = row.getItem();
                    try {
                        showVideoPage(rowData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }

    public void start(Stage stage) throws IOException {
        // TODO Auto-generated method stub
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_coursesinfo.fxml"));
        stage.setTitle("course search");
        stage.setScene(new Scene(root, 800, 450));
        stage.show();
    }

    /**
     * member can search courses by key word and type
     */
    public void search_action() {
        try {
            String string = SearchCourse.getText();
            ObservableList<recordLesson> data = FXCollections.observableArrayList();
            List<recordLesson> recordLessons=ReCourseRecord.searchLesson(username,string,newtype,filename);
            for(int i=0;i<recordLessons.size();i++){
                recordLesson record=recordLessons.get(i);
                data.add(record);
            }
            courseTable.setItems(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * add recorded course into member's course list, if it had selected, it will warn the member
     * @throws IOException
     */
    public void add_action() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage)addCourse .getScene().getWindow();
            ObservableList<recordLesson> data = courseTable.getSelectionModel().getSelectedItems();
            List<MyRecordLesson> MyRecordLessonList = null;
            try {
                MyRecordLessonList = ReCourseRecord.findMyRecordLesson(myFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (data != null) {
                for (recordLesson re : data) {
                    String old = re.toString();
                    String insertContent = username + "," + old;
                    String[] cou = old.split(",");
                    Boolean flag = true;
                    for (MyRecordLesson my : MyRecordLessonList) {
                        String course = my.toString();
                        String[] myCourse = course.split(",");
                        if (myCourse[0].equals(username) && myCourse[1].equals(cou[0])) {
                            new Alert(Alert.AlertType.NONE, "This course is selected.", new ButtonType[]{ButtonType.CLOSE}).show();
                            flag = false;
                        }
                    }
                    if (flag) {
                        try {
                            FileUtil.insert(insertContent, "mylesson.txt");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        stage.hide();
                        try {
                            showUserPage(username);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    /**
     * return to the member's home page
     * @param userName
     * @return
     * @throws IOException
     */
    public Stage showUserPage(String userName) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_homePage.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
        User_HomePage_Controller controller =
                loader.<User_HomePage_Controller>getController();
        controller.initData(userName);
        stage.show();
        return stage;
    }
    /**
     * show the recorded course video page
     * @param lesson
     * @return
     * @throws IOException
     */
    public Stage showVideoPage(recordLesson lesson) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/VideoPlayer.fxml"
                )
        );
        Locale.setDefault(Locale.ENGLISH);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        VideoPalyer_Controller controller =
                loader.<VideoPalyer_Controller>getController();
        loader.<User_OrderSearch_Controller>getController();
        controller.initData(lesson);
        stage.show();
        return stage;
    }
}
