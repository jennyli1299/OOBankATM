package src;

public class SavingsAccount extends Account {
    private static Float interest;

    public SavingsAccount(String IBAN, Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
                          Float closingCharge, Float openingCharge, Float interest) {
        super(IBAN, amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        this.accountType = "Savings";
        this.interest = interest;
        // chargeOpeningCharge();
    }

    public static SavingsAccount openSavingsAccount (String IBAN, Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
    Float closingCharge, Float openingCharge, Float interest) {
        SavingsAccount newSavingsAccount = new SavingsAccount(IBAN, amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge, interest);
        newSavingsAccount.chargeOpeningCharge();
        return newSavingsAccount;
    }

    public void payInterest(){
        setBalanceInLocalCurrency(balanceInLocalCurrency + balanceInLocalCurrency * interest/100);
    }

    public Float getInterest() {
        return interest;
    }

    public static void setSavingsAccountInterest(float manager_set_interest) {
        interest = Float.valueOf(manager_set_interest);
    }
    
}
