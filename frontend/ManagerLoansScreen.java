package frontend;
import src.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ManagerLoansScreen implements ActionListener {

    /* state */
    Manager manager;
    ArrayList<Loan> loans; // TODO get pending loans from query
    DefaultListModel<String> loansListModel;

    /* UI components */
    JFrame frame;
    JLabel titleLabel;
    JList loansList;
    JLabel warningLabel;
    JButton approveButton;
    JButton denyButton;
    JButton backButton;

    public ManagerLoansScreen() {
        // this.manager = manager;
        this.manager = (Manager)StaticVariables.getLoggedInUser();
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
        loans = new ArrayList<Loan>();
        loans = manager.getPendingLoans();

        loansListModel = new DefaultListModel<String>();
        for (Loan loan : loans) {
            loansListModel.addElement(loan.toString());
        }
        /* mock data - a similar format on the toString()? */
        loansListModel.addElement("5/06/20: $300000 (a car) - 12 months");
        loansListModel.addElement("5/06/20: $500 (vacation house) - 24 months");
        loansListModel.addElement("5/09/20: $2000 - 6 months");
    }


    private void createUI() {
        /* titleLabel */
        titleLabel = new JLabel();
        titleLabel.setBounds(50, 0, 600, 50);
        titleLabel.setText("Here are the most recent pending loans. Select a loan to approve or deny it.");
        frame.add(titleLabel);

        /* loansList */
        loansList = new JList<>(loansListModel);
        loansList.setBounds(50, 50, 400, 300);
        frame.add(loansList);

        /* warningLabel */
        warningLabel = new JLabel();
        warningLabel.setBounds(50, 350, 600, 50);
        frame.add(warningLabel);

        /* approveButton */
        approveButton = new JButton("Approve selected loan");
        approveButton.setBounds(50, 400, 200, 50);
        approveButton.addActionListener(this);
        frame.add(approveButton);

        /* denyButton */
        denyButton = new JButton("Deny selected loan");
        denyButton.setBounds(50, 450, 200, 50);
        denyButton.addActionListener(this);
        frame.add(denyButton);

        /* backButton */
        backButton = new JButton("Go back to main menu");
        backButton.setBounds(50, 500, 200, 50);
        backButton.addActionListener(this);
        frame.add(backButton);
    }

    public void actionPerformed(ActionEvent e) {

        /* approve -> approve loan */
        if (e.getSource() == approveButton) {
            
            /* if no loan is selected, display error */
            int index = loansList.getSelectedIndex();
            if (index == -1) {
                warningLabel.setText("Select a loan first.");

            /* okay, try to approve the loan and remove from list */
            } else {
            Loan loan = loans.get(index);
            manager.approveLoan(loan);
            loans.remove(index);
            loansListModel.remove(index);
            }
        /* deny -> deny loan */
        } else if (e.getSource() == denyButton) {

            /* if no loan is selected, display error */
            int index = loansList.getSelectedIndex();
            if (index == -1) {
                warningLabel.setText("Select a loan first.");

            /* okay, try to deny the loan and remove from list */
            } else {
            Loan loan = loans.get(index);
            manager.denyLoan(loan);
            loans.remove(index);
            loansListModel.remove(index);
            }
        /* back -> go back */
        } else if (e.getSource() == backButton) {
            ManagerScreen screen = new ManagerScreen();
            frame.dispose();
            screen.frame.dispose();
        }
    }

    public static void main(String[] args) {
        ManagerLoansScreen screen = new ManagerLoansScreen();
        screen.frame.setVisible(true);
    }
}