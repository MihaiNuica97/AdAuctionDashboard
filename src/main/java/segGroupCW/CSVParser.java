package segGroupCW;

import java.io.*;
import java.sql.SQLException;

public class CSVParser {

    private BufferedReader csvReader;
    private String row;
    //private String[] data;
    private String sqlStatement = "";

    // Parses 3 csvs and inserts into tables
    public void parse(File clicksFile, File serverFile, File impresssionFile) {
        try {
            //System.out.println("Parsing " + impresssionFile.getPath() + ", " + clicksFile.getPath() + ", " + serverFile.getPath());
            DatabaseHandler db = new DatabaseHandler();
            db.sendSQL("DROP TABLE IF EXISTS clicks, server, impressions, users;" + System.lineSeparator() +

                    "CREATE TABLE users ( ID BIGINT PRIMARY KEY," +
                    " Gender VARCHAR(255)," +
                    " Age VARCHAR(255)," +
                    " Income VARCHAR(255) );" + System.lineSeparator() +

                    "CREATE TABLE impressions ( ID BIGINT," +
                    " Date TIMESTAMP," +
                    " Context VARCHAR(255)," +
                    " Cost FLOAT(7)," +
                    " FOREIGN KEY (ID) REFERENCES USERS(ID)," +
                    " PRIMARY KEY (ID, Date) );" + System.lineSeparator() +

                    "CREATE TABLE clicks ( ID BIGINT," +
                    " Date TIMESTAMP," +
                    " ClickCost FLOAT(8)," +
                    " FOREIGN KEY (ID) REFERENCES USERS(ID)," +
                    " PRIMARY KEY (ID, Date) );" + System.lineSeparator() +

                    "CREATE TABLE server ( ID BIGINT," +
                    " EntryDate TIMESTAMP," +
                    " ExitDate TIMESTAMP," +
                    " PagesViewed INT," +
                    " Conversion BOOLEAN," +
                    " FOREIGN KEY (ID) REFERENCES USERS(ID)," +
                    " PRIMARY KEY (ID, EntryDate) );");

                    System.out.println("Table Created. Loading Data...");

                    db.sendSQL(parseImpression(impresssionFile));
                    System.out.println("impressions complete");

                    db.sendSQL(parseClicks(clicksFile));
                    System.out.println("clicks complete");

                    db.sendSQL(parseServer(serverFile));
                    System.out.println("server complete");




            db.closeSQL();
            System.out.println("Data Loaded.");

        } catch (IOException e) {  // If readline function throws error
            System.out.println("Failed to readline");
        } catch (SQLException e) {  // If the dbhandler throws an error
            System.out.println("Failed to DB");
            e.printStackTrace();
        }
    }


    public String parseImpression(File file) throws IOException {
        //ID, Date, Gender, Age range, Income, Context, Impression cost
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        System.out.println(row);
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            sqlStatement += "MERGE INTO users(ID, Gender, Age, Income) VALUES (" + data[1] + ", '" + data[2] + "', '" + data[3] + "', '" + data[4] + "' );";
            sqlStatement += "INSERT INTO impressions(ID, Date, Context, Cost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', '" + data[5] + "', " + data[6] + ");";
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            sqlStatement += "MERGE INTO users(ID, Gender, Age, Income) VALUES (" + data[1] + ", '" + data[2] + "', '" + data[3] + "', '" + data[4] + "' );";
            sqlStatement += "INSERT INTO impressions(ID, Date, Context, Cost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', '" + data[5] + "', " + data[6] + ");";
        }
        System.out.println(sqlStatement);
        return sqlStatement;
    }

    public String parseClicks(File file) throws IOException {
        //Date, ID, Click Cost
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            sqlStatement += "INSERT INTO clicks(ID, Date, ClickCost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[2] + ");";
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            sqlStatement += "INSERT INTO clicks(ID, Date, ClickCost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', "  + data[2] + ");";
        }
        return sqlStatement;
    }

    public String parseServer(File file) throws IOException {
        // EntryDate, ID, ExitDate, Pages viewed, Conversion
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        String conv = "FALSE";
        if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
            if (data[4].equals("Yes")) {
                conv = "TRUE";
            }
            if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                sqlStatement += "INSERT INTO server(ID, EntryDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[3] + ", " + conv + ");";
            } else {
                sqlStatement += "INSERT INTO server(ID, EntryDate, ExitDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', TIMESTAMP '" + data[2] + "', " + data[3] + ", " + conv + ");";
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
                sqlStatement += "INSERT INTO server(ID, EntryDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[3] + ", " + conv + ");";
            } else {
                sqlStatement += "INSERT INTO server(ID, EntryDate, ExitDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', TIMESTAMP '" + data[2] + "', " + data[3] + ", " + conv + ");";
            }
        }
        return sqlStatement;
    }



}
