package Control.tool;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import entity.employee;
import entity.user;
import entity.manager;
/**
 * This class provide several method to get random value
 */

public class randomId {
    private static Random random;
    static{
        random=new Random();
    }
    private randomId(){};

    /**
     * Get the random int value within the given range
     * @param min  the minimum number we need
     * @param max  the maximum number we need
     * @return
     */
    public static int getRandomInt(int min, int max){
        return random.nextInt(max-min+1)+min;
    }

    /**
     * Get a random number between 0 and 9
     * @return
     */
    public static int getNumber(){
        return getRandomInt(0,9);
    }

    /**
     * Get a random upper letter
     * @return
     */
    public static char getUpper(){
        char upper=(char)getRandomInt(65,90);
        return upper;
    }

    /**
     * Get a random lower letter
     * @return
     */
    public static char getLower(){
        char lower=(char)getRandomInt(97,122);
        return lower;
    }

    /**
     * The user's id is randomly assigned, the first id is upper letter, the 2nd one is lower letter
     * the last 3 are random numbers between 0 and 9
     * @return the random id number is String type
     */
    public static String getRandomId(){
            StringBuffer bf=new StringBuffer();
            for(int i=0;i<5;i++){
                if(i==0)
                    bf.append(getUpper());
                if(i==1)
                    bf.append(getLower());
                else
                    bf.append(getNumber());
            }
            String userID=bf.toString();
        return userID;
     }
}
