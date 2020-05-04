package frontend;

import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ManagerFeesScreen implements ActionListener {

    /* state */
    Manager manager;

    /* UI components */
    JFrame frame;
    JLabel titleLabel;
    JLabel depositLabel;
    JTextField deposit;
    JLabel withdrawalLabel;
    JTextField withdrawal;
    JLabel transferLabel;
    JTextField transfer;
    JLabel openingLabel;
    JTextField opening;
    JLabel closingLabel;
    JTextField closing;
    JLabel warningLabel;
    JButton updateButton;
    JButton backButton;

    public ManagerFeesScreen() {
        // this.manager = manager;
        createWindow();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Manager - Change charges and fees");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void createUI() {
        /* titleLabel */
        titleLabel = new JLabel();
        titleLabel.setBounds(50, 50, 600, 50);
        titleLabel.setText("Change the fees by entering new values.");
        frame.add(titleLabel);

        /* depositLabel */
        depositLabel = new JLabel();
        depositLabel.setBounds(50, 100, 200, 50);
        depositLabel.setText("Deposit Fee: $" + Account.getDepositFee());
        frame.add(depositLabel);

        /* deposit */
        deposit = new JTextField();
        deposit.setBounds(250, 100, 200, 50);
        deposit.setText(Account.getDepositFee() + "");
        frame.add(deposit);

        /* withdrawalLabel */
        withdrawalLabel = new JLabel();
        withdrawalLabel.setBounds(50, 150, 200, 50);
        withdrawalLabel.setText("Withdrawal Fee: $" + Account.getWithdrawalFee());
        frame.add(withdrawalLabel);

        /* withdrawal */
        withdrawal = new JTextField();
        withdrawal.setBounds(250, 150, 200, 50);
        withdrawal.setText(Account.getWithdrawalFee() + "");
        frame.add(withdrawal);
        
        /* transferLabel */
        transferLabel = new JLabel();
        transferLabel.setBounds(50, 200, 200, 50);
        transferLabel.setText("Transfer Fee: $" + Account.getTransferFee());
        frame.add(transferLabel);
        
        /* transfer */
        transfer = new JTextField();
        transfer.setBounds(250, 200, 200, 50);
        transfer.setText(Account.getTransferFee() + "");
        frame.add(transfer);

        /* openingLabel */
        openingLabel = new JLabel();
        openingLabel.setBounds(50, 250, 200, 50);
        openingLabel.setText("Opening Charge: $" + Account.getOpeningCharge());
        frame.add(openingLabel);

        /* opening */
        opening = new JTextField();
        opening.setBounds(250, 250, 200, 50);
        opening.setText(Account.getOpeningCharge() + "");
        frame.add(opening);

        /* closingLabel */
        closingLabel = new JLabel();
        closingLabel.setBounds(50, 300, 200, 50);
        closingLabel.setText("Closing Charge: $" + Account.getClosingCharge());
        frame.add(closingLabel);

        /* closing */
        closing = new JTextField();
        closing.setBounds(250, 300, 200, 50);
        closing.setText(Account.getClosingCharge() + "");
        frame.add(closing);

        /* warningLabel */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 350, 600, 50);
        frame.add(warningLabel);

        /* updateButton */
        updateButton = new JButton("Update charges and fees");
        updateButton.setBounds(50, 400, 200, 50);
        updateButton.addActionListener(this);
        frame.add(updateButton);

        /* backButton */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(50, 450, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* update -> try to update charges */
        if (e.getSource() == updateButton) {

            /* if any of the fields are empty, show warning */
            if (deposit.getText().equals("") || withdrawal.getText().equals("") || transfer.getText().equals("") || opening.getText().equals("") || closing.getText().equals("")) {
                warningLabel.setText("One or more fields are missing. Enter all to continue.");
            
            /* okay, try to update charges and fees */
            } else {
                // TODO: update all the charges (they are set to current by default so redundant updates are fine)
                Float depositFee = Float.parseFloat(deposit.getText());
                // manager.set
                Float withdrawalFee = Float.parseFloat(withdrawal.getText());
                manager.setCheckingAccWithdrawalFee(withdrawalFee);
                Float transferFee = Float.parseFloat(transfer.getText());
                manager.setCheckingAccTransferFee(transferFee);
                Float openingCharge = Float.parseFloat(opening.getText());
                manager.setAccountOpeningCharge(openingCharge);
                Float closingCharge = Float.parseFloat(closing.getText());
                manager.setAccountClosingCharge(closingCharge);
            }
        /* back -> navigate back to main menu */
        } else if (e.getSource() == backButton) {
            ManagerScreen screen = new ManagerScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        ManagerFeesScreen screen = new ManagerFeesScreen();
        screen.frame.setVisible(true);
    }

}