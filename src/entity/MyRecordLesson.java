package entity;
/*
member's recorded lesson list
 */
public class MyRecordLesson {

    private String user;//member ID

    private recordLesson recordLesson;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public recordLesson getRecordLesson() {
        return recordLesson;
    }

    public void setRecordLesson(recordLesson recordLesson) {
        this.recordLesson = recordLesson;
    }

    @Override
    public String toString() {
        return user + "," + recordLesson.toString();
    }
}
