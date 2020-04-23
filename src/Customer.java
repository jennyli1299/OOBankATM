package src;

import java.util.ArrayList;

public class Customer extends User {

    // Where does DatabaseManager come into play?

    /* constructor */
    public Customer(String firstName, String lastName, int id, String username, String password) {
        super(firstName, lastName, id, username, password);
    }


     /* Open an Account */
     public void openAccount() {
        // Currency
        // Amount
        // Direct to other method
     }

     public void openCheckingAccount(float amount, Currency c) {
        // Create Checking Account
        // Add to Database
     }
     public void openSavingsAccount(float amount, Currency c) {
        // Create Savings Account
        // Add to Database
     }
     public void openSecuritiesAccount(float amount, Currency c) {
        // Check if Customers are rich enough >$5000.00 in their Savings
        // Create Securities Account and transfer >$1000.00 into this account but MUST keep at least $2500.00 in other accounts
        // Add to Database

        // Else fail
        // Should we keep max 1?
     }
     // Close Accounts?


     /* View Current Balances (database query) */
     public int viewAllBalances() {
        ArrayList<Account> allAccounts = DatabaseManager.getAccounts(getfirstName(), getlastName(), "CheckingAccount");
        return -1;
     }


     /* View Current Balances (database query) */
     public void viewallPastTransactions() {
        // get past transactions and display
     }


     /* Make a Withdrawal from an account */
     public void withdrawal(Account acc, float amount) {
        // Need to decide from which account and which currency
        // withdrawal fee
     }

     /* Transfer Money from one Account to another */
     public void transferFunds() {
         // which account -> account
         // how much money
         // does this incur fees?
     }

     // PAY INTEREST?


     /* Request a Loan */
     public void requestLoan(float amount, Currency c) {
         // COLLATERAL

     }

     /* View the Status of all Loans/Loan Requests */
     public void viewAllLoanStatus() {

     }


     /* Within Securities Accounts (Stocks) */
    // BUY/SELL Stocks
    // View Owned stocks
}
