package tool;
import Control.tool.*;
import entity.manager;
import entity.user;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRecordTest {

    @Test
    void searchUser() throws IOException {
        List<user> testAll=UserRecord.searchUser("");
        System.out.println("all user");
        for(user user:testAll){
            System.out.println(user.toString());
        }
        List<user> testSpecify=UserRecord.searchUser("F");
        for(user user:testSpecify){
            if(user.getSex().equals("M")){
                fail("search fail");
            }
        }
    }

    @Test
    void searchInviter() throws IOException {
        user testInvite=UserRecord.searchInviter("1E23A1");
        String invitation=testInvite.getInvitation();
        assertEquals(invitation,"1E23A1");
    }


    @Test
    void updateManager() throws FileNotFoundException {
        UserRecord.updateManager("001","1111","001@126.com","1997-09-30","19088323371","F");
        manager manager=UserRecord.getManagerInfo("001");
        assertEquals(manager.getEmail(),"001@126.com");
        assertEquals(manager.getBirth(),"1997-09-30");
        assertEquals(manager.getPhone(),"19088323371");
        assertEquals(manager.getSex(),"F");
        assertEquals(manager.getPassword(),"1111");
    }

}