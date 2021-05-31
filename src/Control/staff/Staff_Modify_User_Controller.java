package  Control.staff;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import Control.tool.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import entity.user;
import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.scene.control.RadioButton;

import javafx.scene.control.DatePicker;

/**
 * staff modify the member's personal information
 */
public class Staff_Modify_User_Controller extends Application implements Initializable  {
	@FXML
	private Button update_button;
	@FXML
	private TextField updateUserName;
	@FXML
	private TextField updateUserEmail;
	@FXML
	private TextField updateUserPhone;
	@FXML
	private RadioButton female_radioButton,resetPassword,male_radioButton;
	@FXML
	private DatePicker updateUserBirth;
	private String sex;
	private String password;
    private String userID;
    private String managerID;
	// Event Listener on Button[#update_button].onAction
	@FXML
	public void update_action(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) update_button.getScene().getWindow();
            String userName = updateUserName.getText();
            String userEmail = updateUserEmail.getText();
            String userPhone = updateUserPhone.getText();
            String dateOfBirth = updateBirth_action(event);
            System.out.println(dateOfBirth);
            user temp = null;
            try {
                temp = UserRecord.getUserInfo(userID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (userName.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in user name", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (userEmail.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in email address", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (!userEmail.contains("@")) {
                new Alert(Alert.AlertType.NONE, "Please check email address", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (userPhone.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in phone no", new ButtonType[]{ButtonType.CLOSE}).show();
            } else {
                try {
                    if (male_radioButton.isSelected()) {
                        this.sex = "M";
                    } else if (female_radioButton.isSelected()) {
                        this.sex = "F";
                    } else if (!male_radioButton.isSelected() && !female_radioButton.isSelected()) {
                        new Alert(Alert.AlertType.NONE, "Please choose sex", new ButtonType[]{ButtonType.CLOSE}).show();
                    }
                    if (resetPassword.isSelected()) {
                        this.password = "000000";
                    } else if (!resetPassword.isSelected()) {
                        if (temp.getUserPassword() != null) {
                            this.password = temp.getUserPassword();
                        }
                    }
                    String invitation = randomId.getRandomId();
                    String old = userID + "," + password + "," + userEmail + "," + dateOfBirth + "," + userPhone + "," + sex + "," + userName + "," + invitation;
                    FileUtil.change(userID, old, "cus.txt");
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
	// Event Listener on DatePicker[#updateUserBirth].onAction
	@FXML
	public String updateBirth_action(ActionEvent event) {
		LocalDate date =updateUserBirth.getValue();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        updateUserBirth.setOnShowing(e-> Locale.setDefault(Locale.Category.FORMAT, Locale.ENGLISH));
        String Birthday=date.format(fmt);
        //new Alert(Alert.AlertType.NONE, dateChoose, new ButtonType[]{ButtonType.CLOSE}).show();
        return Birthday;
	}
	public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/Staff_modify_user.fxml"));
        stage.setTitle("Staff modify user information");
        stage.setScene(new Scene(root, 432, 600));
        stage.show();
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	public void initData(String userID, String managerID) throws IOException {
	    this.userID=userID;
	    this.managerID=managerID;
	    user use=UserRecord.getUserInfo(userID);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(use.getUserBirth(), formatter);
        updateUserName.setText(use.getUserName());
        updateUserEmail.setText(use.getUserEmail());
        updateUserPhone.setText(use.getUserPhoneNo());
        updateUserBirth.setValue(localDate);
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
