package src;


public class Manager extends User{

    public static double currentYearlyInterestRate = 0.10;

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
    
}