package src;

import java.time.LocalDateTime;

public abstract class Transaction {

    private Account account;
    private float amount;
    private LocalDateTime date;

    public Transaction(Account account, float amount) {
        this.account = account;
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public Account getAccount() {
        return this.account;
    }

    public float getAmount() {
        return this.amount;
    }

}