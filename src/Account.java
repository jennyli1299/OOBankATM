package src;

import java.util.ArrayList;
import java.util.Random;

public abstract class Account {
    protected float balanceInLocalCurrency;
    protected int routingNumber;
    protected int accountNumber;
    protected String IBAN;
    private boolean active;
    private Currency currency;
    protected String accountType;
    // private String accountStatus;

    /* charges and fees */ // TODO: TAKE OUT
    protected static float openingCharge;
    protected static float closingCharge;
    protected static float depositFee;
    protected static float withdrawalFee;
    protected static float transferFee;

    public Account(String IBAN, Float balanceInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency, Float closing, Float opening) { //TODO remove last two
        this.balanceInLocalCurrency = balanceInLocalCurrency;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.active = active;
        this.currency = currency;
        closingCharge = StaticVariables.getClosingCharge();
        openingCharge = StaticVariables.getOpeningCharge();
        this.IBAN = IBAN;
        // this.accountStatus = "Open";
    }
    public Account(float balanceInLocalCurrency, boolean active, Currency currency) {
        this.balanceInLocalCurrency = balanceInLocalCurrency;
        this.IBAN = Account.uniqueIBAN();
        this.routingNumber = Account.uniqueRoutingNumber();
        this.accountNumber = Account.uniqueAccountNumber();
        this.active = active;
        this.currency = currency;
        closingCharge = StaticVariables.getClosingCharge();
        openingCharge = StaticVariables.getOpeningCharge();
        // this.accountStatus = "Open";
    }
    public Account(float balanceInLocalCurrency, Currency currency) {
        this.balanceInLocalCurrency = balanceInLocalCurrency;
        this.IBAN = Account.uniqueIBAN();
        this.routingNumber = Account.uniqueRoutingNumber();
        this.accountNumber = Account.uniqueAccountNumber();
        this.active = true;
        this.currency = currency;
        closingCharge = StaticVariables.getClosingCharge();
        openingCharge = StaticVariables.getOpeningCharge();
        // this.accountStatus = "Open";
    }
    public Account() { // USED ONLY FOR NULL ACCOUNT
        this.balanceInLocalCurrency = -1;
        this.IBAN = "null";
        // this.routingNumber = Account.uniqueRoutingNumber();
        // this.accountNumber = Account.uniqueAccountNumber();
        this.active = false;
        this.currency = new Currency("USD");
        closingCharge = StaticVariables.getClosingCharge();
        openingCharge = StaticVariables.getOpeningCharge();
        // this.accountStatus = "Open";
        // this.setIBAN("null");
    }

    /* Opening & Closing Accounts */
    public static boolean canOpenAccount(float amount, Currency c) {
        return (Currency.convertCurrencies(amount, c, "USD") - StaticVariables.getOpeningCharge() > 0);
    }
    public void chargeOpeningCharge(){
        float OCinLocalCurrency = Currency.convertCurrencies(StaticVariables.getOpeningCharge(), "USD", this.getCurrency());
        this.balanceInLocalCurrency -= OCinLocalCurrency;
        // StaticVariables.getDatabaseManager().updateAccount(this);
        StaticVariables.updateLifetimeGain(StaticVariables.getOpeningCharge());

        // Fee openingFee = new Fee(this, StaticVariables.getOpeningCharge());
        // StaticVariables.getDatabaseManager().addTransaction(openingFee);
    }
    public void chargeClosingCharge(){
        float OCinLocalCurrency = Currency.convertCurrencies(StaticVariables.getOpeningCharge(), "USD", this.getCurrency());
        this.balanceInLocalCurrency -= OCinLocalCurrency;
        StaticVariables.updateLifetimeGain(StaticVariables.getClosingCharge());

        // Fee closingFee = new Fee(this, StaticVariables.getClosingCharge());
        // StaticVariables.getDatabaseManager().addTransaction(closingFee);
    }
    public boolean canClose() {
        return (this.getBalanceInLocalCurrency() - Currency.convertCurrencies(StaticVariables.getClosingCharge(), "USD", this.getCurrency()) > 0);
    }
    public void close() { //When closing an account, do not call this. Call customer.closeAccount(Acount account)
        chargeClosingCharge();
        // accountStatus = "Closed";
        setActive(false);
        StaticVariables.getDatabaseManager().updateAccount(this);
        StaticVariables.getDatabaseManager().closeAccount(this);
        // return true;
    }

    /* TRANSACTIONS */
    public String[] makeTransfer(float amount, String recIBAN)
    {
        String[] ret = new String[2];
        float transferFee = StaticVariables.getTransferFee();
        float localTransferFee = Currency.convertCurrencies(transferFee, "USD", this.getCurrency().getAbbrev());
        if (balanceInLocalCurrency < amount + localTransferFee){
            ret[0] = "Error";
            ret[1] = "There is not enough balance in your account to make this transfer.";
        }
        else {
            Account transferTo = Account.getAccountFromIBAN(recIBAN);
            if (transferTo.equals("null")) {
                ret[0] = "Error";
                ret[1] = "There is no account corresponding to this receiving IBAN.";
            }
            else {
                float amtAfterFees = amount + localTransferFee;
                this.nofeeUpdateBalance(-amtAfterFees);
                // StaticVariables.getDatabaseManager().increaseBalanceBy(-amtAfterFees, this.getIBAN());
                float otherlocalTransferAmt = Currency.convertCurrencies(amount, this.getCurrency(), transferTo.getCurrency());
                transferTo.nofeeUpdateBalance(otherlocalTransferAmt);
                // StaticVariables.getDatabaseManager().increaseBalanceBy(otherlocalTransferAmt, recIBAN);

                Transfer transfer = new Transfer(this, recIBAN, amount);
                StaticVariables.getDatabaseManager().addTransfer(this, transfer); // diff from other Transactions
                StaticVariables.updateLifetimeGain(transferFee);

                ret[1] = "Transfer complete.";
                StaticVariables.getDatabaseManager().addTransfer(this, transfer);
            }
        }
        return ret;
    }

    public void payInterest()
    {
        makeNoFeeDeposit(getBalanceInLocalCurrency()*StaticVariables.getLoanInterestRate());
    }

    public void makeNoFeeDeposit(float amount)
    {
        float amtAfterFees = amount;
        this.nofeeUpdateBalance(amtAfterFees);
        // StaticVariables.getDatabaseManager().increaseBalanceBy(amtAfterFees, this.getIBAN());
        StaticVariables.updateLifetimeGain(depositFee);
        Deposit deposit = new Deposit(StaticVariables.getSelectedAccount(), amount);
        StaticVariables.getDatabaseManager().addDeposit(this, deposit);

    }

    public String[] makeDeposit(float amount)
    {
        String[] ret = new String[2];
        float depositFee = StaticVariables.getDepositFee();
        float localDepositFee = Currency.convertCurrencies(depositFee, "USD", this.getCurrency().getAbbrev());
        if (balanceInLocalCurrency + amount < localDepositFee){
            ret[0] = "Error";
            ret[1] = "There is not enough balance to cover the deposit fee.";
        }
        else {
            float amtAfterFees = amount - localDepositFee;
            this.nofeeUpdateBalance(amtAfterFees);
            // StaticVariables.getDatabaseManager().increaseBalanceBy(amtAfterFees, this.getIBAN());

            Deposit deposit = new Deposit(this, amount);
            StaticVariables.getDatabaseManager().addDeposit(this, deposit);

            StaticVariables.updateLifetimeGain(depositFee);
            ret[1] = "Deposit successful.";
            StaticVariables.getDatabaseManager().addDeposit(this, deposit);

        }
        return ret;
    }

    public String[] makeWithdrawal(float amount) {
        String[] ret = new String[2];
        float withdrawalFee = StaticVariables.getWithdrawalFee();
        float localWithdrawalFee = Currency.convertCurrencies(withdrawalFee, "USD", this.getCurrency().getAbbrev());
        if (amount + localWithdrawalFee > balanceInLocalCurrency){
            ret[0] = "Error";
            ret[1] = "You cannot withdraw this amount. It overwhelms the balance in this account.";
        }
        else {
            float amtAfterFees = amount + localWithdrawalFee;
            this.nofeeUpdateBalance(-amtAfterFees);
            // StaticVariables.getDatabaseManager().increaseBalanceBy(-amtAfterFees, this.getIBAN());

            Withdrawal withdrawal = new Withdrawal(this, -amount);
            StaticVariables.getDatabaseManager().addWithdrawal(this, withdrawal);

            StaticVariables.updateLifetimeGain(withdrawalFee);
            ret[1] = "Withdrawal accepted. Please retrieve your money.";
            StaticVariables.getDatabaseManager().addWithdrawal(this, withdrawal);
        }
        return ret;
    }

    public void nofeeUpdateBalance(float amount) {
        this.balanceInLocalCurrency += amount;
        StaticVariables.getDatabaseManager().increaseBalanceBy(amount, this.getIBAN());
        StaticVariables.getDatabaseManager().updateAccount(this);
    }


    /* Generate Unique Account IDs (String IBAN, int routingNumber, int accountNumber) */
    public static String uniqueIBAN() {
        ArrayList<String> takenIBAN = new ArrayList<String>();
        ArrayList<Customer> allCustomers = StaticVariables.getDatabaseManager().getCustomers();
        for (Customer c: allCustomers) {
            ArrayList<Account> customerAccounts = DatabaseManager.getAllAccounts(c);
            for (Account a: customerAccounts) {
                takenIBAN.add(a.getIBAN());
            }
        }
        String potentialIBAN = Account.generateIBAN();
        do {
            potentialIBAN = Account.generateIBAN();
        }
        while (takenIBAN.contains(potentialIBAN));
        return potentialIBAN;
    }
    public static String generateIBAN() {
        String IBAN = "";
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            int next = r.nextInt(10);
            IBAN = IBAN + Integer.toString(next);
        }
        for (int i = 0; i < 3; i++) {
            char c = (char) (r.nextInt('Z' - 'A' + 1) + 'A');
            IBAN = IBAN + c;
        }
        return IBAN;
    }

    public static int uniqueRoutingNumber() {
        ArrayList<Integer> takenRN = new ArrayList<Integer>();
        ArrayList<Customer> allCustomers = StaticVariables.getDatabaseManager().getCustomers();
        for (Customer c: allCustomers) {
            ArrayList<Account> customerAccounts = DatabaseManager.getAllAccounts(c);
            for (Account a: customerAccounts) {
                takenRN.add(a.getRoutingNumber());
            }
        }
        int routingNumber = Account.generateNumber(9);
        do {
            routingNumber = Account.generateNumber(9);
        }
        while (takenRN.contains(routingNumber));
        return routingNumber;
    }
    public static int uniqueAccountNumber() {
        ArrayList<Integer> takenAN = new ArrayList<Integer>();
        ArrayList<Customer> allCustomers = StaticVariables.getDatabaseManager().getCustomers();
        for (Customer c: allCustomers) {
            ArrayList<Account> customerAccounts = DatabaseManager.getAllAccounts(c);
            for (Account a: customerAccounts) {
                takenAN.add(a.getRoutingNumber());
            }
        }
        int accountNumber = Account.generateNumber(8);
        do {
            accountNumber = Account.generateNumber(8);
        }
        while (takenAN.contains(accountNumber));
        return accountNumber;
    }
    public static int generateNumber(int n) {
        int number = 0;
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int next = r.nextInt(10);
            number += next*Math.pow(10, i);
        }
        return number;
    }

    // public static Float getOpeningCharge() {
    //     return openingCharge;
    // }

    // public static Float getClosingCharge() {
    //     return closingCharge;
    // }

    // // set Opening & Closing charges by manager?
    // public static void setOpeningCharge(float openingcharge) {
    //     openingCharge = openingcharge;
    // }
    // public static void setClosingCharge(float closingcharge) {
    //     closingCharge = closingcharge;
    // }
    // public static void setTransferFee(Float manager_set_transferFee) {
    //     transferFee = manager_set_transferFee;
    // }
    // public static void setWithdrawalFee(Float manager_set_withdrawalFee) {
    //     withdrawalFee = manager_set_withdrawalFee;
    // }
    // public static void setDepositFee(float manager_set_depositFee) {
    //     depositFee = manager_set_depositFee;
    // }


    /* getters & setters */
    public String getIBAN()
    {
        return IBAN;
    }
    public int getRoutingNumber() {
        return routingNumber;
    }
    public int getAccountNumber() {
        return accountNumber;
    }
    public void setIBAN(String IBAN)
    {
        this.IBAN = IBAN;
    }
    public void setRouingNumber(int n) {
        this.routingNumber = n;
    }
    public void setAccountNumber(int n) {
        this.accountNumber = n;
    }

    public String getAccountType() {
        return accountType;
    }

    public Float getBalanceInLocalCurrency() {
        return balanceInLocalCurrency;
    }
    public void setBalanceInLocalCurrency(Float balanceInLocalCurrency) {
        this.balanceInLocalCurrency = balanceInLocalCurrency;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public static ArrayList<Account> getALLAccounts() {
        ArrayList<Account> allAccounts = new ArrayList<Account>();
        ArrayList<Customer> allCustomers = StaticVariables.getDatabaseManager().getCustomers();
        for (Customer c: allCustomers) {
            ArrayList<Account> customerAccounts = DatabaseManager.getAllAccounts(c);
            allAccounts.addAll(customerAccounts);
        }
        return allAccounts;
    }

    public static Account getAccountFromIBAN(String IBAN) {
        ArrayList<Account> allAccounts = Account.getALLAccounts();
        for (Account a: allAccounts) {
            if (a.equals(IBAN)) {
                return a;
            }
        }
        return new NullAccount();
    }

    public static ArrayList<Account> filterAccountsByBal (float balance) {
        ArrayList<Account> filtered = new ArrayList<Account>();
        ArrayList<Account> allAccounts = Account.getALLAccounts();
        for (Account a: allAccounts) {
            float balanceUSD = Currency.convertCurrencies(a.getBalanceInLocalCurrency(), a.getCurrency(), "USD");
            if (balanceUSD > balance) {
                filtered.add(a);
            }
        }
        return filtered;
    }
    public static ArrayList<Account> filterAccountsByBal (ArrayList<Account> list, float balance) {
        ArrayList<Account> filtered = new ArrayList<Account>();
        for (Account a: list) {
            float balanceUSD = Currency.convertCurrencies(a.getBalanceInLocalCurrency(), a.getCurrency(), "USD");
            if (balanceUSD > balance) {
                filtered.add(a);
            }
        }
        return filtered;
    }

    // public static Float getDepositFee() {
    //     return depositFee;
    // }
    // public static Float getWithdrawalFee() {
    //     return withdrawalFee;
    // }
    // public static Float getTransferFee() {
    //     return transferFee;
    // }

    @Override
    public String toString() {
        // return "<Account number:> " + accountNumber + " balance: " + balanceInLocalCurrency*Currency.getRate(currency.toString()) + currency.toString();
        String repr = "<" + this.getIBAN() + "> - ";
        if (this instanceof CheckingAccount) {
            repr += "Checking";
        } else if (this instanceof SavingsAccount) {
            repr += "Savings";
        } else if (this instanceof SecurityAccount) {
            repr += "Security";
        }
        return repr;
    }

    public boolean equals(Account other) {
        return this.getIBAN().equals(other.getIBAN());
    }

    public boolean equals(String IBAN) {
        return this.getIBAN().equals(IBAN);
    }

    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    public void changeCurrency(Currency c) {
        float newLocalBal = Currency.convertCurrencies(this.getBalanceInLocalCurrency(), this.getCurrency(), c);
        this.setBalanceInLocalCurrency(newLocalBal);
        this.setCurrency(c);
        StaticVariables.getDatabaseManager().updateCurrencyOnAccount(this, c.getAbbrev());
    }

}
