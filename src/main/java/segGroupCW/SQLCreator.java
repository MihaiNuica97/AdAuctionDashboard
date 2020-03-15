package segGroupCW;

import java.util.ArrayList;

public class SQLCreator {

    /**
     * SQL statement to calculate the total impressions.
     *
     * @return    String of the SQL statement
     */
    public String numOfImps() {
        return "SELECT COUNT(*) FROM IMPRESSIONS;";
    }

    /**
     * SQL statement to calculate the total clicks.
     *
     * @return      String of the SQL statement
     */
    public String numOfClicks() { return "SELECT COUNT(*) FROM CLICKS;";}

    /**
     * SQL statement to calculate the total clicks from unique users.
     *
     * @return      String of the SQL statement
     */
    public String numOfUniques() { return "SELECT COUNT(DISTINCT (ID)) FROM CLICKS;";}

    /**SQL statement for total bounces based on time spent on the site. The user can define the
     * unit used and how many of said unit. Unit can be second, minute, hour, day, month, or year.
     * <p>
     * Ex. unit as seconds and time as 10 means stays under 10 seconds will be counted as bounces
     *
     * @param unit  What unit the time is in
     * @param time  How long the visit needs to be to not be a bounce
     * @return      String of the SQL statement
     */
    public String bounceByTime(String unit, String time) {
        return "SELECT COUNT(*) FROM SERVER WHERE DATEDIFF(" + unit + ", ENTRYDATE, EXITDATE) < " + time + ";";
    }

    /**
     * SQL statement for total bounces based on pages visited. The user can define
     * the minimum no. of pages visited for visit to not be a bounce.
     * <p>
     * Ex. pages as 3 means visits of 1 or 2 pages are bounces and 3 pages are not
     *
     * @param pages Number of pages to not count as a bounce
     * @return      String of the SQL statement
     */
    public String bounceByPages(String pages) {
        return "SELECT COUNT(*) FROM SERVER WHERE PAGESVIEWED < " + pages + ";";
    }

    public String bounceByConv() {
        return "SELECT COUNT(*) FROM SERVER WHERE CONVERSION = FALSE;";
    }

    /**
     * SQL statement to calculate the total conversions.
     *
     * @return      String of the SQL statement
     */
    public String numOfConvs() { return "SELECT COUNT(*) FROM SERVER WHERE CONVERSION = TRUE;";}

    /**
     * SQL statements to calculate the total cost.
     *
     * @return      ArrayList of the SQL statements
     */
    public ArrayList<String> totalCost() {
        ArrayList<String> result = new ArrayList<>();
        result.add("SELECT SUM(COST) FROM IMPRESSIONS;");
        result.add("SELECT SUM(CLICKCOST) FROM CLICKS;");
        return result;
    }

    /**
     * SQL statements to calculate the click-through-rate.
     *
     * @return      ArrayList of the SQL statements
     */
    public ArrayList<String> ctr() {
        ArrayList<String> result = new ArrayList<>();
        result.add(numOfClicks());
        result.add(numOfImps());
        return result;
    }

    /**
     * SQL statements to calculate the clicks-per-acquisition.
     *
     * @return      ArrayList of the SQL statements
     */
    public ArrayList<String> cpa() {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(totalCost());
        result.add(numOfConvs());
        return result;
    }

    /**
     * SQL statements to calculate the cost-per-click.
     *
     * @return      ArrayList of the SQL statements
     */
    public ArrayList<String> cpc() {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(totalCost());
        result.add(numOfClicks());
        return result;
    }

    /**
     * SQL statements to calculate the cost-per-thousand-impressions.
     *
     * @return      ArrayList of the SQL statements
     */
    public ArrayList<String> cpm() {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(totalCost());
        result.add(numOfImps());
        return result;
    }

    /**
     * SQL statements to calculate the bounce rate using the page definition.
     *
     * @param pages The maximum number of pages that counts as a bounce
     * @return      ArrayList of the SQL statements
     */
    public ArrayList<String> bounceRatePage(String pages){
        ArrayList<String> result = new ArrayList<>();
        result.add(bounceByPages(pages));
        result.add(numOfClicks());
        return result;
    }

    /**
     * SQL statements to calculate the bounce rate using the time definition.
     *
     * @param unit  The unit of the time
     * @param time  The amount of time that qualifies a visit as a bounce
     * @return      ArrayList of the SQL statements
     */
    public ArrayList<String> bounceRateTime(String unit, String time){
        ArrayList<String> result = new ArrayList<>();
        result.add(bounceByTime(unit, time));
        result.add(numOfClicks());
        return result;
    }

    public ArrayList<String> bounceRateConv() {
        ArrayList<String> result = new ArrayList<>();
        result.add(bounceByConv());
        result.add(numOfClicks());
        return result;
    }

    /* Started customisation statements
     * splitting out "between date" or "unique ID" or "cost greater than"
     * into their own functions so that they can be reused by each different metric
     * ie. if you want clicks with between a date the statement is clicks + date(start, end)
     * but you can also do impressions + date(start, end)
     */

    //CUSTOM endings
    /**
     * SQL statement for a generic where statement between 2 dates. Both dates should be in YYYY-MM-DD [HH:MM:SS] format.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      String of the SQL statement
     */
    public String betweenDate(String date, String start, String end) {
        return date + "=> TIMESTAMP '" + start + "' AND " + date + " <= '" + end + "'";
    }

    //CUSTOM builders
    /**
     * SQL statement for the total impressions between 2 dates. Both dates should be in YYYY-MM-DD [HH:MM:SS] format.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      String of the SQL statement
     */
    public String betweenDateImps(String start, String end) {
        return "SELECT COUNT(*) FROM IMPRESSIONS WHERE " + betweenDate("DATE", start, end) + ";";
    }

    /**
     * SQL statement for the total clicks between 2 dates. Both dates should be in YYYY-MM-DD [HH:MM:SS] format.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      String of the SQL statement
     */
    public String betweenDateClicks(String start, String end) {
        return "SELECT COUNT(*) FROM CLICKS WHERE " + betweenDate("DATE", start, end) + ";";
    }

    /**
     * SQL statement for total uniques between 2 dates.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      String of the SQL statement
     */
    public String betweenDateUniques(String start, String end) {
        return "SELECT COUNT(DISTINCT(ID)) FROM CLICKS WHERE " + betweenDate("DATE", start, end) + ";";
    }

    /**
     * SQL statement for bounces between 2 dates using the pages viewed definition.
     *
     * @param start The earlier date
     * @param end   The later date
     * @param pages Maximum number of pages that counts as a bounce
     * @return      String of the SQL statement
     */
    public String betweenDateBouncesPage(String start, String end, String pages) {
        return "SELECT COUNT(*) FROM SERVER WHERE PAGESVIEWED = " + pages + " AND " + betweenDate("ENTRYDATE", start, end)   + ";";
    }

    /**
     * SQL statement for bounces between 2 dates using the time definition
     *
     * @param start The earlier date
     * @param end   The later date
     * @param unit  The unit that the time is in
     * @param time  The amount of time that qualifies a visit as not a bounce
     * @return      String of the SQL statement
     */
    public String betweenDateBouncesTime(String start, String end, String unit, String time) {
        return "SELECT COUNT(*) FROM SERVER WHERE DATEDIFF(" + unit + ", ENTRYDATE, EXITDATE) < " + time + " AND " + betweenDate("ENTRYDATE", start, end) + ";";
    }

    /**
     * SQL statement for conversions between 2 dates.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      String of the SQL statement
     */
    public String betweenDateConvs(String start, String end) {
        return "SELECT COUNT(*) FROM SERVER WHERE CONVERSION = TRUE AND " + betweenDate("ENTRYDATE", start, end) + ";";
    }

    /**
     * SQL statements for the total cost between 2 dates.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      ArrayList of strings of the SQL statements
     */
    public ArrayList<String> betweenDateTotalCost(String start, String end) {
        ArrayList<String> result = new ArrayList<>();
        result.add("SELECT SUM(COST) FROM IMPRESSIONS" + betweenDate("DATE", start, end) + ";");
        result.add("SELECT SUM(CLICKCOST) FROM CLICKS" + betweenDate("DATE", start, end) + " ;");
        return result;
    }

    /**
     * SQL statements for the Click-Through-Rate between 2 dates.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      ArrayList of strings of the SQL statements
     */
   public ArrayList<String> betweenDateCTR(String start, String end) {
       ArrayList<String> result = new ArrayList<>();
       result.add(betweenDateClicks(start, end));
       result.add(betweenDateImps(start, end));
       return result;
   }

    /**
     * SQL statements for the Cost-Per-Acquisition between 2 dates
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      ArrayList of the strings of the SQL statements
     */
   public ArrayList<String> betweenDateCPA(String start, String end) {
       ArrayList<String> result = new ArrayList<>();
       result.addAll(betweenDateTotalCost(start, end));
       result.add(betweenDateConvs(start, end));
       return result;
   }

    /**
     * SQL statements for the Cost-Per-Click between 2 dates
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      ArrayList of the strings of the SQL statements
     */
   public ArrayList<String> betweenDateCPC(String start, String end) {
       ArrayList<String> result = new ArrayList<>();
       result.addAll(betweenDateTotalCost(start, end));
       result.add(betweenDateClicks(start, end));
       return result;
   }

    /**
     * SQL statements for the Cost-Per-Thousand-Impressions between 2 dates
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      ArrayList of the strings of the SQL statements
     */
   public ArrayList<String> betweenDateCPM(String start, String end) {
       ArrayList<String> result = new ArrayList<>();
       result.addAll(betweenDateTotalCost(start, end));
       result.add(betweenDateImps(start, end));
       return result;
   }

    /**
     * SQL statements for the bounce rate between 2 dates using the page definition.
     *
     * @param start The earlier date
     * @param end   The later date
     * @param pages The maximum number of pages that counts as a bounce
     * @return      ArrayList of the strings of the SQL statements
     */
   public ArrayList<String> betweenDateBRPage(String start, String end, String pages){
        ArrayList<String> result = new ArrayList<>();
        result.add(betweenDateBouncesPage(start, end, pages));
        result.add(betweenDateClicks(start, end));
        return result;
    }

    /**
     * SQL statements for the bounce rate between 2 dates using the time definition.
     *
     * @param start The earlier date
     * @param end   The later date
     * @param unit  The unit for the time
     * @param time  The amount of time that qualifies a visit as not a bounce
     * @return      ArrayList of the strings of the SQL statements
     */
    public ArrayList<String> betweenDateBRTime(String start, String end, String unit, String time){
        ArrayList<String> result = new ArrayList<>();
        result.add(betweenDateBouncesTime(start, end, unit, time));
        result.add(betweenDateClicks(start, end));
        return result;
    }

    /**
     * SQL statements to remove old tables if they exist and create new ones for the new database.
     * @return  String of all the SQL statements
     */
    public String createDB(){

             return "DROP TABLE IF EXISTS clicks, server, impressions, users;" + System.lineSeparator() +

                    "CREATE TABLE users ( ID BIGINT PRIMARY KEY," +
                    " Gender VARCHAR(255)," +
                    " Age VARCHAR(255)," +
                    " Income VARCHAR(255) );" + System.lineSeparator() +

                    "CREATE TABLE impressions ( ID BIGINT," +
                    " Date TIMESTAMP," +
                    " Context VARCHAR(255)," +
                    " Cost FLOAT(7)," +
                    " FOREIGN KEY (ID) REFERENCES USERS(ID)," +
                    " PRIMARY KEY (ID, Date) );" + System.lineSeparator() +

                    "CREATE TABLE clicks ( ID BIGINT," +
                    " Date TIMESTAMP," +
                    " ClickCost FLOAT(8)," +
                    " FOREIGN KEY (ID) REFERENCES USERS(ID)," +
                    " PRIMARY KEY (ID, Date) );" + System.lineSeparator() +

                    "CREATE TABLE server ( ID BIGINT," +
                    " EntryDate TIMESTAMP," +
                    " ExitDate TIMESTAMP," +
                    " PagesViewed INT," +
                    " Conversion BOOLEAN," +
                    " FOREIGN KEY (ID) REFERENCES USERS(ID)," +
                    " PRIMARY KEY (ID, EntryDate) );";

    }

    /**
     * SQL statement to get the first and last date from a given database table.
     *
     * @param table The table that the dates will come from
     * @return      String of the SQL statement
     */
    public String FirstLastDate(String table){
        if(table.equals("server")){
            return "select MIN(EntryDate), max(EntryDate) from server" + System.lineSeparator() +
                    "select MIN(ExitDate), max(ExitDate) from server";
        }
        return "select MIN(Date), max(Date) from " + table.toLowerCase();
    }
}
