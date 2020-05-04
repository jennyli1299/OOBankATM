package src;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class Manager extends User {

    // public static double currentYearlyInterestRate = 0.10;
    // public static double lifetimeLoss = 50000;
    // public static double lifetimeGain = 60000;

    public Manager(String firstName, String lastName, String id, String username, String password)
    {
        super(firstName, lastName, id, username, password);
    }


    // TODO Do these need to be added to database
    // public static void updateLifetimeLoss(double n) {
    //     lifetimeLoss += n;
    // }
    // public static void updateLifetimeGain(double n) {
    //     lifetimeGain += n;
    // }
    // public static double calculateProfit() {
    //     return lifetimeGain - lifetimeLoss;
    // }

    /** TODO
     * Loans (Approve/Deny)
     *      Loan -> setStatus(Status status)
     *      Approve will make customer need to pay loan accordingly
     *      Charge Interest
Loan -> setInterestRate(float interestRate)
Pay Interest (Savings accounts w high balance ← Variable) (>$5000)

Add/delete stock options
maintaining the list of stocks
updating the current price of each stock.

     */

    /* Account Charges */
    public void setAccountOpeningCharge(float oc) {
        StaticVariables.setOpeningCharge(oc);
    }
    public void setAccountClosingCharge(float cc) {
        StaticVariables.setClosingCharge(cc);
    }
    public void setAccountDepositFree(float df) {
        StaticVariables.setDepositFee(df);
    }
    public void setCheckingAccWithdrawalFee(float wf) {
        StaticVariables.setWithdrawalFee(wf);
    }
    public void setCheckingAccTransferFee(float tf) {
        StaticVariables.setTransferFee(tf);
    }
    public void setSavingsAccInterest(float interest) {
        StaticVariables.setSavingsAccountInterest(interest);
    }

    
    /* LOANS */
    public String setnewLoanInterestRate(float rate) {
        boolean done = Loan.setnewInterestRate(rate);
        if (done) return "The interest rate for loans has now been set to " + String.valueOf(rate);
        else return "Action not successful.";
    }

    public ArrayList<Loan> getAllLoans() {
        ArrayList<Customer> allCustomers = StaticVariables.getDatabaseManager().getCustomers();
        ArrayList<Loan> allLoans = new ArrayList<Loan>();
        for (Customer customer : allCustomers) {
            ArrayList<Loan> loans = StaticVariables.getDatabaseManager().getLoans(customer);
            allLoans.addAll(loans);
        }
        // /* Sorted by date Applied */
        // Collections.sort(allLoans, Loan.LoanAppliedDateComparator);
        /* Sorted by date Issued */
        Collections.sort(allLoans, Loan.LoanIssuedDateComparator);
        return allLoans;
    }

    public ArrayList<Loan> getPendingLoans() {
        ArrayList<Loan> allLoans = getAllLoans();
        ArrayList<Loan> allPending = new ArrayList<Loan>();
        for (Loan l : allLoans) {
            if (l.getStatus() == Loan.Status.Pending) {
                allPending.add(l);
            }
        }
        // /* Sorted by date Applied */
        // Collections.sort(allPending, Loan.LoanAppliedDateComparator);
        /* Sorted by date Issued */
        Collections.sort(allPending, Loan.LoanIssuedDateComparator);
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

    public void approveLoan(Loan loan) {
        loan.approveLoan();
    }
    public void denyLoan(Loan loan) {
        loan.denyLoan();
    }


    /* TRANSACTIONS */
    public ArrayList<Transaction> getAllTransactions() {
        ArrayList<Customer> allCustomers = StaticVariables.getDatabaseManager().getCustomers();
        ArrayList<Transaction> allTransactions = new ArrayList<Transaction>();
        for (Customer c : allCustomers) {
            ArrayList<Transaction> cTransactions = StaticVariables.getDatabaseManager().getTransactions(c);
            allTransactions.addAll(cTransactions);
        }
        Collections.sort(allTransactions);
        return allTransactions;
    }

    public ArrayList<Transaction> getDailyTransactions() {
        ArrayList<Transaction> dailyTransactions = StaticVariables.getDatabaseManager().getDailyTransactions();
        Collections.sort(dailyTransactions);
        return dailyTransactions;
    }

    public ArrayList<Transaction> getTransactionsfromDate(LocalDateTime date) {
        // TODO Is this possible? Is date stored?
        return null;
    }


    /* STOCKS */
    /* TODO: Add/delete stock options
     * TODO: maintaining the list of stocks
     */
    public void addStock(Stock stock) {
        //TODO
    }

    public void updateAvailableStockShares(Stock stock, int n) {
        int avaiableShares = stock.getCurrentlyAvailableShares() + n;
        stock.setCurrentlyAvailableShares(avaiableShares);
        int totalShares = stock.getTotalShares() + n;
        stock.setTotalShares(totalShares);
    }

    public void setTotalStockShares(Stock stock, int n) {
        int avaiableShares = n - stock.getTotalShares() + stock.getCurrentlyAvailableShares();
        stock.setCurrentlyAvailableShares(avaiableShares);
        stock.setTotalShares(n);
    }

    public void setStockSharePrice(Stock stock, float price) {
        Float shareprice = Float.valueOf(price);
        stock.setPrice(shareprice);
    }
    
    public String toString() {
        return "Manager: " + getfirstName() + " " + getlastName();
    }
}