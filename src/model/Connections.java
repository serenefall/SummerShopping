package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
    private static Connection con=null;

    private Connections() throws SQLException{
        con = DriverManager.getConnection(
                "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522/ug", "ora_z8v0b", "a47465166");
    }

    public static Connection getConnection() throws SQLException{
        if(con == null){
            new Connections();
        }
        return con;

    }
}