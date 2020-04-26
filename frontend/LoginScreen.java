package frontend;

import javax.swing.*;

import java.awt.Container;
import java.awt.event.*;

public class LoginScreen {  
    
    /* UI components */
    JFrame frame;
    JLabel welcomeLabel;
    JLabel usernameLabel;
    JTextField usernameField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JButton loginButton;
    JButton signupButton;

    /* initializes the login screen */
    public LoginScreen() {
        createWindow();
        createUI();
        frame.setVisible(true);
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Bank ATM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {

        /* add welcomeLabel */
        welcomeLabel = new JLabel();
        welcomeLabel.setBounds(50, 50, 400, 50);
        welcomeLabel.setText("Welcome to the OO Bank ATM!");
        frame.add(welcomeLabel);
        
        /* add usernameLabel */
        usernameLabel = new JLabel();
        usernameLabel.setBounds(50, 100, 100, 50);
        usernameLabel.setText("Username:");
        frame.add(usernameLabel);

        /* add username field */
        usernameField = new JTextField();
        usernameField.setBounds(150, 100, 300, 50);
        frame.add(usernameField);

        /* add passwordLabel */
        passwordLabel = new JLabel();
        passwordLabel.setBounds(50, 150, 100, 50);
        passwordLabel.setText("Password:");
        frame.add(passwordLabel);

        /* add password field */
        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 300, 50);
        frame.add(passwordField);
        
        /* add login button */
        loginButton = new JButton("Login as existing user");
        loginButton.setBounds(50, 210, 150, 50);
        frame.add(loginButton);

        /* add signup button */
        signupButton = new JButton("Signup as new user");
        signupButton.setBounds(50, 260, 150, 50);
        frame.add(signupButton);
    }

    public static void main(String[] args) {  
        new LoginScreen();
    }
}  
