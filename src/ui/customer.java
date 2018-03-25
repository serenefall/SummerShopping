package ui;

import model.Connections;
import model.Fields;
import model.Operation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.Date;

public class customer {
    private JButton buyButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField11;
    private JTextField textField1;
    private JTextField textField2;
    private JButton completeOrderButton;
    private JTextField textField3;
    private JButton totalPurchasedCostButton;
    private JButton totalPurchasedListButton;
    private JButton bestSellerButton;
    private JButton searchProductsButton;
    private JTextField textField4;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField8;
    private JTextField textField7;
    private JTextField textField9;
    private JTextField textField10;
    private JButton rateProductButton;
    public JPanel CustomerUI;
    private String customerID;

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }


    public void customer(){
       try{
            Operation ope = new Operation();
            Connection con = (Connection) Connections.getConnection();

            searchProductsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String productName = textField4.getText();
                    // Changed from original code, may have problem (Tao)
                    String Category = textField14.getText();
                    String price_range_1 = textField12.getText();
                    String price_range_2 = textField13.getText();
                    String manufacturer = textField15.getText(); //Manufacturer isn't in customer's UI, how to get this information?
                    String ratingOfSeller = textField8.getText();
                    boolean logged = true;
                    ArrayList<Fields> returnedArray = new ArrayList<>();
                    try {
                        returnedArray = ope.searchProducts(productName, Category, price_range_1, price_range_2, manufacturer, ratingOfSeller, con);
                        Object[] cols = {"Product_ID","Product_name","Manufacturer","Price","Seller_name","Seller_ID","Rating"};
                        Object [][] rows = new Object[returnedArray.size()][7];
                        for (int i = 0; i < returnedArray.size();i++) {
                            rows[i][0] = returnedArray.get(i).getProduct_id();
                            rows[i][1] = returnedArray.get(i).getProduct_name();
                            rows[i][2] = returnedArray.get(i).getManufacturer();
                            rows[i][3] = returnedArray.get(i).getPrice();
                            rows[i][4] = returnedArray.get(i).getSeller_name();
                            rows[i][5] = returnedArray.get(i).getSeller_id();
                            rows[i][6] = returnedArray.get(i).getRating();
                        }
                        JTable table = new JTable(rows, cols);
                        JOptionPane.showMessageDialog(null, new JScrollPane(table));
                    } catch (java.sql.SQLException e2) {
                        JOptionPane.showMessageDialog(null, "e2");
                    }
                }
            });


            completeOrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String order_id = textField3.getText();
                    boolean status = false;
                    try {
                        status = ope.completeOrder(order_id,con);
                        if(true == status){
                            JOptionPane.showMessageDialog(null,"order completed");
                        }
                    } catch (java.sql.SQLException e2) {
                        JOptionPane.showMessageDialog(null, "cannot complete order");
                    }
                }
            });

           rateProductButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   //Product ID, Order ID, Seller ID, Rating(1/2/3/4/5)
                   // corresponding to text field 7 9 10 8
                   String productName = textField7.getText();
                   String order_id = textField9.getText();
                   String seller_id  = textField10.getText();
                   String rating = textField8.getText();
                   boolean status = false;
                   try {
                       status = ope.rateProduct(productName,order_id,seller_id,rating,con);
                       if(true == status){
                           JOptionPane.showMessageDialog(null,"Rating complete!");
                       }
                   } catch (java.sql.SQLException e2) {
                       JOptionPane.showMessageDialog(null, "Fail to rate! Please try again!");
                   }
               }
           });

           totalPurchasedCostButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   // cannot find related Date range in the customer.form, so temporarily set the date range to a specific one, need
                   // to change to the Date get from customer input later
                   Date date1 = new Date(2018,3,1);
                   Date date2 = new Date(2018,3,25);
                   double totolCost = 0.0;
                   try {
                       totolCost = ope.getTotalPurchaseCost(date1,date2,con);
                       JOptionPane.showMessageDialog(null,totolCost);

                   } catch (java.sql.SQLException e2) {
                       JOptionPane.showMessageDialog(null,"fail to get the tocal cost!");
                   }
               }
           });

           totalPurchasedListButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   // cannot find related Date range in the customer.form, so temporarily set the date range to a specific one, need
                   // to change to the Date get from customer input later
                   Date date1 = new Date(2018,3,1);
                   Date date2 = new Date(2018,3,25);
                   ArrayList<Fields> returnedArray = new ArrayList<>();
                   try {
                       returnedArray = ope.getTotalPurchaseList(date1,date2,con);
                       Object[] cols = {"Product_name","Brand","Price"};
                       Object [][] rows = new Object[returnedArray.size()][3];
                       for (int i = 0; i < returnedArray.size();i++) {
                           rows[i][0] = returnedArray.get(i).getProduct_name();
                           rows[i][1] = returnedArray.get(i).getBrand();
                           rows[i][2] = returnedArray.get(i).getPrice();
                       }
                       JTable table = new JTable(rows, cols);
                       JOptionPane.showMessageDialog(null, new JScrollPane(table));
                   } catch (java.sql.SQLException e2) {
                       JOptionPane.showMessageDialog(null, "Fail to get total purchase list! Please try again!");
                   }
               }
           });

           bestSellerButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                    // needs to fulfill the logic
                   ArrayList<Fields> returnedArray = new ArrayList<>();
                   //********to do: needs to properly initialize ratings*********************//
                   ArrayList<Integer> ratings = new ArrayList<Integer>();

                   try {
                        //******************* to be done*************************//
                       returnedArray = ope.getBestSeller(ratings,con);
                       Object[] cols = {"Seller_Id","Name"};
                       Object [][] rows = new Object[returnedArray.size()][3];
                       for (int i = 0; i < returnedArray.size();i++) {
                           rows[i][0] = returnedArray.get(i).getSeller_id();
                           rows[i][1] = returnedArray.get(i).getSeller_name();
                       }
                       JTable table = new JTable(rows, cols);
                       JOptionPane.showMessageDialog(null, new JScrollPane(table));
                   } catch (java.sql.SQLException e2) {
                       JOptionPane.showMessageDialog(null, "Fail to get best sellers! Please try again!");
                   }
               }
           });


       }catch (Exception e1) {
            JOptionPane.showMessageDialog(null,"e1");
        };
    }
    }
