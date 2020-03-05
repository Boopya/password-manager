
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.table.DefaultTableModel;

public class PasswordManagerFrame extends JFrame implements DatabaseAccessCredentials, DatabaseConstants, SQLStatements {
    JFrame frame;
    JPanel pane;
    JTable table;
    JScrollPane scrollPane;
    JLabel siteNameLabel, siteUsernameLabel, sitePasswordLabel, titleLabel;
    JTextField siteUsernameField, siteNameField;
    JPasswordField sitePasswordField;
    JButton addAccountButton, editAccountButton, deleteAccountButton;
    Connection con;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DefaultTableModel accountsTableModel;

    public static void main(String[] args) {
        new PasswordManagerFrame();
    }

    public PasswordManagerFrame() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            con = DriverManager.getConnection(DATABASE_HOST, DATABASE_USERNAME, DATABASE_PASSWORD);
        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        initComponents();
    }

    public void initComponents() {
        frame = new JFrame();
        frame.setTitle("Password Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        pane = new JPanel(new GridBagLayout());
        frame.add(pane);

        GridBagConstraints c = new GridBagConstraints();
        
        accountsTableModel = new DefaultTableModel(COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(accountsTableModel);
        scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        fillTable();
        c.insets = new Insets(15,15,15,15);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        pane.add(scrollPane, c);
        
        titleLabel = new JLabel("Password Manager", SwingConstants.CENTER);
        c.insets = new Insets(15,15,15,15);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        pane.add(titleLabel, c);
        c.gridwidth = 1;
        c.insets = new Insets(15,0,0,0);
        
        siteNameLabel = new JLabel("Site Name: ", SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 2;
        c.ipadx = 50;
        c.ipady = 20;
        c.weightx = 0.3;
        pane.add(siteNameLabel, c);

        siteUsernameLabel = new JLabel("Username: ", SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 3;
        c.ipadx = 50;
        c.ipady = 20;
        c.weightx = 0.3;
        pane.add(siteUsernameLabel, c);
        
        sitePasswordLabel = new JLabel("Password: ", SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 4;
        c.ipadx = 50;
        c.ipady = 20;
        c.weightx = 0.3;
        pane.add(sitePasswordLabel, c);
        
        siteNameField = new JTextField();
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 300;
        c.ipady = 12;
        c.weightx = 0.7;
        pane.add(siteNameField, c);

        siteUsernameField = new JTextField();
        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = 300;
        c.ipady = 12;
        c.weightx = 0.7;
        pane.add(siteUsernameField, c);
        
        sitePasswordField = new JPasswordField();
        c.gridx = 1;
        c.gridy = 4;
        c.ipadx = 300;
        c.ipady = 12;
        c.weightx = 0.7;
        pane.add(sitePasswordField, c);

        addAccountButton = new JButton("Add Account");
        addAccountButton.addActionListener(new ButtonListener());
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 5;
        c.ipadx = 50;
        c.ipady = 10;
        c.weightx = 0.5;
        c.gridwidth = 2;
        pane.add(addAccountButton, c);
        
        editAccountButton = new JButton("Edit Account");
        editAccountButton.addActionListener(new ButtonListener());
        c.gridx = 0;
        c.gridy = 6;
        c.ipadx = 50;
        c.ipady = 10;
        c.weightx = 0.5;
        c.gridwidth = 2;
        pane.add(editAccountButton, c);
        
        deleteAccountButton = new JButton("Delete Account");
        deleteAccountButton.addActionListener(new ButtonListener());
        c.insets = new Insets(15,0,15,0);
        c.gridx = 0;
        c.gridy = 7;
        c.ipadx = 35;
        c.ipady = 10;
        c.weightx = 0.5;
        c.gridwidth = 2;
        pane.add(deleteAccountButton, c);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            switch(ae.getActionCommand()) {
                case "Add Account":
                    addAccount();
                    break;
                case "Edit Account":
                    int selectedRowCount = table.getSelectedRowCount();
                    boolean isMoreThanOneRow = selectedRowCount > 1;
                    
                    if(isMoreThanOneRow) {
                        JOptionPane.showMessageDialog(rootPane, "You can only edit one account at a time.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    editAccount();
                    break;
                case "Delete Account":
                    deleteAccount();
                    break;
            }
        }   
    }
    
    private class EditFrame {
        JFrame editFrame;
        JPanel pane;
        JButton saveButton;
        String siteName, siteUsername, sitePassword;
        int selectedRow;
        
        public EditFrame(int selectedRow) {
            this.selectedRow = selectedRow;
            siteName = accountsTableModel.getValueAt(selectedRow, SITE_NAME_COL_INDEX).toString();
            siteUsername = accountsTableModel.getValueAt(selectedRow, SITE_USERNAME_COL_INDEX).toString();
            sitePassword = accountsTableModel.getValueAt(selectedRow, SITE_PASSWORD_COL_INDEX).toString();
            initComponents();
        }
        
        public void initComponents() {
            editFrame = new JFrame();
            editFrame.setTitle("Edit Account");
            editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editFrame.setResizable(false);
            
            pane = new JPanel(new GridBagLayout());
            editFrame.add(pane);
            
            GridBagConstraints c = new GridBagConstraints();
            
            c.insets = new Insets(15,15,15,15);
            
            c.gridx = 0;
            c.gridy = 0;
            pane.add(siteNameLabel, c);
            
            c.gridx = 0;
            c.gridy = 1;
            pane.add(siteUsernameLabel, c);
            
            c.gridx = 0;
            c.gridy = 2;
            pane.add(sitePasswordLabel, c);
            
            siteNameField.setPreferredSize(new Dimension(300, 30));
            c.gridx = 1;
            c.gridy = 0;
            pane.add(siteNameField, c);
            
            siteUsernameField.setPreferredSize(new Dimension(300, 30));
            c.gridx = 1;
            c.gridy = 1;
            pane.add(siteUsernameField, c);
            
            sitePasswordField.setPreferredSize(new Dimension(300, 30));
            c.gridx = 1;
            c.gridy = 2;
            pane.add(sitePasswordField, c);
            
            saveButton = new JButton("Save");
            saveButton.addActionListener(new EditButtonListener());
            c.ipadx = 50;
            c.ipady = 10;
            c.gridx = 0;
            c.gridy = 3;
            c.gridwidth = 2;
            pane.add(saveButton, c);
            
            siteNameField.setText(siteName);
            siteUsernameField.setText(siteUsername);
            sitePasswordField.setText(sitePassword);
            
            editFrame.pack();
            editFrame.setLocationRelativeTo(null);
            editFrame.setVisible(true);
        }
        
        private class EditButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(ae.getActionCommand().equals("Save")) {
                    saveAccountChanges();
                    frame.dispose();
                    new PasswordManagerFrame();
                }
            }
        }
        
        public void saveAccountChanges() {
            int response = JOptionPane.showConfirmDialog(rootPane, "Save Changes?", "Save", JOptionPane.YES_NO_OPTION);
            
            if(response == JOptionPane.YES_OPTION) {
                String updatedSiteName = siteNameField.getText();
                String updatedSiteUsername = siteUsernameField.getText();
                String updatedSitePassword = sitePasswordField.getText();
                try {
                    preparedStatement = con.prepareStatement(UPDATE_ENTRY + SITE_NAME_COLUMN + "='" + updatedSiteName + "', " +
                                                             SITE_USERNAME_COLUMN + "='" + updatedSiteUsername + "', " +
                                                             SITE_PASSWORD_COLUMN + "='" + updatedSitePassword + "'");
                    preparedStatement.executeUpdate();
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
                accountsTableModel.setValueAt(updatedSiteName, selectedRow, SITE_NAME_COL_INDEX);
                accountsTableModel.setValueAt(updatedSiteUsername, selectedRow, SITE_USERNAME_COL_INDEX);
                accountsTableModel.setValueAt(updatedSitePassword, selectedRow, SITE_PASSWORD_COL_INDEX);
                JOptionPane.showMessageDialog(rootPane, "Successfully updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                editFrame.dispose();
            }
        }
    }
    
    public void addAccount() {
        String siteName = siteNameField.getText();
        String siteUsername = siteUsernameField.getText();
        String sitePassword = String.valueOf(sitePasswordField.getPassword());
        
        if(siteName.equals("") || siteUsername.equals("") || sitePassword.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please fill the fields completely.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            preparedStatement = con.prepareStatement(ADD_ENTRY + "('" + siteName + "', '" + siteUsername + "', '" + sitePassword + "')");
            preparedStatement.executeUpdate();
            siteNameField.setText("");
            siteUsernameField.setText("");
            sitePasswordField.setText("");
            
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        
        accountsTableModel.addRow(new Object[]{siteName, siteUsername, sitePassword});
    }
    
    public void editAccount() {
        int selectedRow = table.getSelectedRow();
        
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(rootPane, "Please select at least one account.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        new EditFrame(selectedRow);
    }
    
    public void deleteAccount() {
        int selectedRowsCount = table.getSelectedRowCount();
        
        if(selectedRowsCount < 1) {
            JOptionPane.showMessageDialog(rootPane, "Please select at least one account.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
            
        }
        
        int response = JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete the selected account(s)?", "Delete", JOptionPane.YES_NO_OPTION);
        
        if(response == JOptionPane.YES_OPTION) {
            for(int i = 0; i < selectedRowsCount; i++) {
                Object siteName = accountsTableModel.getValueAt(table.getSelectedRow(), SITE_NAME_COL_INDEX);
                Object siteUsername = accountsTableModel.getValueAt(table.getSelectedRow(), SITE_USERNAME_COL_INDEX);
                Object sitePassword = accountsTableModel.getValueAt(table.getSelectedRow(), SITE_PASSWORD_COL_INDEX);

                try {
                preparedStatement = con.prepareStatement(DELETE_ENTRY + SITE_NAME_COLUMN + "='" + siteName + "' AND " +
                                                         SITE_USERNAME_COLUMN + "='" + siteUsername + "' AND " +
                                                         SITE_PASSWORD_COLUMN + "='" + sitePassword + "'");
                preparedStatement.executeUpdate();
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
                accountsTableModel.removeRow(table.getSelectedRow());
            }
            JOptionPane.showMessageDialog(rootPane, "Successfully deleted.", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void fillTable() {
        try{
            preparedStatement = con.prepareStatement(QUERY_ENTRIES);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                String siteName = resultSet.getString(SITE_NAME_COLUMN);
                String siteUsername = resultSet.getString(SITE_USERNAME_COLUMN);
                String sitePassword = resultSet.getString(SITE_PASSWORD_COLUMN);
                accountsTableModel.addRow(new Object[]{siteName, siteUsername, sitePassword});
            }
            table.setModel(accountsTableModel);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
