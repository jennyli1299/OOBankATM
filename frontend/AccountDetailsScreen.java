package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import src.*;

public class AccountDetailsScreen implements ActionListener {

    /* state */
    Customer customer; 
    Account account;
    ArrayList<Transaction> transactions;
    DefaultListModel<String> transactionsListModel;
    boolean close;

    /* UI components */
    JFrame frame;
    JLabel ibanLabel;
    JLabel iban;
    JLabel balanceLabel;
    JLabel balance;
    JLabel routingNumberLabel;
    JLabel routingNumber;
    JLabel accountNumberLabel;
    JLabel accountNumber;

    JLabel transactionsLabel;
    JList transactionsList;
    JButton depositButton;
    JButton withdrawalButton;
    JButton transferButton;
    JComboBox currencyDropdown;
    JButton currencyButton;
    JButton closeButton;
    JButton backButton;
    JLabel warningLabel;

    public AccountDetailsScreen() {
        // this.customer = customer;
        this.customer = (Customer)StaticVariables.getLoggedInUser();
        // this.account = account;
        this.account = StaticVariables.getSelectedAccount();
        
        createWindow();
        initState();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer - Account Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(null);
    }

    private void initState() {
        /* below implementation is only for testing purposes */
        account = new CheckingAccount("GB12345678", (float) 5000, 12345678, 87654321, true, new Currency("USD"), (float)10, (float)20, (float)30, (float)40);

        //account = StaticVariables.getSelectedAccount();
        transactions = new ArrayList<Transaction>();
        //transactions = StaticVariables.getDatabaseManager().getTransactions(account);

        transactionsListModel = new DefaultListModel<>();
        if(transactions != null)
        {
            for (Transaction transaction : transactions) {
                transactionsListModel.addElement(transaction.toString());
            }
        }
    }

    private void createUI() {

        /* add ibanLabel */
        ibanLabel = new JLabel();
        ibanLabel.setBounds(25, 25, 150, 50);
        ibanLabel.setText("Account:");
        frame.add(ibanLabel);

        /* add iban */
        iban = new JLabel();
        iban.setBounds(175, 25, 650, 50);
        iban.setText(account.getAccountType() + " - " + account.getIBAN());
        frame.add(iban);

        /* add balance label */
        balanceLabel = new JLabel();
        balanceLabel.setBounds(25, 75, 150, 50);
        balanceLabel.setText("Balance:");
        frame.add(balanceLabel);

        /* add balance */
        balance = new JLabel();
        balance.setBounds(175, 75, 650, 50);
        balance.setText(account.getCurrency().toString() + " "+ account.getBalanceInLocalCurrency());
        frame.add(balance);

        /* add routingNumberLabel */
        routingNumberLabel = new JLabel();
        routingNumberLabel.setBounds(25, 125, 150, 50);
        routingNumberLabel.setText("Routing Number:");
        frame.add(routingNumberLabel);

        /* add routingNumber */
        routingNumber = new JLabel();
        routingNumber.setBounds(175, 125, 650, 50);
        routingNumber.setText(account.getRoutingNumber()+ "");
        frame.add(routingNumber);

        /* add accountNumberLabel */
        accountNumberLabel = new JLabel();
        accountNumberLabel.setBounds(25, 175, 150, 50);
        accountNumberLabel.setText("Account Number:");
        frame.add(accountNumberLabel);

        /* add accountNumber */
        accountNumber = new JLabel();
        accountNumber.setBounds(175, 175, 650, 50);
        accountNumber.setText(account.getAccountNumber() + "");
        frame.add(accountNumber);

        /* add transactions label */
        transactionsLabel = new JLabel();
        transactionsLabel.setBounds(25, 225, 150, 50);
        transactionsLabel.setText("Transactions");
        frame.add(transactionsLabel);

        /* add transactions list */
        transactionsList = new JList<>(transactionsListModel);
        transactionsList.setBounds(25, 275, 300, 300);
        frame.add(transactionsList);

        /* add deposit button */
        depositButton = new JButton("Make a deposit (FEE: " + StaticVariables.getDepositFee() + ")");
        depositButton.setBounds(350, 275, 350, 50);
        depositButton.addActionListener(this);
        frame.add(depositButton);

        /* add withdrawal button */
        withdrawalButton = new JButton("Make a withdrawal (FEE: " + StaticVariables.getWithdrawalFee() + ")");
        withdrawalButton.setBounds(350, 325, 350, 50);
        withdrawalButton.addActionListener(this);
        frame.add(withdrawalButton);

        /* add transfer button */
        transferButton = new JButton("Make a transfer (FEE: " + StaticVariables.getTransferFee() + ")");
        transferButton.setBounds(350, 375, 350, 50);
        transferButton.addActionListener(this);
        frame.add(transferButton);

        /* add currency dropdown */
        currencyDropdown = new JComboBox<>(Currency.supportedCurrencies);
        currencyDropdown.setBounds(350, 425, 150, 50);
        frame.add(currencyDropdown);

        /* add currency button */
        currencyButton = new JButton("Change currency");
        currencyButton.setBounds(500, 425, 200, 50);
        currencyButton.addActionListener(this);
        frame.add(currencyButton);

        /* add close button */
        closeButton = new JButton("Close account (FEE: " + StaticVariables.getClosingCharge() + ")");
        closeButton.setBounds(350, 475, 350, 50);
        closeButton.addActionListener(this);
        frame.add(closeButton);

        /* add backButton */
        backButton = new JButton("Back to accounts");
        backButton.setBounds(350, 525, 350, 50);
        backButton.addActionListener(this);
        frame.add(backButton);

        /* add warningLabel */
        warningLabel = new JLabel();
        warningLabel.setBounds(25, 625, 600, 50);
        frame.add(warningLabel);

    }

    public void actionPerformed(ActionEvent e) {
        
        /* deposit -> deposit screen */
        if (e.getSource() == depositButton) {
            DepositScreen screen = new DepositScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* withdrawal -> withdrawal screen */
        } else if (e.getSource() == withdrawalButton) {
            WithdrawalScreen screen = new WithdrawalScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        
        /* transfer -> transfer screen */
        } else if (e.getSource() == transferButton) {
            TransferScreen screen = new TransferScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* close -> try to close the account */
        } else if (e.getSource() == closeButton) {

            /* two attempts and enough money to close the account */
            if (close && account.canClose()) { //account.getBalanceInLocalCurrency() >= StaticVariables.getClosingCharge()

                // StaticVariables.getDatabaseManager().closeAccount(account);
                // StaticVariables.updateLifetimeGain(Float.parseFloat(String.valueOf(StaticVariables.getClosingCharge())));
                String[] result = customer.closeAccount(account); // result[0] = "" or "Error", result[1] = msg

                /* navigate back to customer accounts */
                CustomerAccountsScreen screen = new CustomerAccountsScreen();
                frame.dispose();
                screen.frame.setVisible(true);
            
            /* two attempts and not enough money to close the account */
            } else if (close) {
                warningLabel.setText("Not enough money to close the account.");

            /* one attempt */
            } else {
                close = true;
                warningLabel.setText("Are you sure you want to close the account? Click again to confirm.");
            }
        
        /* back -> navigate back to accounts */
        } else if (e.getSource() == backButton) {
            CustomerAccountsScreen screen = new CustomerAccountsScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* change currency */
        } else if (e.getSource() == currencyButton) {
            if (currencyDropdown.getSelectedIndex() == -1) {
                warningLabel.setText("Please select the Currency you would like to switch to from the dropdown.");
            }
            else {
                String currencyToChange = Currency.supportedCurrencies[currencyDropdown.getSelectedIndex()];
                Currency c = new Currency(currencyToChange);
                account.changeCurrency(c);
            }
        }
    }

    public static void main(String[] args) {
        AccountDetailsScreen screen = new AccountDetailsScreen();
        screen.frame.setVisible(true);
    }
}