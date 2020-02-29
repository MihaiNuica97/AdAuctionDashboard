package segGroupCW;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;



public class DatabaseHandler {

    private Connection conn;
    private Statement sqlcmd;

    public void connectDB(){

        try {
            //Fetch the Driver
            Class.forName("org.h2.Driver");

            /*
            * make a connection to the database (at file path: working direction + path to Database folder)
            * if the database doesn't exist, it will create one
             */
            Connection conn = DriverManager.getConnection("jdbc:h2:" + getWorkingDir() + "/src/main/resources/Database/baseDB","sa","");

            //creates a statement object to send to the DB
            sqlcmd = conn.createStatement();


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
    
    //close the connection to DB, DBc will consist
    public void closeConnection() throws SQLException {
        conn.close();
    }

    /*
     * String passed is a sql statement, statement is sent to DB DBMS through JDBS
     * connection to DB persists after command is executed, statement object can be changed and re-executed on DB
     */
    public void sendSQL(String sql) throws SQLException {
        sqlcmd.executeUpdate(sql);
    }

    //close sql statement
    public void closeSQL() throws SQLException {
        sqlcmd.close();

    }


    // gets the working directory of current project (file path up-until the project file)
    private static String getWorkingDir(){ return System.getProperty("user.dir"); }

}