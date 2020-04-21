package segGroupCW;

import java.io.File;
import java.io.IOException;
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

    //Calculations

    public int calcImpressions() {
        return impressions.size();
    }

    public int calcImpressions(List<Impression> impressions) {
        return impressions.size();
    }

    public List<Impression> listImps() {
        return impressions;
    }

    /*
            setNoBounceLabelPages(db, sqlCreator, "2");
            setNoConvLabel(db, sqlCreator);
            setTotalCostLabel(db, sqlCreator);
            setCtrLabel(db, sqlCreator);
            setCpaLabel(db, sqlCreator);
            setCpcLabel(db, sqlCreator);
            setCpmLabel(db, sqlCreator);
            setBounceRateLabelPages(db, sqlCreator, "2");
     */

    public int calcClicks() {
        return clicks.size();
    }


    public int calcClicks(List<Click> clicks) {
        return clicks.size();
    }

    public int calcUniques() {
        return (int) clicks.stream().filter(distinctByKey(Click::getId)).count();
    }

    public int calcUniques(List<Click> clicks) {
        return (int) clicks.stream().filter(distinctByKey(Click::getId)).count();
    }

    public int calcBouncePage(int pages) {
        return (int) serverLogs.stream().filter(p -> p.getPages() > pages).count();
    }


    // Predicates


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
