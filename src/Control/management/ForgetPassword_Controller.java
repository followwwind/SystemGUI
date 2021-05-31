package Control.management;

import entity.employee;
import entity.manager;
import entity.user;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Control.tool.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * this class will check the identity of the user if he forget his password
 * staff, coach and member can reset the password
 */
public class ForgetPassword_Controller extends Application implements Initializable {
    @FXML
    private TextField authenticateName, authenticateEmail;
    @FXML
    private RadioButton member;
    @FXML
    private RadioButton staff;
    @FXML
    private RadioButton coach;
    @FXML
    private Button register;
    private String name;
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/forgetPassword.fxml"));
        stage.setTitle("Authentication");
        stage.setScene(new Scene(root, 401, 600));
        stage.show();
    }

    /**
     * action after the user click "register"
     */
    public void register_au_action(){
        Platform.runLater(() -> {
                    Stage stage = (Stage) register.getScene().getWindow();
                    Register_Controller open = new Register_Controller();
                    try {
                        open.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * action after the user click "authenticate"
     * @throws IOException
     */
    public void authenticate_action() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) register.getScene().getWindow();
            String string2 = authenticateEmail.getText();
            this.name = authenticateName.getText();
            user conbuf = null;    //user object read from file
            employee emp=null;
            manager man=null;
            if(member.isSelected()) {
                try {
                    conbuf = UserRecord.getUserInfo(name);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (conbuf != null) {
                    if (name.isEmpty() || string2.isEmpty()) {     //if the input is empty
                        new Alert(Alert.AlertType.NONE, "empty input", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (conbuf.getUserPassword() == null) {
                        new Alert(Alert.AlertType.NONE, "Wrong User name Or not register", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (!conbuf.getUserEmail().equals(string2)) {
                        new Alert(Alert.AlertType.NONE, "Wrong Email Address", new ButtonType[]{ButtonType.CLOSE}).show();
                    }
                    //enter personal information correctly
                    else if (conbuf.getUserEmail().equals(string2)) {
                        try {
                            showUserPage("member",name);
                            stage.hide();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.NONE, "Wrong User name Or not register", new ButtonType[]{ButtonType.CLOSE}).show();
                }
            }
            else if(coach.isSelected()){
                try {
                    emp = UserRecord.getEmployeeInfo(name);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (emp != null) {
                    if (name.isEmpty() || string2.isEmpty()) {     //if the input is empty
                        new Alert(Alert.AlertType.NONE, "empty input", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (emp.getEmployeePassword() == null) {
                        new Alert(Alert.AlertType.NONE, "Wrong User name Or not register", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (!emp.getEmployeeEmail().equals(string2)) {
                        new Alert(Alert.AlertType.NONE, "Wrong Email Address", new ButtonType[]{ButtonType.CLOSE}).show();
                    }
                    //enter personal information correctly
                    else if (emp.getEmployeeEmail().equals(string2)) {
                        try {
                            showUserPage("coach",name);
                            stage.hide();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.NONE, "Wrong User name Or not register", new ButtonType[]{ButtonType.CLOSE}).show();
                }
            }
            else if(staff.isSelected()){
                man = UserRecord.getManagerInfo(name);
                if (man != null) {
                    if (name.isEmpty() || string2.isEmpty()) {     //if the input is empty
                        new Alert(Alert.AlertType.NONE, "empty input", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (man.getPassword() == null) {
                        new Alert(Alert.AlertType.NONE, "Wrong User name Or not register", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (!man.getEmail().equals(string2)) {
                        new Alert(Alert.AlertType.NONE, "Wrong Email Address", new ButtonType[]{ButtonType.CLOSE}).show();
                    }
                    //enter personal information correctly
                    else if (man.getEmail().equals(string2)) {
                        try {
                            showUserPage("staff",name);
                            stage.hide();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    new Alert(Alert.AlertType.NONE, "Wrong User name Or not register", new ButtonType[]{ButtonType.CLOSE}).show();
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    /**
     * show the password modify page
     * @param userID
     * @return
     * @throws IOException
     */
    public Stage showUserPage(String identity,String userID) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/forget_resetPassword.fxml"
                )
        );
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        Forget_ResetPassword_Controller controller = loader.<Forget_ResetPassword_Controller>getController();
        controller.initData(identity,userID);
        stage.show();
        return stage;
    }
}
