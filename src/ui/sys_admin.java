package ui;

import model.Connections;
import model.Operation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class sys_admin {
    public JPanel AdminUI;
    private JTextField customerIDtextField;
    private JTextField sellerIDtextField;
    private JButton deleteCustomerButton;
    private JButton deleteSellerButton;
    private String adminID;

    public sys_admin(){
        try {
            Operation ope = new Operation();
            Connections con = (Connections) Connections.getConnection();

            deleteCustomerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String customerID = customerIDtextField.getText();
                        if(ope.deleteCustomer(customerID)){
                            JOptionPane.showMessageDialog(null, "Customer Deleted!");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Failed to delete Customer!");
                        }

                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(null,"failed"+e1);
                    }
                }




            });


            deleteSellerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String sellerID = sellerIDtextField.getText();
                    if(ope.deleteSeller(sellerID)){
                        JOptionPane.showMessageDialog(null, "Seller Deleted!");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Failed to delete seller!");

                    }
                }
            });





        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void setAdminID(String adminID) {
        this.adminID = adminID;
    }
}
