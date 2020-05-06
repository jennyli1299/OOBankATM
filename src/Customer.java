package src;

import java.util.ArrayList;

public class Customer extends User {

   // Where does DatabaseManager come into play?

   /* constructor */
   public Customer(String firstName, String lastName, String id, String username, String password) {
      super(firstName, lastName, id, username, password);
   }

   public static Customer signupCustomer(String firstNameField, String lastNameField, String SSNField,
         String usernameField, String passwordField) {
      Customer customer = new Customer(firstNameField, lastNameField, SSNField, usernameField, passwordField);
      StaticVariables.getDatabaseManager().addCustomer(customer);
      return customer;
   }

   /* Open an Account */
   public void openAccount() {
      // Currency
      // Amount
      // Direct to other method
   }

   public CheckingAccount openCheckingAccount(float amount, Currency c) {
         CheckingAccount newCheckingAccount = CheckingAccount.openCheckingAccount(Account.uniqueIBAN(), amount,
               Account.uniqueRoutingNumber(), Account.uniqueAccountNumber(), true, c,
               StaticVariables.getClosingCharge(), StaticVariables.getOpeningCharge(), StaticVariables.getTransferFee(),
               StaticVariables.getWithdrawalFee(), this);
         return newCheckingAccount;
   }

   public SavingsAccount openSavingsAccount(float amount, Currency currency) {
      SavingsAccount newSavingsAccount = SavingsAccount.openSavingsAccount(Account.uniqueIBAN(), amount,
            Account.uniqueRoutingNumber(), Account.uniqueAccountNumber(), true, currency,
            StaticVariables.getClosingCharge(), StaticVariables.getOpeningCharge(),
            StaticVariables.getAccountInterest(), this);
      return newSavingsAccount;
   }

   public boolean enoughToOpenSECAcc() {
      // Check if Customers are rich enough >$5000.00 in their Savings
      ArrayList<Account> customerAccs = DatabaseManager.getAllAccounts(this);
      // ArrayList<Account> customerAccs =
      // StaticVariables.getDatabaseManager().getAllAccounts(this);
      float totalUSDbalance = 0;
      boolean over1000 = false;
      for (Account a : customerAccs) {
         // if (a.getAccountType().equals("Savings")) {
         float localbalance = a.getBalanceInLocalCurrency();
         float USDbalance = Currency.convertCurrencies(localbalance, a.getCurrency().getAbbrev(), "USD");
         totalUSDbalance += USDbalance;
         if (USDbalance >= 1000) {
            over1000 = true;
         }
         // }
      }
      if (totalUSDbalance >= 5000.0 && over1000) {
         return true;
      } else
         return false;
   }

   public String[] openSecuritiesAccount(Account account, float amount, Currency c) {
      // Create Securities Account & transfer >$1000.00 into securities account from
      // specified Account(s) but MUST keep at least $2500.00 in other accounts
      String[] ret = new String[2];
      if (enoughToOpenSECAcc()) {
         SecurityAccount newSecurityAccount = SecurityAccount.openSecurityAccount(Account.uniqueIBAN(), 0,
               Account.uniqueRoutingNumber(), Account.uniqueAccountNumber(), true, c,
               StaticVariables.getClosingCharge(), StaticVariables.getOpeningCharge(), this);
         account.makeTransfer(amount, newSecurityAccount.getIBAN());
         // StaticVariables.getDatabaseManager().addSecurityAccount(newSecurityAccount, this);
         ret[0] = "";
         ret[1] = "Successfully opened Securities Account! Happy stock shopping!";
      } else {
         ret[0] = "Error";
         ret[1] = "You do not reach the criteria to open a Securities Account.";
      }
      return ret;
   }

   public String[] closeAccount(Account account) {
      String[] ret = new String[2];
      boolean canClose = account.canClose();
      if (canClose) {
         account.close();
         ret[1] = "Your account [" + account.getAccountNumber() + "] has been successfully closed.";
      } else {
         ret[0] = "Error";
         ret[1] = "You do not have enough balance in this account to cover the closing charge. Closure denied.";
      }
      return ret;
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
      // acc.withdrawAmount(Famount);
      CheckingAccount chAcc = (CheckingAccount) (acc);
      // CHARGE WITHDRAWAL FEE
   }

   /* Transfer Money from one Account to another */
   public void transferFunds(float amount, Account from, Account to) {
      from.makeTransfer(amount, to);
   }

   // PAY INTEREST?

   /* Request a Loan */
   public void requestLoan(double initialPrincipal, Currency c, String collateral, int termInMonths) {
      Loan.requestALoan(this, initialPrincipal, collateral, termInMonths);
   }

   /* View the Status of all Loans/Loan Requests */
   public ArrayList<Loan> viewAllLoanStatus() {
      ArrayList<Loan> allLoans = StaticVariables.getDatabaseManager().getLoans(this);
      return allLoans;
   }

   /* Within Securities Accounts (Stocks) */
   // BUY/SELL Stocks
   public boolean buyStock(Stock stock, SecurityAccount secAcc) {
      // TODO
      return true;
   }

   public boolean sellStock(Stock stock) {
      // TODO
      return true;
   }

   @Override
   public String toString()
   {
      return this.getUsername() + " / " + this.getfirstName() + " " + this.getlastName();
   }

   // View Owned stocks
   public ArrayList<Stock> viewOwnedStocks() {
      return StaticVariables.getDatabaseManager().getStocks(this);
   }
}
