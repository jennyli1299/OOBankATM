package src;

import java.util.ArrayList;
import java.util.Random;

public class StaticVariables
{
    private static User loggedInUser = null;
    private static DatabaseManager databaseManager = null;
    private static Account selectedAccount = null;
    private static Loan selectedDueLoan = null;
    private static Loan selectedLoan = null;
    private static Customer selectedCustomer = null;

    /* Static Bank Variables */
    private static float openingCharge = 10;
    private static float closingCharge = 20;
    private static float depositFee = 5;
    private static float withdrawalFee = 3;
    private static float transferFee = 1;
    private static float AccountInterest = 0.015f;
    private static float LoanInterestRate = 0.015f;

    /* Bank Lifetime Profit Markers */ //TODO Store this in DB
    private static float BanklifetimeLoss = 50000;

    private static float BanklifetimeGain = 60000;

    /* Static Account # */
    private static int accumulator;


    public static User getLoggedInUser()
    {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser)
    {
        StaticVariables.loggedInUser = loggedInUser;
    }

    public static DatabaseManager getDatabaseManager()
    {
        return databaseManager;
    }

    public static void setDatabaseManager(DatabaseManager databaseManager)
    {
        StaticVariables.databaseManager = databaseManager;
    }

    public static Customer getSelectedCustomer()
    {
        return selectedCustomer;
    }

    public static void setSelectedCustomer(Customer selectedCustomer)
    {
        StaticVariables.selectedCustomer = selectedCustomer;
    }

    public static Account getSelectedAccount()
    {
        return selectedAccount;
    }

    public static void setSelectedAccount(Account selectedAccount)
    {
        StaticVariables.selectedAccount = selectedAccount;
    }

    public static Loan getSelectedLoan()
    {
        return selectedLoan;
    }

    public static void setSelectedLoan(Loan selectedLoan)
    {
        StaticVariables.selectedLoan = selectedLoan;
    }

    public static Loan getSelectedDueLoan()
    {
        return selectedDueLoan;
    }

    public static void setSelectedDueLoan(Loan selectedDueLoan)
    {
        StaticVariables.selectedDueLoan = selectedDueLoan;
    }



    public static float getOpeningCharge() {
        return openingCharge;
    }
    public static float getClosingCharge() {
        return closingCharge;
    }
    public static float getDepositFee() {
        return depositFee;
    }
    public static float getWithdrawalFee() {
        return withdrawalFee;
    }
    public static float getTransferFee() {
        return transferFee;
    }
    public static float getAccountInterest() {
        return AccountInterest;
    }
    public static float getLoanInterestRate() {
        return LoanInterestRate;
    }
    

    // set Opening & Closing charges by manager
    public static void setOpeningCharge(float openingcharge) {
        openingCharge = openingcharge;
    }
    public static void setClosingCharge(float closingcharge) {
        closingCharge = closingcharge;
    }
    public static void setTransferFee(Float manager_set_transferFee) {
        transferFee = manager_set_transferFee;
    }
    public static void setWithdrawalFee(Float manager_set_withdrawalFee) {
        withdrawalFee = manager_set_withdrawalFee;
    }
    public static void setDepositFee(float manager_set_depositFee) {
        depositFee = manager_set_depositFee;
    }
    public static void setSavingsAccountInterest(float manager_set_interest) {
        AccountInterest = manager_set_interest;
    }
    public static void setLoanInterestRate(float manager_set_loaninterest) {
        LoanInterestRate = manager_set_loaninterest;
    }

    /* Bank Lifetime Gains/Losses */
    public static void updateLifetimeLoss(float n) {
        BanklifetimeLoss += n;
        // TODO: UPDATE THIS IN DB
    }
    public static void updateLifetimeGain(float n) {
        BanklifetimeGain += n;
        // TODO: update in db
    }
    public static float calculateProfit() {
        return BanklifetimeGain - BanklifetimeLoss;
    }
    public static float getLifetimeLoss() {
        return BanklifetimeLoss;
    }
    public static float getLifetimeGain() {
        return BanklifetimeGain;
    }
}
