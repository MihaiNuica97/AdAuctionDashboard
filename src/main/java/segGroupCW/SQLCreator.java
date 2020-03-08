package segGroupCW;

public class SQLCreator {
    public String[] initialise() {
        String[] statements = new String[11];
        statements[1] = numOfImps();
        return statements;
    }

    private String numOfImps() {
        return "SELECT COUNT(*) FROM impressions;";
    }

    private String numOfImps(String start, String end) {
        return "SELECT COUNT(*) FROM impressions WHERE DATE => " + start + " AND DATE <= " + end + ";";
    }

    private String numOfClicks() { return "SELECT COUNT(*) FROM clicks;";}

    private String numOfClicks(String start, String end) {
        return "SELECT COUNT(*) FROM clicks WHERE DATE => " + start + " AND DATE <= " + end + ";";
    }

    private String numOfUniques() { return "SELECT COUNT(DISTINCT (ID)) FROM CLICKS;";}

    private String numOfBounces() { return "SELECT COUNT(*) FROM SERVER WHERE PAGESVIEWED = 1;";}

    private String numOfConvs() { return "SELECT COUNT (*) FROM SERVER WHERE CONVERSION = TRUE;";}

    private String[] totalCost() {
        String[] result = new String[2];
        result[0] = "SELECT SUM(COST) FROM IMPRESSIONS;";
        result[1] = "SELECT SUM(CLICKCOST) FROM CLICKS;";
        return result;
    }

    private String[] ctr() {
        String[] result = new String[2];
        result[0] = numOfClicks();
        result[1] = numOfImps();
        return result;
    }

    private String[] cpa() {
        String[] result = new String[3];
        String[] temp = totalCost();
        result[0] = temp[0];
        result[1] = temp[1];
        result[2] = numOfConvs();
        return result;
    }

    private String[] cpc() {
        String[] result = new String[3];
        String[] temp = totalCost();
        result[0] = temp[0];
        result[1] = temp[1];
        result[2] = numOfClicks();
        return result;
    }

    private String[] cpm() {
        String[] result = new String[3];
        String[] temp = totalCost();
        result[0] = temp[0];
        result[1] = temp[1];
        result[2] = numOfImps();
        return result;
    }

    private String[] bounceRate(){
        String[] result = new String[2];
        result[0] = numOfBounces();
        result[1] = numOfClicks();
        return result;
    }

    /* Consider splitting "between date" or "unique ID" or "cost greater than"
     * into their own functions so that they can be reused by each different metric
     * ie. if you want clicks with between a date the statement is clicks + date(start, end)
     * but you can also do impressions + date(start, end)
     */
}
