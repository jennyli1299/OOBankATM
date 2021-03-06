package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Comparable<Transaction> {

    private Account account;
    private float amount;
    private LocalDateTime date;

    public Transaction(Account account, float amount) {
        this.account = account;
        this.amount = amount;
        this.date = Time.getCurrentTime();
    }
    public Transaction(float amount)
    {
        this.amount = amount;
    }


    public Account getAccount() {
        return this.account;
    }

    public float getAmount() {
        return this.amount;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public int compareTo(Transaction t) {
        return this.getDate().compareTo(t.getDate());
    }

    public String toString() {
        String ret = this.getAccount().toString() + " := " + this.getAmount();
        return ret;
    }

}