package Control.coach;

import entity.Course;
import entity.coachLesson;
import entity.employee;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import Control.tool.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * coach modify personal information
 */
public class Coach_Update_Controller extends Application implements Initializable {
    @FXML
    private RadioButton female_radioButton;
    @FXML
    private RadioButton male_radioButton;
    @FXML
    private TextField updateUserEmail,updateUserPhone,updateUserName;
    @FXML
    private PasswordField updateUserPass,updatePassCon;
    @FXML
    private DatePicker updateUserBirth;
    @FXML
    private Button update_button;
    private String empIDSet;
    private employee emp;
    private String userSex;
    public void radio_button() {
        ToggleGroup tg = new ToggleGroup();
        RadioButton r1 = female_radioButton;
        r1.setToggleGroup(tg);
        RadioButton r2 = male_radioButton;
        r2.setToggleGroup(tg);
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton r = (RadioButton) newValue;
            }
        });
    }
    public String updateBirth_action(){
        LocalDate date =updateUserBirth.getValue();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Birthday=date.format(fmt);
        return Birthday;
    }
    public void start(Stage stage) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/coach_update.fxml"));
        stage.setScene(new Scene(root, 432, 600));
        stage.show();
    }
    public void update_action() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) update_button.getScene().getWindow();
            String password1 = new String(updateUserPass.getText());
            String password2 = new String(updatePassCon.getText());
            if (!password1.equals(password2)) {
                new Alert(Alert.AlertType.NONE, "password different", new ButtonType[]{ButtonType.CLOSE}).show();
            } else if (password1 == null || password1.equals(" ")) {
                new Alert(Alert.AlertType.NONE, "please fill in password", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            String userEmail = updateUserEmail.getText();
            String userPhone = updateUserPhone.getText();
            String userName = updateUserName.getText();
            String dateOfBirth = updateBirth_action();
            try {
                employee temp = UserRecord.getEmployeeInfo(empIDSet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            boolean flag = true;
            if (userEmail.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in email address", new ButtonType[]{ButtonType.CLOSE}).show();
                flag = false;
            }
            if (!userEmail.contains("@")) {
                new Alert(Alert.AlertType.NONE, "Please check email address", new ButtonType[]{ButtonType.CLOSE}).show();
                flag = false;
            }
            if (userPhone.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in phone no", new ButtonType[]{ButtonType.CLOSE}).show();
                flag = false;
            }
            if (userName.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in name", new ButtonType[]{ButtonType.CLOSE}).show();
                flag = false;
            }
            if (male_radioButton.isSelected()) {
                this.userSex = "M";
            }
            if (female_radioButton.isSelected()) {
                this.userSex = "F";
            } else if (!male_radioButton.isSelected() && !female_radioButton.isSelected()) {
                new Alert(Alert.AlertType.NONE, "Please choose sex", new ButtonType[]{ButtonType.CLOSE}).show();
                flag = false;
            }
            if (flag) {
                try {
                    String newStr = empIDSet + "," + password1 + "," + userEmail + "," + dateOfBirth + "," + userPhone + "," + userSex + "," + userName;
                    FileUtil.change(empIDSet, newStr, "employee.txt");
                    List<coachLesson> coachLessonList = UserRecord.findCoachInfo("coachLesson.txt");
                    for (int i = 0; i < coachLessonList.size(); i++) {
                        coachLesson coachLesson = coachLessonList.get(i);
                        String coachNo = coachLesson.getcoaId();
                        if (coachNo.equals(empIDSet)) {
                            Course course=coachLesson.getCoaCourse();
                            String newCourse = course.getCourseId() + "," + empIDSet + "," + userName + "," + course.getLessonNo()+ "," +course.getPrice()+ "," + course.getFlag();
                            FileUtil.change(empIDSet, newCourse, "course.txt");
                            String newCoachLesson=empIDSet+","+userName+ "," +empIDSet+","+course.getCourseId()+ "," +course.getLessonNo()+ "," +course.getPrice()+ "," + course.getFlag();
                            FileUtil.change(empIDSet, newCoachLesson, "coachLesson.txt");
                        }
                    }
                    stage.hide();
                    showCoachPage(empIDSet);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        radio_button();
    }

    public void initCoachData(String empID) throws IOException {
        this.empIDSet = empID;
        this.emp=UserRecord.getEmployeeInfo(empID);
        updateUserName.setText(emp.getEmployeeName());
        updatePassCon.setText(emp.getEmployeePassword());
        updateUserPass.setText(emp.getEmployeePassword());
        updateUserPhone.setText(emp.getEmployeePhoneNo());
        updateUserEmail.setText(emp.getEmployeeEmail());
        String dateBirth=emp.getEmployeeBirth();
        String[] temp=dateBirth.split("-");
        String timeNew=temp[1]+"/"+temp[2]+"/"+temp[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/DD/yyyy");
        updateUserBirth.setValue(LocalDate.parse(timeNew,formatter));
    }
    /**
     * Show coach's home page
     * @param empID the employ's ID
     * @return
     * @throws IOException
     */
    public Stage showCoachPage(String empID) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/coach_homePage.fxml"
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
        Coach_HomePage_Controller controller =
                loader.<Coach_HomePage_Controller>getController();
        controller.initCoachData(empID);
        stage.show();
        return stage;
    }
}
