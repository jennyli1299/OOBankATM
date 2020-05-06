package frontend;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ManagerDetailsScreen implements ActionListener {

    /* state */
    Customer customer;
    Manager manager;
    ArrayList<Account> accounts; // database query
    DefaultListModel<String> accountsListModel;
    ArrayList<Transaction> transactions; // database query
    DefaultListModel<String> transactionsListModel;

    /* UI components */
    JFrame frame;
    JLabel detailsLabel;
    JLabel accountsLabel;
    JList accountsList; 
    JLabel transactionsLabel;
    JList transactionsList;
    JLabel warningLabel;
    JButton backButton;

    public ManagerDetailsScreen() {
        // this.manager = manager;
        this.manager = (Manager)StaticVariables.getLoggedInUser();
        createWindow();
        initState();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Manager - Customer Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void initState() {
        accounts = new ArrayList<Account>();
        accounts = StaticVariables.getDatabaseManager().getAllAccounts(StaticVariables.getSelectedCustomer());
        accountsListModel = new DefaultListModel<String>();
        for (Account account : accounts) {
            accountsListModel.addElement(account.toString());
        }

        transactions = new ArrayList<Transaction>();
        transactionsListModel = new DefaultListModel<String>();
        transactions = StaticVariables.getDatabaseManager().getTransactions(StaticVariables.getSelectedCustomer());
        for (Transaction transaction : transactions) {
            transactionsListModel.addElement(transaction.toString());
        }
    }

    private void createUI() {

        /* add details label */
        detailsLabel = new JLabel();
        detailsLabel.setBounds(50, 25, 600, 25);
        detailsLabel.setText("Showing information for customer with username: "+ StaticVariables.getSelectedCustomer().getUsername());
        frame.add(detailsLabel);

        /* add accounts label */
        accountsLabel = new JLabel();
        accountsLabel.setBounds(50, 50, 200, 25);
        accountsLabel.setText("Accounts:");
        frame.add(accountsLabel);

        /* add accounts list */
        accountsList = new JList<>(accountsListModel);
        accountsList.setBounds(50, 75, 300, 200);
        frame.add(accountsList);

        /* add transactions label */
        transactionsLabel = new JLabel();
        transactionsLabel.setBounds(50, 275, 200, 50);
        transactionsLabel.setText("Transactions:");
        frame.add(transactionsLabel);

        /* add transactions list */
        transactionsList = new JList<>(transactionsListModel);
        transactionsList.setBounds(50, 325, 300, 200);
        frame.add(transactionsList);

        /* add back button */
        backButton = new JButton("Go back to customers");
        backButton.setBounds(600, 25, 150, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* back -> back to customers */
        if (e.getSource() == backButton) {
            ManagerCustomerScreen screen = new ManagerCustomerScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }
    public static void main(String[] args) {
        ManagerDetailsScreen screen = new ManagerDetailsScreen();
        screen.frame.setVisible(true);
    }
}