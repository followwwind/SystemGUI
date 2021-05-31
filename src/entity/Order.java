package entity;

import javafx.beans.property.SimpleStringProperty;
/*
Live course order information
 */
public class Order {
    private SimpleStringProperty orderId;
    private SimpleStringProperty userId;
    private SimpleStringProperty coachId;
    private SimpleStringProperty courseId;
    private SimpleStringProperty lessonNo;
    private SimpleStringProperty lessonDate;
    private SimpleStringProperty payment;//price of the course
    private SimpleStringProperty status; //status of the order
    public Order(String orderId, String userId,String coachId, String courseId, String lessonNo,String lessonDate, String payment, String status) {
        this.orderId=new SimpleStringProperty(orderId);
        this.userId=new SimpleStringProperty(userId);
        this.coachId=new SimpleStringProperty(coachId);
        this.courseId=new SimpleStringProperty(courseId);
        this.lessonNo=new SimpleStringProperty(lessonNo);
        this.lessonDate=new SimpleStringProperty(lessonDate);
        this.payment=new SimpleStringProperty(payment);
        this.status=new SimpleStringProperty(status);
    }
    public String getOrderId() {
        return orderId.get();
    }
    public void setOrderId(String orderId) {
        this.orderId.set(orderId);
    }
    public String getCoachId() {
        return coachId.get();
    }
    public void setCoachId(String coachId) {
        this.coachId.set(coachId);
    }
    public String getCourseId(){return courseId.get();}
    public void setCourseId(String courseId){this.courseId.set(courseId);};
    public String getLessonNo() {
        return lessonNo.get();
    }
    public void setLessonNo(String lessonNo) {
        this.lessonNo.set(lessonNo);
    }
    public String getLessonDate() {
        return lessonDate.get();
    }
    public void setLessonDate(String lessonDate) {
        this.lessonDate.set(lessonDate);
    }
    public String getPayment() {
        return payment.get();
    }
    public void setPayment(String payment) {
        this.payment.set(payment);
    }
    public String getUserId() {
        return userId.get();
    }
    public void setUserId(String userId) {
        this.userId.set(userId);
    }
    public void setStatus(String flag) {
    	this.status.set(flag);
    }
    public String getStatus(){
    	return status.get();
    }
    public String toString() {
        String re=orderId.get()+","+userId.get()+","+coachId.get()+","+courseId.get()+","+lessonNo.get()+","+lessonDate.get()
                +","+payment.get()+","+status.get();
        return re;
    }
}
