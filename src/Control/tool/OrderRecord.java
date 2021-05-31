package Control.tool;

import entity.Order;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class will record the member's order information
 */
public class OrderRecord {
    /**
     * find the member's order information
     * @param fileName
     * @param name the member's name
     * @return List of member's order information
     */
    public static List<Order> findOrder(String fileName, String name) throws IOException {
        List<Order> order = new ArrayList<Order>();
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
            if (!string1.isEmpty()) {
                    String[] line = string1.split(",");
                    if(!name.equals("")) {
                        if (line[1].equals(name)) {
                            Order mr = new Order(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]);
                            order.add(mr);
                        }
                    }
                    else{
                        Order mr = new Order(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]);
                        order.add(mr);
                    }
                }
            }
        return order;
    }

    /**
     * find the member's order information by search key word
     * @param fileName
     * @return List of member's order information
     */
    public static List<Order> findKeyOrder(String fileName,String name, String key) throws IOException {
        List<Order> order=findOrder(fileName,name);
        List<Order> result=new ArrayList<>();
        for(Order re:order){
            String temp=re.toString();
            if(temp.contains(key)){
                result.add(re);
            }
        }
        return result;
    }
    /**
     * find the coach's order information
     * @param fileName
     * @param coaId
     * @return List of member's order information
     */
    public static List<Order> coachFindOrder(String fileName,String coaId) throws IOException {
        List<Order> order = new ArrayList<Order>();
        List<String> line1 = file.open(fileName);
        for (String string1 : line1) {
            if (!string1.isEmpty()) {
                    String[] line = string1.split(",");
                    if (line[2].equals(coaId)) {
                        Order mr = new Order(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]);
                        order.add(mr);
                    }
                }
            }

        return order;
    }
}
