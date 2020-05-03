package frontend;

import javax.swing.*;
import java.awt.event.*;
import src.*;

public class DepositScreen implements ActionListener {

    /* state */
    Customer customer;
    Account account;
    boolean transact;

    /* UI components */
    JFrame frame;
    JLabel depositLabel;
    JLabel amountLabel;
    JTextField amount;
    JLabel warningLabel;
    JButton transactButton;
    JButton backButton;

    public DepositScreen() {
        // this.customer = customer;
        // this.account = account;

        account = new CheckingAccount("GB12345678", (float) 5000, 12345678, 87654321, true, new Currency("USD"), (float)10, (float)20, (float)30, (float)40);

        createWindow();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Customer - Make a deposit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(null);
    }

    private void createUI() {
        /* add depositLabel */
        depositLabel = new JLabel();
        depositLabel.setBounds(50, 50, 600, 50);
        depositLabel.setText("<" + account.getIBAN() + "> - " + account.getBalanceInLocalCurrency());
        frame.add(depositLabel);

        /* add amountLabel */
        amountLabel = new JLabel();
        amountLabel.setBounds(50, 100, 200, 50);
        amountLabel.setText("Deposit (" + account.getCurrency().toString() + "):");
        frame.add(amountLabel);

        /* add amount */
        amount = new JTextField();
        amount.setBounds(225, 100, 200, 50);
        frame.add(amount);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 300, 600, 50);
        warningLabel.setText("There is a " + account.getDepositFee() + " fee for deposits.");
        frame.add(warningLabel);

        /* add transact button */
        transactButton = new JButton("Complete transaction");
        transactButton.setBounds(50, 350, 300, 50);
        transactButton.addActionListener(this);
        frame.add(transactButton);

        /* add back button */
        backButton = new JButton("Go back to account");
        backButton.setBounds(50, 400, 300, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* transact -> complete transaction */
        if (e.getSource() == transactButton) {
            
            /* no input amount */
            if (amount.getText().equals("")) {
                warningLabel.setText("Enter an amount.");

            /* two attempts and enough money to make deposit */
            } else if (transact && ((account.getBalanceInLocalCurrency() + Integer.parseInt(amount.getText())) >= account.getDepositFee())) {

                // TODO make transaction
                try
                {
                    account.makeDeposit(Integer.parseInt(amount.getText()));
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                    //TODO warn the user to type number not string
                }

                /* navigate back to account */
                AccountDetailsScreen screen = new AccountDetailsScreen();
                frame.dispose();
                screen.frame.setVisible(true);

            /* two attempts and not enough money */
            } else if (transact) {
                warningLabel.setText("Not enough money to make this transaction.");

            /* first attempt */
            } else {
                transact = true;
                warningLabel.setText("Are you sure you want to make this transaction? Your new account balance will be " + (account.getBalanceInLocalCurrency() + Integer.parseInt(amount.getText()) - account.getDepositFee()));
            }

        /* back -> navigate back to account */
        } else if (e.getSource() == backButton) {
            AccountDetailsScreen screen = new AccountDetailsScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        DepositScreen screen = new DepositScreen();
        screen.frame.setVisible(true);
    }
}