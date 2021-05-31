package Control.tool;

import entity.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * find and update the member, employee and manager's information
 */
public class UserRecord {
    private static String fileName="cus.txt";
    private static String managerName="manager.txt";
    private static String coachName="employee.txt";
    private static Properties man;
    static {
        man=new Properties();
        FileInputStream reader= null;
        try {
            reader = new FileInputStream(managerName);
            man.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error in file");
            System.exit(0);
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception ex) {}
        }
    }

    /**
     *  Write data into customer's file
     */
    private static void listInfo(String file, Properties m, String list) throws FileNotFoundException {
        OutputStream ps=new FileOutputStream(file);
        try {
            m.store(ps,list);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Exception in file operation.");
            System.exit(0);
        }
        finally {
            try {
                ps.close();
            }
            catch (Exception ex) {}
        }
    }

    /**
     * return "user" object from userId, getting user's password and email
     * to get the member
     * @param userId
     * @return
     */
    public static user getUserInfo(String userId) throws IOException {
        user use = new user();
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
            if(!string1.isEmpty()) {
                String[] line = string1.split(",");
                if (line[0].equals(userId)) {
                    use = new user(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]);
                    return use;
                }
            }
        }
       return null;
    }

    /**
     * search member's information
     * @param key the key word for search, if search all, key="";
     * @return list of all user
     */
    public static List<user> searchUser(String key) throws IOException {
        List<user> userList = new ArrayList<user>();
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
                if(!string1.isEmpty()&&string1.contains(key)) {
                    String[] line = string1.split(",");
                    user use = new user(line[0], line[1], line[2], line[3], line[4],line[5],line[6],line[7]);
                    userList.add(use);
                }
            }
        return userList;
    }
    /**
     * search the inviter
     * @param key the invite word for search
     * @return list of user
     */
    public static user searchInviter(String key) throws IOException {
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
            if(!string1.isEmpty()) {
                String[] line = string1.split(",");
                if(line[7].equals(key)) {
                    user use = new user(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]);
                    return use;
                }

            }
        }
        return null;
    }

    /**
     * return "employee" object from userId, getting employee's password and email
     * @param empId
     * @return
     */
    public static employee getEmployeeInfo(String empId) throws IOException {
        employee use = new employee();
        List<String> line1 = file.open(coachName);
        for (String string1 : line1) {
            if(!string1.isEmpty()) {
                    String[] line = string1.split(",");
                    if(line[0].equals(empId)) {
                        use = new employee(line[0], line[1], line[2], line[3], line[4], line[5], line[6]);
                        return use;
                    }
                }
            }
        return null;

    }
    /**
     * search employee' information
     * @param key the key word for search, if search all, key="";
     * @return list of all user
     */
    public static List<employee> searchEmployee(String key) throws IOException {
        List<employee> userList = new ArrayList<employee>();
        List<String> line1 = file.open(coachName);
        for (String string1 : line1) {
            if(!string1.isEmpty()&&string1.contains(key)) {
                    String[] line =string1.split(",");
                    employee use = new employee(line[0], line[1], line[2], line[3], line[4],line[5],line[6]);
                    userList.add(use);
                }
            }
        return userList;
    }
    /**
     *get manager's informaiton
     * @param mangeId the manager's ID
     * @return
     */
    public static manager getManagerInfo(String mangeId) {
        String cusInfo=man.getProperty(mangeId);
        manager manage=new manager();
        if(cusInfo!=null) {
            String[] infos=cusInfo.split("#");
            manage.setId(mangeId);
            manage.setPassword(infos[0]);
            manage.setEmail(infos[1]);
            manage.setBirth(infos[2]);
            manage.setPhone(infos[3]);
            manage.setSex(infos[4]);
            return manage;
        }
        else
            return null;
    }
    /**
     * Write manager information into "manager.txt"
     * @param userId
     * @param userPassword
     * @param userEmail
     * @param userBirth
     * @param userPhoneNo
     * @param Sex
     */
    public static void updateManager(String userId,
                                      String userPassword,String userEmail, String userBirth,
                                      String userPhoneNo, String Sex) throws FileNotFoundException {
        String list=userPassword+"#"+userEmail+"#"+userBirth+"#"+userPhoneNo+"#"+Sex;
        man.setProperty(userId,list);
        listInfo(managerName,man,list);
    }
    /**
     * find coach info by lesson number
     * @param file1 the file's name
     * @return
     */
    public static List<coachLesson> findCoachInfo(String file1) throws IOException {
        List<coachLesson> coachLessons = new ArrayList<coachLesson>();
        List<String> line1 = file.open(file1);
        for (String string1 : line1) {
            if (!string1.isEmpty()) {
                String[] line = string1.split(",");
                coachLesson cl = new coachLesson();
                Course course = new Course(line[3], line[2], line[1], line[4], line[5], line[6]);
                cl.setCoaCourse(course);
                cl.setCoaId(line[0]);
                coachLessons.add(cl);
            }
        }
        return coachLessons;
    }
}
