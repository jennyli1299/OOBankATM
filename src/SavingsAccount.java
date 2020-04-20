package src;

public class SavingsAccount extends Account {
    private Float interest;
    public SavingsAccount(Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
                          Float closingCharge, Float openingCharge, Float interest) {
        super(amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        this.interest = interest;
    }

    public void payInterest(){
        setBalanceInLocalCurrency(balanceInLocalCurrency + balanceInLocalCurrency * interest/100);
    }

    public Float getInterest() {
        return interest;
    }

}
