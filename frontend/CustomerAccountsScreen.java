package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import src.*;

public class CustomerAccountsScreen implements ActionListener {

    /* state */
    Customer customer;
    ArrayList<Account> accounts; // keep database query here
    DefaultListModel<String> listModel;


    /* UI components */
    JFrame frame;
    JLabel label;
    JList accountsList;
    JLabel warningLabel;
    JButton createButton;
    JButton selectButton;
    JButton backButton;

    /* pass in customer as parameter */
    public CustomerAccountsScreen() {
        // this.customer = customer;
        createWindow();
        initDummyState();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer - Manage Accounts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void initDummyState() {
        
        /* mock data */
        accounts = new ArrayList<Account>();
        //accounts.add(new CheckingAccount("GB12345678", (float) 5000, 12345678, 87654321, true, new Currency("USD"), (float)10, (float)20, (float)30, (float)40));
        //accounts.add(new CheckingAccount("US87654321", (float) 12345, 23456789, 98765432, true, new Currency("USD"), (float)10, (float)20, (float)30, (float)40));
        //accounts.add(new SavingsAccount("CH88888888", (float) 8888, 99887766, 55443322, true, new Currency("CDY"), (float)10, (float)20, (float)0.03));
        
        /* add string representation to list */
        listModel = new DefaultListModel<>();
        for (Account account : accounts) {
            listModel.addElement(account.toString());
        }
        listModel.addElement("<GB12345678> - Checking");
        listModel.addElement("<US87654321> - Checking");
        listModel.addElement("<CH88888888> - Savings");

    }

    private void createUI() {

        /* add label */
        label = new JLabel();
        label.setBounds(50, 50, 800, 50);
        label.setText("Select an account to view balance, make transactions, or close the account.");
        frame.add(label);

        /* add accountsList */
        accountsList = new JList<>(listModel);
        accountsList.setBounds(50, 100, 400, 300);
        frame.add(accountsList);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 400, 600, 50);
        frame.add(warningLabel);

        /* add select button */
        selectButton = new JButton("Select account");
        selectButton.setBounds(450, 100, 200, 50);
        selectButton.addActionListener(this);
        frame.add(selectButton);

        /* add create button */
        createButton = new JButton("Open account");
        createButton.setBounds(450, 150, 200, 50);
        createButton.addActionListener(this);
        frame.add(createButton);

        /* add back button */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(50, 500, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);

    }
    public void actionPerformed(ActionEvent e) {

        /* select button -> view details about account */
        if (e.getSource() == selectButton) {
            
            /* if selected account is invalid, display warning */
            int index = accountsList.getSelectedIndex();
            if (index == -1) {
                warningLabel.setText("Select an account first.");
            }

            /* okay, navigate to selected account */
            // Account selectedAccount = accounts.get(index);
            AccountDetailsScreen screen = new AccountDetailsScreen();

        /* back button -> navigate to customer screen */
        } else if (e.getSource() == backButton) {
            CustomerScreen customerScreen = new CustomerScreen();
            frame.dispose();
            customerScreen.frame.setVisible(true);
        }
       
       
    }

    public static void main(String[] args) {
        CustomerAccountsScreen screen = new CustomerAccountsScreen();
        screen.frame.setVisible(true);
    }
}