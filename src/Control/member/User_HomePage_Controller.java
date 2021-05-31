package Control.member;

import entity.MyRecordLesson;
import entity.recordLesson;
import entity.user;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import Control.management.VideoPalyer_Controller;
import Control.tool.*;

/**
 * this is the member's home page, including the personal information
 * and the recorded course he selected
 */
public class User_HomePage_Controller extends Application implements Initializable {

    @FXML
    private Button update_btn;
    @FXML
    private Button course_search_btn;
    @FXML
    private TableView<recordLesson> tableView;
    @FXML
    private Label nameText, birthText, sexText, emailText, phoneText, IDText;
    private String name;
    @FXML
    private TableColumn<recordLesson,String> courseName, type, duration;
    // Save the information on my course list
    private static List<MyRecordLesson> MyRecordLessonList = new ArrayList<MyRecordLesson>();

    // Path to save file for scheduled course information
    private static String fileName = "mylesson.txt";

    /**
     * action after click "modify"
     * the user can modify his personal information
     */
    public void toUser_update(){
        Platform.runLater(() -> {
            Stage stage = (Stage) update_btn.getScene().getWindow();
            User_Update_Controller open = new User_Update_Controller();
            try {
                showUserPage(name);
                stage.hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * action after click "member information"
     */
    public void toUser_charge(){
        User_Charge_Controller open  = new User_Charge_Controller();
        try {
            showMemberPage(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * action after click "search more", the user
     * can find more recorded course
     */
    public void toUser_course(){
        Platform.runLater(() -> {
            Stage stage = (Stage) update_btn.getScene().getWindow();
            User_CoursesInfo_Controller open = new User_CoursesInfo_Controller();
            try {
                showCoursePage(name);
                stage.hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * delete recorded courses from the user's course list
     * @throws IOException
     */
    public void deleteCourse() throws IOException {
        ObservableList<recordLesson> data=tableView.getSelectionModel().getSelectedItems();
        if(data!=null){
            for (recordLesson re : data) {
                String old = re.toString();
                String[] temp=old.split(",");
                System.out.println(name+"   "+temp[0]);
                FileUtil.delete(name,temp[0], fileName);
            }
        }
        MyRecordLessonList = ReCourseRecord.findMyRecordLesson(fileName);
        ObservableList<recordLesson> dataNew = FXCollections.observableArrayList();
        boolean isFound = false;
        for (int i = 0; i < MyRecordLessonList.size(); i++) {
            MyRecordLesson myRecordLesson = MyRecordLessonList.get(i);
            String u = myRecordLesson.getUser();
            if (u.equals(name)) {
                dataNew.add(myRecordLesson.getRecordLesson());
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("no course selected");
        }
        tableView.setItems(dataNew);
    }

    /**
     * show the invitation page
     * @throws IOException
     */
    public void invite() throws IOException {
        try {
            showInvitePage(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_homePage.fxml"));
        stage.setTitle("User");
        stage.setScene(new Scene(root, 800, 450));
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
    public void initData(String userName) throws IOException {
        this.name=userName;
        user use= UserRecord.getUserInfo(userName);
        nameText.setText(use.getUserName());
        birthText.setText(use.getUserBirth());
        sexText.setText(use.getSex());
        emailText.setText(use.getUserEmail());
        phoneText.setText(use.getUserPhoneNo());
        IDText.setText(userName);
        courseName.setCellValueFactory(new PropertyValueFactory<>("reLessName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        MyRecordLessonList = ReCourseRecord.findMyRecordLesson(fileName);

        ObservableList<recordLesson> data = FXCollections.observableArrayList();
        boolean isFound = false;
        for (int i = 0; i < MyRecordLessonList.size(); i++) {
            MyRecordLesson myRecordLesson = MyRecordLessonList.get(i);
            String u = myRecordLesson.getUser();
            if (u.equals(userName)) {
                data.add(myRecordLesson.getRecordLesson());
                isFound = true;
            }
        }
        if (!isFound) {
            System.out.println("no course selected");
        }
        tableView.setItems(data);
        tableView.setRowFactory( tv -> {
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

    /**
     * show the user's information modification page
     * @param userID
     * @return
     * @throws IOException
     */
    public Stage showUserPage(String userID) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_update.fxml"
                )
        );
        Locale.setDefault(Locale.ENGLISH);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );

        User_Update_Controller controller =
                loader.<User_Update_Controller>getController();
        controller.initData(userID);
        stage.show();
        return stage;
    }

    /**
     * show the member's information page
     * @param userName
     * @return
     * @throws IOException
     */
    public Stage showMemberPage(String userName) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_charge.fxml"
                )
        );
        Locale.setDefault(Locale.ENGLISH);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );

        User_Charge_Controller controller =
                loader.<User_Charge_Controller>getController();
        controller.initMamberData(userName);
        stage.show();
        return stage;
    }

    /**
     * show all the recorded course page
     * @param userName1
     * @return
     * @throws IOException
     */
    public Stage showCoursePage(String userName1) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_coursesinfo.fxml"
                )
        );
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        User_CoursesInfo_Controller controller =
                loader.<User_CoursesInfo_Controller>getController();
        loader.<User_OrderSearch_Controller>getController();
        controller.initData(userName1);
        stage.show();
        return stage;
    }

    /**
     * show the invitation page
     * @param userName
     * @return
     * @throws IOException
     */
    public Stage showInvitePage(String userName) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_invite.fxml"
                )
        );
        Locale.setDefault(Locale.ENGLISH);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        User_Invite_Controller controller =
                loader.<User_Invite_Controller>getController();
        loader.<User_OrderSearch_Controller>getController();
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
