package ui;

import model.Connections;
import model.Operation;
import model.Fields;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class customer {
    private JButton buyButton;
    private JTextField productIDTextField5;
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
    private JTextField productNameTextField;
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

            searchProductsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clearWindows();


                    String productName = productNameTextField.getText();
                    // Changed from original code, may have problem (Tao)
                    String price_range_1 = priceRangetextFieldLow.getText();
                    String price_range_2 = priceRangetextFieldHigh.getText();
                    boolean logged = true;
                    ArrayList<Fields> returnedArray = new ArrayList<>();
                    try {
                        returnedArray = ope.searchProducts(productName, price_range_1, price_range_2, con);
                        for (int i = 0; i < returnedArray.size();i++) {
                            String productIdReturned = Integer.toString(returnedArray.get(i).getProduct_id());
                            String productNameReturned = returnedArray.get(i).getProduct_name();
                            String manufacturerReturned = returnedArray.get(i).getManufacturer();
                            String priceReturned = Integer.toString(returnedArray.get(i).getPrice());
                            String sellerNameReturned = returnedArray.get(i).getSeller_name();
                            String selleridReturned = Integer.toString(returnedArray.get(i).getSeller_id());
                            productNameTextArea.append(productNameReturned+'\n');
                            productIDTextArea.append(productIdReturned+'\n');
                            productBrandTextArea.append(manufacturerReturned+'\n');
                            productPriceTextArea.append(priceReturned+'\n');
                            sellerNameTextArea.append(sellerNameReturned+'\n');
                            sellerIDTextArea.append(selleridReturned+'\n');
                        }
                    } catch (java.sql.SQLException e2) {
                        JOptionPane.showMessageDialog(null, "e2" + e2.getMessage());
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
                    String order_id = orderIDTextField.getText();
                    String rating = ratingTextField.getText();
                    if(ope.rateProduct(order_id,rating,con)){
                        JOptionPane.showMessageDialog(null,"Rating complete!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Fail to rate! Please try again!");
                    }
                }
            });



            wholeSellerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        clearWindows();
                        ArrayList<Fields> returned = new ArrayList<>();
                        returned = ope.getWholeSeller(con);
                        for (int i = 0; i < returned.size();i++){
                            sellerIDTextArea.append(Integer.toString(returned.get(i).getSeller_id()) + "\n");
                            sellerNameTextArea.append(returned.get(i).getSeller_name() + "\n");
                        }

                        JOptionPane.showMessageDialog(null,"Whole seller is shown!");

                    }catch (SQLException e1){
                        JOptionPane.showMessageDialog(null,"Whole seller cannot show!" + e1.getMessage());

                    }
                }
            });


            highestProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        clearWindows();
                        String returned = ope.findHighestProduct(con);
                        productNameTextArea.append(returned + '\n');

                        JOptionPane.showMessageDialog(null,"Highest product is shown!");

                    }catch (SQLException e1){
                        JOptionPane.showMessageDialog(null,"Highest product fails to show!"+e1.getMessage());
                    }
                }
            });


            cheapestProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        clearWindows();
                        String returned = ope.findCheapestProduct(con);
                        productNameTextArea.append(returned + '\n');

                        JOptionPane.showMessageDialog(null,"Cheapest product is shown!");
                    }catch (SQLException e1){
                        JOptionPane.showMessageDialog(null,"Cheapest product fails to show!"+e1.getMessage());
                    }
                }
            });



            buyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String productID = productIDTextField5.getText();
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
                    if(total!=-1){
                        JOptionPane.showMessageDialog(null, "Put Order is done! You spent $" + total);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Put Order failed due to a lack of VIP points");
                    }
                }
            });


        }catch (Exception e1) {
            JOptionPane.showMessageDialog(null,"e1");
        }
    }

    public void clearWindows(){
        this.productBrandTextArea.setText(null);
        this.productNameTextArea.setText(null);
        this.productIDTextArea.setText(null);
        this.sellerIDTextArea.setText(null);
        this.sellerNameTextArea.setText(null);
        this.productPriceTextArea.setText(null);
    }

}