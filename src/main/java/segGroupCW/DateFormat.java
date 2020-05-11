package segGroupCW;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateFormat {
    private static SimpleDateFormat parser = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date convertDate(String time) {
        try {
            return new Date(parser.parse(time).getTime());
        } catch (ParseException e) {
            System.out.println("Date converter failed");
            e.printStackTrace();
            return null;
        }
    }

    public Date largestDate(Date firstDate, Date secondDate){
        if(firstDate != null && secondDate != null) {
            if (firstDate.compareTo(secondDate) > 0) {
                return firstDate;
            }
            return secondDate;
        }
        else {
            return new Date();
        }
    }

    public Date smallestDate(Date firstDate, Date secondDate){
        if(firstDate != null && secondDate != null) {
            if (firstDate.compareTo(secondDate) < 0) {
                return firstDate;
            }
            return secondDate;
        }
        else return new Date();
    }

    public static LocalDate asLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        return zdt.toLocalDate();
    }

    public static boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        LocalDate localDate2 = date2.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    public static LocalDate dayComparable(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
