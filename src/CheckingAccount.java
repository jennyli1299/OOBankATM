package src;

public class CheckingAccount extends Account {
    private Float transferFee;
    private Float withdrawalFee;

    public CheckingAccount(Float amountInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency,
                           Float closingCharge, Float openingCharge, Float transferFee, Float withdrawalFee) {
        super(amountInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        this.transferFee = transferFee;
        this.withdrawalFee = withdrawalFee;
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

    public void setTransferFee(Float transferFee) {
        this.transferFee = transferFee;
    }

    public Float getWithdrawalFee() {
        return withdrawalFee;
    }

    public void setWithdrawalFee(Float withdrawalFee) {
        this.withdrawalFee = withdrawalFee;
    }

}
