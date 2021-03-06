package src;

import java.time.format.DateTimeFormatter;

public class Withdrawal extends Transaction {

    /* constructor */
    public Withdrawal(Account account, float amount) {
        super(account, amount);
    }

    public Withdrawal(float amount)
    {
        super(amount);
    }
    public String toString() {
        //return this.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ": -" + this.getAmount() + " (Withdrawal)";
        return "Withdrawal: -" + this.getAmount();

    }
}
