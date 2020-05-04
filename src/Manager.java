package src;

import java.util.ArrayList;
import java.util.Collections;

public class Manager extends User {

    public static double currentYearlyInterestRate = 0.10;
    public static double lifetimeLoss = 50000;
    public static double lifetimeGain = 60000;

    public Manager(String firstName, String lastName, String id, String username, String password)
    {
        super(firstName, lastName, id, username, password);
    }

    /** TODO
     * Loans (Approve/Deny)
     *      Loan -> setStatus(Status status)
     *      Manager clicks a button and sees a list of all pending loans
     *      Each loan card displays relevant info and approve/deny buttons
     *      Approve will make customer need to pay loan accordingly
     *      Charge Interest
Loan -> setInterestRate(float interestRate)
Pay Interest (Savings accounts w high balance ← Variable) (>$5000)

Able to query all tables (customers/transactions)
Get a daily report on transactions for that day
Set fees for loans/account openings done
Add/delete stock options
maintaining the list of stocks
updating the current price of each stock.

     */

    /* Account Charges */
    public void setAccountOpeningCharge(float oc) {
        Account.setOpeningCharge(oc);
    }
    public void setAccountClosingCharge(float cc) {
        Account.setClosingCharge(cc);
    }
    public void setCheckingAccWithdrawalFee(float wf) {
        CheckingAccount.setWithdrawalFee(wf);
    }
    public void setCheckingAccTransferFee(float tf) {
        CheckingAccount.setTransferFee(tf);
    }
    public void setSavingsAccInterest(float interest) {
        SavingsAccount.setSavingsAccountInterest(interest);
    }

    
    /* LOANS */
    public String setnewLoanInterestRate(double rate) {
        boolean done = Loan.setnewInterestRate(rate);
        if (done) return "The interest rate for loans has now been set to " + String.valueOf(rate);
        else return "Action not successful.";
    }

    public ArrayList<Loan> getPendingLoans() {
        ArrayList<Loan> allLoans = getAllLoans();
        ArrayList<Loan> allPending = new ArrayList<Loan>();
        for (Loan l : allLoans) {
            if (l.getStatus() == Loan.Status.Pending) {
                allPending.add(l);
            }
        }
        /* Sorted by date Applied */
        Collections.sort(allPending, Loan.LoanAppliedDateComparator);
        return allPending;
    }
    public ArrayList<Loan> getApprovedLoans() {
        ArrayList<Loan> allLoans = getAllLoans();
        ArrayList<Loan> allApproved = new ArrayList<Loan>();
        for (Loan l : allLoans) {
            if (l.getStatus() == Loan.Status.Approved) {
                allApproved.add(l);
            }
        }
        /* Sorted by date Issued/Approved */
        Collections.sort(allApproved, Loan.LoanIssuedDateComparator);
        return allApproved;
    }
    public ArrayList<Loan> getAllLoans() {
        ArrayList<Customer> allCustomers = StaticVariables.getDatabaseManager().getCustomers();
        ArrayList<Loan> allLoans = new ArrayList<Loan>();
        for (Customer customer : allCustomers) {
            ArrayList<Loan> loans = StaticVariables.getDatabaseManager().getLoans(customer);
            allLoans.addAll(loans);
        }
        /* Sorted by date Applied */
        Collections.sort(allLoans, Loan.LoanAppliedDateComparator);
        return allLoans;
    }

    public void approveLoan(Loan loan) {
        loan.approveLoan();
    }
    public void denyLoan(Loan loan) {
        loan.denyLoan();
    }


    /* TRANSACTIONS */
    public ArrayList<Transaction> getAllTransactions() {

    }


    /* STOCKS */
    
    public String toString() {
        return "Manager: " + getfirstName() + " " + getlastName();
    }
}