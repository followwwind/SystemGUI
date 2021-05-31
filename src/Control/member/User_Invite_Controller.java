package Control.member;

import entity.user;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Control.tool.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * member can invite others using their own invite code
 * every member has an unique invite code
 * if invite successfully, the inviter and the member can both get 50$
 * they cannot invite themselves
 */
public class User_Invite_Controller  extends Application implements Initializable {
    public String name;
    @FXML
    public TextField invitation;
    @FXML
    public Label INVITECODE;
    @FXML Button verifyButton;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_invite.fxml"));
        stage.setTitle("INVITATION");
        stage.setScene(new Scene(root, 401, 600));
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }
    public void initData(String userID) throws IOException {
        this.name=userID;
        user use= UserRecord.getUserInfo(userID);
        this.INVITECODE.setText(use.getInvitation());
    }

    /**
     * operation after the member fill in the invitation code
     */
    public void verify() throws IOException {
        Platform.runLater(() -> {
            Stage stage = (Stage) verifyButton.getScene().getWindow();
            String invite = invitation.getText();
            try {
                user inviter = UserRecord.searchInviter(invite);
                if(inviter==null){
                    new Alert(Alert.AlertType.NONE, "Please Check The Invite Code", new ButtonType[]{ButtonType.CLOSE}).show();
                }
                else {
                    String money = "50";
                    if (!inviter.getUserId().equals(name)) {
                        try {
                            RechargeRecord.rechargeMoney(name, money);
                            RechargeRecord.rechargeMoney(inviter.getUserId(), money);
                            new Alert(Alert.AlertType.NONE, "Invite Successfully", new ButtonType[]{ButtonType.CLOSE}).show();
                            stage.hide();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        new Alert(Alert.AlertType.NONE, "Can not inviter yourself", new ButtonType[]{ButtonType.CLOSE}).show();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

}
