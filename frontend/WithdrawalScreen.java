package frontend;

import javax.swing.*;
import java.awt.event.*;
import src.*;

public class WithdrawalScreen implements ActionListener {

    /* state */
    Customer customer;
    Account account;
    boolean transact;

    /* UI components */
    JFrame frame;
    JLabel withdrawalLabel;
    JLabel amountLabel;
    JTextField amount;
    JLabel warningLabel;
    JButton transactButton;
    JButton backButton;

    public WithdrawalScreen() {
        // this.customer = customer;
        // this.account = account;

        //account = new CheckingAccount("GB12345678", (float) 5000, 12345678, 87654321, true, new Currency("USD"), (float)10, (float)20, (float)30, (float)40);
        account = StaticVariables.getSelectedAccount();
        createWindow();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Customer - Make a withdrawal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(null);
    }

    private void createUI() {
        /* add withdrawalLabel */
        withdrawalLabel = new JLabel();
        withdrawalLabel.setBounds(50, 50, 600, 50);
        withdrawalLabel.setText("<" + account.getIBAN() + "> - " + account.getBalanceInLocalCurrency());
        frame.add(withdrawalLabel);

        /* add amountLabel */
        amountLabel = new JLabel();
        amountLabel.setBounds(50, 100, 200, 50);
        amountLabel.setText("Withdrawal (" + account.getCurrency().toString() + "):");
        frame.add(amountLabel);

        /* add amount */
        amount = new JTextField();
        amount.setBounds(225, 100, 200, 50);
        frame.add(amount);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 300, 600, 50);
        warningLabel.setText("There is a " + account.getDepositFee() + " fee for withdrawals.");
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

            /* two attempts and enough money to make withdrawal */
            } else if (transact && ((account.getBalanceInLocalCurrency() - Integer.parseInt(amount.getText())) >= account.getWithdrawalFee())) {

                // TODO make transaction
                try
                {
                    account.makeWithdrawal(Float.parseFloat(amount.getText()));

                }catch (Exception exception)
                {
                    warningLabel.setText("Invalid input. Please correct entries and retry.");
                }
                /* navigate back to account */
                AccountDetailsScreen screen = new AccountDetailsScreen();
                frame.dispose();
                screen.frame.setVisible(true);

            /* two attempts and not enough money */
            } else if (transact) {
                warningLabel.setText("Not enough money to make this transaction.");
                transact = false;

            /* first attempt */
            } else {
                transact = true;
                warningLabel.setText("Are you sure you want to make this transaction? Your new account balance will be " + (account.getBalanceInLocalCurrency() - Integer.parseInt(amount.getText()) - account.getWithdrawalFee()));
            }

        /* back -> navigate back to account */
        } else if (e.getSource() == backButton) {
            AccountDetailsScreen screen = new AccountDetailsScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        WithdrawalScreen screen = new WithdrawalScreen();
        screen.frame.setVisible(true);
    }
}