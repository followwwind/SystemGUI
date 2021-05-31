package  Control.staff;

import entity.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.event.Event;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Control.member.User_OrderSearch_Controller;
import Control.tool.*;

/**
 * the manager's home page
 */
public class staff_home_Page_Controller extends Application implements Initializable{
	@FXML
	private ChoiceBox<String> chooseCoursetype;
	@FXML
	private Label staffName,staffPhone, staffSex, staffEmail;
	@FXML
	private TextField searchCourse,searchUser,searchCoach,searchOrder;
	@FXML
	private TabPane managerPage;
	@FXML
	private TableView<recordLesson> recordedTable=new TableView<>();
	@FXML
	private TableView<user> userTable=new TableView<>();
	@FXML
	private TableView<employee> coachTable=new TableView<>();
	@FXML
	private TableView<Order> orderTable=new TableView<>();
	@FXML
	private TableColumn<recordLesson,String> courseId, courseName, CourseDescription, CourseType,CourseDuration,VideoURL,Vip;
	@FXML
	private TableColumn<user,String> userID, userSex, userEmail, userPhone, userBirth, userName;
	@FXML
	private TableColumn<employee,String> coachID,coachSex,coachEmail,coachPhone,coachBirth,coachName;
	@FXML
	private TableColumn<Order,String> orderId, userId, coachId,  couId,  startTime, sequence,  payment,  status;
	private String courseType = "";
	
	@FXML
	private TableView<coachRank> coachrankTable;
	
	@FXML
	private TableColumn<coachRank, String> coachRank;
	@FXML
	private TableColumn<coachRank, String> CoachID;
	@FXML
	private TableColumn<coachRank, String> CourseNo;
	@FXML
	private TableView<memberRank> membertable;
	@FXML
	private TableColumn<memberRank, String> memberrank;
	@FXML
	private TableColumn<memberRank, String> memberID;
	@FXML
	private TableColumn<memberRank, String> Money;
	 
	 
	
	private int flag=0;
	
    @FXML
    private PieChart userpiechart;
    @FXML
    private PieChart usersexPieChart;
    
    @FXML
    private TableView<recordCourseRank> recordtable;
    @FXML
    private TableColumn<recordCourseRank,String> recordrank;
    @FXML
    private TableColumn<recordCourseRank,String> recordID;
    @FXML
    private TableColumn<recordCourseRank,String> recordname;
    @FXML
    private TableColumn<recordCourseRank,String> recordnumber;
    private String managerNameSet;

	/**
	 * manager home page setup
	 * @param event
	 */
	@FXML
	public void showHomePage(Event event) {

	}

	/**
	 * Recorded courses management pane set up
	 * @param event
	 * @throws IOException 
	 */
	@FXML
	public void showRecordCoursePage(Event event) throws IOException {
		ObservableList<recordLesson> data = FXCollections.observableArrayList();
		List<recordLesson> record= ReCourseRecord.searchAllManager("LessonInfo.txt");
		for(int i=0;i<record.size();i++){
			recordLesson re=record.get(i);
			data.add(re);
		}
		courseId.setCellValueFactory(new PropertyValueFactory<>("reLessId"));
		courseName.setCellValueFactory(new PropertyValueFactory<>("reLessName"));
		CourseDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		CourseType.setCellValueFactory(new PropertyValueFactory<>("type"));
		CourseDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
		VideoURL.setCellValueFactory(new PropertyValueFactory<>("url"));
		Vip.setCellValueFactory(new PropertyValueFactory<>("VIP"));
		recordedTable.setItems(data);
	}

	/**
	 * manager add course
	 * @param event
	 */
	@FXML
	public void addCourse(ActionEvent event) {
		Platform.runLater(() -> {
			Stage stage = (Stage) managerPage.getScene().getWindow();
			try {
				showCourseAddPage();
				stage.hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * manager modify recorded course
	 * @param event
	 */
	public void modifyCourse(ActionEvent event){
		Platform.runLater(() -> {
			Stage stage = (Stage) managerPage.getScene().getWindow();
			try {
				ObservableList<recordLesson> data=recordedTable.getSelectionModel().getSelectedItems();
				if(data!=null){
					for (recordLesson re : data) {
						showCourseModifyPage(re);
						stage.hide();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * manager delete recorded course
	 * @param event
	 */
	public void deleteCourse(ActionEvent event) throws IOException {
		ObservableList<recordLesson> data=recordedTable.getSelectionModel().getSelectedItems();
		if(data!=null){
			for (recordLesson re : data) {
				String old = re.toString();
				System.out.println(old);
				String[] temp=old.split(",");
				FileUtil.delete(temp[0],temp[0], "LessonInfo.txt");
				FileUtil.delete(temp[0],temp[0],"mylesson.txt");
			}
		}
		ObservableList<recordLesson> dataNew = FXCollections.observableArrayList();
		List<recordLesson> record= ReCourseRecord.searchAllManager("LessonInfo.txt");
		for(int i=0;i<record.size();i++){
			recordLesson re=record.get(i);
			dataNew.add(re);
		}
		recordedTable.setItems(dataNew);
	}

	/**
	 * manager search course information
	 */
	public void searchCourseAction(){
		try {
			//recordedTable.refresh();
			String string = searchCourse.getText();
			if(courseType == "") {
				courseType = "all";
			}
			if (courseType.equals("all")) {
				ObservableList<recordLesson> data = FXCollections.observableArrayList();
				List<recordLesson> record=ReCourseRecord.searchLesson("",string,"all","LessonInfo.txt");
				for(int i=0;i<record.size();i++){
					recordLesson re=record.get(i);
					data.add(re);
				}
				recordedTable.setItems(data);
			}
			else{
				ObservableList<recordLesson> data = FXCollections.observableArrayList();
				List<recordLesson> recordLessons=ReCourseRecord.searchLesson("",string,courseType,"LessonInfo.txt");
				for(int i=0;i<recordLessons.size();i++){
					recordLesson record=recordLessons.get(i);
					data.add(record);
				}
				recordedTable.setItems(data);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * refresh the recorded course table
	 */
	public void refresh() throws IOException {
		ObservableList<recordLesson> data = FXCollections.observableArrayList();
		List<recordLesson> record= ReCourseRecord.searchAllManager("LessonInfo.txt");
		for(int i=0;i<record.size();i++){
			recordLesson re=record.get(i);
			data.add(re);
		}
		recordedTable.setItems(data);
	}

	/**
	 * user management page setup
	 * @param event
	 */
	@FXML
	public void UserManage(Event event) throws IOException {
		ObservableList<user> data = FXCollections.observableArrayList();
		List<user> use= UserRecord.searchUser("");
		for(int i=0;i<use.size();i++){
			user re=use.get(i);
			data.add(re);
		}
		userID.setCellValueFactory(new PropertyValueFactory<>("userId"));
		userSex.setCellValueFactory(new PropertyValueFactory<>("Sex"));
		userPhone.setCellValueFactory(new PropertyValueFactory<>("userPhoneNo"));
		userBirth.setCellValueFactory(new PropertyValueFactory<>("userBirth"));
		userEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
		userTable.setItems(data);
	}

	/**
	 * manager modify member's information
	 * @param event
	 */
	public void modifyUser(Event event) throws IOException {
		Platform.runLater(() -> {
			Stage stage = (Stage) managerPage.getScene().getWindow();
			ObservableList<user> data = userTable.getSelectionModel().getSelectedItems();
			if (data != null) {
				for (user re : data) {
					String old = re.toString();
					String[] temp = old.split(",");
					try {
						showMemberPage(temp[0]);
						stage.hide();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * manager search user's information by key word
	 * @param event
	 */
	public void searchUserAction(Event event) throws IOException {
		//userTable.refresh();
		String key = searchUser.getText();
		ObservableList<user> data = FXCollections.observableArrayList();
		List<user> users=UserRecord.searchUser(key);
		for(int i=0;i<users.size();i++){
			user use=users.get(i);
			data.add(use);
		}
		userTable.setItems(data);
	}

	public void refresh_Member() throws IOException {
		ObservableList<user> data = FXCollections.observableArrayList();
		List<user> use= UserRecord.searchUser("");
		for(int i=0;i<use.size();i++){
			user re=use.get(i);
			data.add(re);
		}
		userTable.setItems(data);
	}


	/**
	 * coach management page setup
	 * @param event
	 */
	@FXML
	public void coachManage(Event event) throws IOException {
		ObservableList<employee> data = FXCollections.observableArrayList();
		List<employee> employee= UserRecord.searchEmployee("");
		for(int i=0;i<employee.size();i++){
			employee re=employee.get(i);
			data.add(re);
		}
		coachID.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
		coachSex.setCellValueFactory(new PropertyValueFactory<>("EmployeeSex"));
		coachPhone.setCellValueFactory(new PropertyValueFactory<>("EmployeePhoneNo"));
		coachBirth.setCellValueFactory(new PropertyValueFactory<>("EmployeeBirth"));
		coachEmail.setCellValueFactory(new PropertyValueFactory<>("EmployeeEmail"));
		coachName.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
		coachTable.setItems(data);
	}

	/**
	 * manager modify coach's information
	 * @param event
	 */
	public void modifyCoach(Event event){
		Platform.runLater(() -> {
			Stage stage = (Stage) managerPage.getScene().getWindow();
			ObservableList<employee> data = coachTable.getSelectionModel().getSelectedItems();
			if (data != null) {
				for (employee re : data) {
					String old = re.toString();
					String[] temp = old.split(",");
					try {
						showCoachModifyPage(temp[0]);
						stage.hide();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * manager add coach's information
	 * @param event
	 */
	public void addCoachAction(Event event){
		Platform.runLater(() -> {
			Stage stage = (Stage) managerPage.getScene().getWindow();
			try {
				showCoachAddPage();
				stage.hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * manager delete coach, all file contains coach will be deleted
	 * @param event
	 */
	public void deleteCoach(Event event) throws IOException {
		ObservableList<employee> data=coachTable.getSelectionModel().getSelectedItems();
		if(data!=null){
			for (employee re : data) {
				String old = re.toString();
				String[] temp=old.split(",");
				String coachID=temp[0];
				FileUtil.delete(temp[0],"employee.txt");
				FileUtil.delete(temp[0],"course.txt");
				FileUtil.delete(temp[0],"coachLesson.txt");
				FileUtil.delete(temp[0],"order.txt");
			}
		}
		ObservableList<employee> dataNew = FXCollections.observableArrayList();
		List<employee> record= UserRecord.searchEmployee("");
		for(int i=0;i<record.size();i++){
			employee re=record.get(i);
			dataNew.add(re);
		}
		coachTable.setItems(dataNew);
	}
	/**
	 * manager search coach
	 * @param event
	 */
	public void searchCoach(Event event) throws IOException {
		//coachTable.refresh();
		String key = searchCoach.getText();
		ObservableList<employee> data = FXCollections.observableArrayList();
		List<employee> users=UserRecord.searchEmployee(key);
		for(int i=0;i<users.size();i++){
			employee use=users.get(i);
			data.add(use);
		}
		coachTable.setItems(data);
	}

	public void refresh_coach() throws IOException {
		ObservableList<employee> data = FXCollections.observableArrayList();
		List<employee> employee= UserRecord.searchEmployee("");
		for(int i=0;i<employee.size();i++){
			employee re=employee.get(i);
			data.add(re);
		}
		coachTable.setItems(data);
	}
	/**
	 * order management page setup
	 * @param event
	 */
	@FXML
	public void OrderManage(Event event) throws IOException {
		ObservableList<Order> data = FXCollections.observableArrayList();
		List<Order> use= OrderRecord.findOrder("order.txt","");
		for(int i=0;i<use.size();i++){
			Order re=use.get(i);
			data.add(re);
		}
		orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
		userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
		coachId.setCellValueFactory(new PropertyValueFactory<>("coachId"));
		couId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
		startTime.setCellValueFactory(new PropertyValueFactory<>("lessonDate"));
		sequence.setCellValueFactory(new PropertyValueFactory<>("lessonNo"));
		payment.setCellValueFactory(new PropertyValueFactory<>("payment"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
		orderTable.setItems(data);
	}

	/**
	 * manager search order by key word
	 * @param event
	 */
	public void searchOrderAction(Event event) throws IOException {
		//orderTable.refresh();
		String key = searchOrder.getText();
		ObservableList<Order> data = FXCollections.observableArrayList();
		List<Order> users=OrderRecord.findKeyOrder("order.txt","",key);
		for(int i=0;i<users.size();i++){
			Order use=users.get(i);
			data.add(use);
		}
		orderTable.setItems(data);
	}

	/**
	 * open the modify page for manager
	 * @param event
	 */
	@FXML
	public void modify_manage_info(MouseEvent event) {
		Platform.runLater(() -> {
			Stage stage = (Stage) managerPage.getScene().getWindow();
			Staff_modify_infoController open = new Staff_modify_infoController();
			try {
				showManagerPage();
				stage.hide();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * show the chart for user statics
	 * show the user's age and sex statics information
	 * @throws IOException
	 */
	@FXML
	public void showUserPieChart() throws IOException {
		userpiechart.setVisible(true);
		usersexPieChart.setVisible(true);
		coachrankTable.setVisible(false);
		membertable.setVisible(false);
		recordtable.setVisible(false);
		int a =0,b=0,c=0,d = 0,m=0,f = 0;
		List<String> string = file.open("cus.txt");
		Calendar now = Calendar.getInstance();  
		int yearnow = now.get(Calendar.YEAR);
		for (String string1 :string) { 
			String[] line1 = string1.split(",");
			String old = line1[3];
			String sex = line1[5];
			String[] line2 = old.split("-");
			int yearcus = Integer.valueOf(line2[0]);
			int year = yearnow-yearcus;
			if (year<=18) {
				a++;
			}
			else if((year>18)&&(year<=30)) {
				b++;
			}
			else if ((year>30)&&(year<=40)) {
				c++;
			}
			else{
				d++;
			}
			if(sex.contentEquals("M")) {
				m++;
			}
			else {
				f++;
			}
		}
		int total = a+b+c+d;
		int pa = a*100/total;
		int pb= b*100/total;
		int pc = c*100/total;
		int pd = d*100/total;
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("age<=18: "+a+" "+pa+"%", a),
                new PieChart.Data("18<age<=30: "+b+" "+pb+"%", b),
                new PieChart.Data("30<age<=40: "+c+" "+pc+"%", c),
                new PieChart.Data("age>40: "+d+" "+pd+"%", d) );
		userpiechart.setData(pieChartData);
		int total2= f+m;
		int pf = f*100/total;
		int pm = m*100/total;
		ObservableList<PieChart.Data> pieChartData2 =
                FXCollections.observableArrayList(
                new PieChart.Data("F: "+f+ " "+ pf+"%", f),
                new PieChart.Data("M: "+m+" "+pm+"%", m));
		usersexPieChart.setData(pieChartData2);
	}


	/**
	 * show the table for coach statics
	 * the coach will be ranked by the number they are scheduled for live course
	 * if the order is canceled, it will not take into account
	 * @throws IOException
	 */
	@FXML
	public void showCoachchart() throws IOException {
		coachrankTable.setVisible(true);
		List<String> string = file.open("order.txt");
		membertable.setVisible(false);
		userpiechart.setVisible(false);
		usersexPieChart.setVisible(false);
		recordtable.setVisible(false);
		List<String> string2 = file.open("employee.txt");
		ObservableList<coachRank> obList = FXCollections.observableArrayList();
		int count = 0, flag = 0;
		for (String string0: string2) {
			String[] line2 = string0.split(",");
			String idcoach1 = line2[0];
			count=0;
			for (String string1 :string) { 
				String[] line1 = string1.split(",");
				String idcoach2 = line1[2];
				if(idcoach1.contentEquals(idcoach2)&&!line1[7].equals("canceled")) {
					count++;
				}
			}
			if(count!=0) {
				flag++;
				coachRank coachrank = new coachRank();
				coachrank.setRank(flag+"");
				coachrank.setId(idcoach1);
				coachrank.setNumber(count+"");
				obList.add(coachrank);
			}
		}
		coachCompare mc = new coachCompare() ; 
		Collections.sort(obList, mc); 
		int flag1 = 1;
		for (coachRank string3: obList) {
			string3.setRank(flag1+"");
			flag1++;
		}		 
		coachRank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		CoachID.setCellValueFactory(new PropertyValueFactory<>("id"));
		CourseNo.setCellValueFactory(new PropertyValueFactory<>("number"));
		coachrankTable.setItems(obList);
	}

	/**
	 * show the table for member's total recharge statics
	 * @throws IOException
	 */
	@FXML
	public void showmemberchart() throws IOException {
		coachrankTable.setVisible(false);
		membertable.setVisible(true);
		userpiechart.setVisible(false);
		usersexPieChart.setVisible(false);
		recordtable.setVisible(false);
		List<String> string = file.open("member.txt");
		ObservableList<memberRank> obList = FXCollections.observableArrayList();
		int line=0;
		int count = 0, flag = 0;
		for (String string0: string) {
			line++;
			if(line<=3) {
					continue;
			}
			else {
				String[] line2 = string0.split("#");
				String userid = line2[0];
				userid = userid.substring(0, userid.length()-1);
				int index = userid.indexOf("=");
				userid = userid.substring(0, index);
				String money = line2[2];
				money = money.substring(0, money.length()-1);
				for (memberRank list: obList) {
					if(list.getId().contentEquals(userid)) {
						String string1 = list.getMoney();
						int  count1 = Integer.parseInt(list.getRank());
						String str1 = string1.substring(0, string1.length());
						if((Double.valueOf(money))>(Double.valueOf(str1))){
							memberRank memberRank2 = new memberRank();
							memberRank2.setRank(flag+"");
							memberRank2.setId(userid);
							memberRank2.setMoney(money);
							obList.set(count1,memberRank2);
						}
						flag = 1;
					}
				}
				if(flag==0) {
					memberRank memberRank1 = new memberRank();
					memberRank1.setRank(count+"");
					memberRank1.setId(userid);
					memberRank1.setMoney(money);
					obList.add(memberRank1);
					count++;
				}
				flag=0;
			}
		}
		memberCompare mc = new memberCompare() ; 
		Collections.sort(obList, mc); 
		int flag1 = 1;
		for (memberRank string3: obList) {
			string3.setRank(flag1+"");
			flag1++;
		}
		memberrank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		memberID.setCellValueFactory(new PropertyValueFactory<>("id"));
		Money.setCellValueFactory(new PropertyValueFactory<>("money"));
		membertable.setItems(obList);
	}

	/**
	 * show the table for recorded course statics
	 * it will show the rank of ordered recorded course number
	 * @throws IOException
	 */
	@FXML
	public void showrecordchart() throws IOException {
		coachrankTable.setVisible(false);
		membertable.setVisible(false);
		recordtable.setVisible(true);
		userpiechart.setVisible(false);
		usersexPieChart.setVisible(false);
		List<String> string = file.open("mylesson.txt");
		List<String> string2 = file.open("LessonInfo.txt");
		ObservableList<recordCourseRank> obList = FXCollections.observableArrayList();
		int count = 0, flag = 0;
		for (String string0: string2) {
			String[] line2 = string0.split(",");
			String idcoach1 = line2[0];
			String name = line2[1];
			count=0;
			for (String string1 :string) {
				String[] line1 = string1.split(",");
				String idcoach2 = line1[1];
				if(idcoach1.contentEquals(idcoach2)) {
					count++;
				}
			}
			if(count!=0) {
				flag++;
				recordCourseRank recordCourseRank = new recordCourseRank();
				recordCourseRank.setRank(flag+"");
				recordCourseRank.setId(idcoach1);
				recordCourseRank.setNumber(count+"");
				recordCourseRank.setName(name);
				obList.add(recordCourseRank);
			}
		}

		recordCompare mc = new recordCompare() ; 
		Collections.sort(obList, mc); 
		int flag1 = 1;
		for (recordCourseRank string3: obList) {
			string3.setRank(flag1+"");
			flag1++;
		}
		recordrank.setCellValueFactory(new PropertyValueFactory<>("rank"));
		recordID.setCellValueFactory(new PropertyValueFactory<>("id"));
		recordname.setCellValueFactory(new PropertyValueFactory<>("name"));
		recordnumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		recordtable.setItems(obList);
	}

	
	 public void start(Stage stage) throws Exception {
	        Parent root = FXMLLoader.load(getClass().getResource("../../Boundary/staff_homePage.fxml"));
	        stage.setTitle("Staff");
	        stage.setScene(new Scene(root, 800, 600));
	        stage.show();
	    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		chooseCoursetype.setItems(FXCollections.observableArrayList("all", "yoga", "lose weight", "muscle building"));
		chooseCoursetype.getSelectionModel().select(0);
		final String[] string1 = new String[] { "all", "yoga", "lose weight", "muscle building" };
		chooseCoursetype.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				courseType = string1[newValue.intValue()];
			}
		});
		try {
			showUserPieChart();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void initData(String userName){
		staffName.setText(userName);
		manager me=UserRecord.getManagerInfo(userName);
		this.managerNameSet=userName;
		staffSex.setText(me.getSex());
		staffEmail.setText(me.getEmail());
		staffPhone.setText(me.getPhone());
	}

	/**
	 * modify the manager's personal information
	 * @return
	 * @throws IOException
	 */
	public Stage showManagerPage() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						"../../Boundary/Staff_modify_info.fxml"
				)
		);
		Locale.setDefault(Locale.ENGLISH);
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(
				new Scene(
						(Pane) loader.load()
				)
		);
		Staff_modify_infoController controller =
				loader.<Staff_modify_infoController>getController();
		loader.<User_OrderSearch_Controller>getController();
		controller.initData(this.managerNameSet);
		stage.show();
		return stage;
	}

	/**
	 *pass data to modify member's page
	 * @param userID
	 * @return
	 * @throws IOException
	 */
	public Stage showMemberPage(String userID) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						"../../Boundary/Staff_modify_user.fxml"
				)
		);
		Locale.setDefault(Locale.ENGLISH);
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(
				new Scene(
						(Pane) loader.load()
				)
		);

		Staff_Modify_User_Controller controller =
				loader.<Staff_Modify_User_Controller>getController();
		loader.<User_OrderSearch_Controller>getController();
		controller.initData(userID,this.managerNameSet);
		stage.show();
		return stage;
	}

	/**
	 *pass data to modify coach's page
	 * @param empID
	 * @return
	 * @throws IOException
	 */
	public Stage showCoachModifyPage(String empID) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						"../../Boundary/Staff_modify_coach.fxml"
				)
		);
		Locale.setDefault(Locale.ENGLISH);
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(
				new Scene(
						(Pane) loader.load()
				)
		);

		Staff_modify_coach_Controller controller =
				loader.<Staff_modify_coach_Controller>getController();
		loader.<User_OrderSearch_Controller>getController();
		controller.initData(empID,this.managerNameSet);
		stage.show();
		return stage;
	}
	/**
	 *pass data to add coach's page
	 * @return
	 * @throws IOException
	 */
	public Stage showCoachAddPage() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						"../../Boundary/staff_add_coach.fxml"
				)
		);
		Locale.setDefault(Locale.ENGLISH);
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(
				new Scene(
						(Pane) loader.load()
				)
		);

		Staff_add_coach_Controller controller =
				loader.<Staff_add_coach_Controller>getController();
		loader.<User_OrderSearch_Controller>getController();
		controller.initData(this.managerNameSet);
		stage.show();
		return stage;
	}

	/**
	 * pass data to recorded course modify page
	 * @param courseId
	 * @return
	 * @throws IOException
	 */
	public Stage showCourseModifyPage(recordLesson courseId) throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						"../../Boundary/Staff_update_Course.fxml"
				)
		);
		Locale.setDefault(Locale.ENGLISH);
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(
				new Scene(
						(Pane) loader.load()
				)
		);

		Staff_update_Course_Controller controller =
				loader.<Staff_update_Course_Controller>getController();
		loader.<User_OrderSearch_Controller>getController();
		controller.initData(courseId,this.managerNameSet);
		stage.show();
		return stage;
	}
	/**
	 * pass data to  "add recorded course" page
	 * @return
	 * @throws IOException
	 */
	public Stage showCourseAddPage() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource(
						"../../Boundary/Staff_addCourse.fxml"
				)
		);
		Locale.setDefault(Locale.ENGLISH);
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(
				new Scene(
						(Pane) loader.load()
				)
		);

		Staff_addCourse_Controller controller =
				loader.<Staff_addCourse_Controller>getController();
		loader.<User_OrderSearch_Controller>getController();
		controller.initData(this.managerNameSet);
		stage.show();
		return stage;
	}
}
