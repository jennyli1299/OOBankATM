package frontend;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import src.*;

public class CustomerLoansScreen implements ActionListener {

    /* state */
    Customer customer;
    ArrayList<Loan> loans; // keep database query here
    DefaultListModel<String> loansListModel;
    ArrayList<Loan> dueLoans; // keep database query here
    DefaultListModel<String> dueLoansListModel;
    ArrayList<Account> accounts;
    DefaultListModel<String> accountsListModel;


    /* UI components */
    JFrame frame;
    JLabel label;
    JList allLoansList;
    JButton requestButton;
    JButton selectAllButton;
    JList dueLoansList;
    JButton payButton;
    JButton selectPayButton;
    JLabel warningLabel;
    JComboBox payDropdown;
    JButton backButton;

    /* pass in customer as parameter */
    public CustomerLoansScreen() {
        // this.customer = customer;
        createWindow();
        //initDummyState();
        loadAccountsFromDatabase();
        loadLoansFromDatabase();
        createUI();
    }

    private void createWindow() {

        /* initialize the frame */
        frame = new JFrame("Customer - Manage Loans");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void loadLoansFromDatabase() {

        /* mock data - Loan(Customer borrower, double initialPrincipal, String collateral, int termInMonths) */
        loans = new ArrayList<Loan>();
        loans = StaticVariables.getDatabaseManager().getLoans(StaticVariables.getLoggedInUser());

        /* add string representation to list */
        loansListModel = new DefaultListModel<>();
        for (Loan loan : loans) {
            loansListModel.addElement(loan.toString());
        }

        dueLoans = loans;

        dueLoansListModel = new DefaultListModel<>();
        for (Loan loan : dueLoans) {
            dueLoansListModel.addElement(loan.toString() + ": " + loan.getMonthlyPayment() + " DUE");
        }
    }

    private void loadAccountsFromDatabase() {

        accounts = new ArrayList<Account>();
        accounts = StaticVariables.getDatabaseManager().getAllAccounts(StaticVariables.getLoggedInUser());

        accountsListModel = new DefaultListModel<>();
        for (Account account : accounts) {
            // TODO: need a way on the backend to convert currencies
            accountsListModel.addElement(account.toString() + " - " + account.getBalanceInLocalCurrency());
        }
    }

    private void initDummyState() {
        
//        /* mock data - Loan(Customer borrower, double initialPrincipal, String collateral, int termInMonths) */
//        loans = new ArrayList<Loan>();
//        loans.add(new Loan(customer, 30000, "second house mortgage", 12));
//        loans.add(new Loan(customer, 10000, "car", 24));
//        loans.add(new Loan(customer, 5000, "", 18));
//        loans.add(new Loan(customer, 75000, "trust fund", 24));
//
//        /* add string representation to list */
//        loansListModel = new DefaultListModel<>();
//        for (Loan loan : loans) {
//            loansListModel.addElement(loan.toString());
//        }
//
//        dueLoans = loans;
//
//        dueLoansListModel = new DefaultListModel<>();
//        for (Loan loan : dueLoans) {
//            dueLoansListModel.addElement(loan.toString() + ": " + loan.getMonthlyPayment() + " DUE");
//        }
//        /* mock data */
//        accounts = new ArrayList<Account>();
//        accounts.add(new CheckingAccount("GB12345678", (float) 5000, 12345678, 87654321, true, new Currency("USD"), (float)10, (float)20, (float)30, (float)40));
//        accounts.add(new CheckingAccount("US87654321", (float) 12345, 23456789, 98765432, true, new Currency("USD"), (float)10, (float)20, (float)30, (float)40));
//        accounts.add(new SavingsAccount("CH88888888", (float) 8888, 99887766, 55443322, true, new Currency("CDY"), (float)10, (float)20, (float)0.03));
//        accountsListModel = new DefaultListModel<>();
//        for (Account account : accounts) {
//            // TODO: need a way on the backend to convert currencies
//            accountsListModel.addElement(account.toString() + " - " + account.getBalanceInLocalCurrency());
//        }
    }

    private void createUI() {

        /* add label */
        label = new JLabel();
        label.setBounds(50, 25, 600, 50);
        label.setText("All loans are shown at the top. Due loans are shown at the bottom.");
        frame.add(label);

        /* add loans list */
        allLoansList = new JList<>(loansListModel);
        allLoansList.setBounds(50, 100, 300, 200);
        frame.add(allLoansList);

        /* add request button */
        requestButton = new JButton("Request new loan");
        requestButton.setBounds(375, 100, 150, 50);
        requestButton.addActionListener(this);
        frame.add(requestButton);

        /* add select button */
        selectAllButton = new JButton("Select loan");
        selectAllButton.setBounds(375, 150, 150, 50);
        selectAllButton.addActionListener(this);
        frame.add(selectAllButton);

        /* add dueLoansList */
        dueLoansList = new JList<>(dueLoansListModel);
        dueLoansList.setBounds(50, 325, 300, 200);
        frame.add(dueLoansList);

        /* add pay button */
        payButton = new JButton("Pay monthly payment");
        payButton.setBounds(375, 325, 150, 50);
        payButton.addActionListener(this);
        frame.add(payButton);

        /* add pay dropdown */
        payDropdown = new JComboBox(accountsListModel.toArray());
        payDropdown.setBounds(375, 375, 150, 50);
        frame.add(payDropdown);

        /* add warning label */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 525, 600, 50);
        frame.add(warningLabel);

        /* add back button */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(575, 50, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);

    }
    public void actionPerformed(ActionEvent e) {

        //StaticVariables.setSelectedLoan(loans.get(allLoansList.getSelectedIndex())); TODO fix DIMITRIS 
        /* back -> go back to customer screen */
        if (e.getSource() == backButton) {
            CustomerScreen screen = new CustomerScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* request -> navigate to request screen */
        } else if (e.getSource() == requestButton) {
            LoanDetailsScreen screen = new LoanDetailsScreen();
            frame.dispose();
            screen.frame.setVisible(true);

        /* select -> navigate to loan details */
        } else if (e.getSource() == selectAllButton) {

            /* if selected account is invalid, display warning */
            int index = allLoansList.getSelectedIndex();
            if (index == -1) {
                warningLabel.setText("Select a loan first.");

            /* okay, navigate to selected account */
            } else {
                Loan selectedLoan = loans.get(index);
                LoanDetailsScreen screen = new LoanDetailsScreen();
                frame.dispose();
                // screen.frame.setVisible(true);
            }
        /* pay -> pay the amount on the selected due loan */
        } else if (e.getSource() == payButton) {
            StaticVariables.getSelectedLoan().payMonthlyInstallment();


        } else if (e.getSource() == selectPayButton) {
            
            /* if selected loan/account is invalid, display warning */
            int loanIndex = dueLoansList.getSelectedIndex();
            int accountIndex = payDropdown.getSelectedIndex();
            if (loanIndex == -1 || accountIndex == -1) {
                warningLabel.setText("Select a loan and account.");

            /* okay, do loan payment on the backend */
            } else {
                // TODO make a monthly payment and update database
            }
        }
    }

    public static void main(String[] args) {
        CustomerLoansScreen screen = new CustomerLoansScreen();
        screen.frame.setVisible(true);
    }
}