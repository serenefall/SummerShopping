package ui;

import javax.swing.*;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.sql.Connection;
>>>>>>> parent of 327341b... update by Tao
=======
import java.sql.Connection;
>>>>>>> parent of 327341b... update by Tao
=======
import java.sql.Connection;
>>>>>>> parent of 327341b... update by Tao

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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
        System.out.println("123");
=======
=======
>>>>>>> parent of 327341b... update by Tao
=======
>>>>>>> parent of 327341b... update by Tao
        Operation ope = new Operation();
        Connection con = Connections.getConnection();







>>>>>>> parent of 327341b... update by Tao
    }
    public void setSellerID(String username) {
        this.sellerID = username;

    }
}
