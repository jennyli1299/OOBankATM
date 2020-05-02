package src;

public abstract class Account {
    protected Float balanceInLocalCurrency;
    protected int routingNumber;
    protected int accountNumber;
    protected String IBAN;
    private boolean active;
    private Currency currency;
    protected static float openingCharge;
    protected static float closingCharge;
    protected String accountType;
    private String accountStatus;

    public Account(String IBAN, Float balanceInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency, Float closing, Float opening) {
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.active = active;
        this.currency = currency;
        closingCharge = closing;
        openingCharge = opening;
        this.IBAN = IBAN;
        this.accountStatus = "Open";
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

    public Float getOpeningCharge() {
        return openingCharge;
    }

    public Float getClosingCharge() {
        return closingCharge;
    }

    // set Opening & Closing charges by manager?
    public static void setOpeningCharge(float openingcharge) {
        openingCharge = openingcharge;
    }
    public static void setClosingCharge(float closingcharge) {
        closingCharge = closingcharge;
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

    public int getRoutingNumber() {
        return routingNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void chargeOpeningCharge(){
        this.balanceInLocalCurrency -= openingCharge;
    }

    public void chargeClosingCharge(){
        this.balanceInLocalCurrency -= closingCharge;
    }

    public boolean withdrawAmount(Float amount){
        if (amount > balanceInLocalCurrency){
            return false;
        }
        balanceInLocalCurrency -= amount;
        return true;
    }

    public void depositAmount(Float amount){
        balanceInLocalCurrency += amount;
    }

    public boolean close() {
        if (closingCharge > balanceInLocalCurrency.floatValue()) {
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
