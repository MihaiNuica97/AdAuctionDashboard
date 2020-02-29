package segGroupCW;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;

public class SecondaryController {

    public void connectDB(){

        try {
            //Fetch the Driver
            Class.forName("org.h2.Driver");

            /*
            * make a connection to the database (at file path: working direction + path to Database folder)
            * if the database doesn't exist, it will create one
             */
            Connection conn = DriverManager.getConnection("jdbc:h2:" + getWorkingDir() + "/src/main/resources/Database/baseDB","sa","");

            /*
            * creates a statement object to send to the DB
            * enter sql command/query in 'sql' String to be sent to DB
            * connection to DB persists after command is executed, statement object can be changed and re-executed on DB
             */
            Statement sqlcmd = conn.createStatement();
            String sql =  "";
            sqlcmd.executeUpdate(sql);

            /*
            * closes connection to DB, DB will still exist after connection is gone
            * can remove or make conditional
             */
            sqlcmd.close();
            conn.close();

        }
        //catch errors with JDBC
        catch(SQLException se) {
            se.printStackTrace();
        }
        // Class errors
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    // gets the working directory of current project (file path up-until the project file)
    private static String getWorkingDir(){ return System.getProperty("user.dir"); }


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
        connectDB();
    }
}