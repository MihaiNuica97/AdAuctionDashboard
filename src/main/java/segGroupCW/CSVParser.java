package segGroupCW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private BufferedReader csvReader;
    private String row;

    // Parses 3 csvs and inserts into Lists

    public List<Object> parseImpression(File file) throws IOException {
        //ID, Date, Gender, Age range, Income, Context, Impression cost
        List<User> users = new ArrayList<>();
        List<Impression> impressions = new ArrayList<>();
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        System.out.println(row);
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            users.add(new User(data[1], data[2], data[3], data[4]));
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            String id = data[1];
            if (users.stream().noneMatch(p -> p.getId().equals(id))) {
                users.add(new User(data[1], data[2], data[3], data[4]));
            }
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
        }
        List<Object> list = new ArrayList<>();
        list.add(users);
        list.add(impressions);
        return list;
        // return is [users, impressions]
    }

    public List<Click> parseClicks(File file) throws IOException {
        //Date, ID, Click Cost
        List<Click> clicks = new ArrayList<>();
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            clicks.add(new Click(data[1], data[0], data[2]));
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            clicks.add(new Click(data[1], data[0], data[2]));
        }
        return clicks;
    }

    public List<Server> parseServer(File file) throws IOException {
        // EntryDate, ID, ExitDate, Pages viewed, Conversion
        List<Server> servers = new ArrayList<>();
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        String conv = "FALSE";
        if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
            if (data[4].equals("Yes")) {
                conv = "TRUE";
            }
            if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                servers.add(new Server(data[1], data[0], data[3], conv));
            } else {
                servers.add(new Server(data[1], data[0], data[2], data[3], conv));
            }
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            if (data[4].equals("Yes")) {
                conv = "TRUE";
            } else {
                conv = "FALSE";
            }
            if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                servers.add(new Server(data[1], data[0], data[3], conv));
            } else {
                servers.add(new Server(data[1], data[0], data[2], data[3], conv));
            }
        }
        return servers;
    }
}
