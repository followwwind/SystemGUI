package tool;

import Control.tool.RechargeRecord;
import entity.recharge;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class RechargeRecordTest {
    private String fileName="member.txt";

    @Test
    void payMoney() throws FileNotFoundException {
        float result= RechargeRecord.payMoney("103913","50");
        assertEquals(result,150);
    }

    @Test
    void rechargeMoney() throws FileNotFoundException {
        float balance=RechargeRecord.rechargeMoney("103913","100");
        recharge re=RechargeRecord.getRechargeInfo("103913");
        String vip=re.getVIPState();
        assertEquals(vip,"3");
        assertEquals(350,balance);

    }
}