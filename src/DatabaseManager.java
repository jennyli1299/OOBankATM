package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseManager{

    static Connection con;

    public DatabaseManager(){
        connect();
    }

    public void connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/BankATM","admin","admin");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Users");
            while(rs.next())
                System.out.println(rs.getString(2));
        }catch(Exception e){ System.out.println(e);}
    }

    public void close(){
        try {
            con.close();
        }
        catch(Exception e){ System.out.println(e);}
    }
    public static void addCustomer(Customer customer){
        String sql = "";
        sqlExecute(sql);
    }
    public static void addCheckingAccount(CheckingAccount checkingAccount, Customer customer){
        String sql = "";
        sqlExecute(sql);
    }
    public static void addSavingsAccount(SavingsAccount savingsAccount, Customer customer){
        String sql = "";
        sqlExecute(sql);
    }
    public static void addSecurityAccount(SecurityAccount securitiesAccount, Customer customer){
        String sql = "";
        sqlExecute(sql);
    }


    public static void addDeposit(Deposit transaction){

        //add to transactions table
    }
    public static void addWithdrawal(Withdrawal transaction){
        //add to transactions table
    }
    public static void addTransfer(Transfer transaction){
        //add to transactiosn table
    }

    public static void addLoan(Loan loan, Customer customer){
        //addd to loans
    }

    public static void addStock(Stock stock){

        //insert to stocks table
    }


    // SELECT
    public static ArrayList<Customer> getCustomers(){
        ArrayList<Customer> list = new ArrayList<>();


        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM bank_atm.Customers";
            ResultSet rs=stmt.executeQuery(sql);
            Customer temp;
            while(rs.next()) {
                //add customers to list and return
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    public static ArrayList<Loan> getLoans(String firstName, String lastName){
        ArrayList<Loan> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM bank_atm.Loans WHERE owner = \'"+firstName + " " + lastName+"\'";
            ResultSet rs=stmt.executeQuery(sql);
            Loan temp;
            while(rs.next()) {
                //add loans to list and return
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }



    public static ArrayList<Account> getAccounts(String firstName, String lastName, String type){
        ArrayList<CheckingAccount> list = new ArrayList<>();
        //type can be CH, SAV, SEC
        //find accounts and return
        //the schema will have 3 tables, one for each account type
        return null;
    }


    public static ArrayList<Stock> getStocks(){
        ArrayList<Stock> list = new ArrayList<>();
        try {
            Statement stmt=con.createStatement();

            String sql = "select * from bank_atm.Stocks";
            ResultSet rs=stmt.executeQuery(sql);
            Stock temp;
            while(rs.next()) {

                //add stocks to list and return
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    public static ArrayList<Transaction> getCustomerTransactions(Customer customer) {
        String customerfirstName = customer.getfirstName();
        String customerlastName = customer.getlastName();
        // return all transactions related to customer with an indefinite date
        return null;
    }

    /* manager - daily report on transactions */
    public static ArrayList<Transaction> getDailyTransactions() {

        /* select * from transactions such that date == today */
        return null;
    }

    // UPDATES PENDING

    /* manager - update stock price */
    public static Stock updateStockPrice(Stock stock, float price) {
        /* change the given stock's price */
        return null;
    }

    /* manager - add (or remove) more of an existing stock */
    public static Stock updateStockQuantity(Stock stock, int quantity) {
        /*
        stock.totalShares += quantity
        stock.currentlyAvailableShares += quantity

        quantity could be negative too, to represent taking away shares
        in which case check if quantity > totalAvailableShares
        */
        return null;
    }


    // DELETES PENDING


    public static void sqlExecute(String sql){
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        }
        catch(Exception e){ System.out.println(e);}
    }
}

