package src;

import java.util.ArrayList;

import javax.xml.crypto.Data;

public class Customer extends User {

    // Where does DatabaseManager come into play?

    /* constructor */
    public Customer(String firstName, String lastName, int id, String username, String password) {
        super(firstName, lastName, id, username, password);
        DatabaseManager.addCustomer(this);
    }


     /* Open an Account */
     public void openAccount() {
        // Currency
        // Amount
        // Direct to other method
     }

     public void openCheckingAccount(float amount, Currency c) {
        // Create Checking Account
        //CheckingAccount checkingAccount = new CheckingAccount(IBAN, amount, routingNumber, accountNumber, active, c, closingCharge, openingCharge, transferFee, withdrawalFee);
        // Add to Database
        //DatabaseManager.addCheckingAccount(checkingAccount, this);
     }
     public void openSavingsAccount(float amount, Currency c) {
        // Create Savings Account
        //SavingsAccount savingsAccount = new SavingsAccount(IBAN, amount, routingNumber, accountNumber, active, c, closingCharge, openingCharge, interest);
        // Add to Database
        //DatabaseManager.addSavingsAccount(savingsAccount, this);
     }
     public boolean enoughToOpenSECAcc() {
        // Check if Customers are rich enough >$5000.00 in their Savings
        ArrayList<Account> customerAccs = DatabaseManager.getAccounts(this.getfirstName(), this.getlastName(), "SAV");
        float totalbalance = 0;
        boolean over1000 = false;
        for (Account a : customerAccs) {
            if (a.getAccountType().equals("Savings")) {
               totalbalance += a.getBalanceInLocalCurrency(); // What is Local Currency?
               if (a.getBalanceInLocalCurrency() >= 1000) {
                  over1000 = true;
               }
            }
        }
        if (totalbalance >= 5000.0 && over1000) {
           return true;
        }
        else return false;
     }
     public void openSecuritiesAccount(Account account, float amount, Currency c) {
        // Create Securities Account & transfer >$1000.00 into securities account from specified Account(s) but MUST keep at least $2500.00 in other accounts
        //SecurityAccount securitiesAccount = new SecurityAccount(IBAN, amount, routingNumber, accountNumber, active, c, closingCharge, openingCharge);
        account.withdrawAmount(amount);
        // Add to Database
         //DatabaseManager.addSecurityAccount(securitiesAccount, this);
        // Should we keep max 1?
     }
     // Close Accounts?

     public String closeAccount(Account account)
     {
         boolean closed = account.close();
         if (closed)
         {
             return "Your account [" + account.getAccountNumber() + "] has been successfully closed.";
         } else
         {
             return "You do not have enough balance in this account to cover the closing charge. Closure denied.";
         }
     }


     /* View Current Balances (database query) */
     public int viewAllBalances() {
        ArrayList<Account> allAccounts = DatabaseManager.getAccounts(getfirstName(), getlastName(), "CH");
        allAccounts.addAll(DatabaseManager.getAccounts(getfirstName(), getlastName(), "SAV"));
        allAccounts.addAll(DatabaseManager.getAccounts(getfirstName(), getlastName(), "SEC"));

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
        Float Famount = Float.valueOf(amount);
        acc.withdrawAmount(Famount);
        CheckingAccount chAcc = (CheckingAccount)(acc);
        // CHARGE WITHDRAWAL FEE
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
