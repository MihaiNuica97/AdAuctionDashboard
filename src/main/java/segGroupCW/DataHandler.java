package segGroupCW;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
    static List<String> genders = Arrays.asList("Male", "Female");
    static List<String> ages = Arrays.asList("< 25 years", "25 - 34 years", "35 - 44 years", "45 - 54 years", "> 54 years");
    static List<String> income = Arrays.asList("Low", "Medium", "High");
    static List<String> context = Arrays.asList("Blog", "News", "Shopping", "Social Media", "Hobbies", "Travel");

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

    public List<String> filterUsers(List<String> filters) {
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
            return users.stream().filter(userFilterPredicate(filterIncome, filterAge)).map(User::getId).collect(Collectors.toList());
        } else {
            return users.stream().filter(userFilterPredicate(filterIncome, filterAge, filterGender)).map(User::getId).collect(Collectors.toList());
        }
    }

    private Predicate<User> userFilterPredicate(List<String> incomes, List<String> ages, String gender) {
        if (incomes.size() > 0) {
            if (ages.size() > 0) {
                return p -> incomes.contains(p.getIncome()) && ages.contains(p.getAge()) && p.getGender().equals(gender);
            } else {
                return p -> incomes.contains(p.getIncome()) && p.getGender().equals(gender);
            }
        } else {
            if (ages.size() > 0) {
                return p -> ages.contains(p.getAge()) && p.getGender().equals(gender);
            } else {
                return p -> p.getGender().equals(gender);
            }
        }
    }

    private Predicate<User> userFilterPredicate(List<String> incomes, List<String> ages) {
        if (incomes.size() > 0) {
            if (ages.size() > 0) {
                return p -> incomes.contains(p.getIncome()) && ages.contains(p.getAge());
            } else {
                return p -> incomes.contains(p.getIncome());
            }
        } else {
            return p -> ages.contains(p.getAge());
        }
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

    public List<Impression> filterImpressions(List<String> ids) {
        return impressions.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toList());
    }

    public List<Impression> filterImpressions(List<String> ids, ArrayList<String> contexts) {
        return impressions.stream().filter(p -> ids.contains(p.getId()) && contexts.contains(p.getContext())).collect(Collectors.toList());
    }

    public List<Server> filterServers(List<String> ids) {
        return serverLogs.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toList());
    }

    public List<Click> filterClicks(List<String> users, List<Impression> impressions) {
        return null;
    }

    public List<Click> filterClicks(List<String> ids) {
        return clicks.stream().filter(p -> ids.contains(p.getId())).collect(Collectors.toList());
    }

    public List<Server> filterServers(List<String> users, List<Impression> impressions) {
        return null;
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


    // Predicates


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
