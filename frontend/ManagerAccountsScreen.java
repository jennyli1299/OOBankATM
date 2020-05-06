package frontend;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


public class ManagerAccountsScreen implements ActionListener {

    /* state */
    Manager manager;
    ArrayList<Account> accounts; // database query needed
    DefaultListModel<String> accountsListModel;

    /* UI components */
    JFrame frame;
    JLabel accountsLabel;
    JList accountsList;
    JLabel warningLabel;
    JButton paySelectedButton;
    JButton payAllButton;
    JButton backButton;

    public ManagerAccountsScreen() {
        // this.manager = manager;
        this.manager = (Manager)StaticVariables.getLoggedInUser();
        createWindow();
        initState();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Manager - View Accounts");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void initState() {
        // ArrayList<Account> highBalAccounts = Account.filterAccountsByBal(5000);
        // accounts = highBalAccounts;
        // TODO get all high balance accounts
        accounts = new ArrayList<Account>();
        accounts = StaticVariables.getDatabaseManager().getAllAccounts();
        accountsListModel = new DefaultListModel<String>();
        for (Account account : accounts) {
            // TODO backend needs to know if account has paid interest this month/how much interest to pay (add it to toString)
            accountsListModel.addElement(account.toString());
        }
    }

    private void createUI() {
        /* add accounts label */
        accountsLabel = new JLabel();
        accountsLabel.setBounds(50, 25, 600, 50);
        accountsLabel.setText("Below, you can see all accounts.");
        frame.add(accountsLabel);

        /* add accounts list */
        accountsList = new JList<>(accountsListModel);
        accountsList.setBounds(50, 75, 300, 325);
        frame.add(accountsList);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 450, 600, 50);
        frame.add(warningLabel);

        /* add paySelected button */
        paySelectedButton = new JButton("Pay interest on selected account");
        paySelectedButton.setBounds(350, 75, 300, 50);
        paySelectedButton.addActionListener(this);
        frame.add(paySelectedButton);

        /* add payAllButton */
        payAllButton = new JButton("Pay interest on all accounts");
        payAllButton.setBounds(350, 125, 300, 50);
        payAllButton.addActionListener(this);
        frame.add(payAllButton);

        /* add back button */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(350, 175, 300, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* payAll -> pay interest on all accounts */
        if (e.getSource() == payAllButton) {
            // float accumulatedInterestPay = 0;
            // for (Account a : accounts) {
            //     if (a instanceof SavingsAccount) {
            //         accumulatedInterestPay += manager.payInterest((SavingsAccount)a);
            //     }
            // }
            // accounts = new ArrayList<Account>();
            // accountsListModel.clear();
            // warningLabel.setText("All interest has been paid, totalling " + accumulatedInterestPay + "USD");

            for(Account account : accounts)
            {
                account.payInterest();
            }
            accountsListModel.clear();
            warningLabel.setText("Paid interest for all accounts.");

        /* paySelected -> pay interest on selected account */
        } else if (e.getSource() == paySelectedButton) {

            /* if index is invalid, display error */
            int index = accountsList.getSelectedIndex();
            if (index == -1) {
                warningLabel.setText("Select an account first.");

            /* okay, pay interest on acount */
            } else {
                Account account = accounts.get(index);
                //     if (account instanceof SavingsAccount) {
                //         float paid = manager.payInterest((SavingsAccount)account);
                //         accounts.remove(index);
                //         accountsListModel.remove(index);  
                //         warningLabel.setText("Interest on account " + account + " has been paid, totalling " + paid +"USD");        
                //     }
                // warningLabel.setText("");

                account.payInterest();
                accountsListModel.remove(index);  
                warningLabel.setText("Paid interest " + StaticVariables.getAccountInterest() * account.getBalanceInLocalCurrency() + " " + account.getCurrency().getAbbrev());
            }

        /* back -> go back to main menu */
        } else if (e.getSource() == backButton) {
            ManagerScreen screen = new ManagerScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        ManagerAccountsScreen screen = new ManagerAccountsScreen();
        screen.frame.setVisible(true);
    }

}