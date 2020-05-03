package segGroupCW;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataHandler {
    private List<Click> clicks;
    private List<User> users;
    private List<Impression> impressions;
    private List<Server> serverLogs;

    public DataHandler(File impFile, File clickFile, File serverFile) {
        try {
            CSVParser.parseImpression(impFile, this);
            clicks = CSVParser.parseClicks(clickFile);
            serverLogs = CSVParser.parseServer(serverFile);

        } catch (IOException e) {
            System.out.println("Failed to parse files");
            e.printStackTrace();
        }
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setImpressions(List<Impression> impressions) {
        this.impressions = impressions;
    }

    public void applyFilters(List<String> filters) {

    }

    //Normal Calculations

    public int calcImpressions(){ return impressions.size(); }

    public int calcClicks() { return clicks.size();
    }

    public int calcUniques() { return (int) clicks.stream().filter(distinctByKey(Click::getId)).count(); }

    public int calcBouncePage(int pages) { return (int) serverLogs.stream().filter(p -> p.getPages() > pages).count(); }

    public int calcBounceConv() {return (int) serverLogs.stream().filter(p -> !p.getConversion()).count(); }

    public int calcBounceTime(int time) { return (int) serverLogs.stream().filter(p -> p.getExitDate().getTime() - p.getEntryDate().getTime() < time).count(); }

    public int calcConversions() { return (int) serverLogs.stream().filter(Server::getConversion).count(); }

    public double calcTotalCost() {
        double clickCost = clicks.stream().mapToDouble(p -> p.getCost().doubleValue()).sum();
        double impCost = impressions.stream().mapToDouble(p -> p.getCost().doubleValue()).sum();
        return Math.round( (impCost + clickCost) * 100.0) / 100.0;
    }

    public double calcCTR() { return Math.round( ((double) calcClicks() / calcImpressions() * 100.0)) / 100.0; }

    public double calcCPA() { return Math.round( (calcTotalCost() / calcConversions()) * 100.0) / 100.0; }

    public double calcCPC() { return Math.round( (calcTotalCost() / calcClicks()) * 100.0) / 100.0; }

    public double calcCPM() { return Math.round( (calcTotalCost() / (calcImpressions() * 1000)) * 100.0) / 100.0; }

    public double calcBounceRatePages(int pages) { return Math.round( ((double) calcBouncePage(pages) / calcClicks()) * 100.0) / 100.0;}

    public double calcBounceRateConv() { return Math.round( ((double) calcBounceConv() / calcClicks()) * 100.0) / 100.0; }

    public double calcBounceRateTime(int time) { return Math.round( ((double) calcBounceTime(time) / calcClicks()) * 100.0) / 100.0; }

    /*
            setBounceRateLabelPages(db, sqlCreator, "2");
     */

    //Filter Calculations

    public int calcImpressions(List<Impression> impressions) { return impressions.size(); }

    public int calcClicks(List<Click> clicks) { return clicks.size(); }

    public int calcUniques(List<Click> clicks) { return (int) clicks.stream().filter(distinctByKey(Click::getId)).count(); }

    public int calcBouncePage(List<Server> serverLogs, int pages) {return (int) serverLogs.stream().filter(p -> p.getPages() > pages).count(); }

    public int calcBounceConv(List<Server> serverLogs) {return (int) serverLogs.stream().filter(p -> !p.getConversion()).count(); }

    public int calcBounceTime(List<Server> serverLogs, int time) { return (int) serverLogs.stream().filter(p -> p.getExitDate().getTime() - p.getEntryDate().getTime() < time).count(); }

    public int calcConversions(List<Server> serverLogs) { return (int) serverLogs.stream().filter(Server::getConversion).count(); }

    public double calcTotalCost(List<Click> clicks, List<Impression> impressions) {
        double impCost = impressions.stream().mapToDouble(p -> p.getCost().doubleValue()).sum();
        double clickCost = clicks.stream().mapToDouble(p -> p.getCost().doubleValue()).sum();
        return Math.round( (impCost + clickCost) * 100.0) / 100.0;
    }

    public double calcCTR(List<Click> clicks, List<Impression> impressions) { return Math.round( ((double) calcClicks(clicks) / calcImpressions(impressions)) * 100.0) / 100.0; }

    public double calcCPA(List<Click> clicks, List<Impression> impressions, List<Server> serverLogs) { return Math.round( (calcTotalCost(clicks, impressions) / calcConversions(serverLogs)) * 100.0) / 100.0; }

    public double calcCPC(List<Click> clicks, List<Impression> impressions) { return Math.round( (calcTotalCost(clicks, impressions) / calcClicks(clicks)) * 100.0) / 100.0; }

    public double calcCPM(List<Click> clicks, List<Impression> impressions) { return Math.round( (calcTotalCost(clicks, impressions) / (calcImpressions(impressions) * 1000)) * 100.0) / 100.0; }

    public double calcBounceRatePages(List<Server> serverLogs, List<Click> clicks, int pages) { return Math.round( ((double) calcBouncePage(serverLogs, pages) / calcClicks(clicks)) * 100.0) / 100.0;}

    public double calcBounceRateConv(List<Server> serverLogs, List<Click> clicks) { return Math.round( ((double) calcBounceConv(serverLogs) / calcClicks(clicks)) * 100.0) / 100.0; }

    public double calcBounceRateTime(List<Server> serverLogs, List<Click> clicks, int time) { return Math.round( ((double) calcBounceTime(serverLogs, time) / calcClicks(clicks)) * 100.0) / 100.0; }

    // Calculations that take values instead of calling methods again

    public double calcTotalCost(double clickCost, double impCost) { return Math.round( (impCost + clickCost) * 100.0) / 100.0; }

    public double calcCTR(int clicks, int impressions) { return Math.round( ((double) clicks / impressions * 100.0)) / 100.0; }

    public double calcCPA(double totalCost, int conversions) { return Math.round( (totalCost / conversions) * 100.0) / 100.0; }

    public double calcCPC(double totalCost, int clicks) { return Math.round( (totalCost / clicks) * 100.0) / 100.0; }

    public double calcCPM(double totalCost, int impressions) { return Math.round( (totalCost / (impressions * 1000)) * 100.0) / 100.0; }

    public double calcBounceRatePages(int bouncePages, int clicks) { return Math.round( ((double) bouncePages / clicks) * 100.0) / 100.0;}

    public double calcBounceRateConv(int bounceConv, int clicks) { return Math.round( ((double) bounceConv / clicks) * 100.0) / 100.0; }

    public double calcBounceRateTime(int bounceTime, int clicks) { return Math.round( ((double) bounceTime / clicks) * 100.0) / 100.0; }


    // Predicates


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
