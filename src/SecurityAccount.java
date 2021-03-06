package src;

import java.util.ArrayList;

public class SecurityAccount extends Account {
    private ArrayList<BoughtStock> boughtStocks;

    public SecurityAccount(String IBAN, float balanceInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency, float closingCharge, float openingCharge) {
        super(IBAN,0f, routingNumber, accountNumber, active, new Currency("USD"), 0f, 0f);
        this.accountType = "Security";
        this.boughtStocks = new ArrayList<BoughtStock>();
        // chargeOpeningCharge();
    }

    public SecurityAccount(float amountInLocalCurrency, Currency currency) {
        super(amountInLocalCurrency,  currency);
        this.accountType = "Savings";
        // chargeOpeningCharge();
    }

    public static SecurityAccount openSecurityAccount(String IBAN, float balanceInLocalCurrency, int routingNumber, int accountNumber, boolean active, Currency currency, float closingCharge, float openingCharge, User user) {
        SecurityAccount newSecurityAccount = new SecurityAccount(IBAN, balanceInLocalCurrency, routingNumber, accountNumber, active, currency, closingCharge, openingCharge);
        newSecurityAccount.chargeOpeningCharge();
        StaticVariables.getDatabaseManager().addSecurityAccount(newSecurityAccount, user);
        return newSecurityAccount;
    }

    // Adds shares to the stock. If this stock has not been bought before
    // we create a new BoughtStock for this stock and instantiate it with "amount" shares
    public void addNewSharesToStock(Stock stock, int amount){
        int boughtStockIndex = stockExists(stock);
        if (boughtStockIndex != -1){
            // If the new stock that we are trying to add exists already just add to this
            // amount shares
            boughtStocks.get(boughtStockIndex).addShares(amount);
            return;
        }
        // Otherwise if it did not exist before, create a new BoughtStock object and add the shares to that
        boughtStocks.add(new BoughtStock(stock, amount));
    }

    // Returns the profit that the user would have if he decided to sell n shares of this stock
    public Float peekPossibleProfit(Stock stock, int n){
        int boughtStockIndex = stockExists(stock);
        if (boughtStockIndex != -1){
            return boughtStocks.get(boughtStockIndex).returnPossibleProfit(n);
        }
        // If the stock requested was not found in this Security Account return null
        return null;
    }

    // Checks if the requested stock exists in this Security Account, if it does it returns its index
    // else -1
    public int stockExists(Stock stock){
        for (int i = 0; i < boughtStocks.size(); i ++){
            if (boughtStocks.get(i).getStock().getName().equals(stock.getName())){
                return i;
            }
        }
        return -1;
    }

    public int stockExists(String stock){
        for (int i = 0; i < boughtStocks.size(); i ++){
            if (boughtStocks.get(i).getStock().getName().equals(stock)){
                return i;
            }
        }
        return -1;
    }

    // Checks if the stock exists, if it does and there are enough shares to be sold it returns the profit
    // of the transaction
    // If the stock does not exist in this account it returns null
    public Float sellSharesOfStock(Stock stock, int n){
        int boughtStockIndex = stockExists(stock);
        if (boughtStockIndex != -1){
            return boughtStocks.get(boughtStockIndex).sellShares(n);
        }
        return null;
    }

    public ArrayList<BoughtStock> getBoughtStocks() {
        return boughtStocks;
    }

    public BoughtStock getBoughtStockByName(String name) {
        int temp = stockExists(name);
        return boughtStocks.get(temp);
    }

    public void setBoughtStocks(ArrayList<BoughtStock> boughtStocks) {
        this.boughtStocks = boughtStocks;
    }
}
