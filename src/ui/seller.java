package ui;

import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class seller {
    private JButton showProductsButton;
    private JTextField productIDTextField;
    private JTextField nameTextField;
    private JTextField priceTextField;
    private JTextField brandTextField;
    private JTextField categoryTextField;
    private JTextField quantityTextField;
    private JButton addProductButton;
    private JButton deleteProductButton;
    private JButton updatePriceButton;
    private JTextField productID2TextFieldF;
    private JTextField startDateTextField;
    private JButton salesByProductButton;
    private JTextField endDateTextField;
    public JPanel SellerUI;
    private JTextArea productIDTextArea;
    private JTextArea categoryTextArea;
    private JTextArea manufacturerTextArea;
    private JTextArea quantityTextArea;
    private JTextArea nameTextArea;
    private JTextArea priceTextArea;
    private String sellerID;

    public seller(){

        try {
            Operation ope = new Operation();
            Connection con = Connections.getConnection();
            addProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String productID = productIDTextField.getText();
                    String quantity = quantityTextField.getText();
                    String brand = brandTextField.getText();
                    String price = priceTextField.getText();
                    String name = nameTextField.getText();
                    String category = categoryTextField.getText();

                    if(ope.addProduct(productID,quantity,brand,price,name,category,con)){
                        JOptionPane.showMessageDialog(null, "Product Added!");
                    }else{
                        JOptionPane.showMessageDialog(null, "Failed to add Product!");
                    }
                }
            });



            deleteProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String productID = productIDTextField.getText();

                    if(ope.deleteProduct(productID, getSellerID(),con)){
                        JOptionPane.showMessageDialog(null, "Product Deleted!");
                    }else{
                        JOptionPane.showMessageDialog(null, "Failed to delete Product!");
                    }
                }
            });




            updatePriceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String productID = productIDTextField.getText();
                    String price = priceTextField.getText();
                    if(ope.updatePrice(sellerID,productID,price,con)){
                        JOptionPane.showMessageDialog(null, "Price updated for " + productID + " !");
                    }else{
                        JOptionPane.showMessageDialog(null, "Failed to update price for "+ productID + " !");
                    }
                }
            });

            salesByProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String productID = productID2TextFieldF.getText();
                    String startDate = startDateTextField.getText();
                    String endDate = endDateTextField.getText();

                    int totalSales = ope.salesByProduct(productID,startDate,endDate,con);
                    if(totalSales == -1){
                        JOptionPane.showMessageDialog(null, "Cannot get sales!");

                    }else{
                        priceTextArea.setText(Integer.toString(totalSales));
                    }

                }
            });

            showProductsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<ArrayList<String>> products = new ArrayList<ArrayList<String>>();
                    products = ope.viewProduct(getSellerID(),con);
                    int numOfObjects = (products.get(0)).size();
                    ArrayList<JTextArea> columns = new ArrayList<>();
                    columns.add(productIDTextArea);
                    columns.add(categoryTextArea);
                    columns.add(manufacturerTextArea);
                    columns.add(quantityTextArea);
                    columns.add(nameTextArea);
                    columns.add(priceTextArea);


                    // fill in productID
                    for (int i = 0; i < products.size(); i++) {
                        for (int j = 0; j < numOfObjects;j++) {
                            JTextArea toBeFilled = columns.get(i);
                            toBeFilled.append((products.get(i)).get(j) + "\n");
                        }
                    }
                }
            });


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSellerID(String username) {
        this.sellerID = username;
    }

    public String getSellerID() {
        return this.sellerID;
    }

}
