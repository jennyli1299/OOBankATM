package src;

import java.time.format.DateTimeFormatter;

public class Transfer extends Transaction {

    private Account recipient;

    /* constructor */
    public Transfer(Account account, Account recipient, float amount) {
        super(account, amount);
        this.recipient = recipient;
    }

    // TODO figure out good way to represent transfering money into account vs. out of account 
    public String toString() {
        /* for testing purposes, incomplete */
        return this.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) + ": +" + this.getAmount() + " (Transfer)";
    }
}
