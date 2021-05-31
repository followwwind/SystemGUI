package entity;

import javafx.beans.property.SimpleStringProperty;

import javax.swing.*;
import java.io.FileReader;
import java.util.Properties;
/*
The live courses information
 */
public class Course{
	
    private SimpleStringProperty courseId;
    private SimpleStringProperty coachId;
    private SimpleStringProperty coachName;
    //the no. of lesson, No1 is 7:00-9:00, Lesson 2:"09:00 ~ 11:00";
    //Lesson 3:13:00 ~ 15:00"; Lesson 4:"15:00 ~ 17:00";  Lesson 5:"19:00 ~ 21:00";
    private SimpleStringProperty lessonNo;
    private SimpleStringProperty price;
    private SimpleStringProperty flag;

    public void setCourseId(String courseId){this.courseId.set(courseId);}
    public String getCourseId(){return courseId.get();}
    public void setCoachId(String coachId){this.courseId.set(coachId);}
    public String getCoachId(){return coachId.get();}
    public void setCoachName(String coachName){this.coachName.set(coachName);}
    public String getCoachName(){return coachName.get();}
    public void setLessonNo(String lessonNo){this.courseId.set(lessonNo);}
    public String getLessonNo(){return lessonNo.get();}
    public void setPrice(String price){this.price.set(price);}
    public String getPrice(){return price.get();}
    public void setFlag(String flag){this.flag.set(flag);}
    public String getFlag(){return flag.get();}

    public Course(){}
    public Course(String courseId, String coachId, String coachName, String lessonNO, String price, String flag){
        this.courseId = new SimpleStringProperty(courseId);
        this.coachId = new SimpleStringProperty(coachId);
        this.coachName = new SimpleStringProperty(coachName);
        this.lessonNo = new SimpleStringProperty(lessonNO);
        this.price = new SimpleStringProperty(price);
        this.flag = new SimpleStringProperty(flag);
    }

    @Override
    public String toString(){
        String temp = courseId.get() + ","+ coachId.get() + "," + coachName.get() + "," + lessonNo.get() + "," + price.get() + "," + flag.get();
        return temp;
    }
}
