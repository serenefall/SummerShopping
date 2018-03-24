package model;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Operation {
    public boolean userLogin(String username, String pwd, Connection con) throws SQLException {
        String c_pwd = "";
        PreparedStatement ps = con.prepareStatement
                ("SELECT PASSWORD " +
                        "FROM ORA_P3L0B.CUSTOMER " +
                        "WHERE username = ?");
        ps.setString(1, username);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            c_pwd = temp.getString("PASSWORD");
        }
        ps.close();
        if (c_pwd.trim().equals(pwd)) {
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
