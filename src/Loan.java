package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


enum Status {
    Pending, Approved, Denied
}

public class Loan {

    /* member variables */
    private Customer borrower;
    private Manager lender;
    private Status status;
    private double initialPrincipal;
    private double amountDue;
    private double monthlyPayment;
    private String collateral;
    private double interestRate;
    private int termInMonths;
    private LocalDateTime dateIssued;
    private LocalDateTime dateDue;

    /* constructor */
    public Loan(Customer borrower, double initialPrincipal, String collateral, int termInMonths) {
        this.borrower = borrower;
        // the manager is null until a manager decides on a loan status */
        this.status = Status.Pending; // TODO: DB

        /* terms of the loan */ 
        this.initialPrincipal = initialPrincipal;
        this.amountDue = this.initialPrincipal;
        this.collateral = collateral;
        this.interestRate = 0.05; /* the interest rate is dependent on the manager */
        this.termInMonths = termInMonths;
        this.dateIssued = LocalDateTime.now();
        this.dateDue = this.dateIssued.plusMonths(termInMonths);

        /* calculate monthly payment */
        double numerator = this.initialPrincipal * (this.interestRate / 12) * Math.pow(1 + (this.interestRate / 12), this.termInMonths);
        double denominator = Math.pow(1 + (this.interestRate / 12), this.termInMonths) - 1;
        this.monthlyPayment = Math.round((numerator / denominator) * 100.0) / 100.0;
    }

    public static void RequestALoan(Customer borrower, double initialPrincipal, String collateral, int termInMonths) {
        Loan loan = new Loan(borrower, initialPrincipal, collateral, termInMonths);
        DatabaseManager.addLoan(loan, borrower);
    }

    /* getters and setters */
    public void setStatus(Status status) {
        this.status = status;
    }
    public Status getStatus() {
        return this.status;
    }

    public double getMonthlyPayment() {
        return this.monthlyPayment;
    }


    public String toString() {
        String repr = "<" + dateIssued.format(DateTimeFormatter.ofPattern("MM/dd/YYYY")) + "> - $" + initialPrincipal;
        return repr;
    }
}
