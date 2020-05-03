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
    JButton closeButton;
    JButton backButton;
    JLabel warningLabel;

    public AccountDetailsScreen() {
        // this.customer = customer;
        // this.account = account;
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
        // TODO database: select transactions involving given account
        
        /* below implementation is only for testing purposes */
        account = new CheckingAccount("GB12345678", (float) 5000, 12345678, 87654321, true, new Currency("USD"), (float)10, (float)20, (float)30, (float)40);

        transactions = new ArrayList<Transaction>();
        transactions.add(new Deposit(account, 5000));
        transactions.add(new Deposit(account, 3000));
        transactions.add(new Withdrawal(account, 400));
        transactions.add(new Withdrawal(account, (float)6575.35));
        transactions.add(new Transfer(account, account, 6000));
        transactions.add(new Deposit(account, 1000));

        transactionsListModel = new DefaultListModel<>();
        for (Transaction transaction : transactions) {
            transactionsListModel.addElement(transaction.toString());
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
        iban.setText(account.getAccountType() + " - " +account.getIBAN());
        frame.add(iban);

        /* add balance label */
        balanceLabel = new JLabel();
        balanceLabel.setBounds(25, 75, 150, 50);
        balanceLabel.setText("Balance:");
        frame.add(balanceLabel);

        /* add balance */
        balance = new JLabel();
        balance.setBounds(175, 75, 650, 50);
        balance.setText(account.getCurrency().toString() + account.getBalanceInLocalCurrency());
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
        depositButton = new JButton("Make a deposit (FEE: " + account.getDepositFee() + ")");
        depositButton.setBounds(350, 275, 350, 50);
        depositButton.addActionListener(this);
        frame.add(depositButton);

        /* add withdrawal button */
        withdrawalButton = new JButton("Make a withdrawal (FEE: " + account.getWithdrawalFee() + ")");
        withdrawalButton.setBounds(350, 325, 350, 50);
        withdrawalButton.addActionListener(this);
        frame.add(withdrawalButton);

        /* add transfer button */
        transferButton = new JButton("Make a transfer (FEE: " + account.getTransferFee() + ")");
        transferButton.setBounds(350, 375, 350, 50);
        transferButton.addActionListener(this);
        frame.add(transferButton);

        /* add close button */
        closeButton = new JButton("Close account (FEE: " + account.getClosingCharge() + ")");
        closeButton.setBounds(350, 425, 350, 50);
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
            if (close && account.getBalanceInLocalCurrency() >= account.getClosingCharge()) {

                // TODO database: delete account, transfer fee to bank manager account

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
        }
    }

    public static void main(String[] args) {
        AccountDetailsScreen screen = new AccountDetailsScreen();
        screen.frame.setVisible(true);
    }
}