package Control.member;

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
import Control.tool.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * this class will modify the member's personal information
 */
public class User_Update_Controller extends Application implements Initializable {
    @FXML
    private RadioButton female_radioButton;
    @FXML
    private RadioButton male_radioButton;
    @FXML
    private TextField updateUserName,updateUserEmail,updateUserPhone;
    @FXML
    private PasswordField updateUserPass,updatePassCon;
    @FXML
    private Button update_button;
    @FXML
    private DatePicker updateUserBirth;
    private String userIDSet;
    private String userSex;
    public void radio_button() {
        //单选按钮组
        ToggleGroup tg = new ToggleGroup();
        //单选按钮
        RadioButton r1 = female_radioButton;
        r1.setToggleGroup(tg);
        RadioButton r2 = male_radioButton;
        r2.setToggleGroup(tg);

        //单选组添加选择事件
        tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton r = (RadioButton) newValue;
                System.out.println("已选择:" + r.getText());
            }
        });
    }

    /**
     * update the member's birthday
     * @return
     */
    public String updateBirth_action(){
        LocalDate date =updateUserBirth.getValue();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //updateUserBirth.setOnShowing(e-> Locale.setDefault(Locale.Category.FORMAT, Locale.ENGLISH));
        String Birthday=date.format(fmt);
        //new Alert(Alert.AlertType.NONE, dateChoose, new ButtonType[]{ButtonType.CLOSE}).show();
        return Birthday;
    }

    public void start(Stage stage) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_update.fxml"));
        stage.setTitle("Users Update Information");
        stage.setScene(new Scene(root, 432, 600));
        stage.show();
    }

    /**
     * member click "update" and his personal information will be updated
     * @throws IOException
     */
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
            String userName = updateUserName.getText();
            String userEmail = updateUserEmail.getText();
            String userPhone = updateUserPhone.getText();
            String dateOfBirth = updateBirth_action();
            System.out.println(dateOfBirth);
            try {
                user temp = UserRecord.getUserInfo(userIDSet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String invitation = randomId.getRandomId();
            boolean flag = true;
            if (userName.isEmpty()) {
                new Alert(Alert.AlertType.NONE, "Please fill in user name", new ButtonType[]{ButtonType.CLOSE}).show();
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
            try {
                if (flag) {
                    String old = userIDSet + "," + password1 + "," + userEmail + "," + dateOfBirth + "," + userPhone + "," + userSex + "," + userName + "," + invitation;
                    FileUtil.change(userIDSet, old, "cus.txt");
                    stage.hide();
                    showUserPage(userIDSet);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        radio_button();
    }
    public void initData(String userID) throws IOException {
        user use= UserRecord.getUserInfo(userID);
        this.userIDSet=userID;
        updateUserName.setText(use.getUserName());
        updateUserEmail.setText(use.getUserEmail());
        updateUserPhone.setText(use.getUserPhoneNo());
        updateUserPass.setText(use.getUserPassword());
        updatePassCon.setText(use.getUserPassword());
        String dateBirth=use.getUserBirth();
        String[] temp=dateBirth.split("-");
        String timeNew=temp[1]+"/"+temp[2]+"/"+temp[0];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm/DD/yyyy");
        updateUserBirth.setValue(LocalDate.parse(timeNew,formatter));
    }

    /**
     * return to the member's home page
     * @param userName
     * @return
     * @throws IOException
     */
    public Stage showUserPage(String userName) throws IOException {
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
        controller.initData(userName);
        stage.show();
        return stage;
    }
}
