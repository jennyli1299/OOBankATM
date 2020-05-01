package frontend;

import javax.swing.*;
import java.awt.event.*;

import src.Customer;

public class CustomerScreen implements ActionListener {
    
    /* state - hold a copy of the logged in customer
        pass to other screens and use to do database queries/backend calls */
    // Customer customer;

    /* UI components */
    JFrame frame;
    JLabel label;
    JButton accountsButton;
    JButton loansButton;
    JButton stocksButton;
    JButton logoutButton;
    Customer logedInCustomer;

    /* pass in customer as parameter */
    public CustomerScreen(Customer logedInCustomer) {
        // this.customer = customer;
        this.logedInCustomer = logedInCustomer;
        createWindow();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {

        /* add welcome label */
        label = new JLabel();
        label.setBounds(50, 50, 400, 50);
        label.setText("Welcome back, valued customer. Select an action to view more.");
        frame.add(label);

        /* add accounts button */
        accountsButton = new JButton("Manage accounts");
        accountsButton.setBounds(50, 100, 300, 50);
        accountsButton.addActionListener(this);
        frame.add(accountsButton);

        /* add loans button */
        loansButton = new JButton("Manage loans");
        loansButton.setBounds(50, 150, 300, 50);
        loansButton.addActionListener(this);
        frame.add(loansButton);

        /* add stocks button */
        stocksButton = new JButton("Manage stocks");
        stocksButton.setBounds(50, 200, 300, 50);
        stocksButton.addActionListener(this);
        frame.add(stocksButton);

        /* add logout button */
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(50, 250, 300, 50);
        logoutButton.addActionListener(this);
        frame.add(logoutButton);

    }

    public void actionPerformed(ActionEvent e) {

        /* accounts button -> navigate to accounts screen */
        if (e.getSource() == accountsButton) {
            CustomerAccountsScreen screen = new CustomerAccountsScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* loans button -> navigate to loans screen */
        } else if (e.getSource() == loansButton) {
            CustomerLoansScreen screen = new CustomerLoansScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* stocks button -> navigate to stocks screen */
        } else if (e.getSource() == stocksButton) {
            CustomerStocksScreen screen = new CustomerStocksScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* logout button -> close app */
        } else if (e.getSource() == logoutButton) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        CustomerScreen screen = new CustomerScreen(null);
        screen.frame.setVisible(true);
    }
}