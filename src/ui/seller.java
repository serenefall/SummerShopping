package ui;

import model.Connections;
import model.Operation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

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
    private JTextField textField9;
    private JTextField textField10;
    private JButton salesByProductButton;
    private JTextField textField12;
    public JPanel SellerUI;
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

                    if(ope.addProduct(productID,quantity,brand,price,name,category)){
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

                    if(ope.deleteProduct(productID)){
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
                    if(ope.updatePrice(productID,price)){
                        JOptionPane.showMessageDialog(null, "Price updated for " + productID + " !");
                    }else{
                        JOptionPane.showMessageDialog(null, "Failed to update price for "+ productID + " !");
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
}
