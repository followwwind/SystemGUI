package Control.staff;

import entity.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import Control.tool.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;
import Control.tool.*;

/**
 * manager modify the selected employee's personal information
 * files which contains the selected employee will be modified
 */
public class Staff_modify_coach_Controller extends Application implements Initializable  {
	@FXML
	private Button update_button;
	@FXML
	private TextField updateCoachName;
	@FXML
	private TextField updateCoachEmail;
	@FXML
	private TextField updateCoachPhone;
	@FXML
	private RadioButton female_radioButton, resetPassword,male_radioButton;
	@FXML
	private DatePicker updateCoachBirth;
	private String coachSex;
	private String managerID;
	private String empID;
	private String password;

	// Event Listener on Button[#update_button].onAction
	@FXML
	public void update_action(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) update_button.getScene().getWindow();
            String coachName = updateCoachName.getText();
            String coachEmail = updateCoachEmail.getText();
            String coachPhone = updateCoachPhone.getText();
            String dateOfBirth = updateBirth_action(event);
            employee temp = null;
            try {
                temp = UserRecord.getEmployeeInfo(empID);
                if (resetPassword.isSelected()) {
                    this.password = "000000";
                } else if (!resetPassword.isSelected()) {
                    this.password = temp.getEmployeePassword();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (coachName.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in user name", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (coachEmail.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in email address", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (!coachEmail.contains("@")) {
                new Alert(Alert.AlertType.NONE, "Please check email address", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (coachPhone.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in phone no", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (male_radioButton.isSelected()) {
                this.coachSex = "M";
            }
            if (female_radioButton.isSelected()) {
                this.coachSex = "F";
            } else if (!male_radioButton.isSelected() && !female_radioButton.isSelected()) {
                new Alert(Alert.AlertType.NONE, "Please choose sex", new ButtonType[]{ButtonType.CLOSE}).show();
            }

            try {
                String newString = empID + "," + password + "," + coachEmail + "," + dateOfBirth + "," + coachPhone + "," + coachSex + "," + coachName;
                FileUtil.change(coachName, newString, "employee.txt");
                List<coachLesson> coachLessonList = UserRecord.findCoachInfo("coachLesson.txt");
                for (int i = 0; i < coachLessonList.size(); i++) {
                    coachLesson coachLesson = coachLessonList.get(i);
                    String coachNo = coachLesson.getcoaId();
                    if (coachNo.equals(empID)) {
                        Course course=coachLesson.getCoaCourse();
                        String newCourse = course.getCourseId() + "," + empID + "," + coachName + "," + course.getLessonNo()+ "," +course.getPrice()+ "," + course.getFlag();
                        FileUtil.change(empID, newCourse, "course.txt");
                        String newCoachLesson=empID+","+coachName+ "," +empID+","+course.getCourseId()+ "," +course.getLessonNo()+ "," +course.getPrice()+ "," + course.getFlag();
                        FileUtil.change(empID, newCoachLesson, "coachLesson.txt");
                    }
                }
                stage.hide();
                showStaffPage(managerID);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
	// Event Listener on DatePicker[#updateUserBirth].onAction
	@FXML
	public String updateBirth_action(ActionEvent event) {
		LocalDate date =updateCoachBirth.getValue();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        updateCoachBirth.setOnShowing(e-> Locale.setDefault(Locale.Category.FORMAT, Locale.ENGLISH));
        String Birthday=date.format(fmt);
        return Birthday;
	}
	
	public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/Staff_modify_coach.fxml"));
        stage.setTitle("Staff modify coach information");
        stage.setScene(new Scene(root, 432, 600));
        stage.show();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
    public void initData(String userID, String managerID) throws IOException {
        this.empID=userID;
        this.managerID=managerID;
        employee emp=UserRecord.getEmployeeInfo(empID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(emp.getEmployeeBirth(),formatter);
        updateCoachName.setText(emp.getEmployeeName());
        updateCoachEmail.setText(emp.getEmployeeEmail());
        updateCoachPhone.setText(emp.getEmployeePhoneNo());
        updateCoachBirth.setValue(localDate);
    }
    /**
     * show the manager's home page
     * @param managerID
     * @return
     * @throws IOException
     */
    public Stage showStaffPage(String managerID) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/staff_homePage.fxml"
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
        staff_home_Page_Controller controller =
                loader.<staff_home_Page_Controller>getController();
        controller.initData(managerID);
        stage.show();
        return stage;
    }
}
