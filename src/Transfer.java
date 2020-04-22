package src;

public class Transfer extends Transaction {

    private Account recipient;

    /* constructor */
    public Transfer(Account account, Account recipient, float amount) {
        super(account, amount);
        this.recipient = recipient;
    }
}
