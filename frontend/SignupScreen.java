package frontend;

import src.Customer;
import src.DatabaseManager;
import src.StaticVariables;

import javax.swing.*;
import java.awt.event.*;

public class SignupScreen implements ActionListener {  
    
    /* UI components */
    JFrame frame;
    JLabel directionsLabel;
    JLabel usernameLabel;
    JTextField usernameField;
    JLabel passwordLabel;

    JLabel firstNameLabel;
    JTextField firstNameField;

    JLabel lastNameLabel;
    JTextField lastNameField;

    JLabel SSNLabel;
    JTextField SSNField;

    JPasswordField passwordField;
    JLabel verifyLabel;
    JPasswordField verifyField;
    JButton signupButton;
    JButton backButton;

    /* initializes the login screen */
    public SignupScreen() {
        createWindow();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Signup as new user");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {
        
        /* add directions label */
        directionsLabel = new JLabel();
        directionsLabel.setBounds(50, 50, 400, 50);
        directionsLabel.setText("Enter a username and password to signup.");
        frame.add(directionsLabel);

        firstNameLabel = new JLabel();
        firstNameLabel.setBounds(400, 100, 150, 50);
        firstNameLabel.setText("Firstname:");
        frame.add(firstNameLabel);

        firstNameField = new JTextField();
        firstNameField.setBounds(500, 100, 200, 50);
        frame.add(firstNameField);

        lastNameLabel = new JLabel();
        lastNameLabel.setBounds(400, 150, 150, 50);
        lastNameLabel.setText("Lastname:");
        frame.add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(500, 150, 200, 50);
        frame.add(lastNameField);

        SSNLabel = new JLabel();
        SSNLabel.setBounds(400, 200, 150, 50);
        SSNLabel.setText("ID (SSN):");
        frame.add(SSNLabel);

        SSNField = new JTextField();
        SSNField.setBounds(500, 200, 200, 50);
        frame.add(SSNField);

        /* add username label */
        usernameLabel = new JLabel();
        usernameLabel.setBounds(50, 100, 150, 50);
        usernameLabel.setText("Username:");
        frame.add(usernameLabel);

        /* add username field */
        usernameField = new JTextField();
        usernameField.setBounds(200, 100, 200, 50);
        frame.add(usernameField);

        /* add password label */
        passwordLabel = new JLabel();
        passwordLabel.setBounds(50, 150, 150, 50);
        passwordLabel.setText("Password:");
        frame.add(passwordLabel);

        /* add password field */
        passwordField = new JPasswordField();
        passwordField.setBounds(200, 150, 200, 50);
        frame.add(passwordField);

        /* add verify label */
        verifyLabel = new JLabel();
        verifyLabel.setBounds(50, 200, 150, 50);
        verifyLabel.setText("Verify password:");
        frame.add(verifyLabel);

        /* add verify field */
        verifyField = new JPasswordField();
        verifyField.setBounds(200, 200, 200, 50);
        frame.add(verifyField);

        /* add signup button */
        signupButton = new JButton("Signup as new user");
        signupButton.setBounds(50, 275, 150, 50);
        signupButton.addActionListener(this);
        frame.add(signupButton);

        /* add back button */
        backButton = new JButton("Back to main menu");
        backButton.setBounds(50, 325, 150, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* signupButton -> add user to database */
        if (e.getSource() == signupButton) {

            //TODO show that passwords do not match
            //TODO make passwords save as hashes and not plaintext

            // Customer customer = new Customer(firstNameField.getText(), lastNameField.getText(), SSNField.getText(), usernameField.getText(), new String(passwordField.getPassword()));
            // StaticVariables.getDatabaseManager().addCustomer(customer);
            Customer customer = Customer.signupCustomer(firstNameField.getText(), lastNameField.getText(), SSNField.getText(), usernameField.getText(), new String(passwordField.getPassword()));

            
            /* navigate back to login screen OR go straight to Customer screen */
            LoginScreen loginScreen = new LoginScreen();
            frame.dispose();
            loginScreen.frame.setVisible(true);
        
        /* backButton -> go back to login screen */
        } else if (e.getSource() == backButton) {
            LoginScreen loginScreen = new LoginScreen();
            frame.dispose();
            loginScreen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {  
        SignupScreen screen = new SignupScreen();
        screen.frame.setVisible(true);
    }
} 