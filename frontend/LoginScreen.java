package frontend;

import javax.swing.*;
import java.awt.event.*;

public class LoginScreen implements ActionListener {  
    
    /* UI components */
    JFrame frame;
    JLabel welcomeLabel;
    JLabel usernameLabel;
    JTextField usernameField;
    JLabel passwordLabel;
    JPasswordField passwordField;
    JButton loginButton;
    JButton signupButton;
    JLabel warningLabel;

    /* initializes the login screen */
    public LoginScreen() {
        createWindow();
        createUI();
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

        /* add password label */
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
        loginButton.setBounds(50, 225, 150, 50);
        loginButton.addActionListener(this);
        frame.add(loginButton);

        /* add signup button */
        signupButton = new JButton("Signup as new user");
        signupButton.setBounds(50, 275, 150, 50);
        signupButton.addActionListener(this);
        frame.add(signupButton);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 325, 400, 50);
        frame.add(warningLabel);
    }

    public void actionPerformed(ActionEvent e) {

        /* loginButton -> navigate to Customer or Manager screen */
        if (e.getSource() == loginButton) {

            /* IMPLEMENTATION NEEDED
                verify user credentials before logging in */

            Boolean userExists = true;
            if (!userExists) {
                warningLabel.setText("Your username or password are incorrect. Try again.");
            } else {
                CustomerScreen customerScreen = new CustomerScreen();
                frame.dispose();
                customerScreen.frame.setVisible(true);
            }

        /* signupButton -> navigate to Signup screen */
        } else if (e.getSource() == signupButton) {
            SignupScreen signupScreen = new SignupScreen();
            frame.dispose();
            signupScreen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {  
        LoginScreen screen = new LoginScreen();
        screen.frame.setVisible(true);
    }
}  

