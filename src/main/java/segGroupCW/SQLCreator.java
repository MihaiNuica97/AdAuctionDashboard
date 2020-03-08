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
     * @return    string of the SQL statement
     */
    private String numOfImps() {
        return "SELECT COUNT(*) FROM impressions;";
    }

    /**
     * SQL statement to calculate the total clicks.
     *
     * @return      string of the SQL statement
     */
    private String numOfClicks() { return "SELECT COUNT(*) FROM clicks;";}

    /**
     * SQL statement to calculate the total clicks from unique users.
     *
     * @return      string of the SQL statement
     */
    private String numOfUniques() { return "SELECT COUNT(DISTINCT (ID)) FROM CLICKS;";}

    /**
     * SQL statement to calculate the total bounces. Default bounce definition is only 1 page viewed.
     *
     * @return      string of the SQL statement
     */
    private String numOfBounces() { return "SELECT COUNT(*) FROM SERVER WHERE PAGESVIEWED = 1;";}

    /**
     * SQL statement to calculate the total conversions.
     *
     * @return      string of the SQL statement
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


    private String numOfImps(String start, String end) {
        return "SELECT COUNT(*) FROM impressions WHERE DATE => " + start + " AND DATE <= " + end + ";";
    }

    private String numOfClicks(String start, String end) {
        return "SELECT COUNT(*) FROM clicks WHERE DATE => " + start + " AND DATE <= " + end + ";";
    }

    /* Consider splitting "between date" or "unique ID" or "cost greater than"
     * into their own functions so that they can be reused by each different metric
     * ie. if you want clicks with between a date the statement is clicks + date(start, end)
     * but you can also do impressions + date(start, end)
     */
}
