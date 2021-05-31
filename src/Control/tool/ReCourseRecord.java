package Control.tool;

import entity.MyRecordLesson;
import entity.recharge;
import entity.recordLesson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class manage the file to record lesson
 */
public class ReCourseRecord {
    /**
     * find the member's record lesson
     * @return List of member's record lesson
     */
    public static List<MyRecordLesson> findMyRecordLesson(String fileName) throws IOException {
        List<MyRecordLesson> MyRecordLessonList = new ArrayList<MyRecordLesson>();
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
            if(!string1.isEmpty()) {
                    String[] line = string1.split(",");
                    MyRecordLesson mr = new MyRecordLesson();
                    recordLesson recordLesson = new recordLesson(line[1], line[2], line[3], line[4], line[5], line[6], line[7]);
                    mr.setRecordLesson(recordLesson);
                    mr.setUser(line[0]);
                    MyRecordLessonList.add(mr);
                }
            }
        return MyRecordLessonList;
    }

    /**
     * member find all the record lesson
     * @return List of recorded lesson
     * @throws IOException 
     */
    public static List<recordLesson> searchAll(String userID, String fileName) throws IOException {
    	List<recordLesson> recordLessonList = new ArrayList<recordLesson>();
    	List<String> line = file.open(fileName);
        recharge re=RechargeRecord.getRechargeInfo(userID);
        if(re!=null) {
            String vip = re.getVIPState();
            int vipState=Integer.parseInt(vip);
            for (String string1 : line) {
                if(!line.isEmpty()) {
                    String[] line1 = string1.split(",");
                    int vipMember=Integer.parseInt(line1[6]);
                    if (vipMember<=vipState) {
                        recordLesson recordLesson = new recordLesson(line1[0], line1[1], line1[2], line1[3], line1[4], line1[5], line1[6]);
                        recordLessonList.add(recordLesson);
                    }
                }
            }
        }
		return recordLessonList;
    }

    /**
     * staff find the record lesson
     * @return List of recorded lesson
     * @throws IOException
     */
    public static List<recordLesson> searchAllManager(String fileName) throws IOException {
        List<recordLesson> recordLessonList = new ArrayList<recordLesson>();
        List<String> line = file.open(fileName);
        for (String string1 : line) {
            if(!string1.isEmpty()) {
                String[] line1 = string1.split(",");
                recordLesson recordLesson = new recordLesson(line1[0], line1[1], line1[2], line1[3], line1[4], line1[5], line1[6]);
                recordLessonList.add(recordLesson);
                }
            }
        return recordLessonList;
    }

    /**
     * search recorded lesson by keyword
     * @param keyword the keyword of lesson
     * @param type the type of lesson
     * @return
     * @throws IOException
     */
    public static List<recordLesson> searchLesson(String userID, String keyword, String type,String fileName) throws IOException {
    	List<recordLesson> recordLessonList = new ArrayList<recordLesson>();
    	List<String> line = file.open(fileName);
        recharge re=RechargeRecord.getRechargeInfo(userID);
        if(re!=null) {
            String vip = re.getVIPState();
            int vipState=Integer.parseInt(vip);
            for (String string1 : line) {
                if (type.equals("all")) {
                    if (!string1.isEmpty()&&string1.contains(keyword)) {
                        String[] line1 = string1.split(",");
                        int vipMember=Integer.parseInt(line1[6]);
                        if (vipMember<=vipState) {
                            recordLesson lesson = new recordLesson(line1[0], line1[1], line1[2], line1[3], line1[4], line1[5], line1[6]);
                            recordLessonList.add(lesson); // just preserve those lines which have the type we would like to
                        }
                    }
                } else {
                    if (!string1.isEmpty()) {
                        String[] line1 = string1.split(",");
                        int vipMember=Integer.parseInt(line1[6]);
                        if (string1.contains(keyword) && line1[3].contains(type)&& vipMember<=vipState) {
                            recordLesson lesson = new recordLesson(line1[0], line1[1], line1[2], line1[3], line1[4], line1[5], line1[6]);
                            recordLessonList.add(lesson); // just preserve those lines which have the type we would like to
                        }
                    }
                }
            }
        }
        else if(userID.equals("")){
            for (String string1 : line) {
                if (type.equals("all")) {
                    if (string1.contains(keyword)) {
                        String[] line1 = string1.split(",");
                        recordLesson lesson = new recordLesson(line1[0], line1[1], line1[2], line1[3], line1[4], line1[5], line1[6]);
                        recordLessonList.add(lesson); // just preserve those lines which have the type we would like to
                    }
                } else {
                    if(!string1.isEmpty()){
                        String[] line1 = string1.split(",");
                        if (string1.contains(keyword) && line1[3].contains(type)) {
                            recordLesson lesson = new recordLesson(line1[0], line1[1], line1[2], line1[3], line1[4], line1[5], line1[6]);
                            recordLessonList.add(lesson); // just preserve those lines which have the type we would like to
                        }
                    }
                }
            }
        }
    	return recordLessonList;
    }
}
