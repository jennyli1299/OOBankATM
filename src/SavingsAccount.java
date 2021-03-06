package src;

public class SavingsAccount extends Account {
    private static float interest;

    public SavingsAccount(String IBAN, float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
                          float closingCharge, float openingCharge, float interest) {
        super(IBAN, amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        this.accountType = "Savings";
        interest = StaticVariables.getAccountInterest();
        // chargeOpeningCharge();
    }

    public SavingsAccount(float amountInLocalCurrency, Currency currency) {
        super(amountInLocalCurrency,  currency);
        this.accountType = "Savings";
        interest = StaticVariables.getAccountInterest();
        // chargeOpeningCharge();
    }

    public static SavingsAccount openSavingsAccount (String IBAN, float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
    float closingCharge, float openingCharge, float interest, User user) {
        SavingsAccount newSavingsAccount = new SavingsAccount(IBAN, amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge, interest);
        newSavingsAccount.chargeOpeningCharge();
        StaticVariables.getDatabaseManager().addSavingsAccount(newSavingsAccount, user);
        return newSavingsAccount;
    }

    public void payInterest(){
        setBalanceInLocalCurrency(balanceInLocalCurrency + balanceInLocalCurrency * (StaticVariables.getAccountInterest())/100);
    }

    // public Float getInterest() {
    //     return interest;
    // }

    // public static void setSavingsAccountInterest(float manager_set_interest) {
    //     interest = Float.valueOf(manager_set_interest);
    // }
    
}
