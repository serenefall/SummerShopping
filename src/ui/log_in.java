package ui;
import java.sql.*;
import model.Connections;
import model.Operation;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class log_in {
    public JPanel Login;
    private JTextField usernameTextField;
    private JTextField passwordTextField;
    private JButton logInButton;

    public Operation ope = new Operation();

    public log_in() {
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = (Connection) Connections.getConnection();
                    String username = usernameTextField.getText();
                    // Changed from original code, may have problem (Tao)
                    String pwd = passwordTextField.getText();
                    boolean logged;

                    try{
                        logged = ope.userLogin(username,pwd,con);
                        if(logged && username.length() == 3){
                            // switch to Customer UI
                            JOptionPane.showMessageDialog(null,"success");
                            JFrame frame = JFrames.get_frame();
                            frame.setTitle("customer_UI");
                            customer customer = new customer();
                            frame.setContentPane(customer.CustomerUI);
                            frame.pack();
                            frame.setVisible(true);
                            customer.setCustomerID(username);
                        }
                        else if(logged && username.length()==6){
                            JOptionPane.showMessageDialog(null,"success");
                            JFrame frame = JFrames.get_frame();
                            frame.setTitle("seller_UI");
                            seller seller = new seller();
                            frame.setContentPane(seller.SellerUI);
                            frame.pack();
                            frame.setVisible(true);
                            seller.setSellerID(username);
                        }
                        else if (logged && username.equals("1")){
                            JOptionPane.showMessageDialog(null,"success admin");
                            JFrame frame = JFrames.get_frame();

                            frame.setTitle("admin_UI");
                            sys_admin admin = new sys_admin();
                            frame.setContentPane(admin.AdminUI);
                            frame.pack();
                            frame.setVisible(true);
                            admin.setAdminID(username);

                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Connection error");
                        }
                    }
                    catch (java.sql.SQLException e2){
                        JOptionPane.showMessageDialog(null,"e2");
                    }
                } catch (java.sql.SQLException e1) {
                    JOptionPane.showMessageDialog(null,"e1");
                }
                //JOptionPane.showMessageDialog(null,"outside");
            }
        });


    }

}
