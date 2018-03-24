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
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
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
