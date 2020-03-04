
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginFrame {
    JFrame frame;
    JPanel pane;
    JLabel usernameLabel, passwordLabel, titleLabel;
    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;

    public static void main(String[] args) {
        new LoginFrame();
    }

    public LoginFrame() {
        initComponents();
    }

    public void initComponents() {
        frame = new JFrame();
        frame.setTitle("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        pane = new JPanel(new GridBagLayout());
        frame.add(pane);

        GridBagConstraints c = new GridBagConstraints();
        
        titleLabel = new JLabel("Password Manager", SwingConstants.CENTER);
        c.insets = new Insets(15,15,15,15);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        pane.add(titleLabel, c);
        
        c.gridwidth = 1;
        
        usernameLabel = new JLabel("Username: ", SwingConstants.CENTER);
        c.insets = new Insets(15,15,15,0);
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 50;
        c.ipady = 20;
        c.weightx = 0.3;
        pane.add(usernameLabel, c);

        passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        c.insets = new Insets(15,15,15,0);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 50;
        c.ipady = 20;
        c.weightx = 0.3;
        pane.add(passwordLabel, c);
        
        usernameField = new JTextField();
        c.insets = new Insets(15,0,15,15);
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 150;
        c.ipady = 12;
        c.weightx = 0.7;
        pane.add(usernameField, c);

        passwordField = new JPasswordField();
        c.insets = new Insets(15,0,15,15);
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 150;
        c.ipady = 12;
        c.weightx = 0.7;
        pane.add(passwordField, c);

        loginButton = new JButton("Login");
        c.insets = new Insets(15,15,15,15);
        c.anchor = GridBagConstraints.PAGE_END;
        c.ipadx = 20;
        c.ipady = 10;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        pane.add(loginButton, c);

        frame.pack();
    }
}
