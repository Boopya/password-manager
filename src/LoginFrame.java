
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class LoginFrame {
    JFrame frame;
    JPanel pane;
    JLabel usernameLabel, passwordLabel;
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
        frame.setTitle("Password Manager");
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pane = new JPanel(new GridBagLayout());
        frame.add(pane);

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(15,15,15,15);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.weighty = 0.5;

        usernameLabel = new JLabel("Username: ", SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 100;
        c.weightx = 0.3;
        pane.add(usernameLabel, c);

        passwordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 1;
        c.ipadx = 100;
        c.weightx = 0.3;
        pane.add(passwordLabel, c);

        usernameField = new JTextField();
        c.gridx = 1;
        c.gridy = 0;
        c.ipadx = 200;
        c.weightx = 0.7;
        pane.add(usernameField, c);

        passwordField = new JPasswordField();
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 200;
        c.weightx = 0.7;
        pane.add(passwordField, c);

        loginButton = new JButton("Login");
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.5;
        c.gridwidth = 2;
        pane.add(loginButton, c);

        frame.pack();
    }
}
