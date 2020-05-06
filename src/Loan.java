package src;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;


public class Loan {

    public enum Status {
        Pending, Approved, Denied
    }

    /* member variables */
    private User borrower;
    private Manager lender;
    private Status status;
    private double initialPrincipal;
    private double amountDue;
    private int id;
    private double monthlyPayment;
    private String collateral;
    private double interestRate;
    // private static double staticinterestRate;
    private int termInMonths;
    private LocalDateTime dateApplied; //TODO: new attribute & are times stored
    private LocalDateTime dateIssued;
    private LocalDateTime dateDue;
    private int numberOfPayments;
    private String currency = "USD";

    /* constructor */
    public Loan(int id, Status status, User borrower, double initialPrincipal, String collateral, int termInMonths, int numberOfPayments)
    {
        this(borrower, initialPrincipal, collateral , termInMonths, numberOfPayments);
        this.id = id;
        this.status = status;

    }

    public Loan(User borrower, double initialPrincipal, String collateral, int termInMonths, int numberOfPayments)
    {
        this.borrower = borrower;
        // the manager is null until a manager decides on a loan status */
        this.status = Status.Pending;
        /* terms of the loan */
        this.numberOfPayments = numberOfPayments;
        this.initialPrincipal = initialPrincipal;
        this.amountDue = this.initialPrincipal;
        this.collateral = collateral;
        this.interestRate = (double)StaticVariables.getLoanInterestRate();
        this.termInMonths = termInMonths;
        this.dateApplied = Time.getCurrentTime();
        this.dateIssued = Time.getCurrentTime();
        this.dateDue = this.dateIssued.plusMonths(termInMonths);

        /* calculate monthly payment */
        double numerator = this.initialPrincipal * (this.interestRate / 12) * Math.pow(1 + (this.interestRate / 12), this.termInMonths);
        double denominator = Math.pow(1 + (this.interestRate / 12), this.termInMonths) - 1;
        this.monthlyPayment = Math.round((numerator / denominator) * 100.0) / 100.0;
    }

    public static Loan requestALoan(User borrower, double initialPrincipal, String collateral, int termInMonths) {
        Loan loan = new Loan(borrower, initialPrincipal, collateral, termInMonths, 0);
        StaticVariables.getDatabaseManager().addLoan(loan, borrower);
        StaticVariables.getDatabaseManager().updateLoan(loan, borrower);
        return loan;
    }

    public void requestALoan(User borrower) {
        // Loan loan = new Loan(borrower, this.initialPrincipal, this.collateral, this.termInMonths, 0);
        // StaticVariables.getDatabaseManager().addLoan(loan, borrower);
        StaticVariables.getDatabaseManager().updateLoan(this, borrower);
    }

    public static Comparator<Loan> LoanAppliedDateComparator = new Comparator<Loan>() {
        
        public int compare(Loan l1, Loan l2) {
            LocalDateTime applyDate1 = l1.getDateApplied();
            LocalDateTime applyDate2 = l2.getDateApplied();
            return applyDate1.compareTo(applyDate2);
        }
    };

    public static Comparator<Loan> LoanIssuedDateComparator = new Comparator<Loan>() {
        
        public int compare(Loan l1, Loan l2) {
            LocalDateTime issueDate1 = l1.getDateIssued();
            LocalDateTime issueDate2 = l2.getDateIssued();
            return issueDate1.compareTo(issueDate2);
        }
    };

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void payMonthlyInstallment()
    {
        numberOfPayments++;
        StaticVariables.getDatabaseManager().payMontlhyInstallment(this.getId());
    }

    public double getAmountDue()
    {
        return initialPrincipal - numberOfPayments * monthlyPayment;
    }

    public String getCurrency() {
        return currency;
    }



    /* getters and setters */
    public void setStatus(Status status) {
        this.status = status;
    }
    public void approveLoan() {
        this.dateIssued = Time.getCurrentTime();
        dateDue = this.dateIssued.plusMonths(termInMonths);
        this.setStatus(Status.Approved);
        StaticVariables.getDatabaseManager().updateLoan(this, borrower);
    }

    public void denyLoan() {
        this.setStatus(Status.Denied);
        StaticVariables.getDatabaseManager().updateLoan(this, borrower);
    }

    public String getCollateral() {
        return this.collateral;
    }

    public Status getStatus() {
        return this.status;
    }

    public static boolean setnewInterestRate(float rate) {
        StaticVariables.setLoanInterestRate(rate);;
        return true;
    }

    public double getInterestRate() {
        return StaticVariables.getLoanInterestRate();
    }

    public double getMonthlyPayment() {
        return this.monthlyPayment;
    }

    public LocalDateTime getDateApplied() {
        return this.dateApplied;
    }

    public LocalDateTime getDateIssued() {
        return this.dateIssued;
    }

    public int getTermInMonths() {
        return this.termInMonths;
    }

    public double getInitialPrincipal() {
        return this.initialPrincipal;
    }

    public String toString() {
        String repr = "<" + dateIssued.format(DateTimeFormatter.ofPattern("MM/dd/YYYY")) + "> - $" + initialPrincipal + " (" + collateral + ") - " + termInMonths + " months";
        return repr;
    }
}
