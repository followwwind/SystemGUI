package Control.staff;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import Control.tool.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.recordLesson;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

/**
 * manager modify the recorded course information
 * after select the specified course
 */
public class Staff_update_Course_Controller extends Application implements Initializable {
	@FXML
	private Button update_button;
	@FXML
	private TextField coursevideo, vipState,updatecoursedur,updateCourseDes,updateCourseName;
	private String id;
	private String newtype = "";
	private String lessonFile="LessonInfo.txt";
	private recordLesson modifyCourse;
	@FXML
	private ChoiceBox<String> choosecoursetype;
	private String managerID;
	// Event Listener on Button[#update_button].onAction
	@FXML
	public void update_action(ActionEvent event) throws IOException {
		String new_lesson = id + "," + updateCourseName.getText() + "," + updateCourseDes.getText() + "," + newtype
				+ "," + updatecoursedur.getText()+ "," +coursevideo.getText()+ "," +vipState.getText();
		FileUtil.change(id,new_lesson, lessonFile);
		Stage stage = (Stage) update_button.getScene().getWindow();
		stage.close();
		showStaffPage(managerID);
	}
	// Event Listener on DatePicker[#updateUserBirth].onAction
	public void initData(recordLesson courseId, String managerID) throws IOException {
		this.managerID=managerID;
		this.modifyCourse=courseId;
		this.id =modifyCourse.getReLessId();
		updateCourseName.setText(modifyCourse.getReLessName());
		updateCourseDes.setText(modifyCourse.getDescription());
		updatecoursedur.setText(modifyCourse.getDuration());
		coursevideo.setText(modifyCourse.getUrl());
		vipState.setText(modifyCourse.getVIP());
		String typeold = modifyCourse.getType();
		choosecoursetype.setValue(typeold);
		choosecoursetype.setItems(FXCollections.observableArrayList( "yoga", "lose weight", "muscle building"));
		final String[] string1 = new String[] { "yoga", "lose weight", "muscle building" };
		choosecoursetype.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println(newValue.intValue());
				newtype = string1[newValue.intValue()];
			}
		});
	}
	public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/Staff_update_Course.fxml"));
        stage.setTitle("Modify the recorded courses information");
        stage.setScene(new Scene(root, 394, 600));
        stage.show();
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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
