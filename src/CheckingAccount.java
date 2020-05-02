package src;

public class CheckingAccount extends Account {
    private static float transferFee;
    private static float withdrawalFee;

    public CheckingAccount(String IBAN, Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
                           Float closingCharge, Float openingCharge, Float transfer, Float withdrawal) {
        super(IBAN,amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        this.accountType = "Checking";
        transferFee = transfer;
        withdrawalFee = withdrawal;
        chargeOpeningCharge();
    }

    @Override
    public boolean withdrawAmount(Float amount){
        if (super.withdrawAmount(amount)){
            if (withdrawalFee > balanceInLocalCurrency){
                balanceInLocalCurrency += amount;
                return false;
            }
            balanceInLocalCurrency -= withdrawalFee;
            return true;
        }
        return false;
    }

    public Float getTransferFee() {
        return transferFee;
    }

    public static void setTransferFee(Float manager_set_transferFee) {
        transferFee = manager_set_transferFee;
    }

    public Float getWithdrawalFee() {
        return withdrawalFee;
    }

    public static void setWithdrawalFee(Float manager_set_withdrawalFee) {
        withdrawalFee = manager_set_withdrawalFee;
    }

}
