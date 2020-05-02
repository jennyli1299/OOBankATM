package src;

public class SavingsAccount extends Account {
    private static Float interest;

    public SavingsAccount(String IBAN, Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency) {
        super(IBAN, amountInLocalCurrency, routingNumber, accountNumber, active, currency);
        this.accountType = "Savings";
        // this.interest = interest;
        chargeOpeningCharge();
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
