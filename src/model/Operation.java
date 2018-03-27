package model;

import javax.swing.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import oracle.jdbc.driver.*;
import java.util.ArrayList;

public class Operation {

    /**************** Queries for Admin/User Login *****************************/

    // revised by Ling
    public boolean userLogin(String username, String pwd, Connection con) throws SQLException {
        int userid = Integer.parseInt(username);
        int iPwd = Integer.parseInt(pwd);
        int rPwd = -1;

        try (PreparedStatement ps = con.prepareStatement
                ("SELECT PASSWORD FROM Users WHERE id = ? ")) {
            ps.setInt(1, userid);
            ResultSet temp = ps.executeQuery();
            while (temp.next()) rPwd = temp.getInt("PASSWORD");

            ps.close();
        }

        return (iPwd == rPwd);
    }

    /**************** Queries for Customer *****************************/

    public ArrayList<Fields> searchProducts(String ProductName, String Category, String price_range_1, String price_range_2,
                                            String manufacturer, Connection con) throws SQLException {
        ArrayList<Fields> target_product = new ArrayList<>();
        int priceRange1 = Integer.parseInt(price_range_1);
        int priceRange2 = Integer.parseInt(price_range_2);
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT Product_ID, Product_name, Manufacturer, Price, Seller_name, Seller_ID, Rating " +
                        "FROM Products p , Seller s, Rate r, Has h" +
                        "WHERE p.Product_Name LIKE ‘%?%’ AND p.Product_ID = h.Product_ID AND s.Seller_ID = h.Seller_ID " +
                        "AND Category = ? AND Price > ? AND Price < ? AND Manufacturer = ? ")) {

            ps.setString(1, ProductName);
            ps.setString(2, Category);
            ps.setInt(3, priceRange1);
            ps.setInt(4, priceRange2);
            ps.setString(5, manufacturer);

            ResultSet temp = ps.executeQuery();

            while (temp.next()) {
                Fields item = new Fields();
                item.setProduct_id(temp.getInt("Product_ID"));
                item.setProduct_name(temp.getString("Product_name"));
                item.setManufacturer(temp.getString("Manufacturer"));
                item.setPrice(temp.getInt("Price"));
                item.setSeller_name(temp.getString("Seller_name"));
                item.setSeller_id(temp.getInt("Seller_ID"));
                item.setRating(temp.getInt("Rating"));
                target_product.add(item);
            }
            ps.close();
        }
        return target_product;
    }

    /*
    Put order to make purchase

    Input: Product ID, Seller ID, Quantity, Payment Method(Visa or MasterCard), VIP points used
    Output: Insert 1 tuple for each Product ID into table PutOrder under the same Order ID:
            Order IDs set to the latest created Order ID + 1.
            Status set to “In Progress”.
            Quantity of Products in the table Has to be updated accordingly.
            Date placed set to Today.
            Shipping Date set to Tomorrow.
            Arrival Date set to 5 days from today.
            VIP points used deducted from the Customer’s VIP points
    Return: Total cost = cost of all products in the order - VIP points/50
    */
    public double putOrder(String customer_ID, String product_ID, String seller_ID, String Quantity,
                           String Payment_method, String VipPoints_used, Connection con) throws SQLException {
        int product_id = Integer.parseInt(product_ID);
        int seller_id = Integer.parseInt(seller_ID);
        int customer_id = Integer.parseInt(customer_ID);
        int quantity = Integer.parseInt(Quantity);
        int vip_points_used = Integer.parseInt(VipPoints_used);
        double totalCost = 0;

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // the date of TODAY which is when order placed
        Calendar c = Calendar.getInstance();
        String today = formatter.format(c.getTime());

        // the shipping date
        c.add(Calendar.DATE, 1);
        String today_plus1 = formatter.format(c.getTime());

        // the expected delivery date
        c.add(Calendar.DATE, 4);
        String today_plus5 = formatter.format(c.getTime());

        // get the current vip points the customer has
        int current_vip_points = 0;
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT VIP_Points FROM VIP_1" +
                        "WHERE VIP_ID = (SELECT VIP_ID FROM VIP_2 WHRER Customer_id = ?)")) {
            ps.setInt(1, customer_id);
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                current_vip_points = temp.getInt("VIP_Points");
            }
        }
        // when VIP points entered are larger than currently having, report error
        if (current_vip_points < vip_points_used) return -1;

        // Get the order number of the last order entered in the system
        int lastOrderID = 0;
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT MAX(Order_number) FROM PutOrder")) {
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                lastOrderID = temp.getInt("Order_number");
            }
        }

        // Insert new order into PutOrder
        try (PreparedStatement ps = con.prepareStatement
                ("INSERT INTO PutOrder" +
                        "(Status, Payment_method, Date_placed, Shipping_date, Arrival_date, " +
                        "VIP_points_used, Order_number , Product_ID, Customer_id, Seller_ID, Quantity) " +
                        "VALUES ('In Progress', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?")) {
            ps.setString(1, Payment_method);
            ps.setString(2, today);
            ps.setString(3, today_plus1);
            ps.setString(4, today_plus5);
            ps.setInt(5, vip_points_used);
            ps.setInt(6, lastOrderID+1);
            ps.setInt(7, product_id);
            ps.setInt(8, customer_id);
            ps.setInt(9, seller_id);
            ps.setInt(8, quantity);
            ps.close();
        }

        // Update VIP points by deducting vip points used
        try (PreparedStatement ps = con.prepareStatement
                ("UPDATE VIP_1 SET VIP_Points = VIP_Points - ?" +
                        "WHERE VIP_ID = (SELECT VIP_ID FROM VIP_2 WHRER Customer_id = ?)")) {
            ps.setInt(1, vip_points_used);
            ps.setInt(2, customer_id);
            ps.executeQuery();
            ps.close();
        }

        // return the total cost of the order
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT Price FROM Has WHERE Seller_ID = ? AND Product_ID = ?")) {
            ps.setInt(1, seller_id);
            ps.setInt(2, product_id);
            ResultSet temp = ps.executeQuery();

            while (temp.next()) {
                totalCost = quantity * temp.getInt("Price") - vip_points_used/50;
            }
            ps.close();
        }

        return totalCost;
    }

    // Input: Order ID
    // Output: Change status of Order from “In Process” to “Completed”. Check whether the customer is VIP.
    //         If yes, increase the VIP points by the floor of the total cost of the Order.
    public boolean completeOrder(String order_ID, Connection con) throws SQLException {
        int order_id = Integer.parseInt(order_ID);
        try (PreparedStatement ps = con.prepareStatement
                ("UPDATE PutOrder SET Status = 'Completed' WHERE Order_number = ?")) {
            ps.setInt(1, order_id);
            ps.executeQuery();
            ps.close();
        }

        int vipPoints_earned;
        int customerID = 0;
        int productID = 0;
        int sellerID = 0;
        int quantity = 0;
        int price = 0;
        int vipPoints_used = 0;
        int vipID = -1;

        try (PreparedStatement ps = con.prepareStatement
                ("SELECT Product_ID, Seller_ID, Customer_ID, Quantity, VIP_points_used " +
                        "FROM PutOrder WHERE Order_number = ?")) {
            ps.setInt(1, order_id);
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                customerID = temp.getInt("Customer_ID");
                productID = temp.getInt("Product_ID");
                sellerID = temp.getInt("Seller_ID");
                quantity = temp.getInt("Quantity");
                vipPoints_used = temp.getInt("VIP_points_used");
            }
            ps.close();
        }

        // get the VIP ID of a customer. If he/she is not a VIP, vipID remains -1
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT VIP_ID FROM VIP_2 WHERE CUSTOMER_ID = ?")) {
            ps.setInt(1, customerID);
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                vipID = temp.getInt("VIP_ID");
            }
            ps.close();
        }

        // If the customer is a VIP
        if (vipID > -1) {
            try (PreparedStatement ps = con.prepareStatement
                    ("SELECT Price FROM Has WHERE Product_ID = ? AND Seller_ID = ?")) {
                ps.setInt(1, productID);
                ps.setInt(2, sellerID);
                ResultSet temp = ps.executeQuery();
                while (temp.next()) {
                    price = temp.getInt("Price");
                }
                ps.close();
            }

            // vipPoints_earned = total order cost
            vipPoints_earned = price * quantity - vipPoints_used/50;

            // update the VIP points of the VIP customer
            try (PreparedStatement ps = con.prepareStatement
                    ("UPDATE VIP_1 SET VIP_Points = VIP_Points + ? WHERE VIP_ID = ?")) {
                ps.setInt(1, vipPoints_earned);
                ps.setInt(2, vipID);
                ps.executeQuery();
                ps.close();
            }
        }

        return true;
    }


    // Input: Product ID, Order ID, Seller ID, Rating(1/2/3/4/5)
    // Output: Insert a tuple in the table Rate if the Order ID is “completed” and return true. Otherwise return false.
    public boolean rateProduct(String order_ID, String Rating, Connection con) throws SQLException {
        int order_id = Integer.parseInt(order_ID);
        int rating = Integer.parseInt(Rating);

        String status = "";
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT Status FROM PurOrder WHERE Order_number = ?")) {
            ps.setInt(1, order_id);
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                status = temp.getString("Status");
            }
            ps.close();
        }

        if (status.equals("Completed")){
            try (PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Rate (Rating, Order_number) VALUES (?, ?) " +
                            "ON DUPLICATE KEY UPDATE Rating = ?")) {
                ps.setInt(1, rating);
                ps.setInt(2, order_id);
                ps.setInt(3, rating);
                ps.executeQuery();
                ps.close();
            }
            return true;
        } else {
            return false;
        }
    }

    // TODO
    //return -1 if cannot get totalcost; else, return the total cost.
    /***********needs to fullfil the SQL statement properly and set the related parameters*****************/
    public double getTotalPurchaseCost(String customer_ID, String startDate, String  endDate, Connection con) throws SQLException {

        double totolCost = -1;
        Date tempStartDate = new Date(Long.parseLong(startDate));
        Date tempEndDate = new Date((Long.parseLong(endDate)));
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
    public ArrayList<Fields>  getTotalPurchaseList (String customer_ID, String date1, String date2, Connection con) throws SQLException {

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
    public ArrayList<Fields>  getBestSeller (Connection con) throws SQLException {

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

    /**************** Queries for Seller *****************************/

    public boolean updatePrice(String seller_ID, String productID, String price,Connection con){
        boolean status = false;
        int product_id = Integer.parseInt(productID);
        int seller_id = Integer.parseInt(seller_ID);
        ArrayList<Fields> target_list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement
                    ("UPDATE Has SET Price = ? where Product_ID = ? AND Seller_ID = ?" );
            ps.setString(1,price);
            ps.setInt(2, product_id);
            ps.setInt(3,seller_id);
            int temp = ps.executeUpdate();
            if (temp!=0) {
                status = true;
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    // return -1 if there is any trouble
    // Otherwise return the totals sales of a product within a given time
    public int salesByProduct(String productID, String startDate, String endDate, Connection con) {
        int totalSales = -1;
        int product_id = Integer.parseInt(productID);
        Date tempStartDate = new Date(Long.parseLong(startDate));
        Date tempEndDate = new Date((Long.parseLong(endDate)));
        try {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT Quantity From PutOrder" +
                            "WHERE Product_ID = ? and date_placed >= ? and date_placted <= endDate");

            ps.setInt(1,product_id);
            ps.setDate(2,tempStartDate);
            ps.setDate(3,tempEndDate);
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                totalSales += temp.getInt("Quantity");
            }
            ps.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return totalSales;
    }

    // return a 2D string arrayList for all the products information
    // array[i][] represents the strings for one column, like productID,category
    // array[][j] represents the strings for one row, like all the attributes for one product
    public ArrayList<ArrayList<String>> viewProduct(String Seller_ID,Connection con) {
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        int seller_id = Integer.parseInt(Seller_ID);
        try {
            PreparedStatement ps = con.prepareStatement
                    ("SELECT has.product_id, Category, Manufacturer, Product_Name, Quantity, Price, seller_ID " +
                            "FROM Products, Has WHERE Seller_ID = ?" );
            ps.setInt(1, seller_id);
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                ArrayList<String> tempResult = new ArrayList<String>();
                tempResult.add(temp.getString("Product_ID"));
                tempResult.add(temp.getString("Category"));
                tempResult.add(temp.getString("Manufacturer"));
                tempResult.add(temp.getString("Product_Name"));
                tempResult.add(temp.getString("Quantity"));
                tempResult.add(temp.getString("Price"));
                tempResult.add(temp.getString("seller_ID"));
                result.add(tempResult);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // TODO: check why status never changed
    // the SQL statement in addProduct remained to be added.
    public boolean addProduct(String productID, String brand, String name, String category,Connection con){
        boolean status = false;
        int product_id = Integer.parseInt(productID);
        ArrayList<Fields> target_list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Products" +
                            "(Product_ID, Category, Manufacturer,Product_Name) VALUES(?,?,?,?)" );
            ps.setInt(1, product_id);
            ps.setString(2,category);
            ps.setString(3,brand);
            ps.setString(4,name);
            int temp = ps.executeUpdate();
            if (temp != 0) {
                status = true;
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean deleteProduct(String productID, String sellerID, Connection con){
        int product_id = Integer.parseInt(productID);
        int seller_id = Integer.parseInt(sellerID);
        boolean status = false;

        try {
            PreparedStatement ps = con.prepareStatement
                    ("DELETE FROM Has " +
                            "WHERE Product_ID = ? AND Seller_ID = ? ");
            ps.setInt(1, product_id);
            ps.setInt(2, seller_id);
            int temp = ps.executeUpdate();
            if (temp != 0) {
                status = true;
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }



    /****************for sys_admin*****************************/
    public boolean deleteCustomer(String customerID, Connection con) {
        int customer_id = Integer.parseInt(customerID);
        boolean status = false;
        try {
            PreparedStatement ps = con.prepareStatement
                    ("DELETE FROM Customer " +
                            "WHERE Customer_id = ? ");
            ps.setInt(1, customer_id);
//            ResultSet temp = null;
            int temp = ps.executeUpdate();
            if (temp!=0) {
                status = true;
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean deleteSeller(String sellerID, Connection con){
        int seller_id = Integer.parseInt(sellerID);
        boolean status = false;

        try{
            PreparedStatement ps = con.prepareStatement
                    ("DELETE FROM Seller " +
                            "WHERE Seller_id = ? " );
            ps.setInt(1,seller_id);
            int temp = ps.executeUpdate();
            if (temp!=0) {
                status = true;
            }
            ps.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
}
