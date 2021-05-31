package Control.member;

import Control.tool.RechargeRecord;
import Control.tool.UserRecord;
import entity.user;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is member information page
 * member can charge in this page
 * and schedule the live course, search the order and their live plan
 */
public class User_Charge_Controller extends Application implements Initializable {
    private String name;
    @FXML
    private Label accountName, balance, VIP;
    @FXML
    private TextField recharge;
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_charge.fxml"));
        stage.setTitle("Charge");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * member can charge and click "charge"
     * their balance will change
     */
    public void charge_action(){
        String money = recharge.getText();
        if(money == null || "".equals(money)){
            new Alert(Alert.AlertType.NONE, "please fill in the money to charge", new ButtonType[]{ButtonType.CLOSE}).show();
        }//If the input of 'money' is null, the output will be money can not be null.
        float newbalance= 0;
        try {
            newbalance = RechargeRecord.rechargeMoney(name, money);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        balance.setText(String.valueOf(newbalance));
        entity.recharge re= RechargeRecord.getRechargeInfo(name);
        VIP.setText(re.getVIPState());
    }

    /**
     * refresh member page's information
     */
    public void refresh() throws IOException {
        user use= UserRecord.getUserInfo(name);
        accountName.setText(use.getUserId());
        entity.recharge re= RechargeRecord.getRechargeInfo(name);
        if (re != null) {
            balance.setText(re.getIsPay());
            VIP.setText(re.getVIPState());
        } else {
            balance.setText("0");
            VIP.setText("0");
        }
    }
    /**
     * show the member's total order
     */
    public void toUser_orderSearch(){
        User_OrderSearch_Controller open  = new User_OrderSearch_Controller();
        try {
            showOrderPage(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * show the living course schedule page, member can schedule the live
     * course by selecting the time and the coach
     */
    public void toLiving_course(){
        User_LivingCourse_Controller open  = new User_LivingCourse_Controller();
        try {
            showLivingPage(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * show the member's live plan page
     */
    public void toTrain_Plan(){
        User_TrainPlan_Controller open = new User_TrainPlan_Controller();
        try{
            showTrainPage(name);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public void initMamberData(String userName) throws IOException {
        this.name=userName;
        user use= UserRecord.getUserInfo(userName);
        accountName.setText(use.getUserId());
        entity.recharge re= RechargeRecord.getRechargeInfo(userName);
        if (re != null) {
            balance.setText(re.getIsPay());
            VIP.setText(re.getVIPState());
        } else {
            balance.setText("0");
            VIP.setText("0");
        }
    }

    /**
     * pass data to the member's total order page
     * @param userName
     * @return
     * @throws IOException
     */
    public Stage showOrderPage(String userName) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_orderSearch.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );

        User_OrderSearch_Controller controller =
                loader.<User_OrderSearch_Controller>getController();
        loader.<User_OrderSearch_Controller>getController();
        controller.initUserData(userName);
        stage.show();
        return stage;
    }

    /**
     * pass data to the member's live course schedule page
     * @param userName
     * @return
     * @throws IOException
     */
    public Stage showLivingPage(String userName) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_livingCourse.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );

        User_LivingCourse_Controller controller =
                loader.<User_LivingCourse_Controller>getController();
        loader.<User_LivingCourse_Controller>getController();
        controller.initLivingData(userName);
        stage.show();
        return stage;
    }

    /**
     * pass data to the member's live course plan page
     * @param userName
     * @return
     * @throws IOException
     */
    public Stage showTrainPage(String userName) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_trainPlan.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );
        User_TrainPlan_Controller controller =
                loader.<User_TrainPlan_Controller>getController();
        loader.<User_TrainPlan_Controller>getController();
        controller.initTrainData(userName);
        stage.show();
        return stage;
    }
}
