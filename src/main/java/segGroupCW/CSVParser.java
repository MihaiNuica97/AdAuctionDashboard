package segGroupCW;

import java.io.*;
import java.sql.SQLException;

public class CSVParser {

    private BufferedReader csvReader;
    private String row;
    //private String[] data;
    private String sqlStatement = "";

    // Parses 3 csvs and inserts into tables

    public String parseImpression(File file) throws IOException {
        //ID, Date, Gender, Age range, Income, Context, Impression cost
        sqlStatement = "";
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        System.out.println(row);
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            //sqlStatement += "MERGE INTO users(ID, Gender, Age, Income) VALUES (" + data[1] + ", '" + data[2] + "', '" + data[3] + "', '" + data[4] + "' );";
            //sqlStatement += "INSERT INTO impressions(ID, Date, Context, Cost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', '" + data[5] + "', " + data[6] + ");";
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            //sqlStatement += "MERGE INTO users(ID, Gender, Age, Income) VALUES (" + data[1] + ", '" + data[2] + "', '" + data[3] + "', '" + data[4] + "' );";
            //sqlStatement += "INSERT INTO impressions(ID, Date, Context, Cost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', '" + data[5] + "', " + data[6] + ");";
        }
        System.out.println(sqlStatement);
        return sqlStatement;
    }

    public String parseClicks(File file) throws IOException {
        //Date, ID, Click Cost
        sqlStatement = "";
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
            //sqlStatement += "INSERT INTO clicks(ID, Date, ClickCost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[2] + ");";
        }
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
            //sqlStatement += "INSERT INTO clicks(ID, Date, ClickCost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', "  + data[2] + ");";
        }
        return sqlStatement;
    }

    public String parseServer(File file) throws IOException {
        // EntryDate, ID, ExitDate, Pages viewed, Conversion
        sqlStatement = "";
        csvReader = new BufferedReader(new FileReader(file));
        row = csvReader.readLine();
        String[] data = row.split(",");
        String conv = "FALSE";
        if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
            if (data[4].equals("Yes")) {
                conv = "TRUE";
            }
            if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                //sqlStatement += "INSERT INTO server(ID, EntryDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[3] + ", " + conv + ");";
            } else {
                //sqlStatement += "INSERT INTO server(ID, EntryDate, ExitDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', TIMESTAMP '" + data[2] + "', " + data[3] + ", " + conv + ");";
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
                //sqlStatement += "INSERT INTO server(ID, EntryDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[3] + ", " + conv + ");";
            } else {
                //sqlStatement += "INSERT INTO server(ID, EntryDate, ExitDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', TIMESTAMP '" + data[2] + "', " + data[3] + ", " + conv + ");";
            }
        }
        return sqlStatement;
    }



}
