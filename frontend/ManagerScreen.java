package frontend;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManagerScreen implements ActionListener {

    /* state */
    Manager manager;
    ArrayList<Transaction> transactions; // database query needed
    DefaultListModel<String> transactionsListModel;

    /* UI components */
    JFrame frame;
    JLabel managerLabel;
    JLabel transactionsLabel;
    JList transactionsList;
    JButton customersButton;
    JButton accountsButton;
    JButton stocksButton;
    JButton feesButton;
    JButton timeButton;
    JButton logoutButton;
    JLabel lossLabel;
    JLabel gainLabel;
    JLabel statistics;

    public ManagerScreen() {
        // this.manager = manager;
        createWindow();
        initState();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void initState() {
        /* mock data */
        manager = new Manager("Elton", "Cheung", "12345678", "eltonc", "pass");
        
        transactions = manager.getDailyTransactions();
        transactionsListModel = new DefaultListModel<String>();
        for (Transaction transaction : transactions) {
            transactionsListModel.addElement(transaction.getAccount().toString() + " - " + transaction.toString());
        }
        transactionsListModel.addElement("<CH12345678> - Checking - 5/3/2020: $500");
        transactionsListModel.addElement("<GB87654321> - Savings - 5/3/2020: $12345678");
        transactionsListModel.addElement("<US44442222> - Securities - 5/3/2020: $2000");
    }

    private void createUI() {
        /* add manager label */
        managerLabel = new JLabel();
        managerLabel.setBounds(50, 25, 600, 50);
        managerLabel.setText("Welcome back, " + manager.getUsername() + ".");
        frame.add(managerLabel);

        /* add transactions label */
        transactionsLabel = new JLabel();
        transactionsLabel.setBounds(50, 75, 200, 50);
        transactionsLabel.setText("Daily transactions: " + Time.getCurrentTime().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        frame.add(transactionsLabel);

        /* add transactions list */
        transactionsList = new JList<>(transactionsListModel);
        transactionsList.setBounds(50, 125, 400, 325);
        frame.add(transactionsList);

        /* add customers button */
        customersButton = new JButton("View all customers");
        customersButton.setBounds(450, 125, 200, 50);
        customersButton.addActionListener(this);
        frame.add(customersButton);

        /* add accounts button */
        accountsButton = new JButton("Manage accounts");
        accountsButton.setBounds(450, 175, 200, 50);
        accountsButton.addActionListener(this);
        frame.add(accountsButton);

        /* add stocks button */
        stocksButton = new JButton("Manage stock market");
        stocksButton.setBounds(450, 225, 200, 50);
        stocksButton.addActionListener(this);
        frame.add(stocksButton);

        /* add fees button */
        feesButton = new JButton("Manage fees and charges");
        feesButton.setBounds(450, 275, 200, 50);
        feesButton.addActionListener(this);
        frame.add(feesButton);

        /* add time button */
        timeButton = new JButton("Change time");
        timeButton.setBounds(450, 325, 200, 50);
        timeButton.addActionListener(this);
        frame.add(timeButton);

        /* add logout button */
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(650, 25, 100, 50);
        logoutButton.addActionListener(this);
        frame.add(logoutButton);

        /* add loss label */
        lossLabel = new JLabel();
        lossLabel.setBounds(50, 450, 300, 50);
        lossLabel.setText("Money Lost: $" + StaticVariables.getLifetimeLoss());
        frame.add(lossLabel);

        /* add gain label */
        gainLabel = new JLabel();
        gainLabel.setBounds(350, 450, 300, 50);
        gainLabel.setText("Money Gained: $" + StaticVariables.getLifetimeGain());
        frame.add(gainLabel);

        /* add statistics label */
        statistics = new JLabel();
        double profit = (double)(StaticVariables.getLifetimeGain() - StaticVariables.getLifetimeLoss());
        String profitMessage = profit >= 0  ? "$" + profit : "-$" + profit;
        statistics.setBounds(50, 500, 600, 50);
        statistics.setText("Your lifetime profits are " + profitMessage);
        frame.add(statistics);
    }

    public void actionPerformed(ActionEvent e) {

        /* customer -> navigate to customers screen */
        if (e.getSource() == customersButton) {
            ManagerCustomerScreen screen = new ManagerCustomerScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        
        /* accounts -> navigate to accounts screen */
        } else if (e.getSource() == accountsButton) {
            ManagerAccountsScreen screen = new ManagerAccountsScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        
        /* stocks - > navigate to stocks screen */
        } else if (e.getSource() == stocksButton) {
            ManagerStocksScreen screen = new ManagerStocksScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        
        /* fees -> navigate to fees screen */
        } else if (e.getSource() == feesButton) {
            ManagerFeesScreen screen = new ManagerFeesScreen();
            frame.dispose();
            screen.frame.dispose();
        
        /* time -> navigate to time screen */
        } else if (e.getSource() == timeButton) {
            ManagerTimeScreen screen = new ManagerTimeScreen();
            frame.dispose();
            screen.frame.dispose();

        /* logout -> logout */
        } else if (e.getSource() == logoutButton) {
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        ManagerScreen screen = new ManagerScreen();
        screen.frame.setVisible(true);
    }
    
}