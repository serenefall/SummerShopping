package ui;

import model.Connections;
import model.Fields;
import model.Operation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class customer {
    private JButton buyButton;
    private JTextField productIDBuyTextField5;
    private JTextField quantityTextField6;
    private JTextField sellerIDBuyTextField;
    private JTextField paymentTextField;
    private JTextField vipTextField;
    private JButton completeOrderButton;
    private JTextField textField3;
    private JButton searchProductsButton;
    private JTextField textField4;
    private JTextField priceRangetextFieldLow;
    private JTextField priceRangetextFieldHigh;
    private JTextField ratingTextField;
    private JTextField sellerIDTextField;
    private JTextField orderIDTextField;
    private JTextField productIDTextField;
    private JButton rateProductButton;
    public JPanel CustomerUI;
    private JTextArea productNameTextArea;
    private JTextArea productBrandTextArea;
    private JTextArea productPriceTextArea;
    private JTextArea productIDTextArea;
    private JTextArea totalCostTextArea;
    private JTextArea sellerIDTextArea;
    private JTextArea sellerNameTextArea;
    private JTextField productNameText;
    private JButton highestProductButton;
    private JButton cheapestProductButton;
    private JButton wholeSellerButton;
    private String customerID;

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }


    public customer(){
        try{
            Operation ope = new Operation();
            Connection con = Connections.getConnection();

//            searchProductsButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String productName = textField4.getText();
//                    // Changed from original code, may have problem (Tao)
//                    String Category = categoryTextField.getText();
//                    String price_range_1 = priceRangetextFieldLow.getText();
//                    String price_range_2 = priceRangetextFieldHigh.getText();
//                    String manufacturer = brandTextField.getText(); //Manufacturer isn't in customer's UI, how to get this information?
//                    String ratingOfSeller = ratingTextField.getText();
//                    boolean logged = true;
//                    ArrayList<Fields> returnedArray = new ArrayList<>();
//                    try {
//                        returnedArray = ope.searchProducts(productName, Category, price_range_1, price_range_2, manufacturer, con);
//                        for (int i = 0; i < returnedArray.size();i++) {
//                            String productIdReturned = Integer.toString(returnedArray.get(i).getProduct_id());
//                            String productNameReturned = returnedArray.get(i).getProduct_name();
//                            String manufacturerReturned = returnedArray.get(i).getManufacturer();
//                            String priceReturned = returnedArray.get(i).getPrice();
//                            String sellerNameReturned = returnedArray.get(i).getSeller_name();
//                            String selleridReturned = Integer.toString(returnedArray.get(i).getSeller_id());
//                            String ratingReturned = Integer.toString(returnedArray.get(i).getRating());
//                            productNameTextArea.append(productNameReturned+'\n');
//                            productIDTextArea.append(productIdReturned+'\n');
//                            productBrandTextArea.append(manufacturerReturned+'\n');
//                            productPriceTextArea.append(priceReturned+'\n');
//                            sellerNameTextArea.append(sellerNameReturned+'\n');
//                            sellerIDTextArea.append(selleridReturned+'\n');
//                            sellerRatingTextArea.append(ratingReturned+'\n');
//                        }
//                    } catch (java.sql.SQLException e2) {
//                        JOptionPane.showMessageDialog(null, "e2");
//                    }
//                }
//            });


            completeOrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String order_id = textField3.getText();
                    boolean status = false;
                    try {
                        status = ope.completeOrder(order_id,con);
                        if(status){
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
                    String productName = sellerIDTextField.getText();
                    String order_id = orderIDTextField.getText();
                    String seller_id  = productIDTextField.getText();
                    String rating = ratingTextField.getText();
                    boolean status = false;
                    try {
                        status = ope.rateProduct(customerID,rating,con);
                        if(status){
                            JOptionPane.showMessageDialog(null,"Rating complete!");
                        }
                    } catch (java.sql.SQLException e2) {
                        JOptionPane.showMessageDialog(null, "Fail to rate! Please try again!");
                    }
                }
            });







            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String productID = productIDBuyTextField5.getText();
                    String sellerID = sellerIDBuyTextField.getText();
                    String quantity = quantityTextField6.getText();
                    String payment = paymentTextField.getText();
                    String vipPoints = vipTextField.getText();
                    double total = 0;
                    try {
                        total = ope.putOrder(customerID, productID,sellerID,quantity,payment,vipPoints,con);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    if(total!=0){
                        JOptionPane.showMessageDialog(null, "Put Order is done!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Put Order failed!");

                    }


                }
            });


        }catch (Exception e1) {
            JOptionPane.showMessageDialog(null,"e1");
        };
    }

}