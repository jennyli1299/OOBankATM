package src;

public class CheckingAccount extends Account {
    // private static Float transferFee;
    // private static Float withdrawalFee;

    // TODO: ACCOUNT INFO
    public CheckingAccount(String IBAN, Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
                           Float closingCharge, Float openingCharge, Float transfer, Float withdrawal) {
        super(IBAN,amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        this.accountType = "Checking";
        transferFee = StaticVariables.getTransferFee();
        withdrawalFee = StaticVariables.getWithdrawalFee();
        // chargeOpeningCharge();
    }

    public static CheckingAccount openCheckingAccount(String IBAN, Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
    Float closingCharge, Float openingCharge, Float transfer, Float withdrawal, User user) {
        CheckingAccount checkingAccount = new CheckingAccount(IBAN, amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge, transfer, withdrawal);
        checkingAccount.chargeOpeningCharge();
        StaticVariables.getDatabaseManager().addCheckingAccount(checkingAccount, user);
        return checkingAccount;
    }


}
