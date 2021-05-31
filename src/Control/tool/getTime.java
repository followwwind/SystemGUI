package Control.tool;

import javax.xml.crypto.Data;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Get time the function need
 */
public class getTime {
    /**
     * get current time
     * @return String
     */
    public static String getNow() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//set the time format
        return df.format(new Date());
    }
}
