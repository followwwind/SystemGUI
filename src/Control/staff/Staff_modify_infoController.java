package  Control.staff;

import entity.manager;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.stage.WindowEvent;
import Control.tool.*;

/**
 * manager modify personal information page
 */
public class Staff_modify_infoController extends Application implements Initializable {
	@FXML
	private Button update_button;
	@FXML
	private TextField updateStaffEmail;
	@FXML
	private TextField updateStaffPhone;
	@FXML
	private RadioButton male_radioButton;
	@FXML
	private RadioButton female_radioButton;
	@FXML
	private PasswordField updateStaffPass;
	@FXML
	private PasswordField updatePassCon;
	@FXML
	private DatePicker updateStaffBirth;
	private String userName;
	private String staffSex;

	// Event Listener on Button[#update_button].onAction
	@FXML
	public void update_action(ActionEvent event) {
        Platform.runLater(() -> {
            Stage stage = (Stage) update_button.getScene().getWindow();
            String password1 = new String(updateStaffPass.getText());
            String password2 = new String(updatePassCon.getText());
            if (!password1.equals(password2)) {
                new Alert(Alert.AlertType.NONE, "password different", new ButtonType[]{ButtonType.CLOSE}).show();
            } else if (password1 == null || password1.equals(" ")) {
                new Alert(Alert.AlertType.NONE, "please fill in password", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            String staffEmail = updateStaffEmail.getText();
            String staffPhone = updateStaffPhone.getText();
            String dateOfBirth = updateBirth_action(event);
            System.out.println(dateOfBirth);
            manager temp = UserRecord.getManagerInfo(userName);
            if (temp.getEmail() != null) {
                new Alert(Alert.AlertType.NONE, "This email address had registered", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (staffEmail.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in email address", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (staffPhone.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in phone no", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            if (male_radioButton.isSelected()) {
                this.staffSex = "M";
            }
            if (female_radioButton.isSelected()) {
                this.staffSex = "F";
            } else if (!male_radioButton.isSelected() && !female_radioButton.isSelected()) {
                new Alert(Alert.AlertType.NONE, "Please choose sex", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            try {
                UserRecord.updateManager(userName, password1, staffEmail, dateOfBirth, staffPhone, staffSex);
                stage.hide();
                showStaffPage(userName);
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
		LocalDate date =updateStaffBirth.getValue();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        updateStaffBirth.setOnShowing(e-> Locale.setDefault(Locale.Category.FORMAT, Locale.ENGLISH));
        String Birthday=date.format(fmt);
        //new Alert(Alert.AlertType.NONE, dateChoose, new ButtonType[]{ButtonType.CLOSE}).show();
        return Birthday;
	}
	
	public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/Staff_modify_info.fxml"));
        stage.setTitle("Staff Update Manager Information");
        stage.setScene(new Scene(root, 432, 600));
        stage.show();
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

    public void initData(String name){
	    this.userName=name;
	    manager manager=UserRecord.getManagerInfo(userName);
	    updatePassCon.setText(manager.getPassword());
        updateStaffPass.setText(manager.getPassword());
        updateStaffEmail.setText(manager.getEmail());
        updateStaffPhone.setText(manager.getPhone());
        String dateBirth=manager.getBirth();
        String[] temp=dateBirth.split("-");
        String timeNew=temp[1]+"/"+temp[2]+"/"+temp[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/DD/yyyy");
        updateStaffBirth.setValue(LocalDate.parse(timeNew,formatter));
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
