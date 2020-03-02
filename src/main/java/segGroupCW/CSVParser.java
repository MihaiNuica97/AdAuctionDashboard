package segGroupCW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class CSVParser {
    // Parses 3 csvs and inserts into tables
    public void parse(File clickLog, File serverLog, File impressionLog) {
        try {
            DatabaseHandler db = new DatabaseHandler();
            db.connectDB();
            db.sendSQL("DROP TABLE IF EXISTS clicks; DROP TABLE IF EXISTS server; DROP TABLE IF EXISTS impressions;");
            db.sendSQL("CREATE TABLE clicks ( entryID INT PRIMARY KEY AUTO_INCREMENT," +
                    "ID BIGINT," +
                    " Date TIMESTAMP," +
                    " ClickCost FLOAT(8) );");
            db.sendSQL("CREATE TABLE server ( entryID INT PRIMARY KEY AUTO_INCREMENT," +
                    " ID BIGINT," +
                    " EntryDate TIMESTAMP," +
                    " ExitDate TIMESTAMP," +
                    " PagesViewed INT," +
                    " Conversion BOOLEAN );");
            db.sendSQL("CREATE TABLE impressions ( entryID INT PRIMARY KEY AUTO_INCREMENT," +
                    " ID BIGINT," +
                    " Date TIMESTAMP," +
                    " Gender VARCHAR(255)," +
                    " Age VARCHAR(255)," +
                    " Income VARCHAR(255)," +
                    " Context VARCHAR(255)," +
                    " Cost FLOAT(7) );");
            System.out.println("Table Created");
            BufferedReader csvReader1 = new BufferedReader(new FileReader(clickLog));
            String row = csvReader1.readLine();
            String[] data = row.split(",");
            if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
                //INSERT INTO ? VALUES (data[1], data[0], data[2])
                db.sendSQL("INSERT INTO clicks(ID, Date, ClickCost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[2] + ");");
            }
            while ((row = csvReader1.readLine()) != null) {
                data = row.split(",");
                //Date, ID, Click Cost
                //INSERT INTO ? VALUES (data[1], data[0], data[2])
                db.sendSQL("INSERT INTO clicks(ID, Date, ClickCost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', "  + data[2] + ");");
            }
            csvReader1.close();

            BufferedReader csvReader2 = new BufferedReader(new FileReader(serverLog));
            row = csvReader2.readLine();
            data = row.split(",");
            if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
                if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                    //INSERT INTO ? (col1, col2, col4, col4) VALUES (data[1], data[0], data[3], data[4])
                    db.sendSQL("INSERT INTO server(ID, EntryDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[3] + ", " + data[4] + ");");
                } else {
                    //INSERT INTO ? VALUES (data[1], data[0], data[2], data[3], data[4])}
                    db.sendSQL("INSERT INTO server(ID, EntryDate, ExitDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', TIMESTAMP '" + data[2] + "', " + data[3] + ", " + data[4] + ");");
                }
            }
            while ((row = csvReader2.readLine()) != null) {
                data = row.split(",");
                // EntryDate, ID, ExitDate, Pages viewed, Conversion
                if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                    //INSERT INTO ? (col1, col2, col4, col4) VALUES (data[1], data[0], data[3], data[4])
                    db.sendSQL("INSERT INTO server(ID, EntryDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[3] + ", " + data[4] + ");");
                } else {
                    //INSERT INTO ? VALUES (data[1], data[0], data[2], data[3], data[4])}
                    db.sendSQL("INSERT INTO server(ID, EntryDate, ExitDate, PagesViewed, Conversion) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', TIMESTAMP '" + data[2] + "', " + data[3] + ", " + data[4] + ");");
                    //INSERT INTO SERVER(ID, ENTRYDATE, EXITDATE, PAGESVIEWED, CONVERSION) VALUES (8895519749317550080, 2015-01-01 12[*]:01:21, TIMESTAMP '2015-01-01 12:05:13', TIMESTAMP '7', NO)
                }
            }
            csvReader2.close();

            BufferedReader csvReader3 = new BufferedReader(new FileReader(impressionLog));
            row = csvReader3.readLine();
            data = row.split(",");
            if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
                //INSERT INTO ? VALUES (data[1], data[0], data[2], data[3], data[4], data[5], data[6])
                db.sendSQL("INSERT INTO server(ID, Date, Gender, Age, Income, Context, Cost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[2] + ", " + data[3] + ", " + data[4] + data[5] + ", " + data[6] + ");");
            }
            while ((row = csvReader3.readLine()) != null) {
                data = row.split(",");
                //ID, Date, Gender, Age range, Income, Context, Impression cost
                //INSERT INTO ? VALUES (data[1], data[0], data[2], data[3], data[4], data[5], data[6])
                db.sendSQL("INSERT INTO server(ID, Date, Gender, Age, Income, Context, Cost) VALUES (" + data[1] + ", TIMESTAMP '" + data[0] + "', " + data[2] + ", " + data[3] + ", " + data[4] + data[5] + ", " + data[6] + ");");
            }
            csvReader3.close();

            db.closeSQL();
        } catch (IOException e) {  // If readline function throws error
            System.out.println("Failed to readline");
        } catch (SQLException e) {  // If the dbhandler throws an error
            System.out.println("Failed to DB");
            e.printStackTrace();
        }
    }
}
