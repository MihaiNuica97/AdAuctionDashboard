package segGroupCW;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataHandler {
    private List<Click> clicks;
    private List<User> users;
    private List<Impression> impressions;
    private List<Server> serverLogs;
    static List<String> genders = Arrays.asList("Male", "Female");
    static List<String> ages = Arrays.asList("< 25 years", "25 - 34 years", "35 - 44 years", "45 - 54 years", "> 54 years");
    static List<String> income = Arrays.asList("Low", "Medium", "High");
    static List<String> context = Arrays.asList("Blog", "News", "Shopping", "Social Media", "Hobbies", "Travel");

    private static DateFormat dateformat = new DateFormat();
    private static Date imprFirstDate, imprLastDate, clickFirstDate, clickLastDate, serverEntFirstDate, serverEntLastDate, serverExFirstDate, serverExLastDate;

    //remove, a stub due to the graph controller not being connected
    public DataHandler(){

    }

    public DataHandler(File impFile, File clickFile, File serverFile) {
        try {
            CSVParser csvParser = new CSVParser();
            csvParser.parseImpression(impFile, this);
            clicks = csvParser.parseClicks(clickFile);
            serverLogs = csvParser.parseServer(serverFile);

            imprFirstDate = csvParser.getImprFirstDate();
            imprLastDate = csvParser.getImprLastDate();
            clickFirstDate = csvParser.getClickFirstDate();
            clickLastDate = csvParser.getClickLastDate();
            serverEntFirstDate = csvParser.getServerEntFirstDate();
            serverEntLastDate = csvParser.getServerEntLastDate();
            serverExFirstDate = csvParser.getServerExFirstDate();
            serverExLastDate = csvParser.getServerExLastDate();
        } catch (IOException e) {
            System.out.println("Failed to parse files");
            e.printStackTrace();
        }
    }

    public List<Click> getClicks(){
        return clicks;
    }

    public List<User> getUsers(){
        return users;
    }

    public List<Impression> getImpressions(){
        return impressions;
    }

    public List<Server> getServerLogs(){
        return serverLogs;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setImpressions(List<Impression> impressions) {
        this.impressions = impressions;
    }

    public HashSet<String> filterUsers(List<String> filters) {
        filters.removeAll(context);
        if (filters.isEmpty()) {
            return (HashSet<String>) users.stream().map(User::getId).collect(Collectors.toSet());
        } else {
            ArrayList<String> filterIncome = new ArrayList<>();
            ArrayList<String> filterAge = new ArrayList<>();
            String filterGender = "NULL";

            for (String option : filters) {
                if (income.contains(option)) {
                    filterIncome.add(option);
                } else if (ages.contains(option)) {
                    filterAge.add(convertAge(option));
                } else if (genders.contains(option)) {
                    filterGender = option;
                }
            }

            if (filterGender.equals("NULL")) {
                return (HashSet<String>) users.stream().filter(userFilterPredicate(filterIncome, filterAge)).map(User::getId).collect(Collectors.toSet());
            } else {
                return (HashSet<String>) users.stream().filter(userFilterPredicate(filterIncome, filterAge, filterGender)).map(User::getId).collect(Collectors.toSet());
            }
        }
    }

    private Predicate<User> userFilterPredicate(List<String> incomes, List<String> ages, String gender) {
        if (!incomes.isEmpty()) {
            if (!ages.isEmpty()) {
                return p -> incomes.contains(p.getIncome()) && ages.contains(p.getAge()) && p.getGender().equals(gender);
            } else {
                return p -> incomes.contains(p.getIncome()) && p.getGender().equals(gender);
            }
        } else {
            if (!ages.isEmpty()) {
                return p -> ages.contains(p.getAge()) && p.getGender().equals(gender);
            } else {
                return p -> p.getGender().equals(gender);
            }
        }
    }

    private Predicate<User> userFilterPredicate(List<String> incomes, List<String> ages) {
        if (!incomes.isEmpty()) {
            if (!ages.isEmpty()) {
                return p -> incomes.contains(p.getIncome()) && ages.contains(p.getAge());
            } else {
                return p -> incomes.contains(p.getIncome());
            }
        } else {
            return p -> ages.contains(p.getAge());
        }
    }

    //int for interval num and string for day/month
    public static ArrayList<LocalDate> iterTimeIntervals(Date start, Date end, String interval, int num){
        ArrayList<LocalDate> intervals = new ArrayList<>();
        LocalDate startDate = DateFormat.asLocalDate(start);
        LocalDate endDate = DateFormat.asLocalDate(end).plusDays(1);
        intervals.add(startDate);

        switch  (interval){
            case "days":
                while (startDate.isBefore(endDate)){
                    startDate = startDate.plusDays(num);
                    intervals.add(startDate);
                }
                break;
            case "months":
                while (startDate.isBefore(endDate)){
                    startDate = startDate.plusMonths(num);
                    intervals.add(startDate);
                }
                break;
            case "weeks":
                while (startDate.isBefore(endDate)){
                    startDate = startDate.plusWeeks(num);
                    intervals.add(startDate);
                }
                break;
        }
        return intervals;
    }

    public ArrayList<LocalDate> initialImprTI(String interval, int num){
        return iterTimeIntervals(imprFirstDate,imprLastDate,interval,num);
    }

    public ArrayList<LocalDate> initialClicksTI(String interval, int num){
        return iterTimeIntervals(clickFirstDate,clickLastDate,interval,num);
    }

    public ArrayList<LocalDate> initialServerTI(String interval, int num){
        return iterTimeIntervals(serverEntFirstDate,serverEntLastDate,interval,num);
    }

    public String convertAge(String wrongAge) {
        String age = "";
        switch (wrongAge) {
            case "< 25 years":
                age = "<25";
                break;
            case "25 - 34 years":
                age = "25-34";
                break;
            case "35 - 44 years":
                age = "35-44";
                break;
            case "45 - 54 years":
                age = "45-54";
                break;
            case "> 54 years":
                age = ">54";
                break;
        }
        return age;
    }

    public HashSet<Impression> filterImpressions(HashSet<String> ids) {
        return (HashSet<Impression>) impressions.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toSet());
    }

    public HashSet<Impression> filterImpressions(HashSet<String> ids, HashSet<String> contexts) {
        return (HashSet<Impression>)impressions.stream().filter(p -> ids.contains(p.getId()) && contexts.contains(p.getContext())).collect(Collectors.toSet());
    }

    public List<Click> filterClicks(HashSet<String> ids) {
        return clicks.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toList());
    }

    public List<Server> filterServers(HashSet<String> ids) {
        return serverLogs.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toList());
    }

    public List<Click> filterCClicks(HashSet<Impression> impressions) {
        return clicks.stream().filter(compareCToImpressions(impressions) ).collect(Collectors.toList());
    }

    public List<Server> filterCServers(HashSet<Impression> impressions) {
        return serverLogs.stream().filter(compareSToImpressions(impressions) ).collect(Collectors.toList());
    }

    private Predicate<Click> compareCToImpressions(HashSet<Impression> imps) {
        return p -> imps.stream().anyMatch(t -> t.getId().equals(p.getId()) && (t.getDate().getTime() - p.getDate().getTime() < 300000 ));
    }

    private Predicate<Server> compareSToImpressions(HashSet<Impression> imps) {
        return p -> imps.stream().anyMatch(t -> t.getId().equals(p.getId()) && (t.getDate().getTime() - p.getEntryDate().getTime() < 300000 ));
    }

    public List<Server> filterCServers(HashMap<String, Date> impressions) {
        return serverLogs.stream().filter(p -> impressions.get(p.getId()) != null && impressions.get(p.getId()).getTime() - p.getEntryDate().getTime() < 300000 ).collect(Collectors.toList());
    }

    public List<Click> filterCClicks(HashMap<String, Date> impressions) {
        return clicks.stream().filter(p -> impressions.get(p.getId()) != null && impressions.get(p.getId()).getTime() - p.getDate().getTime() < 300000 ).collect(Collectors.toList());
    }
    //Normal Calculations

    public int calcImpressions(){ return impressions.size(); }

    public int calcClicks() { return clicks.size();}

    public int calcUniques() { return (int) clicks.stream().filter(distinctByKey(Click::getId)).count(); }

    public int calcBouncePage(Double pages) { return (int) serverLogs.stream().filter(p -> p.getPages() <= pages).count(); }

    public int calcBounceConv() {return (int) serverLogs.stream().filter(p -> !p.getConversion()).count(); }

    public int calcBounceTime(Double time) { return (int) serverLogs.stream().filter(p -> p.getExitDate().getTime() - p.getEntryDate().getTime() < time).count(); }

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

    public double calcBounceRatePages(Double pages) { return Math.round( ((double) calcBouncePage(pages) / calcClicks()) * 100.0) / 100.0;}

    public double calcBounceRateConv() { return Math.round( ((double) calcBounceConv() / calcClicks()) * 100.0) / 100.0; }

    public double calcBounceRateTime(Double time) { return Math.round( ((double) calcBounceTime(time) / calcClicks()) * 100.0) / 100.0; }

    //Filter Calculations

    public int calcImpressions(List<Impression> impressions) { return impressions.size(); }

    public int calcClicks(List<Click> clicks) { return clicks.size(); }

    public int calcUniques(List<Click> clicks) { return (int) clicks.stream().filter(distinctByKey(Click::getId)).count(); }

    public int calcBouncePage(List<Server> serverLogs, Double pages) {return (int) serverLogs.stream().filter(p -> p.getPages() > pages).count(); }

    public int calcBounceConv(List<Server> serverLogs) {return (int) serverLogs.stream().filter(p -> !p.getConversion()).count(); }

    public int calcBounceTime(List<Server> serverLogs, Double time) { return (int) serverLogs.stream().filter(p -> p.getExitDate().getTime() - p.getEntryDate().getTime() < time).count(); }

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

    public double calcBounceRatePages(List<Server> serverLogs, List<Click> clicks, Double pages) { return Math.round( ((double) calcBouncePage(serverLogs, pages) / calcClicks(clicks)) * 100.0) / 100.0;}

    public double calcBounceRateConv(List<Server> serverLogs, List<Click> clicks) { return Math.round( ((double) calcBounceConv(serverLogs) / calcClicks(clicks)) * 100.0) / 100.0; }

    public double calcBounceRateTime(List<Server> serverLogs, List<Click> clicks, Double time) { return Math.round( ((double) calcBounceTime(serverLogs, time) / calcClicks(clicks)) * 100.0) / 100.0; }

    // Calculations that take values instead of calling methods again

    public double calcCTR(int clicks, int impressions) { return Math.round( ((double) clicks / impressions * 100.0)) / 100.0; }

    public double calcCPA(double totalCost, int conversions) { return Math.round( (totalCost / conversions) * 100.0) / 100.0; }

    public double calcCPC(double totalCost, int clicks) { return Math.round( (totalCost / clicks) * 100.0) / 100.0; }

    public double calcCPM(double totalCost, int impressions) { return Math.round( (totalCost / (impressions * 1000)) * 100.0) / 100.0; }

    public double calcBounceRatePages(int bouncePages, int clicks) { return Math.round( ((double) bouncePages / clicks) * 100.0) / 100.0;}

    public double calcBounceRateConv(int bounceConv, int clicks) { return Math.round( ((double) bounceConv / clicks) * 100.0) / 100.0; }

    public double calcBounceRateTime(int bounceTime, int clicks) { return Math.round( ((double) bounceTime / clicks) * 100.0) / 100.0; }

    //init for grpahs

    public int impressionsAtDate(LocalDate date ){
        return (int)impressions.stream().filter(impressionAtDate(date)).count();
    }
    public int clicksAtDate(LocalDate date ){
        return (int)clicks.stream().filter(clickAtDate(date)).count();
    }

    public int uniquesAtDate(LocalDate date ){
        return (int)clicks.stream().filter(uniqueAtDate(date,Click::getId)).count();
    }

    // bounce stuff

    public int bouncePageAtDate(LocalDate date, double page){
        return (int)serverLogs.stream().filter(bouncePageAtDatePred(date,page)).count();
    }

    public int bounceConvAtDate(LocalDate date){
        return (int)serverLogs.stream().filter(bounceConvAtDatePred(date)).count();
    }

    public int bounceTimeAtDate(LocalDate date, double time){
        return (int)serverLogs.stream().filter(bounceTimeAtDatePred(date,time)).count();
    }


    //

    public int conversionsAtDate(LocalDate date ){
        return (int)serverLogs.stream().filter(conversionsDatePredicate(date)).count();
    }

    public double totalCostAtDate(LocalDate date ){
        double impCost = impressions.stream().filter(impressionAtDate(date)).mapToDouble(p -> p.getCost().doubleValue()).sum();
        double clickCost = clicks.stream().filter(clickAtDate(date)).mapToDouble(p -> p.getCost().doubleValue()).sum();
        return Math.round( (impCost + clickCost) * 100.0) / 100.0;    }

    public double ctrAtDate(LocalDate date){
        return Math.round( ((double) clicksAtDate(date) / impressionsAtDate(date)) * 100.0) / 100.0;
    }

    public double cpaAtDate(LocalDate date){
        return Math.round( (totalCostAtDate(date) / conversionsAtDate(date)) * 100.0) / 100.0;
    }

    public double cpcAtDate(LocalDate date){
        return Math.round( (totalCostAtDate(date) / clicksAtDate(date)) * 100.0) / 100.0;
    }

    public double cpmAtDate(LocalDate date){
        return Math.round( (totalCostAtDate(date) / (impressionsAtDate(date) * 1000)) * 100.0) / 100.0;
    }

    public double bounceRatePageAtDate(LocalDate date, double page){
        return calcBounceRatePages(bouncePageAtDate(date,page),clicksAtDate(date));
    }

    public double bounceRateConvAtDate(LocalDate date){
        return calcBounceRateConv(bounceConvAtDate(date),clicksAtDate(date));
    }

    public double bounceRateTimeAtDate(LocalDate date, double time){
        return calcBounceRateTime(bounceTimeAtDate(date,time),clicksAtDate(date));
    }


    //filters for graphs

    public int impressionsAtDate(LocalDate date, List<Impression> impressionList){
        return (int)impressionList.stream().filter(impressionAtDate(date)).count();
    }
    public int clicksAtDate(LocalDate date, List<Click> clicksList){
        return (int)clicksList.stream().filter(clickAtDate(date)).count();
    }

    public int uniquesAtDate(LocalDate date, List<Click> clicksList ){
        return (int)clicksList.stream().filter(uniqueAtDate(date,Click::getId)).count();
    }

    // bounce stuff

    public int bouncePageAtDate(LocalDate date,List<Server> serverList, double page){
        return (int)serverList.stream().filter(bouncePageAtDatePred(date,page)).count();
    }

    public int bounceConvAtDate(LocalDate date,List<Server> serverList){
        return (int)serverList.stream().filter(bounceConvAtDatePred(date)).count();
    }

    public int bounceTimeAtDate(LocalDate date,List<Server> serverList, double time){
        return (int)serverList.stream().filter(bounceTimeAtDatePred(date,time)).count();
    }

    //

    public int conversionsAtDate(LocalDate date, List<Server> serverList){
        return (int)serverList.stream().filter(conversionsDatePredicate(date)).count();
    }

    public double totalCostAtDate(LocalDate date, List<Click> clicksList, List<Impression> impressionList){
        double impCost = impressionList.stream().filter(impressionAtDate(date)).mapToDouble(p -> p.getCost().doubleValue()).sum();
        double clickCost = clicksList.stream().filter(clickAtDate(date)).mapToDouble(p -> p.getCost().doubleValue()).sum();
        return Math.round( (impCost + clickCost) * 100.0) / 100.0;    }

    public double ctrAtDate(LocalDate date, List<Click> clicksList, List<Impression> impressionList){
        return Math.round( ((double) clicksAtDate(date, clicksList) / impressionsAtDate(date, impressionList)) * 100.0) / 100.0;
    }

    public double cpaAtDate(LocalDate date, List<Click> clicksList, List<Impression> impressionList, List<Server> serverList){
        return Math.round( (totalCostAtDate(date,clicksList,impressionList) / conversionsAtDate(date,serverList)) * 100.0) / 100.0;
    }

    public double cpcAtDate(LocalDate date, List<Click> clicksList, List<Impression> impressionList){
        return Math.round( (totalCostAtDate(date,clicksList,impressionList) / clicksAtDate(date,clicksList)) * 100.0) / 100.0;
    }

    public double cpmAtDate(LocalDate date, List<Click> clicksList, List<Impression> impressionList){
        return Math.round( (totalCostAtDate(date,clicksList,impressionList) / (impressionsAtDate(date,impressionList) * 1000)) * 100.0) / 100.0;
    }

    public double bounceRatePageAtDate(LocalDate date, List<Server> serverList, double page){
        return calcBounceRatePages(bouncePageAtDate(date, serverList, page),clicksAtDate(date));
    }

    public double bounceRateConvAtDate(LocalDate date, List<Server> serverList){
        return calcBounceRatePages(bounceConvAtDate(date, serverList),clicksAtDate(date));
    }

    public double bounceRateTimeAtDate(LocalDate date, List<Server> serverList, double time){
        return calcBounceRatePages(bounceTimeAtDate(date,serverList, time),clicksAtDate(date));
    }

    // Predicates


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private static Predicate<Impression> impressionAtDate(LocalDate date){
        return p -> DateFormat.dayComparable(p.getDate()).isEqual(date);
    }

    private static Predicate<Click> clickAtDate(LocalDate date){
        return p -> DateFormat.dayComparable(p.getDate()).isEqual(date);
    }

    private static Predicate<Server> conversionsDatePredicate(LocalDate date){
        return p -> p.getConversion() && DateFormat.dayComparable(p.getEntryDate()).isEqual(date);
    }

    private static Predicate<Click> uniqueAtDate(LocalDate date, Function<? super Click, ?> keyExtractor){
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return p -> DateFormat.dayComparable(p.getDate()).isEqual(date) && seen.putIfAbsent(keyExtractor.apply(p), Boolean.TRUE) == null;
    }

    private static Predicate<Server> bouncePageAtDatePred(LocalDate date, Double pages){
        return p -> DateFormat.dayComparable(p.getEntryDate()).isEqual(date) && p.getPages() <= pages;
    }

    private static Predicate<Server> bounceConvAtDatePred(LocalDate date){
        return p -> DateFormat.dayComparable(p.getEntryDate()).isEqual(date) && !p.getConversion();
    }

    private static Predicate<Server> bounceTimeAtDatePred(LocalDate date, Double time){
        return p -> DateFormat.dayComparable(p.getEntryDate()).isEqual(date) && p.getExitDate().getTime() - p.getEntryDate().getTime() < time;
    }

}