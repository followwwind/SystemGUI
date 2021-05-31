package Control.member;

import entity.Order;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Control.tool.OrderRecord;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * member can check their live course schedule
 * they can click the live course to watch the live course
 */
public class User_TrainPlan_Controller extends Application implements Initializable {
    private String name;
    @FXML
    private TableView<Order> trainInfo;
    @FXML
    private TableColumn<Order, String> trainCoaId, trainCourseId, trainLessonDate, trainLessonNo;
    private String filename = "order.txt";
    private static List<Order> orders;

    public void showPlan() throws IOException {
        trainCoaId.setCellValueFactory(new PropertyValueFactory<>("coachId"));
        trainCourseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        trainLessonDate.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        trainLessonNo.setCellValueFactory(new PropertyValueFactory<>("lessonNo"));
        orders = OrderRecord.findOrder(filename, name);
        ObservableList<Order> data = FXCollections.observableArrayList();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            String sta = order.getStatus();
            if(order.getStatus().contains("payed"))
                data.add(order);
        }
        trainInfo.setItems(data);
        trainInfo.setRowFactory( tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Order rowData = row.getItem();
                    try {
                        showLivePage(rowData);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }




    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    public void initTrainData(String userName) throws IOException {
       this.name = userName;
        showPlan();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/user_trainPlan.fxml"));
        stage.setTitle("Train");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
    /**
     * show the stage of living course
     * @param order
     * @return
     * @throws IOException
     */
    public Stage showLivePage(Order order) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/user_livingPage.fxml"
                )
        );
        Locale.setDefault(Locale.ENGLISH);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Live Course Page");
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );

        User_LivingPage_Controller controller =
                loader.<User_LivingPage_Controller>getController();
        controller.initData(order);
        stage.show();
        return stage;
    }
}
