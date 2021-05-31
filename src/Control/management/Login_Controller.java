package Control.management;

import entity.employee;
import entity.manager;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import entity.user;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import Control.coach.Coach_HomePage_Controller;
import Control.member.User_HomePage_Controller;
import Control.staff.staff_home_Page_Controller;
import Control.tool.*;

public class Login_Controller implements Initializable {

    @FXML
    private RadioButton user_radioButton;
    @FXML
    private RadioButton staff_radioButton;
    @FXML
    private RadioButton coach_radioButton;
    @FXML
    private ImageView imageview;
    @FXML
    private Button login_button;
    @FXML
    private TextField account_textField;
    @FXML
    private TextField passwordField;

    public void radio_button() {
        final ToggleGroup[] tg = {new ToggleGroup()};
        final RadioButton[] r1 = {user_radioButton};
        r1[0].setToggleGroup(tg[0]);
        RadioButton r2 = staff_radioButton;
        r2.setToggleGroup(tg[0]);
        RadioButton r3 = coach_radioButton;
        r3.setToggleGroup(tg[0]);
        tg[0].selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton r = (RadioButton) newValue;
            }
        });
    }

    /**
     * action after click "register"
     */
    public void toRegister(){
        Platform.runLater(() -> {
            Stage stage = (Stage) login_button.getScene().getWindow();
            Register_Controller open = new Register_Controller();
            try {
                open.start(new Stage());
                stage.hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * action after click "forget password"
     */
    public void forgetPass_action(){
        Platform.runLater(() -> {
            Stage stage = (Stage) login_button.getScene().getWindow();
            ForgetPassword_Controller open = new ForgetPassword_Controller();
            try {
                open.start(new Stage());
                stage.hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * action after click "login"
     */
    public void login_action(){
        Platform.runLater(() -> {
            Stage stage = (Stage)login_button.getScene().getWindow();
            if(user_radioButton.isSelected()){
                if(account_textField.getText().equals("")){
                    new Alert(Alert.AlertType.NONE, "please fill in the name", new ButtonType[]{ButtonType.CLOSE}).show();
                }
                else {
                    user userbuf = null;
                    try {
                        userbuf = UserRecord.getUserInfo(account_textField.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (userbuf == null) {
                        new Alert(Alert.AlertType.NONE, "please register", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else {
                        String password = userbuf.getUserPassword();
                        String inputPassword = passwordField.getText();
                        if (password == null) {
                            new Alert(Alert.AlertType.NONE, "please fill in the password", new ButtonType[]{ButtonType.CLOSE}).show();
                        } else if (account_textField.getText() == null) {
                            new Alert(Alert.AlertType.NONE, "please fill in the user name", new ButtonType[]{ButtonType.CLOSE}).show();
                        } else if (!password.equals(inputPassword)) {
                            new Alert(Alert.AlertType.NONE, "password wrong", new ButtonType[]{ButtonType.CLOSE}).show();
                        } else {
                            try {
                                showUserPage(account_textField.getText());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            stage.hide();
                        }
                    }
                }
            }else if(staff_radioButton.isSelected()){
                manager manbuf= UserRecord.getManagerInfo(account_textField.getText());
                if(manbuf==null){
                    new Alert(Alert.AlertType.NONE, "The staff doesn't exist", new ButtonType[]{ButtonType.CLOSE}).show();
                }
                else {
                    String password = manbuf.getPassword();
                    String inputPassword = passwordField.getText();
                    if (inputPassword == null) {
                        new Alert(Alert.AlertType.NONE, "please fill in the password", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (account_textField.getText() == null) {
                        new Alert(Alert.AlertType.NONE, "please fill in the name", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (!password.equals(inputPassword)) {
                        new Alert(Alert.AlertType.NONE, "password wrong", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else {
                        staff_home_Page_Controller open = new staff_home_Page_Controller();
                        try {
                            showStaffPage(account_textField.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stage.hide();
                    }
                }

            }else if(coach_radioButton.isSelected()){
                employee embuf = null;
                try {
                    embuf = UserRecord.getEmployeeInfo(account_textField.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(embuf!=null) {
                    String password = embuf.getEmployeePassword();
                    String inputPassword = passwordField.getText();
                    if (password == null) {
                        new Alert(Alert.AlertType.NONE, "please fill in the password", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (account_textField.getText() == null) {
                        new Alert(Alert.AlertType.NONE, "please fill in the name", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else if (!password.equals(inputPassword)) {
                        new Alert(Alert.AlertType.NONE, "password wrong", new ButtonType[]{ButtonType.CLOSE}).show();
                    } else {
                        try {
                            showCoachPage(account_textField.getText());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        stage.hide();

                    }
                }
                else{
                    new Alert(Alert.AlertType.NONE, "please register", new ButtonType[]{ButtonType.CLOSE}).show();
                }
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        radio_button();
    }

    /**
     * Show Member's home page
     * @param userID
     * @return
     * @throws IOException
     */
    public Stage showUserPage(String userID) throws IOException {
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
        controller.initData(userID);
        stage.show();
        return stage;
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

    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/login.fxml"));
        stage.setTitle("Gym System");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
