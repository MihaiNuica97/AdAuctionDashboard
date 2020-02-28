package segGroupCW;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVParser {
    public void parse(String pathToClickLog, String pathToServerLog, String pathToImpressionLog) {
        
        try {
            BufferedReader csvReader1 = new BufferedReader(new FileReader(pathToClickLog));
            csvReader1.readLine();
            String row;
            while ((row = csvReader1.readLine()) != null) {
                String[] data = row.split(",");
                //Date, ID, Click Cost
                //INSERT INTO ? VALUES (data[0], data[1], data[2])
            }
            csvReader1.close();

            BufferedReader csvReader2 = new BufferedReader(new FileReader(pathToServerLog));
            csvReader2.readLine();
            while ((row = csvReader2.readLine()) != null) {
                String[] data = row.split(",");
                // EntryDate, ID, ExitDate, Pages viewed, Conversion
                if (data[2].equals("n/a")) {
                    //INSERT INTO ? (col1, col2, col4, col4) VALUES (data[0], data[1], data[3], data[4])
                } else {
                    //INSERT INTO ? VALUES (data[0], data[1], data[2], data[3], data[4])}
                }
            }
            csvReader2.close();

            BufferedReader csvReader3 = new BufferedReader(new FileReader(pathToImpressionLog));
            csvReader3.readLine();
            while ((row = csvReader3.readLine()) != null) {
                String[] data = row.split(",");
                //Date, ID, Gender, Age range, Income, Context, Impression cost

                //INSERT INTO ? VALUES (data[0], data[1], data[2], data[3], data[4], data[5], data[6])
            }
            csvReader3.close();
        } catch (IOException e) {
            System.out.println("Failed to readline");
        }
    }
}
