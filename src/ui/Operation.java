package model;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import oracle.jdbc.driver.*;

public class Operation {
    public boolean userLogin(String username, String pwd, Connection con) throws SQLException {
        int iPwd = -1;
        int userid = Integer.parseInt(username);
        PreparedStatement ps = con.prepareStatement
              ("SELECT PASSWORD FROM Users WHERE id = ? ");

        ps.setInt(1,userid);

       // PreparedStatement ps = con.prepareStatement("select * from users");

        ResultSet temp = ps.executeQuery();

       // System.out.println(((OraclePreparedStatementWrapper)ps).getOriginalSQL());


        while (temp.next()) {
            iPwd = temp.getInt("PASSWORD");
        }
        ps.close();
        if (iPwd == -1) {
            return true;
        } else {
            return false;
        }
    }
//    public boolean c_login(String cid, String password, Connection con) {
//        String c_password = "";
//        PreparedStatement ps = con.prepareStatement
//                ("SELECT PASSWORD " +
//                        "FROM ORA_P3L0B.CUSTOMER " +
//                        "WHERE cid = ?");
//        ps.setString(1, cid);
//        ResultSet temp = ps.executeQuery();
//        while (temp.next()) {
//            c_password = temp.getString("PASSWORD");
//        }
//        ps.close();
//        if (c_password.trim().equals(password)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
