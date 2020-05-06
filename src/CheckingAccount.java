package src;

public class CheckingAccount extends Account {
    // private static Float transferFee;
    // private static Float withdrawalFee;

    public CheckingAccount(String IBAN, float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
                           float closingCharge, float openingCharge, float transfer, float withdrawal) {
        super(IBAN,amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        this.accountType = "Checking";
        transferFee = StaticVariables.getTransferFee();
        withdrawalFee = StaticVariables.getWithdrawalFee();
        // chargeOpeningCharge();
    }

    public CheckingAccount(float amountInLocalCurrency, Currency currency) {
        super(amountInLocalCurrency,  currency);
        this.accountType = "Checking";
    }

    public static CheckingAccount openCheckingAccount(String IBAN, float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
    Float closingCharge, float openingCharge, float transfer, float withdrawal, User user) {
        CheckingAccount checkingAccount = new CheckingAccount(IBAN, amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge, transfer, withdrawal);
        checkingAccount.chargeOpeningCharge();
        StaticVariables.getDatabaseManager().addCheckingAccount(checkingAccount, user);
        return checkingAccount;
    }

    public static boolean canOpenCheckingAccount(float amount, Currency c) {
        return (Currency.convertCurrencies(amount, c, "USD") - StaticVariables.getOpeningCharge() > 0);
    }

}
