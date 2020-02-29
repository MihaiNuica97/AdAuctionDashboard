package segGroupCW;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CSVParser {
    // Parses 3 csvs and inserts into tables
    public void parse(File clickLog, File serverLog, File impressionLog) {
        try {
            BufferedReader csvReader1 = new BufferedReader(new FileReader(clickLog));
            String row = csvReader1.readLine();
            String[] data = row.split(",");
            if (!data[0].equals("Date")) {  // Includes first line if it isn't the headings
                //INSERT INTO ? VALUES (data[1], data[0], data[2])
            }
            while ((row = csvReader1.readLine()) != null) {
                data = row.split(",");
                //Date, ID, Click Cost
                //INSERT INTO ? VALUES (data[1], data[0], data[2])
            }
            csvReader1.close();

            BufferedReader csvReader2 = new BufferedReader(new FileReader(serverLog));
            row = csvReader2.readLine();
            data = row.split(",");
            if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
                if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                    //INSERT INTO ? (col1, col2, col4, col4) VALUES (data[1], data[0], data[3], data[4])
                } else {
                    //INSERT INTO ? VALUES (data[1], data[0], data[2], data[3], data[4])}
                }
            }
            while ((row = csvReader2.readLine()) != null) {
                data = row.split(",");
                // EntryDate, ID, ExitDate, Pages viewed, Conversion
                if (data[2].equals("n/a")) {  // If no valid exit date, skip it
                    //INSERT INTO ? (col1, col2, col4, col4) VALUES (data[1], data[0], data[3], data[4])
                } else {
                    //INSERT INTO ? VALUES (data[1], data[0], data[2], data[3], data[4])}
                }
            }
            csvReader2.close();

            BufferedReader csvReader3 = new BufferedReader(new FileReader(impressionLog));
            row = csvReader3.readLine();
            data = row.split(",");
            if (!data[0].equals("Entry Date")) {  // Includes first line if it isn't the headings
                //INSERT INTO ? VALUES (data[1], data[0], data[2], data[3], data[4], data[5], data[6])
            }
            while ((row = csvReader3.readLine()) != null) {
                data = row.split(",");
                //Date, ID, Gender, Age range, Income, Context, Impression cost
                // What type will Age Range be?
                //INSERT INTO ? VALUES (data[1], data[0], data[2], data[3], data[4], data[5], data[6])
            }
            csvReader3.close();
        } catch (IOException e) {  // If readline function throws error
            System.out.println("Failed to readline");
        }
    }
}
