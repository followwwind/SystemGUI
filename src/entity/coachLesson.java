package entity;
/*
store the scheduled coach's information, including the coaches' ID and course entity
 */
public class coachLesson {
    private String coaId;
    private Course coaCourse;
    public String getcoaId(){return coaId;}
    public void setCoaId(String coaId){this.coaId = coaId;}
    public Course getCoaCourse(){return coaCourse;}
    public void setCoaCourse(Course coaCourse){this.coaCourse = coaCourse;}
    @Override
    public String toString(){
        String temp = coaId + "," +coaCourse.toString();
        return temp;
    }

}
