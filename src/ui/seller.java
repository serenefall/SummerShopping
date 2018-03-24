package ui;

import model.Operation;

import javax.swing.*;

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
        Operation ope = new Operation();
//            Connections con = (Connections) Connections.getConnection();







    }
    public void setSellerID(String username) {
        this.sellerID = username;

    }
}
