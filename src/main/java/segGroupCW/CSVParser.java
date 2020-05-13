package segGroupCW;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class CSVParser {
    // Parses 3 csvs and inserts into Lists

    private Date imprFirstDate, imprLastDate, clickFirstDate, clickLastDate, serverEntFirstDate, serverEntLastDate, serverExFirstDate, serverExLastDate;
    private static DateFormat dateformat = new DateFormat();


    // This has no return as it makes 2 lists and can't return both
    public void parseImpression(File file, DataHandler dataHandler) throws IOException {
        //ID, Date, Gender, Age range, Income, Context, Impression cost
        List<User> users = new ArrayList<>();
        List<Impression> impressions = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            imprFirstDate = DateFormat.convertDate(data[0]);
            imprLastDate = DateFormat.convertDate(data[0]);
            users.add(new User(data[1], data[2], data[3], data[4]));
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            String id = data[1];
            Date date = DateFormat.convertDate(data[0]);
            users.add(new User(data[1], data[2], data[3], data[4]));

            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
            imprFirstDate = dateformat.smallestDate(imprFirstDate,date);
            imprLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded impressions");
        dataHandler.setUsers(users);
        dataHandler.setImpressions(impressions);
    }

    public List<Click> parseClicks(File file) throws IOException {
        //Date, ID, Click Cost
        List<Click> clicks = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            clickFirstDate = DateFormat.convertDate(data[0]);
            clickLastDate = DateFormat.convertDate(data[0]);
            clicks.add(new Click(data[1], data[0], data[2]));
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            clicks.add(new Click(data[1], data[0], data[2]));
            Date date = DateFormat.convertDate(data[0]);
            clickFirstDate = dateformat.smallestDate(clickFirstDate,date);
            clickLastDate = dateformat.largestDate(clickLastDate,date);
        }
        System.out.println("Loaded clicks");
        return clicks;
    }

    public List<Server> parseServer(File file) throws IOException {
        // EntryDate, ID, ExitDate, Pages viewed, Conversion
        List<Server> servers = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        String conv = "FALSE";
        List<Server> noExitDate = new ArrayList<>();
        if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
            serverEntFirstDate = DateFormat.convertDate(data[0]);
            serverEntLastDate = DateFormat.convertDate(data[0]);
            serverExFirstDate = DateFormat.convertDate(data[0]);
            serverExLastDate = DateFormat.convertDate(data[0]);
            if (data[4].equals("Yes")) {
                conv = "TRUE";
            }
            if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                noExitDate.add(new Server(data[1], data[0], data[3], conv));
            } else {
                servers.add(new Server(data[1], data[0], data[2], data[3], conv));
            }
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            Date date = DateFormat.convertDate(data[0]);
            serverEntFirstDate = dateformat.smallestDate(serverEntFirstDate, date);
            serverEntLastDate = dateformat.largestDate(serverEntLastDate, date);
            if (data[4].equals("Yes")) {
                conv = "TRUE";
            } else {
                conv = "FALSE";
            }
            if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                noExitDate.add(new Server(data[1], data[0], data[3], conv));
            } else {
                date = DateFormat.convertDate(data[2]);
                serverExFirstDate = dateformat.smallestDate(serverExFirstDate, date);
                serverExLastDate = dateformat.largestDate(serverExLastDate, date);
                servers.add(new Server(data[1], data[0], data[2], data[3], conv));
            }
        }
        for (Server each : noExitDate) {
            each.setExitDate(serverExLastDate);
        }
        System.out.println("Loaded server");
        return servers;
    }

    public Date getImprFirstDate(){return imprFirstDate;}

    public Date getImprLastDate() {
        return imprLastDate;
    }

    public Date getClickFirstDate() {
        return clickFirstDate;
    }

    public Date getClickLastDate() {
        return clickLastDate;
    }

    public Date getServerEntFirstDate() {
        return serverEntFirstDate;
    }

    public Date getServerEntLastDate() {
        return serverEntLastDate;
    }

    public Date getServerExFirstDate() {
        return serverExFirstDate;
    }

    public Date getServerExLastDate() {
        return serverExLastDate;
    }


}
