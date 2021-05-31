package Control.management;
import entity.user;
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
import Control.member.User_HomePage_Controller;
import Control.tool.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * this class will register a new member
 */
public class Register_Controller extends Application implements Initializable {
    @FXML
    private RadioButton female_radioButton;
    @FXML
    private RadioButton male_radioButton;
    @FXML
    private TextField user_name, user_id, user_psw, psw_qua, user_email, user_phoneNo;
    @FXML
    private DatePicker dateOfBirth=new DatePicker();
    @FXML
    private Button register_button;
    private String userID;
    private String userSex;
    private final String pattern = "yyyy-MM-dd";
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
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/register.fxml"));
        stage.setTitle("Register");
        stage.setScene(new Scene(root, 401, 600));
        stage.show();
    }
    public String datePickerCheck(){
        LocalDate date =dateOfBirth.getValue();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String Birthday=date.format(fmt);
        return Birthday;
    }

    /**
     * action after click "register"
     * @throws IOException
     */
    public void register_action() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) register_button.getScene().getWindow();
            String password1 = new String(user_psw.getText());
            String password2 = new String(psw_qua.getText());
            if (!password1.equals(password2)) {
                new Alert(Alert.AlertType.NONE, "password different", new ButtonType[]{ButtonType.CLOSE}).show();
            } else if (password1 == null || password1.equals(" ")) {
                new Alert(Alert.AlertType.NONE, "please fill in password", new ButtonType[]{ButtonType.CLOSE}).show();
            }
            this.userID = user_id.getText();
            String userEmail = user_email.getText();
            String userPhone = user_phoneNo.getText();
            String userName = user_name.getText();
            String dateOfBirth = datePickerCheck();
            String invitation = randomId.getRandomId();
            System.out.println(dateOfBirth);
            boolean flag = true;
            user temp = null;
            try {
                temp = UserRecord.getUserInfo(userID);
                if(temp!=null){
                    new Alert(Alert.AlertType.NONE, "This ID is registered, please fill in a new ID", new ButtonType[]{ButtonType.CLOSE}).show();
                    flag=false;
                }
                if (userName.isEmpty()) {
                    new Alert(Alert.AlertType.NONE, "Please fill in user name", new ButtonType[]{ButtonType.CLOSE}).show();
                    flag = false;
                }
                if (userID.isEmpty()) {
                    new Alert(Alert.AlertType.NONE, "Please fill in member ID", new ButtonType[]{ButtonType.CLOSE}).show();
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
                if (temp == null && flag) {
                    try {
                        String nw = userID + "," + password1 + "," + userEmail + "," + dateOfBirth + "," + userPhone + "," + userSex + "," + userName + "," + invitation;
                        FileUtil.insert(nw, "cus.txt");
                        new Alert(Alert.AlertType.NONE, "Register Successfully", new ButtonType[]{ButtonType.CLOSE}).show();
                        try {
                            showUserPage(userID);
                            stage.hide();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        radio_button();
    }

    /**
     * show the member's home page
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
}
