import model.*;
import ui.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {

    public static void main(String[] args)throws Exception {
        // connection starts
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:dbhost.ugrad.cs.ubc.ca:1522/ug", "ora_z8v0b", "a47465166");

        Operation op = new Operation();

        // tests
        //
    }
}
