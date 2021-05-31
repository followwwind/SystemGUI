package Control.member;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import entity.*;
import Control.tool.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * member can check their total order
 * they can delete the order and delete the live course at the same time, they will also get the refund
 */
public class User_OrderSearch_Controller extends Application implements Initializable {
    private String name;
    @FXML
    private TextField search_text;
    @FXML
    private TableView<Order> table=new TableView<>();
    @FXML
    private Button cancel_btn;
    @FXML
    private TableColumn<Order,String> orderid,username,coachid,courseid,date,sequence,price,status;
    private static List<Order> MyOrderList = new ArrayList<Order>();
    public String filename="order.txt";
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_orderSearch.fxml"));
        stage.setTitle("Order Search");
        stage.setScene(new Scene(root, 800, 450));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public void initUserData(String userName) throws IOException {
        this.name = userName;
        ObservableList<Order> data = FXCollections.observableArrayList();
        List<Order> order=OrderRecord.findOrder(filename,name);
        for(int i=0;i<order.size();i++){
            Order re=order.get(i);
            if(re.getStatus().contains("payed"))
                data.add(re);
        }
        orderid.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        username.setCellValueFactory(new PropertyValueFactory<>("userId"));
        coachid.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        courseid.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        date.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        sequence.setCellValueFactory(new PropertyValueFactory<>("lessonNo"));
        price.setCellValueFactory(new PropertyValueFactory<>("payment"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.setItems(data);
    }

    /**
     * member can delete the order and the corresponding live course and get the refund.
     * @throws IOException
     */
    public void cancel_action() throws IOException {
        ObservableList<Order> data=table.getSelectionModel().getSelectedItems();
        if(data!=null){
            for (Order order : data) {
                if(order.getStatus().contains("payed")) {
                    String oldString = order.toString();
                    String[] temp = oldString.split(",");
                    String newString = temp[0] + "," + temp[1] + "," + temp[2] + "," + temp[3] + "," + temp[4] + "," + temp[5]
                            + "," + temp[6] + "," + "canceled";
                    FileUtil.change(order.getOrderId(), newString, filename);
                    RechargeRecord rechargeRecord = new RechargeRecord();
                    recharge charge = rechargeRecord.getRechargeInfo(name);
                    float balance = Float.parseFloat(charge.getIsPay()) + Float.parseFloat(temp[6]);
                    recharge re=RechargeRecord.getRechargeInfo(name);
                    String vip=re.getVIPState();
                    rechargeRecord.updateRecharge(name, charge.getChargeId(), String.valueOf(balance), String.valueOf(charge.getRecharge()), charge.getTimeOfCharge(),vip);
                }
            }
        }
        MyOrderList = OrderRecord.findOrder("order.txt",name);
        ObservableList<Order> dataNew = FXCollections.observableArrayList();
        boolean isFound = false;
        for (int i = 0; i < MyOrderList.size(); i++) {
            Order myOrder = MyOrderList.get(i);
            String u = myOrder.getUserId();
            if (u.equals(name)) {
                if(myOrder.getStatus().contains("payed")){
                    dataNew.add(myOrder);
                    isFound = true;
                }
            }
        }
        if (!isFound) {
            System.out.println("no order");
        }
        table.setItems(dataNew);
    }
}
