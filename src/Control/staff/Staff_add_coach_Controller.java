package Control.staff;

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
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * manager can add coach in this page
 */
public class Staff_add_coach_Controller extends Application implements Initializable {
    @FXML
    private RadioButton female_radioButton;
    @FXML
    private RadioButton male_radioButton;
    @FXML
    private TextField user_name,user_psw, user_email, user_phoneNo;
    @FXML
    private DatePicker dateOfBirth=new DatePicker();
    @FXML
    private Button register_button;
    private String userName;
    private final String pattern = "yyyy-MM-dd";
    private String managerID;
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

    @Override
    public void start(Stage stage) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/staff_add_coach.fxml"));
        stage.setTitle("Add Coach");
        stage.setScene(new Scene(root, 401, 600));
        stage.show();
    }
    public String datePickerCheck(){
        LocalDate date =dateOfBirth.getValue();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Birthday=date.format(fmt);
        return Birthday;
    }
    public void register_action() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) female_radioButton.getScene().getWindow();
            String password1 = new String(user_psw.getText());
            boolean flag = true;
            if (password1.equals(" ")) {
                new Alert(Alert.AlertType.NONE, "please fill in password", new ButtonType[]{ButtonType.CLOSE}).show();
                flag = false;
            }
            this.userName = user_name.getText();
            String userEmail = user_email.getText();
            String userPhone = user_phoneNo.getText();
            String dateOfBirth = datePickerCheck();
            String userID = null;
            try {
                userID = getID();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (userName.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in coach name", new ButtonType[]{ButtonType.CLOSE}).show();
                flag = false;
            }
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
                    String nw = userID + "," + password1 + "," + userEmail + "," + dateOfBirth + "," + userPhone + "," + userSex + "," + userName;
                    FileUtil.insert(nw, "employee.txt");
                    stage.hide();
                    showStaffPage(managerID);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * coach's id is randomly assigned
     * @return
     * @throws IOException
     */
    public String getID() throws IOException {
        String temp=randomId.getRandomId();
        employee emp=UserRecord.getEmployeeInfo(temp);
        if(emp!=null){
            getID();
        }
        return temp;
    }
    public void initData(String managerID) throws IOException {
        this.managerID=managerID;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
