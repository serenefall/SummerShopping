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
        if (iPwd != -1) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Fields> searchProducts(String ProductName, String Category, String price_range_1, String price_range_2, String manufacturer, String ratingOfSeller, Connection con) throws SQLException {
        ArrayList<Fields> target_product = new ArrayList<>();
        int priceRange1 = Integer.parseInt(price_range_1);
        int priceRange2 = Integer.parseInt(price_range_2);
        int priceRange = priceRange2 - priceRange1;
        PreparedStatement ps = con.prepareStatement
                ("SELECT Product_ID, Product_name, Manufacturer, Price, Seller_name, Seller_ID, Rating " +
                        "FROM Products p , Seller s , Rate r , Has h " +
                        "WHERE p.Name LIKE ‘%?%’ AND Category = ? AND Price < ? AND Price > ? AND Manufacturer = ? ");

       ps.setString(1,ProductName);
       ps.setString(2,Category);
       ps.setInt(3,priceRange1);
       ps.setInt(4,priceRange2);
       ps.setString(5,manufacturer);

       ResultSet temp = ps.executeQuery();

        while (temp.next()) {
            Fields item = new Fields();
            item.setProduct_id(temp.getInt("Product_ID"));
            item.setProduct_name(temp.getString("Product_name"));
            item.setManufacturer(temp.getString("Manufacturer"));
            item.setPrice(temp.getString("Price"));
            item.setSeller_name(temp.getString("Seller_name"));
            item.setSeller_id(temp.getInt("Seller_ID"));
            item.setRating(temp.getInt("Rating"));
            target_product.add(item);
        }
        ps.close();
        return target_product;
    }

    public boolean completeOrder(String order_ID, Connection con) throws SQLException {
        int order_id = Integer.parseInt(order_ID);
        boolean status = false;
        // need to modify SQL statement here about chaning VIP customer points
        PreparedStatement ps = con.prepareStatement
                ("UPDATE PutOrder" +
                        "SET Status = 'In progress' "+
                        "WHERE 'Order number' = ? and Status = 'In progress'" );

        ps.setInt(1,order_id);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            status = true;
        }
        ps.close();
        return status;
    }



    // return: status means rating sucessfully; else fails to rate
    public boolean rateProduct(String ProductName, String order_ID, String seller_Id, String ratingOfSeller, Connection con) throws SQLException {
        int order_id = Integer.parseInt(order_ID);
        int seller_id = Integer.parseInt(seller_Id);
        boolean status = false;
        // need to modify SQL statement here
        PreparedStatement ps = con.prepareStatement
                ("UPDATE PutOrder" +
                        "WHERE 'Order number' = ?" );

        //ps.setInt(1,order_id);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            status = true;
        }
        ps.close();
        return status;
    }

    // Date Range not sure about show to translate data range
    // return: status means rating sucessfully; else fails to rate
    public double getTotalPurchaseCost(Date date1, Date date2, Connection con) throws SQLException {

        double totolCost = 0.0;
        // need to modify SQL statement here, get the total cost within data range date1 to date2
        PreparedStatement ps = con.prepareStatement
                ("UPDATE PutOrder" +
                        "WHERE 'Order number' = ?" );

        //ps.setInt(1,order_id);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            // needs to change the totalcost here
            //totolCost += temp.getDouble("price");
            totolCost = 0.0;

        }
        ps.close();
        return totolCost;
    }

    // Date Range not sure about show to translate data range
    // return: status means rating sucessfully; else fails to rate
    public ArrayList<Fields>  getTotalPurchaseList (Date date1, Date date2, Connection con) throws SQLException {

        boolean status = false;
        ArrayList<Fields> target_list = new ArrayList<>();
        // need to modify SQL statement here, get the total cost within data range date1 to date2
        PreparedStatement ps = con.prepareStatement
                ("UPDATE PutOrder" +
                        "WHERE 'Order number' = ?" );

        //ps.setInt(1,order_id);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            Fields item = new Fields();
            item.setProduct_id(temp.getInt("Product_name"));
            item.setProduct_name(temp.getString("Brand"));
            item.setManufacturer(temp.getString("Price"));
            target_list.add(item);
        }
        ps.close();
        return target_list;
    }

    // wait for SQL Statement
    // return: status means rating sucessfully; else fails to rate
    public ArrayList<Fields>  getBestSeller (ArrayList<Integer> ratings, Connection con) throws SQLException {

        boolean status = false;
        ArrayList<Fields> target_list = new ArrayList<>();
        // need to modify SQL statement here, get the total cost within data range date1 to date2
        PreparedStatement ps = con.prepareStatement
                ("UPDATE PutOrder" +
                        "WHERE 'Order number' = ?" );

        //ps.setInt(1,order_id);
        ResultSet temp = ps.executeQuery();
        while (temp.next()) {
            Fields item = new Fields();
            item.setProduct_id(temp.getInt("Seller ID"));
            item.setProduct_name(temp.getString("Name"));
            target_list.add(item);
        }
        ps.close();
        return target_list;
    }

}
