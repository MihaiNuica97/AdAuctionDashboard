package segGroupCW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class testingTimes {
    private static DateFormat dateformat = new DateFormat();
    private static Date imprFirstDate, imprLastDate, clickFirstDate, clickLastDate, serverEntFirstDate, serverEntLastDate, serverExFirstDate, serverExLastDate;
    long impFile, clickFile, serverFile, start, finish;


    public static void main(String[] args) {
        new testingTimes();
    }

    public testingTimes() {
        try {

            long start = System.currentTimeMillis();
            Date junk = convertingDateStatic("2015-01-01 12:00:00");
            long finish = System.currentTimeMillis();
            long diff = finish - start;
            System.out.println("Converted date static in: " + (finish - start));

            start = System.currentTimeMillis();
            junk = convertingDateInstance("2015-01-01 12:00:00");
            finish = System.currentTimeMillis();
            System.out.println("Converted date instance in: " + (finish - start));

            DataHandler dh = new DataHandler();
            File file1 = new File("F:/Users/Charlotte/DownloadsE/2_week_campaign_2/3dayImp.csv");
            File file2 = new File("F:/Users/Charlotte/DownloadsE/2_week_campaign_2/3dayClick.csv");
            File file3 = new File("F:/Users/Charlotte/DownloadsE/2_week_campaign_2/3dayServer.csv");
            start = System.currentTimeMillis();
            impArrayBR(file1, dh);
            impFile = System.currentTimeMillis();
            dh.clicks = clickArrayBR(file2);
            clickFile = System.currentTimeMillis();
            dh.serverLogs = serverArrayBR(file3);
            serverFile = System.currentTimeMillis();

            System.out.println("*** ArrayList and BufferedReader ***");
            System.out.println("Parsed " + dh.impressions.size() + " impressions and " + dh.users.size() + " users in " + (impFile - start));
            System.out.println("Parsed " + dh.clicks.size() + " clicks in: " + (clickFile - impFile));
            System.out.println("Parsed " + dh.serverLogs.size() + " server logs in: " + (serverFile - clickFile));
            System.out.println("Parsed in total time: " + (serverFile - start));
            long total1 = serverFile - start;

            start = System.currentTimeMillis();
            impLLBR(file1, dh);
            impFile = System.currentTimeMillis();
            dh.clicks = clickLLBR(file2);
            clickFile = System.currentTimeMillis();
            dh.serverLogs = serverLLBR(file3);
            serverFile = System.currentTimeMillis();

            System.out.println("*** LinkedList and BufferedReader ***");
            System.out.println("Parsed " + dh.impressions.size() + " impressions and " + dh.users.size() + " users in " + (impFile - start));
            System.out.println("Parsed " + dh.clicks.size() + " clicks in: " + (clickFile - impFile));
            System.out.println("Parsed " + dh.serverLogs.size() + " server logs in: " + (serverFile - clickFile));
            System.out.println("Parsed in total time: " + (serverFile - start));
            long total2 = serverFile - start;

            start = System.currentTimeMillis();
            impArrayS(file1, dh);
            impFile = System.currentTimeMillis();
            dh.clicks = clickArrayS(file2);
            clickFile = System.currentTimeMillis();
            dh.serverLogs = serverArrayS(file3);
            serverFile = System.currentTimeMillis();

            System.out.println("*** ArrayList and Scanner ***");
            System.out.println("Parsed " + dh.impressions.size() + " impressions and " + dh.users.size() + " users in " + (impFile - start));
            System.out.println("Parsed " + dh.clicks.size() + " clicks in: " + (clickFile - impFile));
            System.out.println("Parsed " + dh.serverLogs.size() + " server logs in: " + (serverFile - clickFile));
            System.out.println("Parsed in total time: " + (serverFile - start));
            long total3 = serverFile - start;

            start = System.currentTimeMillis();
            impLLS(file1, dh);
            impFile = System.currentTimeMillis();
            dh.clicks = clickLLS(file2);
            clickFile = System.currentTimeMillis();
            dh.serverLogs = serverLLS(file3);
            serverFile = System.currentTimeMillis();

            System.out.println("*** LinkedList and Scanner ***");
            System.out.println("Parsed " + dh.impressions.size() + " impressions and " + dh.users.size() + " users in " + (impFile - start));
            System.out.println("Parsed " + dh.clicks.size() + " clicks in: " + (clickFile - impFile));
            System.out.println("Parsed " + dh.serverLogs.size() + " server logs in: " + (serverFile - clickFile));
            System.out.println("Parsed in total time: " + (serverFile - start));
            long total4 = serverFile - start;

            System.out.println("*** Totals ***");
            System.out.println("ArrayList and BufferedReader: " + total1);
            System.out.println("LinkedList and BufferedReader: " + total2);
            System.out.println("ArrayList and Scanner: " + total3);
            System.out.println("LinkedList and Scanner: " + total4);

        } catch (Exception e ){
            e.printStackTrace();
        }
    }

    private Date convertingDateStatic(String str) {
        return DateFormat.convertDate(str);
    }

    private Date convertingDateInstance(String str) {
        return (new DateFormat()).convertDate(str);
    }

    public static void impArrayBR(File file, DataHandler dataHandler) throws IOException {
        //ID, Date, Gender, Age range, Income, Context, Impression cost
        List<User> users = new ArrayList<>();
        List<Impression> impressions = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            imprFirstDate = DateFormat.convertDate(data[0]);
            imprLastDate = dateformat.convertDate(data[0]);
            users.add(new User(data[1], data[2], data[3], data[4]));
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            String id = data[1];
            Date date = dateformat.convertDate(data[0]);
            if (users.stream().noneMatch(p -> p.getId().equals(id))) {
                users.add(new User(data[1], data[2], data[3], data[4]));
            }
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
            imprFirstDate = dateformat.smallestDate(imprFirstDate,date);
            imprLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded impressions");
        dataHandler.setUsers(users);
        dataHandler.setImpressions(impressions);
    }

    public static List<Click> clickArrayBR(File file) throws IOException {
        //Date, ID, Click Cost
        List<Click> clicks = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            clickFirstDate = dateformat.convertDate(data[0]);
            clickLastDate = dateformat.convertDate(data[0]);
            clicks.add(new Click(data[1], data[0], data[2]));
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            clicks.add(new Click(data[1], data[0], data[2]));
            Date date = dateformat.convertDate(data[0]);
            clickFirstDate = dateformat.smallestDate(imprFirstDate,date);
            clickLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded clicks");
        return clicks;
    }

    public static List<Server> serverArrayBR(File file) throws IOException {
        // EntryDate, ID, ExitDate, Pages viewed, Conversion
        List<Server> servers = new ArrayList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        String conv = "FALSE";
        if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
            serverEntFirstDate = dateformat.convertDate(data[0]);
            serverEntLastDate = dateformat.convertDate(data[0]);
            serverExFirstDate = dateformat.convertDate(data[0]);
            serverExLastDate = dateformat.convertDate(data[0]);
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
        System.out.println("Loaded server");
        return servers;
    }


    public static void impLLBR(File file, DataHandler dataHandler) throws IOException {
        //ID, Date, Gender, Age range, Income, Context, Impression cost
        LinkedList<User> users = new LinkedList<>();
        LinkedList<Impression> impressions = new LinkedList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            imprFirstDate = DateFormat.convertDate(data[0]);
            imprLastDate = dateformat.convertDate(data[0]);
            users.add(new User(data[1], data[2], data[3], data[4]));
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            String id = data[1];
            Date date = dateformat.convertDate(data[0]);
            if (users.stream().noneMatch(p -> p.getId().equals(id))) {
                users.add(new User(data[1], data[2], data[3], data[4]));
            }
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
            imprFirstDate = dateformat.smallestDate(imprFirstDate,date);
            imprLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded impressions");
        dataHandler.setUsers(users);
        dataHandler.setImpressions(impressions);
    }

    public static LinkedList<Click> clickLLBR(File file) throws IOException {
        //Date, ID, Click Cost
        LinkedList<Click> clicks = new LinkedList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            clickFirstDate = dateformat.convertDate(data[0]);
            clickLastDate = dateformat.convertDate(data[0]);
            clicks.add(new Click(data[1], data[0], data[2]));
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            clicks.add(new Click(data[1], data[0], data[2]));
            Date date = dateformat.convertDate(data[0]);
            clickFirstDate = dateformat.smallestDate(imprFirstDate,date);
            clickLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded clicks");
        return clicks;
    }

    public static LinkedList<Server> serverLLBR(File file) throws IOException {
        // EntryDate, ID, ExitDate, Pages viewed, Conversion
        LinkedList<Server> servers = new LinkedList<>();
        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = csvReader.readLine();
        String[] data = row.split(",");
        String conv = "FALSE";
        if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
            serverEntFirstDate = dateformat.convertDate(data[0]);
            serverEntLastDate = dateformat.convertDate(data[0]);
            serverExFirstDate = dateformat.convertDate(data[0]);
            serverExLastDate = dateformat.convertDate(data[0]);
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
        System.out.println("Loaded server");
        return servers;
    }


    public static void impArrayS(File file, DataHandler dataHandler) throws IOException {
        //ID, Date, Gender, Age range, Income, Context, Impression cost
        List<User> users = new ArrayList<>();
        List<Impression> impressions = new ArrayList<>();
        Scanner csvReader = new Scanner(file);
        String row = csvReader.nextLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            imprFirstDate = DateFormat.convertDate(data[0]);
            imprLastDate = dateformat.convertDate(data[0]);
            users.add(new User(data[1], data[2], data[3], data[4]));
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
        }
        while (csvReader.hasNext()) {
            row = csvReader.nextLine();
            data = row.split(",");
            String id = data[1];
            Date date = dateformat.convertDate(data[0]);
            if (users.stream().noneMatch(p -> p.getId().equals(id))) {
                users.add(new User(data[1], data[2], data[3], data[4]));
            }
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
            imprFirstDate = dateformat.smallestDate(imprFirstDate,date);
            imprLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded impressions");
        dataHandler.setUsers(users);
        dataHandler.setImpressions(impressions);
    }

    public static List<Click> clickArrayS(File file) throws IOException {
        //Date, ID, Click Cost
        List<Click> clicks = new ArrayList<>();
        Scanner csvReader = new Scanner(file);
        String row = csvReader.nextLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            clickFirstDate = dateformat.convertDate(data[0]);
            clickLastDate = dateformat.convertDate(data[0]);
            clicks.add(new Click(data[1], data[0], data[2]));
        }
        while (csvReader.hasNext()) {
            row = csvReader.nextLine();
            data = row.split(",");
            clicks.add(new Click(data[1], data[0], data[2]));
            Date date = dateformat.convertDate(data[0]);
            clickFirstDate = dateformat.smallestDate(imprFirstDate,date);
            clickLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded clicks");
        return clicks;
    }

    public static List<Server> serverArrayS(File file) throws IOException {
        // EntryDate, ID, ExitDate, Pages viewed, Conversion
        List<Server> servers = new ArrayList<>();
        Scanner csvReader = new Scanner(file);
        String row = csvReader.nextLine();
        String[] data = row.split(",");
        String conv = "FALSE";
        if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
            serverEntFirstDate = dateformat.convertDate(data[0]);
            serverEntLastDate = dateformat.convertDate(data[0]);
            serverExFirstDate = dateformat.convertDate(data[0]);
            serverExLastDate = dateformat.convertDate(data[0]);
            if (data[4].equals("Yes")) {
                conv = "TRUE";
            }
            if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                servers.add(new Server(data[1], data[0], data[3], conv));
            } else {
                servers.add(new Server(data[1], data[0], data[2], data[3], conv));
            }
        }
        while (csvReader.hasNext()) {
            row = csvReader.nextLine();
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
        System.out.println("Loaded server");
        return servers;
    }


    public static void impLLS(File file, DataHandler dataHandler) throws IOException {
        //ID, Date, Gender, Age range, Income, Context, Impression cost
        LinkedList<User> users = new LinkedList<>();
        LinkedList<Impression> impressions = new LinkedList<>();
        Scanner csvReader = new Scanner(file);
        String row = csvReader.nextLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            imprFirstDate = DateFormat.convertDate(data[0]);
            imprLastDate = dateformat.convertDate(data[0]);
            users.add(new User(data[1], data[2], data[3], data[4]));
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
        }
        while (csvReader.hasNext()) {
            row = csvReader.nextLine();
            data = row.split(",");
            String id = data[1];
            Date date = dateformat.convertDate(data[0]);
            if (users.stream().noneMatch(p -> p.getId().equals(id))) {
                users.add(new User(data[1], data[2], data[3], data[4]));
            }
            impressions.add(new Impression(data[1], data[0], data[5], data[6]));
            imprFirstDate = dateformat.smallestDate(imprFirstDate,date);
            imprLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded impressions");
        dataHandler.setUsers(users);
        dataHandler.setImpressions(impressions);
    }

    public static LinkedList<Click> clickLLS(File file) throws IOException {
        //Date, ID, Click Cost
        LinkedList<Click> clicks = new LinkedList<>();
        Scanner csvReader = new Scanner(file);
        String row = csvReader.nextLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            clickFirstDate = dateformat.convertDate(data[0]);
            clickLastDate = dateformat.convertDate(data[0]);
            clicks.add(new Click(data[1], data[0], data[2]));
        }
        while (csvReader.hasNext()) {
            row = csvReader.nextLine();
            data = row.split(",");
            clicks.add(new Click(data[1], data[0], data[2]));
            Date date = dateformat.convertDate(data[0]);
            clickFirstDate = dateformat.smallestDate(imprFirstDate,date);
            clickLastDate = dateformat.largestDate(imprLastDate,date);
        }
        System.out.println("Loaded clicks");
        return clicks;
    }

    public static LinkedList<Server> serverLLS(File file) throws IOException {
        // EntryDate, ID, ExitDate, Pages viewed, Conversion
        LinkedList<Server> servers = new LinkedList<>();
        Scanner csvReader = new Scanner(file);
        String row = csvReader.nextLine();
        String[] data = row.split(",");
        String conv = "FALSE";
        if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
            serverEntFirstDate = dateformat.convertDate(data[0]);
            serverEntLastDate = dateformat.convertDate(data[0]);
            serverExFirstDate = dateformat.convertDate(data[0]);
            serverExLastDate = dateformat.convertDate(data[0]);
            if (data[4].equals("Yes")) {
                conv = "TRUE";
            }
            if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                servers.add(new Server(data[1], data[0], data[3], conv));
            } else {
                servers.add(new Server(data[1], data[0], data[2], data[3], conv));
            }
        }
        while (csvReader.hasNext()) {
            row = csvReader.nextLine();
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
        System.out.println("Loaded server");
        return servers;
    }
}
