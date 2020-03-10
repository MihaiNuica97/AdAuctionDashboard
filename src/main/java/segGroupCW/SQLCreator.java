package segGroupCW;

import java.util.ArrayList;

public class SQLCreator {
    /**
     * Generates all the SQL statements to initialise the dashboard totals.
     *
     * @return      ArrayList of the SQL statements
     */
    public ArrayList<String> initialise() {
        ArrayList<String> statements = new ArrayList<>();
        statements.add(numOfImps());
        statements.add(numOfClicks());
        statements.add(numOfUniques());
        statements.add(numOfBounces());
        statements.add(numOfConvs());
        statements.addAll(totalCost());
        statements.addAll(ctr());
        statements.addAll(cpa());
        statements.addAll(cpc());
        statements.addAll(cpm());
        statements.addAll(bounceRate());
        return statements;
    }

    /**
     * SQL statement to calculate the total impressions.
     *
     * @return    String of the SQL statement
     */
    private String numOfImps() {
        return "SELECT COUNT(*) FROM impressions;";
    }

    /**
     * SQL statement to calculate the total clicks.
     *
     * @return      String of the SQL statement
     */
    private String numOfClicks() { return "SELECT COUNT(*) FROM clicks;";}

    /**
     * SQL statement to calculate the total clicks from unique users.
     *
     * @return      String of the SQL statement
     */
    private String numOfUniques() { return "SELECT COUNT(DISTINCT (ID)) FROM CLICKS;";}

    /**
     * SQL statement to calculate the total bounces. Default bounce definition is only 1 page viewed.
     *
     * @return      String of the SQL statement
     */
    private String numOfBounces() { return "SELECT COUNT(*) FROM SERVER WHERE PAGESVIEWED = 1;";}

    /**
     * SQL statement to calculate the total conversions.
     *
     * @return      String of the SQL statement
     */
    private String numOfConvs() { return "SELECT COUNT (*) FROM SERVER WHERE CONVERSION = TRUE;";}

    /**
     * SQL statements to calculate the total cost.
     *
     * @return      ArrayList of the SQL statements
     */
    private ArrayList<String> totalCost() {
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
    private ArrayList<String> ctr() {
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
    private ArrayList<String> cpa() {
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
    private ArrayList<String> cpc() {
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
    private ArrayList<String> cpm() {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(totalCost());
        result.add(numOfImps());
        return result;
    }

    /**
     * SQL statements to calculate the bounce rate.
     *
     * @return      ArrayList of the SQL statements
     */
    private ArrayList<String> bounceRate(){
        ArrayList<String> result = new ArrayList<>();
        result.add(numOfBounces());
        result.add(numOfClicks());
        return result;
    }


    /* Started customisation statements
     * splitting out "between date" or "unique ID" or "cost greater than"
     * into their own functions so that they can be reused by each different metric
     * ie. if you want clicks with between a date the statement is clicks + date(start, end)
     * but you can also do impressions + date(start, end)
     */

    /**
     * SQL statement for a generic where statement between 2 dates. Both dates should be in YYYY-MM-DD [HH:MM:SS] format.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      String of the SQL statement
     */
    private String betweenDate(String start, String end) {
        return "DATE => TIMESTAMP '" + start + "' AND DATE" + " <= '" + end + "'";
    }

    /**
     * SQL statement for the total impressions between 2 dates. Both dates should be in YYYY-MM-DD [HH:MM:SS] format.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      String of the SQL statement
     */
    private String numOfImps(String start, String end) {
        return "SELECT COUNT(*) FROM impressions WHERE " + betweenDate(start, end) + ";";
    }

    /**
     * SQL statement for the total clicks between 2 dates. Both dates should be in YYYY-MM-DD [HH:MM:SS] format.
     *
     * @param start The earlier date
     * @param end   The later date
     * @return      String of the SQL statement
     */
    private String numOfClicks(String start, String end) {
        return "SELECT COUNT(*) FROM clicks WHERE " + betweenDate(start, end) + ";";
    }

    /**SQL statement for total bounces based on time spent on the site. The user can define the
     * unit used and how many of said unit. Unit can be second, minute, hour, day, month, or year.
     * <p>
     * Ex. unit as seconds and time as 10 means stays under 10 seconds will be counted as bounces
     *
     * @param unit  What unit the time is in
     * @param time  How long the visit needs to be to not be a bounce
     * @return      String of the SQL statement
     */
    private String bounceByTime(String unit, String time) {
        return "SELECT COUNT(*) FROM SERVER WHERE DATEDIFF(" + unit + ", ENTRYDATE, EXITDATE) < " + time;
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
    private String bounceByPages(String pages) {
        return "SELECT COUNT(*) FROM SERVER WHERE PAGESVIEWED < " + pages;
    }
}
