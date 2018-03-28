package model;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

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

    public ArrayList<Fields> searchProducts(String ProductName, String price_range_1, String price_range_2,
                                            Connection con) throws SQLException {
        ArrayList<Fields> target_product = new ArrayList<>();
        int priceRange1 = Integer.parseInt(price_range_1);
        int priceRange2 = Integer.parseInt(price_range_2);
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT p.Product_ID,p.PRODUCT_NAME, p.MANUFACTURER, Price, s.SELLER_NAME, s.Seller_ID FROM Products p , Seller s, Has h WHERE p.PRODUCT_NAME LIKE ? AND p.Product_ID = h.Product_ID AND s.Seller_ID = h.Seller_ID AND PRICE > ? AND PRICE < ?")) {

            ps.setString(1, '%'+ProductName+'%');
            ps.setInt(2, priceRange1);
            ps.setInt(3, priceRange2);

            ResultSet temp = ps.executeQuery();

            while (temp.next()) {
                Fields item = new Fields();
                item.setProduct_id(temp.getInt("Product_ID"));
                item.setProduct_name(temp.getString("Product_name"));
                item.setManufacturer(temp.getString("Manufacturer"));
                item.setPrice(temp.getInt("Price"));
                item.setSeller_name(temp.getString("Seller_name"));
                item.setSeller_id(temp.getInt("Seller_ID"));
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
                ("SELECT VIP_Points FROM VIP_1 WHERE VIP_ID = (SELECT VIP_ID FROM VIP_2 WHERE Customer_id = ?)")) {
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
        int orderID;

        try (PreparedStatement ps = con.prepareStatement
                ("SELECT MAX(Order_number) FROM PutOrder")) {
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                lastOrderID = temp.getInt("MAX(ORDER_NUMBER)");
                System.out.println("test1\n");
            }
        }
        orderID = lastOrderID + 1;

        // Insert new order into PutOrder
        try (PreparedStatement ps = con.prepareStatement
                ("INSERT INTO PutOrder (Status, Payment_method, Date_placed, Shipping_date, Arrival_date, VIP_points_used, Order_number, Product_ID, Customer_id, Seller_ID, Quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, "In Progress");
            ps.setString(2, Payment_method);
            ps.setString(3, today);
            ps.setString(4, today_plus1);
            ps.setString(5, today_plus5);
            ps.setInt(6, vip_points_used);
            ps.setInt(7, orderID);
            ps.setInt(8, product_id);
            ps.setInt(9, customer_id);
            ps.setInt(10, seller_id);
            ps.setInt(11, quantity);

            ps.executeQuery();

            System.out.println("test\n");

            ps.close();
        }

        // Update VIP points by deducting vip points used
        try (PreparedStatement ps = con.prepareStatement
                ("UPDATE VIP_1 SET VIP_Points = VIP_Points - ?" +
                        "WHERE VIP_ID = (SELECT VIP_ID FROM VIP_2 WHERE Customer_id = ?)")) {
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


    // Input: Order ID, Rating(1/2/3/4/5)
    // Output: Insert a tuple in the table Rate if the Order ID is “completed” and return true. Otherwise return false.
    public boolean rateProduct(String order_ID, String Rating, Connection con) {
        int order_id = Integer.parseInt(order_ID);
        int rating = Integer.parseInt(Rating);

        // check whether the order is Completed. Only completed order can be rated
        String status = "";
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT Status FROM PutOrder WHERE Order_number = ? ")) {
            ps.setInt(1, order_id);
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                status = temp.getString("Status");
            }
            ps.close();
        } catch (java.sql.SQLException e2) {
            System.out.println(e2.getMessage());
        }


        // if the Order is completed, rate the order. If the order was rated before, update the rating.
        int order_n = 0;
        if (status.equals("Completed")){
            try (PreparedStatement ps = con.prepareStatement
                    ("SELECT order_number FROM Rate WHERE order_number = ?")) {
                ps.setInt(1, order_id);
                ResultSet temp = ps.executeQuery();
                while (temp.next()) {
                    order_n = temp.getInt("order_number");
                }

                // if order number is already in rate table
                if (order_n == order_id) {
                    try (PreparedStatement ps1 = con.prepareStatement
                            ("update rate set rating = ? where order_number = ?")) {
                        ps1.setInt(1, rating);
                        ps1.setInt(2, order_id);
                        ps1.executeQuery();
                        ps1.close();
                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }

                    // if order number is not in rate table
                } else {
                    try (PreparedStatement ps1 = con.prepareStatement
                            ("insert into rate (rating, order_number) values (?, ?)")) {
                        ps1.setInt(1, rating);
                        ps1.setInt(2, order_id);
                        ps1.executeQuery();
                        ps1.close();
                    } catch (java.sql.SQLException e2) {
                        System.out.println(e2.getMessage());
                    }
                }

                ps.close();
            } catch (java.sql.SQLException e2){
                System.out.println(e2.getMessage());
            }
            return true;
        } else {
            return false;
        }
    }

    // WholeSeller is a seller selling all the products on the platform
    public ArrayList<Fields>  getWholeSeller (Connection con) throws SQLException {
        ArrayList<Fields> target_list = new ArrayList<>();
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT Seller_ID, Seller_Name FROM Seller WHERE Seller_ID = ANY" +
                        "(" +
                        "SELECT s.Seller_ID FROM Has h, Seller s WHERE h.Seller_ID = s.Seller_ID AND h.Product_ID IN (SELECT Product_ID FROM Products)" +
                        "GROUP BY s.Seller_ID HAVING COUNT(*) = (SELECT COUNT(*) FROM Products)" +
                        ")"
                )
        )
        {
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                Fields item = new Fields();
                item.setSeller_id(temp.getInt("Seller_ID"));
                item.setSeller_name(temp.getString("Seller_Name"));
                target_list.add(item);
            }
            ps.close();
        }
        return target_list;
    }

    // Product (list Product_Name) with the lowest average selling price
    public String findCheapestProduct(Connection con) throws SQLException {
        String CheapestProduct = "";
        int avgPrice = 0;
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT Product_Name, AvgPrice FROM Products p, " +
                        "(SELECT Product_ID as PID, AvgPrice FROM " +
                            "(SELECT Product_ID, AVG(Price) as AvgPrice FROM Has GROUP by Product_ID)" +
                            "WHERE AvgPrice = (SELECT  MIN(AvgPrice) " +
                                                "FROM (SELECT Product_ID, AVG(Price) as AvgPrice FROM Has GROUP BY Product_ID)" +
                                                ")" +
                        ") WHERE p.Product_ID = PID" )){
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                CheapestProduct = temp.getString("Product_Name");
                avgPrice = temp.getInt("AvgPrice");
            }

            ps.close();
        }

        return CheapestProduct + " with average price $" + Integer.toString(avgPrice);
    }


    // Product (list Product_Name) with the highest average selling price
    public String findHighestProduct(Connection con) throws SQLException {
        String HighestProduct = "";
        int avgPrice = 0;
        try (PreparedStatement ps = con.prepareStatement
                ("SELECT Product_Name, AvgPrice FROM Products p, " +
                        "(SELECT Product_ID as PID, AvgPrice FROM " +
                        "(SELECT Product_ID, AVG(Price) as AvgPrice FROM Has GROUP by Product_ID)" +
                        "WHERE AvgPrice = (SELECT  MAX(AvgPrice) " +
                        "FROM (SELECT Product_ID, AVG(Price) as AvgPrice FROM Has GROUP BY Product_ID))" +
                        ") WHERE p.Product_ID = PID" )){
            ResultSet temp = ps.executeQuery();
            while (temp.next()) {
                HighestProduct = temp.getString("Product_Name");
                avgPrice = temp.getInt("AvgPrice");
            }

            ps.close();
        }

        return HighestProduct + " with average price $" + Integer.toString(avgPrice);
    }

    /**************** Queries for Seller *****************************/

    public boolean updatePrice(String seller_ID, String productID, String Price, String Quantity, Connection con) throws SQLException{
        int product_id = Integer.parseInt(productID);
        int seller_id = Integer.parseInt(seller_ID);
        int quantity = Integer.parseInt(Quantity);
        int price = Integer.parseInt(Price);


        try (PreparedStatement ps = con.prepareStatement
                    ("UPDATE Has SET Price = ?, Quantity = ? WHERE Product_ID = ? AND Seller_ID = ?" )){
            ps.setInt(1, price);
            ps.setInt(2, quantity);
            ps.setInt(3, product_id);
            ps.setInt(4, seller_id);
            ps.executeUpdate();
            ps.close();
        }
        return true;
    }


    public boolean addProduct(String seller_ID, String product_ID, String brand, String name,
                              String category, String Quantity, String Price, Connection con) throws SQLException {
        int product_id = Integer.parseInt(product_ID);
        int seller_id = Integer.parseInt(seller_ID);
        int quantity = Integer.parseInt(Quantity);
        int price = Integer.parseInt(Price);

        try (PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Products" +
                            "(Product_ID, Category, Manufacturer,Product_Name) VALUES(?,?,?,?)" )){
            ps.setInt(1, product_id);
            ps.setString(2,category);
            ps.setString(3,brand);
            ps.setString(4,name);
            ps.executeUpdate();
            ps.close();
        }

        try (PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO Has (Product_ID, Seller_ID, Quantity, Price) VALUES(?,?,?,?)" )){
            ps.setInt(1, product_id);
            ps.setInt(2, seller_id);
            ps.setInt(3, quantity);
            ps.setInt(4, price);
            ps.executeUpdate();
            ps.close();
        }

        return true;
    }

    public boolean deleteProduct(String productID, String sellerID, Connection con) throws SQLException {
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
    public boolean deleteCustomer(String customerID, Connection con)  {
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

    public boolean deleteSeller(String sellerID, Connection con)  {
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
