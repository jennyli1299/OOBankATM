package src;

import javax.jws.soap.SOAPBinding;
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

    public static void connect(){
        try{
            String url="jdbc:mysql://localhost:3306/BankATM";
            String username="root";
            String password="pass";
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,username,password);
            System.out.println("Connected to database!");
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

    /**
     * returns a customer or null
     * @param password
     * @return
     */
    public static <T extends User> User getUserWithCredentials(String username, String password)
    {
        ArrayList<User> list = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM BankATM.Users where username = \"" + username + "\" AND password = \"" + password+"\"";
            System.out.println("sql: " + sql);
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("results: " + rs);
            User temp;
            while(rs.next()) {
                //add customers to list and return
                if(rs.getString("status").equals("Manager"))
                {
                    temp = new Manager(rs.getString("firstname"), rs.getString("lastname"), rs.getString("id"), rs.getString("username"), rs.getString("password"));
                }else
                {
                    temp = new Customer(rs.getString("firstname"), rs.getString("lastname"), rs.getString("id"), rs.getString("username"), rs.getString("password"));
                }
                list.add(temp);
            }
        }
        catch(Exception e){ System.out.println(e);}
        if(list.size() > 0)
        {
            return list.get(0);
        }else
        {
            return null;
        }

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


    public void addUser(User user){
        String sql = "INSERT INTO BankATM.Users (firstname, lastname, id, username, status,  password) VALUES (\""+  user.getfirstName() + "\", \"" + user.getlastName()+ "\", \"" +user.getId()+ "\", \"" +user.getUsername() + "\", \"Customer\", \"" + user.getPassword() + "\");";
        System.out.println(sql);
        sqlExecute(sql);
    }

//    public void addTransfer(Transfer transaction){
//        String sql = "INSERT INTO bank_atm.transaction (sender_acc_num, sender_routing_num, rec_acc_num, rec_routing_num, currency, amount, type) VALUES (\'" +transaction.getSenderAccountNumber()+ "\', \'"+ transaction.getSenderRoutingNumber()+"\', \'" + transaction.getReceiverAccountNumber()+ "\', \'"+transaction.getReceiverRoutingNumber()+"\', \'"+transaction.getCurrency().toString()+"\', \'"+transaction.getAmount()+"\', \'T\');";
//        System.out.println(sql);
//        sqlExecute(sql);
//    }
//
//    public void addLoan(Loan loan, CustomerAccount customerAccount){
//        String sql = " INSERT INTO bank_atm.loan (initial_amount, debt, owner, interest) VALUES (\'"+loan.getInitialAmountInLocalCurrency()+"\', \'"+loan.getDebtInLocalCurrency()+"\', \'"+customerAccount.getPerson().getName().getFirstName()+ " " +  customerAccount.getPerson().getName().getLastName()+"\', \'"+loan.getInterest()+"\');";
//        sqlExecute(sql);
//    }
//
//    public void addStock(Stock stock){
//
//        String sql = "INSERT INTO bank_atm.stock (name, price, total_shares, avai_shares) VALUES (\'"+stock.getName()+"\', \' " + stock.getCurrentPrice()+ "\', \'"+ stock.getTotalShares()+"\', \'"+ stock.getCurrentlyAvailableShares()+"\');";
//        System.out.println(sql);
//        sqlExecute(sql);
//
//    }


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

