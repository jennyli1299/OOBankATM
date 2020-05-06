package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import src.*;

public class LoanDetailsScreen implements ActionListener {

    /* state */
    Customer customer;
    Loan loan;

    /* UI components */
    JFrame frame;
    JLabel dateIssuedLabel;
    JLabel dateIssued;
    JLabel dateDueLabel;
    JLabel dateDue;
    JLabel statusLabel;
    JLabel status;
    JLabel initialPrincipalLabel;
    JLabel initialPrincipal;
    JLabel amountLeftLabel;
    JLabel amountLeft;
    JLabel collateralLabel;
    JLabel collateral;
    JButton backButton;

    public LoanDetailsScreen() {
        // this.customer = customer;
        this.customer = (Customer)StaticVariables.getLoggedInUser();
        // this.loan = loan;

        createWindow();
        initState();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Customer - Loan Details");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void initState() {
        /* mock data */
        //loan = new Loan(customer, 30000, "second house mortgage", 12, 0);
        loan = StaticVariables.getSelectedLoan();
    }

    private void createUI() {

        /* add dateIssued label */
        dateIssuedLabel = new JLabel();
        dateIssuedLabel.setBounds(50, 50, 200, 50);
        dateIssuedLabel.setText("Date Issued:");
        frame.add(dateIssuedLabel);

        /* add dateIssued */
        dateIssued = new JLabel();
        dateIssued.setBounds(250, 50, 400, 50);
        dateIssued.setText("<" + loan.getDateIssued().format(DateTimeFormatter.ofPattern("MM/dd/YYYY")) + ">");
        frame.add(dateIssued);

        /* add dateDue label */
        dateDueLabel = new JLabel();
        dateDueLabel.setBounds(50, 100, 200, 50);
        dateDueLabel.setText("Date Due:");
        frame.add(dateDueLabel);

        /* add dateDue */
        dateDue = new JLabel();
        String dateDueString = loan.getDateIssued().plusMonths(loan.getTermInMonths()).format(DateTimeFormatter.ofPattern("MM/dd/YYYY"));
        dateDue.setBounds(250, 100, 400, 50);
        dateDue.setText("<" + dateDueString + ">");
        frame.add(dateDue);

        /* add status label */
        statusLabel = new JLabel();
        statusLabel.setBounds(50, 150, 200, 50);
        statusLabel.setText("Status:");
        frame.add(statusLabel);

        /* add status */
        status = new JLabel();
        status.setBounds(250, 150, 400, 50);
        status.setText(loan.getStatus().toString());
        frame.add(status);

        /* add initialPrincipal label */
        initialPrincipalLabel = new JLabel();
        initialPrincipalLabel.setBounds(50, 200, 200, 50);
        initialPrincipalLabel.setText("Initial Principal (USD):");
        frame.add(initialPrincipalLabel);

        /* add initialPrincipal */
        initialPrincipal = new JLabel();
        initialPrincipal.setBounds(250, 200, 400, 50);
        initialPrincipal.setText("$" + loan.getInitialPrincipal());
        frame.add(initialPrincipal);

        /* add amountLeft label */
        amountLeftLabel = new JLabel();
        amountLeftLabel.setBounds(50, 250, 200, 50);
        amountLeftLabel.setText("Amount Left To Pay:");
        frame.add(amountLeftLabel);

        /* add amountLeft */
        amountLeft = new JLabel();
        amountLeft.setBounds(250, 250, 400, 50);
        amountLeft.setText("$" + loan.getAmountDue());
        frame.add(amountLeft);

        /* add collateral label */
        collateralLabel = new JLabel();
        collateralLabel.setBounds(50, 300, 200, 50);
        collateralLabel.setText("Collateral");
        frame.add(collateralLabel);

        /* add collateral */
        collateral = new JLabel();
        collateral.setBounds(250, 300, 400, 50);
        collateral.setText(loan.getCollateral());
        frame.add(collateral);

        /* add backButton */
        backButton = new JButton("Go back to loans");
        backButton.setBounds(50, 350, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* back -> navigate back to loans */
        if (e.getSource() == backButton) {
            CustomerLoansScreen screen = new CustomerLoansScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        LoanDetailsScreen screen = new LoanDetailsScreen();
        screen.frame.setVisible(true);
    }


}