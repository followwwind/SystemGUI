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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import Control.tool.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * this class will reset the user's password
 */
public class Forget_ResetPassword_Controller extends Application implements Initializable {
    @FXML
    private PasswordField password,qualify;
    @FXML
    private Button res;
    private String userID;
    @FXML
    private Stage stage;
    private String identity;
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/forget_resetPassword.fxml"));
        this.stage.setTitle("Reset Password");
        this.stage.setScene(new Scene(root, 401, 600));
        this.stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }

    /**
     * action after click "reset"
     * @throws IOException
     */
    public void reset() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) res.getScene().getWindow();
            String password1= password.getText();
            String password2=qualify.getText();
            if(!password1.equals(password2)){
                new Alert(Alert.AlertType.NONE, "password different", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            else if(password1.equals(" ")){
                new Alert(Alert.AlertType.NONE, "please fill in password", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            else{
                //member reset password
                if(this.identity.equals("member")) {
                    String invite = randomId.getRandomId();
                    user user= null;
                    try {
                        user = UserRecord.getUserInfo(userID);
                        String newString = user.getUserId() + "," + password1 + "," + user.getUserEmail() + "," + user.getUserBirth() + "," + user.getUserPhoneNo() + "," + user.getSex() + "," + user.getUserName() + "," + invite;
                        try {
                            FileUtil.change(user.getUserId(), newString, "cus.txt");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //coach reset password
                else if(this.identity.equals("coach")){
                    try {
                        employee emp=UserRecord.getEmployeeInfo(userID);
                        String newStr =  userID+ "," + password1 + "," + emp.getEmployeeEmail() + "," + emp.getEmployeeBirth() + "," + emp.getEmployeePhoneNo() + "," + emp.getEmployeeSex() + "," + emp.getEmployeeName();
                        try {
                            FileUtil.change(userID, newStr, "employee.txt");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(this.identity.equals("staff")){
                    try {
                        manager man=UserRecord.getManagerInfo(userID);
                        UserRecord.updateManager(userID, password1, man.getEmail(), man.getBirth(), man.getPhone(), man.getSex());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                    new Alert(Alert.AlertType.NONE, "reset password successfully", new ButtonType[]{ButtonType.CLOSE}).show();
                    Login_Controller open = new Login_Controller();
                    try {
                        open.start(new Stage());
                        stage.hide();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        });
    }
    public void initData(String identity, String userName) throws IOException {
        this.userID= userName;
        this.identity=identity;
    }

}
