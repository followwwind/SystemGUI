package Control.coach;

import entity.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Control.tool.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * the coach's home page
 */
public class Coach_HomePage_Controller extends Application implements Initializable {
    @FXML
    private Pane coach_homePage_pane;
    @FXML
    private TabPane coach_tabPane;
    @FXML
    private Pane coaOrderInfo_pane;
    @FXML
    private Pane image_pane;
    @FXML
    private Label coachId_lab, coachPhone_lab, coachEmail_lab,coachName;

    private String name;
    @FXML
    private TableView<Order> coaOrderInfo;
    @FXML
    private TableColumn<Order, String> coaCourseId, traineeId, coaLessonNo, coaLessonDate;
    @FXML
    private Button Modify;
    private String filename = "order.txt";
    private List<Order> orderList;
    private String couName;
    @FXML
    public void show_coach_homePage() {
        coach_homePage_pane.setVisible(true);
        coach_tabPane.setVisible(true);
        coaOrderInfo_pane.setVisible(false);
        image_pane.setVisible(true);
    }

    /**
     * jump to the coach's living schedule page
     */
    public void toCoach_livingCourse_Info() {
        coach_homePage_pane.setVisible(false);
        coach_tabPane.setVisible(true);
        coaOrderInfo_pane.setVisible(true);
        image_pane.setVisible(false);
    }


    /**
     * show the coach's personal information
     * @param name
     * @throws IOException
     */
    public void showCoachInfo(String name) throws IOException {
        employee em = new employee();
        em = UserRecord.getEmployeeInfo(name);
        coachId_lab.setText(em.getEmployeeId());
        String empName=em.getEmployeeName();
        coachName.setText(empName);
        coachPhone_lab.setText(em.getEmployeePhoneNo());
        coachEmail_lab.setText(em.getEmployeeEmail());
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/coach_homePage.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Coach");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * show the information of the live course
     * @throws IOException
     */
    public void showLessonInfo() throws IOException {
        coaCourseId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        traineeId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        coaLessonNo.setCellValueFactory(new PropertyValueFactory<>("lessonNo"));
        coaLessonDate.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
        orderList = OrderRecord.coachFindOrder(filename, name);
        ObservableList<Order> data = FXCollections.observableArrayList();
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            data.add(order);
        }
        coaOrderInfo.setItems(data);
        coaOrderInfo.setRowFactory( tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Order rowData = row.getItem();
                    try {
                        showLivePage(rowData);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
    }

    /**
     * modify the coach's personal information
     */
    public void modify(){
        Platform.runLater(() -> {
            Stage stage = (Stage) Modify.getScene().getWindow();
            try {
                showCoachPage(name);
                stage.hide();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void initCoachData(String userName) throws IOException {
        System.out.println("This is coach page");
        this.name = userName;
        showCoachInfo(name);
        showLessonInfo();
    }

    /**
     * show the stage of coach's information modification page
     * @param userName
     * @return
     * @throws IOException
     */
    public Stage showCoachPage(String userName) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "../../Boundary/coach_update.fxml"
                )
        );
        Locale.setDefault(Locale.ENGLISH);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Coach Update Information");
        stage.setScene(
                new Scene(
                        (Pane) loader.load()
                )
        );

        Coach_Update_Controller controller =
                loader.<Coach_Update_Controller>getController();
        controller.initCoachData(userName);
        stage.show();
        return stage;
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
                        "../../Boundary/coach_liveCourse.fxml"
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

        Coach_LiveCourse_Controller controller =
                loader.<Coach_LiveCourse_Controller>getController();
        controller.initData(order);
        stage.show();
        return stage;
    }

}
