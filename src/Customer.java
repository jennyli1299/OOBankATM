package src;

import java.util.ArrayList;

import javax.xml.crypto.Data;

public class Customer extends User {

    // Where does DatabaseManager come into play?

    /* constructor */
    public Customer(String firstName, String lastName, String id, String username, String password) {
        super(firstName, lastName, id, username, password);
        StaticVariables.getDatabaseManager().addCustomer(this);
    }


     /* Open an Account */
     public void openAccount() {
        // Currency
        // Amount
        // Direct to other method
     }

     public void openCheckingAccount(float amount, Currency c) {
        // Create Checking Account
        //CheckingAccount newCheckingAccount = CheckingAccount.openCheckingAccount(IBAN, amount, routingNumber, accountNumber, active, c, closingCharge, openingCharge, transfer, withdrawal, this);
        // Add to Database [Handled when Account is openned]
      //   StaticVariables.getDatabaseManager().addCheckingAccount(newCheckingAccount, this);
     }
     public void openSavingsAccount(float amount, Currency currency) {
        // Create Savings Account
        //SavingsAccount newSavingsAccount = SavingsAccount.openSavingsAccount(IBAN, amount, routingNumber, accountNumber, active, currency, closingCharge, openingCharge, interest, this);
        // Add to Database [Handled when Account is opened]
      //   StaticVariables.getDatabaseManager().addSavingsAccount(newSavingsAccount, this);
     }
     public boolean enoughToOpenSECAcc() {
        // Check if Customers are rich enough >$5000.00 in their Savings
        ArrayList<Account> customerAccs = StaticVariables.getDatabaseManager().getAllAccounts(this);
        float totalbalance = 0;
        boolean over1000 = false;
        for (Account a : customerAccs) {
            // if (a.getAccountType().equals("Savings")) {
               totalbalance += a.getBalanceInLocalCurrency(); // What is Local Currency?
               if (a.getBalanceInLocalCurrency() >= 1000) {
                  over1000 = true;
               }
            // }
        }
        if (totalbalance >= 5000.0 && over1000) {
           return true;
        }
        else return false;
     }
     public void openSecuritiesAccount(Account account, float amount, Currency c) {
        // Create Securities Account & transfer >$1000.00 into securities account from specified Account(s) but MUST keep at least $2500.00 in other accounts
        //SecurityAccount securitiesAccount = new SecurityAccount(IBAN, amount, routingNumber, accountNumber, active, c, closingCharge, openingCharge);
        //SecurityAccount newSecurityAccount = SecurityAccount.openSecurityAccount(IBAN, balanceInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge)
        //account.withdrawAmount(amount);
        //newSecurityAccount.depositAmount(amount);
        // Add to Database
         //StaticVariables.getDatabaseManager().addSecurityAccount(newSecurityAccount, this);
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
     public <T extends Account> ArrayList<Account> viewAllBalances() {
        ArrayList<Account> allAccounts = new ArrayList<Account>();
        ArrayList<CheckingAccount> checking = DatabaseManager.getCheckingAccounts(this);
        ArrayList<SavingsAccount> savings = DatabaseManager.getSavingsAccounts(this);
        ArrayList<SecurityAccount> security = DatabaseManager.getSecurityAccounts(this);
        allAccounts.addAll(checking);
        allAccounts.addAll(savings);
        allAccounts.addAll(security);

        return allAccounts;
     }


     /* View Current Balances (database query) */
     public ArrayList<Transaction> viewallPastTransactions() {
        ArrayList<Transaction> allTransactions = StaticVariables.getDatabaseManager().getTransactions(this);
         return allTransactions;
      }


     /* Make a Withdrawal from an account */
     public void withdrawal(Account acc, float amount) {
        // Need to decide from which account and which currency
        // withdrawal fee
        Float Famount = Float.valueOf(amount);
        //acc.withdrawAmount(Famount);
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
     public void requestLoan(double initialPrincipal, Currency c, String collateral, int termInMonths) {
         //Loan.RequestALoan(this, initialPrincipal, collateral, termInMonths);
     }

     /* View the Status of all Loans/Loan Requests */
     public ArrayList<Loan> viewAllLoanStatus() {
         ArrayList<Loan> allLoans = StaticVariables.getDatabaseManager().getLoans(this);
         return allLoans;
     }


     /* Within Securities Accounts (Stocks) */
    // BUY/SELL Stocks
    // View Owned stocks
}
