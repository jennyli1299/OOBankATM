package frontend;

import javax.swing.*;
import java.awt.event.*;
import src.*;

public class LoanRequestScreen implements ActionListener {

    /* state */
    Customer customer;
    boolean request;

    /* UI components */
    JFrame frame;
    JLabel requestLabel;
    JLabel amountLabel;
    JTextField amount;
    JLabel currencyLabel;
    JComboBox currency;
    JLabel termLabel;
    JTextField term;
    JLabel collateralLabel;
    JTextArea collateral;
    JLabel warningLabel;
    JButton requestButton;
    JButton backButton;

    public LoanRequestScreen() {
        // this.customer = customer;
        this.customer = (Customer)StaticVariables.getLoggedInUser();
        
        createWindow();
        createUI();
    }

    private void createWindow() {
        /* initialize the frame */
        frame = new JFrame("Customer - Request a loan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLayout(null);
    }

    private void createUI() {
        /* add requestLabel */
        requestLabel = new JLabel();
        requestLabel.setBounds(50, 50, 600, 50);
        requestLabel.setText("Enter the following information to request a loan.");
        frame.add(requestLabel);

        /* add amountLabel */
        amountLabel = new JLabel();
        amountLabel.setBounds(50, 100, 200, 50);
        amountLabel.setText("Amount borrowed (USD):");
        frame.add(amountLabel);

        /* add amount */
        amount = new JTextField();
        amount.setBounds(225, 100, 200, 50);
        frame.add(amount);

        /* add term label */
        termLabel = new JLabel();
        termLabel.setBounds(50, 150, 200, 50);
        termLabel.setText("Term (in # of months):");
        frame.add(termLabel);

        /* add term */
        term = new JTextField();
        term.setBounds(225, 150, 200, 50);
        frame.add(term);

        /* add collateral label */
        collateralLabel = new JLabel();
        collateralLabel.setBounds(50, 200, 200, 50);
        collateralLabel.setText("Collateral (optional):");
        frame.add(collateralLabel);

        /* add collateral */
        collateral = new JTextArea();
        collateral.setBounds(225, 200, 400, 100);
        frame.add(collateral);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 300, 600, 50);
        warningLabel.setText("The current interest rate is " + StaticVariables.getLoanInterestRate() + "% per year.");
        frame.add(warningLabel);

        /* add transact button */
        requestButton = new JButton("Send request");
        requestButton.setBounds(50, 350, 300, 50);
        requestButton.addActionListener(this);
        frame.add(requestButton);

        /* add back button */
        backButton = new JButton("Go back to loans");
        backButton.setBounds(50, 400, 300, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* transact -> complete transaction */
        if (e.getSource() == requestButton) {
            
            /* no inputs */
            if (amount.getText().equals("") || term.getText().equals("")) {
                request = false;
                warningLabel.setText("Enter an amount and the term in months.");

            /* on second attempt make the request */
            } else if (request) {
                try{
                    // Loan loan = new Loan(StaticVariables.getLoggedInUser(), Double.parseDouble(amount.getText()), collateral.getText(), Integer.parseInt(term.getText()), 0);
                    // loan.requestALoan(StaticVariables.getLoggedInUser());
                    // customer.requestLoan(Double.parseDouble(amount.getText()), collateral.getText(), Integer.parseInt(term.getText()));
                    Loan.requestALoan(customer, Double.parseDouble(amount.getText()), collateral.getText(), Integer.parseInt(term.getText()));
                    
                    SuccessfulScreen success = new SuccessfulScreen("Loan has been requested.");

                }catch(Exception excep)
                {
                    warningLabel.setText("Please verify all information is in the correct format in order to apply for a Loan."); //+"\n The amount requested should be a positive and valid number.\n Enter what you wish to have as collateral for this loan in English\n Term should be a valid number of months.");
                }


                /* navigate back to account */
                CustomerLoansScreen screen = new CustomerLoansScreen();
                frame.dispose();
                screen.frame.setVisible(true);

            /* first attempt */
            } else {
                request = true;
                warningLabel.setText("Verify that all the information is correct, then click again to send the request.");
            }

        /* back -> navigate back to account */
        } else if (e.getSource() == backButton) {
            CustomerLoansScreen screen = new CustomerLoansScreen();
            frame.dispose();
            screen.frame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        LoanRequestScreen screen = new LoanRequestScreen();
        screen.frame.setVisible(true);
    }
}