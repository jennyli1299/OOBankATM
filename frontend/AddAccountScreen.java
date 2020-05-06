package frontend;

import src.*;
import javax.swing.*;

// import javafx.util.Pair;

import java.awt.event.*;
import java.util.ArrayList;

public class AddAccountScreen implements ActionListener {

    /* state */
    Customer customer;
    // Customer customer = (Customer)StaticVariables.getLoggedInUser();

    /* UI components */
    JFrame frame;
    JLabel titleLabel;
    JLabel typeLabel;
    JComboBox type;
    JLabel amountLabel;
    JTextField amount;
    JLabel currencyLabel;
    JComboBox currency;
    JLabel warningLabel;
    JButton addButton;
    JButton backButton;

    public AddAccountScreen() {
        customer = (Customer)StaticVariables.getLoggedInUser();
        createWindow();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer - Add New Account");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {
        /* titlelabel */
        titleLabel = new JLabel();
        titleLabel.setBounds(50, 50, 600, 50);
        titleLabel.setText("Enter the following fields to create a new account.");
        frame.add(titleLabel);

        /* typeLabel */
        typeLabel = new JLabel();
        typeLabel.setBounds(50, 100, 200, 50);
        typeLabel.setText("Account Type:");
        frame.add(typeLabel);

        /* type */
        String[] accountTypes = new String[]{"Checkings", "Savings", "Securities"};
        type = new JComboBox<>(accountTypes);
        type.setBounds(250, 100, 300, 50);
        frame.add(type);

        /* amountLabel */
        amountLabel = new JLabel();
        amountLabel.setBounds(50, 150, 200, 50);
        amountLabel.setText("Starting amount:");
        frame.add(amountLabel);

        /* amount */
        amount = new JTextField();
        amount.setBounds(250, 150, 300, 50);
        frame.add(amount);

        /* currencyLabel */
        currencyLabel = new JLabel();
        currencyLabel.setBounds(50, 200, 200, 50);
        currencyLabel.setText("Currency:");
        frame.add(currencyLabel);
        
        /* currency */
        currency = new JComboBox<>(Currency.supportedCurrencies);
        currency.setBounds(250, 200, 300, 50);
        frame.add(currency);

        /* warningLabel */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 250, 600, 50);
        frame.add(warningLabel);

        /* addButton */
        addButton = new JButton("Add new account");
        addButton.setBounds(50, 300, 200, 50);
        addButton.addActionListener(this);
        frame.add(addButton);

        /* backButton */
        backButton = new JButton("Back to accounts");
        backButton.setBounds(50, 350, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* add button */
        if (e.getSource() == addButton) {

            /* if any of the fields are empty, display warning */
            if (type.getSelectedIndex() == -1 || amount.getText().equals("") || currency.getSelectedIndex() == -1) {
                warningLabel.setText("Enter all of the fields to add a new account.");
            
            /* else, if amount is not int */
            } else {
                try {
                    Float startingAmount = Float.parseFloat(amount.getText());
                    int index = type.getSelectedIndex();
                    if (index == 1) {
                        customer.openCheckingAccount(startingAmount, new Currency((String)currency.getSelectedItem()));
                        warningLabel.setText("Great! An account has been created."); 
                    }
                    else if (index == 2) {
                        customer.openSavingsAccount(startingAmount, new Currency((String)currency.getSelectedItem()));
                        warningLabel.setText("Great! An account has been created."); 
                    }
                    else if (index == 3) { //TODO: NEED TO TRANSFER MONEY FROM SOME ACCOUNT & SANTIZE STARTINGAMT > 1000
                        String[] result = customer.openSecuritiesAccount(startingAmount, new Currency((String)currency.getSelectedItem()));
                        warningLabel.setText(result[1]);
                    }
                    // warningLabel.setText("Great! An account has been created."); 
                } catch (Exception err) {
                    warningLabel.setText("The amount can only be a number.");
                }
            }
        /* back button */
        } else if (e.getSource() == backButton) {

            CustomerAccountsScreen screen = new CustomerAccountsScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        AddAccountScreen screen = new AddAccountScreen();
        screen.frame.setVisible(true);

    }

}