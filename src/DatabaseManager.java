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
    public static void addCheckingAccount(CheckingAccount checkingAccount, User user){
        String sql = "";
        sqlExecute(sql);
    }
    public static void addSavingsAccount(SavingsAccount savingsAccount, User user){
        String sql = "";
        sqlExecute(sql);
    }
    public static void addSecurityAccount(SecurityAccount securitiesAccount, User user){
        String sql = "";
        sqlExecute(sql);
    }

    public void addManager(User user)
    {
        String sql = "INSERT INTO BankATM.Users (firstname, lastname, id, username, status,  password) VALUES (\""+  user.getfirstName() + "\", \"" + user.getlastName()+ "\", \"" +user.getId()+ "\", \"" +user.getUsername() + "\", \"Manager\", \"" + user.getPassword() + "\");";
        System.out.println(sql);
        sqlExecute(sql);
    }

    public void addCustomer(User user){
        String sql = "INSERT INTO BankATM.Users (firstname, lastname, id, username, status,  password) VALUES (\""+  user.getfirstName() + "\", \"" + user.getlastName()+ "\", \"" +user.getId()+ "\", \"" +user.getUsername() + "\", \"Customer\", \"" + user.getPassword() + "\");";
        System.out.println(sql);
        sqlExecute(sql);
    }

    public void addTransaction(Transaction transaction){
        //TODO
    }

    public void addLoan(Loan loan, User user){
        //TODO
    }

    public void addStock(Stock stock){

       //TODO
    }

    public void addStock(Stock stock, User user){

        //TODO
    }


    // SELECT
    public static ArrayList<Customer> getCustomers(){
        ArrayList<Customer> list = new ArrayList<>();


        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.Users WHERE status = \"Customer\"";
            ResultSet rs=stmt.executeQuery(sql);
            Customer temp;
            while(rs.next()) {
                temp = new Customer(rs.getString("firstname"), rs.getString("lastname"), rs.getString("id"), rs.getString("username"), rs.getString("password" ));
                list.add(temp);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    // SELECT
    public static ArrayList<Manager> getManagers(){
        ArrayList<Manager> list = new ArrayList<>();


        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.Users WHERE status = \"Manager\"";
            ResultSet rs=stmt.executeQuery(sql);
            Manager temp;
            while(rs.next()) {
                temp = new Manager(rs.getString("firstname"), rs.getString("lastname"), rs.getString("id"), rs.getString("username"), rs.getString("password" ));
                list.add(temp);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }


    public static ArrayList<Loan> getLoans(User user){
        ArrayList<Loan> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.Loans WHERE user_id = \""+ user.getId() + "\"";
            String sql2 = "SELECT * FROM BankATM.Users WHERE user_id = \""+ user.getId() + "\"";
            ResultSet rs=stmt.executeQuery(sql);
            ResultSet rsCustomer = stmt.executeQuery(sql2);

            Customer borrower = new Customer(rsCustomer.getString("firstname"), rsCustomer.getString("lastname"), rsCustomer.getString("id"), rsCustomer.getString("username"), rsCustomer.getString("password" ));

            Loan temp;
            while(rs.next()) {
                temp = new Loan(borrower, rs.getDouble("initial_principal"), rs.getString("collateral"), rs.getInt("termInMonths"));
                list.add(temp);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }



    public static ArrayList<CheckingAccount> getCheckingAccounts(User user){
        ArrayList<CheckingAccount> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.checkings_accounts WHERE user_id = \""+ user.getId() + "\"";
            ResultSet rs=stmt.executeQuery(sql);
            CheckingAccount temp = null;
            while(rs.next())
            {
                temp = new CheckingAccount(rs.getString("iban"), rs.getFloat("balance_in_local_currency"), rs.getInt("routing_number"), rs.getInt("account_number"), rs.getString("is_active").equals("true"), new Currency(rs.getString("currency")), rs.getFloat("closing_charge"), rs.getFloat("opening_charge"), rs.getFloat("transferFee") , rs.getFloat("withdrawalFee"));
                list.add(temp);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    public static ArrayList<SavingsAccount> getSavingsAccounts(User user){
        ArrayList<SavingsAccount> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.savings_accounts WHERE user_id = \""+ user.getId() + "\"";
            ResultSet rs=stmt.executeQuery(sql);
            SavingsAccount temp = null;
            while(rs.next())
            {
                temp = new SavingsAccount(rs.getString("iban"), rs.getFloat("balance_in_local_currency"), rs.getInt("routing_number"), rs.getInt("account_number"), rs.getString("is_active").equals("true"), new Currency(rs.getString("currency")), rs.getFloat("closing_charge"), rs.getFloat("opening_charge"), rs.getFloat("interest"));
                list.add(temp);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    public static ArrayList<SecurityAccount> getSecurityAccounts(User user){
        ArrayList<SecurityAccount> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.security_accounts WHERE user_id = \""+ user.getId() + "\"";
            ResultSet rs=stmt.executeQuery(sql);
            SecurityAccount temp = null;
            while(rs.next())
            {
                temp = new SecurityAccount(rs.getString("iban"), rs.getFloat("balance_in_local_currency"), rs.getInt("routing_number"), rs.getInt("account_number"), rs.getString("is_active").equals("true"), new Currency(rs.getString("currency")), rs.getFloat("closing_charge"), rs.getFloat("opening_charge"));
                list.add(temp);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    public static ArrayList<Account> getAllAccounts(User user){
        ArrayList<Account> list = new ArrayList<>();
        list.addAll(getCheckingAccounts(user));
        list.addAll(getSavingsAccounts(user));
        list.addAll(getSecurityAccounts(user));
        return list;
    }


    public static ArrayList<Stock> getStocks(User user){
        ArrayList<Stock> list = new ArrayList<>();
        try {
            //TODO
//            Statement stmt=con.createStatement();
//
//            String sql = "select * from bank_atm.Stocks";
//            ResultSet rs=stmt.executeQuery(sql);
//            Stock temp;
//            while(rs.next()) {
//
//                //add stocks to list and return
//            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    public static ArrayList<Transaction> getTransactions(User user) {
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

