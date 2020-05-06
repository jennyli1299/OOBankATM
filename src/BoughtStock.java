package src;

import java.time.LocalDateTime;

public class BoughtStock {
    private Stock stock;
    private int numberOfShares;
    private Float totalAmountSpentOnBuying;
    private LocalDateTime firstTimeStockWasBought;

    public BoughtStock(Stock stock, int numberOfShares) {
        this.stock = stock;
        this.numberOfShares = numberOfShares;
        this.totalAmountSpentOnBuying = stock.getCurrentPrice() * numberOfShares;
        this.firstTimeStockWasBought = Time.getCurrentTime();
    }

    public int getNumberOfShares() {
        return numberOfShares;
    }

    public Float getTotalAmountSpentOnBuying() {
        return totalAmountSpentOnBuying;
    }

    public Stock getStock() {
        return stock;
    }

    public int getAmountOfShares() {
        return numberOfShares;
    }

    public LocalDateTime getFirstTimeStockWasBought() {
        return firstTimeStockWasBought;
    }

    // adds n more shares of that stock
    public void addShares(int n){
        numberOfShares += n;
        totalAmountSpentOnBuying += stock.getCurrentPrice() * n;
    }

    // Deducts n shares from this BoughtStock and returns the profit that the user made by selling those
    public Float sellShares(int n){
        Float averagePricePaidForEachShare = totalAmountSpentOnBuying / numberOfShares;
        numberOfShares -= n;
        totalAmountSpentOnBuying -= averagePricePaidForEachShare * n;
        return n * (stock.getCurrentPrice() - averagePricePaidForEachShare);
    }

    // Returns to the user how much money will he make if he sells right now n shares
    public Float returnPossibleProfit(int n){
        Float averagePricePaidForEachShare = totalAmountSpentOnBuying / numberOfShares;
        return n * (stock.getCurrentPrice() - averagePricePaidForEachShare);

    }

    @Override
    public String toString() {
        return "BoughtStock{" +
                "stock=" + stock +
                ", numberOfShares=" + numberOfShares +
                ", totalAmountSpentOnBuying=" + totalAmountSpentOnBuying +
                '}';
    }
}
