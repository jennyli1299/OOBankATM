package src;

public abstract class Account {
    protected Float balanceInLocalCurrency;
    protected int routingNumber;
    protected int accountNumber;
    protected String IBAN;
    private boolean active;
    private Currency currency;
    protected String accountType;
    private String accountStatus;

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
        this.accountStatus = "Open";
    }

    public void makeTransfer(float amount, String recIBAN)
    {
        this.makeWithdrawal(amount);
        StaticVariables.getDatabaseManager().increaseBalanceBy(amount, recIBAN);
    }

    public void makeDeposit(int amount)
    {
        balanceInLocalCurrency += amount;
        StaticVariables.getDatabaseManager().increaseBalanceBy(amount, this.getIBAN());
    }

    public void makeWithdrawal(Float amount){
        if (amount > balanceInLocalCurrency){
            //TODO show error message that the account does not have enough money
        }
        balanceInLocalCurrency -= amount;
        StaticVariables.getDatabaseManager().increaseBalanceBy(-amount, this.getIBAN());

    }
    public String getIBAN()
    {
        return IBAN;
    }

    public void setIBAN(String IBAN)
    {
        this.IBAN = IBAN;
    }

    public String getAccountType() {
        return accountType;
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

    public int getRoutingNumber() {
        return routingNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
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

    public void chargeOpeningCharge(){
        this.balanceInLocalCurrency -= StaticVariables.getOpeningCharge();
        StaticVariables.updateLifetimeGain(StaticVariables.getOpeningCharge());
    }

    public void chargeClosingCharge(){
        this.balanceInLocalCurrency -= StaticVariables.getClosingCharge();
        StaticVariables.updateLifetimeGain(StaticVariables.getClosingCharge());
    }

    public void depositAmount(Float amount){
        balanceInLocalCurrency += amount;
    }

    public boolean close() {
        if (StaticVariables.getClosingCharge() > balanceInLocalCurrency.floatValue()) {
            return false;
        }
        else {
            chargeClosingCharge();
            accountStatus = "Closed";
            return true;
        }
    }

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

    public Currency getCurrency() {
        return currency;
    }

}
