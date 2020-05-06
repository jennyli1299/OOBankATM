package src;

import java.time.format.DateTimeFormatter;

public class Deposit extends Transaction {

    /* constructor */
    public Deposit(Account account, float amount) {
        super(account, amount);
    }

    public Deposit(float amount)
    {
        super(amount);
    }

    public String toString() {
        //return this.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ": +" + this.getAmount() + " (Deposit)";
        return "Deposit: +" + this.getAmount();
    }
}
