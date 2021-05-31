package tool;
import Control.tool.*;
import entity.Order;
import org.junit.jupiter.api.Test;
import Control.tool.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderRecordTest {

    @Test
    void findOrder() throws IOException {
        List<Order> testOrder=OrderRecord.findOrder("order.txt","");
        for(Order order:testOrder){
            System.out.println(order.toString());
        }
    }

    @Test
    void findKeyOrder() throws IOException {
        List<Order> testOrder=OrderRecord.findKeyOrder("order.txt","m1","canceled");
        for(Order order:testOrder){
            System.out.println(order.toString());
        }
    }

    @Test
    void coachFindOrder() throws IOException {
        List<Order> testOrder=OrderRecord.coachFindOrder("order.txt","0000001");
        for(Order order:testOrder){
            System.out.println(order.toString());
        }
    }
}