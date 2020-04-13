package segGroupCW;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DateFormat {
    private static SimpleDateFormat parser = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static Date convertDate(String time) {
        try {
            return new Date(parser.parse(time).getTime());
        } catch (ParseException e) {
            System.out.println("Date converter failed");
            e.printStackTrace();
            return null;
        }
    }
}
