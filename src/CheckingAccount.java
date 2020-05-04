package src;

public class CheckingAccount extends Account {
    private static Float transferFee;
    private static Float withdrawalFee;

    public CheckingAccount(String IBAN, Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
                           Float closingCharge, Float openingCharge, Float transfer, Float withdrawal) {
        super(IBAN,amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        this.accountType = "Checking";
        transferFee = transfer;
        withdrawalFee = withdrawal;
        // chargeOpeningCharge();
    }

    public static CheckingAccount openCheckingAccount(String IBAN, Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
    Float closingCharge, Float openingCharge, Float transfer, Float withdrawal, User user) {
        CheckingAccount checkingAccount = new CheckingAccount(IBAN, amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge, transfer, withdrawal);
        checkingAccount.chargeOpeningCharge();
        StaticVariables.getDatabaseManager().addCheckingAccount(checkingAccount, user);
        return checkingAccount;
    }


    public static Float getTransferFee() {
        return transferFee;
    }

    public static void setTransferFee(Float manager_set_transferFee) {
        transferFee = manager_set_transferFee;
    }

    public static Float getWithdrawalFee() {
        return withdrawalFee;
    }

    public static void setWithdrawalFee(Float manager_set_withdrawalFee) {
        withdrawalFee = manager_set_withdrawalFee;
    }

}
