package src;

import java.time.LocalDateTime;


enum Status {
    Pending, Approved, Denied
}

public class Loan {

    /* member variables */
    private Customer borrower;
    private Manager lender;
    private Status status;
    private float initialPrincipal;
    private float principal;
    private String collateral;
    private float interestRate;
    private int termInMonths;
    private LocalDateTime dateIssued;

    /* constructor */
    public Loan(Customer borrower, float initialPrincipal, String collateral, int termInMonths) {
        this.borrower = borrower;
        /* the manager is null until a manager decides on a loan status */
        this.status = Status.Pending;
        this.initialPrincipal = initialPrincipal;
        this.principal = this.initialPrincipal;
        this.collateral = collateral;
        /* the interest rate is dependent on the manager */
        this.termInMonths = termInMonths;
        this.dateIssued = LocalDateTime.now();
    }

    /* getters and setters */
    public void setStatus(Status status) {
        this.status = status;
    }
}
