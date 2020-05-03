package src;

public class Stock {
    private Float price;
    private int totalShares;
    private int currentlyAvailableShares;
    private final String name;


    public Stock(Float price, int totalShares, int currentlyAvailableShares, String name) {
        this.price = price;
        this.totalShares = totalShares;
        this.currentlyAvailableShares = currentlyAvailableShares;
        this.name = name;
    }

    public Float getCurrentPrice() {
        return price;
    }

    public int getTotalShares() {
        return totalShares;
    }

    public int getCurrentlyAvailableShares() {
        return currentlyAvailableShares;
    }

    public String getName() {
        return name;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public void setCurrentlyAvailableShares(int currentlyAvailableShares) {
        this.currentlyAvailableShares = currentlyAvailableShares;
    }

    // Checks if there are at least n available shares to be bought from this stock
    public boolean checkIfThereAreAvailableShares(int n){
        return currentlyAvailableShares >= n;
    }

    // Try to buy n shares of this stock. If there are enough available deduct n and return true
    // otherwise reply false
    public boolean buyShares(int n){
        if (checkIfThereAreAvailableShares(n)){
            currentlyAvailableShares -= n;
            return true;
        }
        return false;
    }

    public void setTotalShares(int totalShares) {
        this.totalShares = totalShares;
    }

    public String toString() {
        return "<" + this.name + "> - $" + this.price + " per share";
    }
}
