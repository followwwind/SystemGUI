package  Control.staff;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import Control.tool.*;
/**
 * page for manager to add course
 * the course ID will general randomly
 */
public class Staff_addCourse_Controller extends Application implements Initializable {
	@FXML
	private Button add_button;
	@FXML
	private TextField addCourseName,coursevideo,addCourseDes,addcoursedur,membership;
	@FXML
	private ChoiceBox<String> coursetype;

	String newtype = "";
	String filename="LessonInfo.txt";
	private String managerID;
	
	// Event Listener on Button[#update_button].onAction
	@FXML
	public void add_action(ActionEvent event) throws IOException {
		Platform.runLater(() -> {
			Stage stage = (Stage) add_button.getScene().getWindow();
			String newname = addCourseName.getText();
			String newdescription = addCourseDes.getText();
			String newduration = addcoursedur.getText();
			// manager add a new lesson
			String newid = randomId.getRandomId();
			String url = coursevideo.getText();
			String vip = membership.getText();
			if (newtype == "") {
				newtype = "yoga";
			}
			String content = newid + "," + newname + "," + newdescription + "," + newtype + "," + newduration + "," + url + "," + vip;
			try {
				FileUtil.insert(content, filename);
				showStaffPage(managerID);
				stage.hide();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/Staff_addCourse.fxml"));
        stage.setTitle("Staff add recorded courses");
        stage.setScene(new Scene(root, 391, 600));
        stage.show();
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		coursetype.setItems(FXCollections.observableArrayList("yoga", "lose weight", "muscle building"));
		coursetype.getSelectionModel().select(0);
		final String[] string1 = new String[] { "yoga", "lose weight", "muscle building" };
		coursetype.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println(newValue.intValue());
				newtype = string1[newValue.intValue()];
			}
		});
	}
	public void initData(String manID){
		this.managerID=manID;
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

}
