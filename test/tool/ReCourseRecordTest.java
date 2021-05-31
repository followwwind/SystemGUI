package tool;
import Control.tool.*;
import entity.MyRecordLesson;
import entity.Order;
import entity.recordLesson;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReCourseRecordTest {

    @Test
    void findMyRecordLesson() throws IOException {
        List<MyRecordLesson> testOrder=ReCourseRecord.findMyRecordLesson("mylesson.txt");
        for(MyRecordLesson order:testOrder){
            System.out.println(order.toString());
        }
    }

    @Test
    void searchAll() throws IOException {
        List<recordLesson> re=ReCourseRecord.searchAll("103913","LessonInfo.txt");//vip state=2
        for(recordLesson rec:re){
            if(rec.getVIP().equals("3")){
                fail("search fail");
            }
            System.out.println(rec.toString());
        }

    }


    @Test
    void searchLesson() throws IOException {
        List<recordLesson> recordLessons=ReCourseRecord.searchLesson("103913","","all","LessonInfo.txt");
        for(recordLesson rec:recordLessons) {
            if (rec.getVIP().equals("3")) {
                fail("search fail");
            }
            System.out.println(rec.toString());
        }
        List<recordLesson> recordLesson2=ReCourseRecord.searchLesson("110091","back","lose weight","LessonInfo.txt");
        for(recordLesson rec:recordLesson2) {
            if (rec.getVIP().equals("3")) {
                fail("search fail");
            }
            System.out.println(rec.toString());
        }
    }
}