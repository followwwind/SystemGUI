package tool;

import Control.tool.*;
import entity.Course;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FileUtilTest {
    private String fileName="test.txt";
    private String testStr="test,1010,tset@qq.com,2000-01-01,19837882345,F,goodTest,tset@qq.com";
    @Test
    void insert() throws IOException {
        FileUtil.insert(testStr,fileName);
        List<String> line1 = file.open(fileName);
        boolean flag=true;
        for (String string1 : line1) {
            if (string1.contains(testStr)) {
                assertEquals(testStr,string1);
            }
        }
    }

    @Test
    void change() throws IOException {
        String newStr="2,good,good,yoga,30min,http,2";
        String oldStr="2";
        FileUtil.change(oldStr,newStr,fileName);
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
            System.out.println(string1);
        }
    }

    @Test
    void delete() throws IOException {
        FileUtil.delete("2","",fileName);
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
            System.out.println(string1);
        }
    }

    @Test
    void testDelete() throws IOException {
        FileUtil.delete("test",fileName);
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
            if (string1.contains("test")) {
                fail("Change Failed");
            }
        }
    }

    @Test
    void findLegalOrder() throws IOException {
        boolean flag=FileUtil.findLegalOrder("order.txt","0000001","2021-05-28");
        assertTrue(flag);
        boolean flag2=FileUtil.findLegalOrder("order.txt","0000001","2021-05-23");
        assertTrue(flag);
    }

    @Test
    void findCourseInfo() throws IOException {
        Course course=FileUtil.findCourseInfo("course.txt","001");
        Course trueCourse=new Course("001","0000001","Messy","1","200","false");
        assertEquals(course.toString(),trueCourse.toString());
        Course falseCourse=new Course("0002","0000002","Messy","1","200","false");
        if(falseCourse.toString().equals(course.toString())){
            fail("find fail");
        }
    }
}