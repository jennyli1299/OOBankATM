package src;

import javax.jws.soap.SOAPBinding;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

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

    public Customer getCustomerWithID(String id)
    {
        try {
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM BankATM.Users where id = \"" + id + "\"";
            ResultSet rs = stmt.executeQuery(sql);
            Customer temp;
            rs.next();
            temp = new Customer(rs.getString("firstname"), rs.getString("lastname"), rs.getString("id"), rs.getString("username"), rs.getString("password"));
            return temp;
        }
        catch(Exception e){ System.out.println(e);}
        return null;
    }


    public void addCheckingAccount(CheckingAccount checkingAccount, User user){
        String sql = "INSERT INTO checkings_accounts VALUES (\"" + user.getId() + "\", \"" + checkingAccount.getIBAN()+ "\", " + checkingAccount.getBalanceInLocalCurrency() +" , 123, 456, \"TRUE\", \""+ checkingAccount.getCurrency().getAbbrev()+"\", 1, 1, 1, 1);"; //transfer fee and withdrawal fee are hard coded
        sqlExecute(sql);
    }
    public void addSavingsAccount(SavingsAccount savingsAccount, User user){
        String sql = "INSERT INTO savings_accounts VALUES (\"" + user.getId() + "\", \"" + savingsAccount.getIBAN()+ "\", " + savingsAccount.getBalanceInLocalCurrency() +" , 123, 456, \"TRUE\", \""+ savingsAccount.getCurrency().getAbbrev()+"\", 1, 1, 0.5);"; //interest is hard coded
        sqlExecute(sql);
    }
    public void addSecurityAccount(SecurityAccount securitiesAccount, User user){
        String sql = "INSERT INTO security_accounts VALUES (\"" + user.getId() + "\", \"" + securitiesAccount.getIBAN()+ "\", " + securitiesAccount.getBalanceInLocalCurrency() +" , 123, 456, \"TRUE\", \""+ securitiesAccount.getCurrency().getAbbrev()+"\", 1, 1);";
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



    public void addLoan(Loan loan, User borrower)
    {
        String sql = "INSERT INTO Loans (user_id, status, collateral, date_issued, termInMonths, initial_principal, number_of_payments) VALUES (\""+ borrower.getId() +"\", \"Pending\",\""+ loan.getCollateral() +"\", \""+loan.getDateIssued().toString() +"\", "+loan.getTermInMonths() +", " + loan.getInitialPrincipal() + " , 0)";
        System.out.println(sql);
        sqlExecute(sql);
    }

    public void updateAccount(Account account) {
        //TODO
    }

    public void updateLoan(Loan loan, User user){
        //TODO
    }

    public void updateStock(Stock stock){

       //TODO
    }

    public void updateUser(Stock stock, User user){

        //TODO
    }


    // SELECT
    public ArrayList<Customer> getCustomers(){
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
    public ArrayList<Manager> getManagers(){
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


    public ArrayList<Loan> getLoans(User user){
        ArrayList<Loan> list = new ArrayList<>();

        try {
            Statement stmt= con.createStatement();
            String sql2 = "SELECT * FROM BankATM.Users WHERE id = \""+ user.getId() + "\"";
            ResultSet rsCustomer = stmt.executeQuery(sql2);
            rsCustomer.next();
            Customer borrower = new Customer(rsCustomer.getString("firstname"), rsCustomer.getString("lastname"), rsCustomer.getString("id"), rsCustomer.getString("username"), rsCustomer.getString("password" ));


            stmt = con.createStatement();
            String sql = "SELECT * FROM BankATM.Loans WHERE user_id = \""+ user.getId() + "\"";
            ResultSet rs=stmt.executeQuery(sql);

            Loan temp;
            while(rs.next()) {

                temp = new Loan(rs.getInt("id"), getStatus(rs), borrower, rs.getDouble("initial_principal"), rs.getString("collateral"), rs.getInt("termInMonths"), rs.getInt("number_of_payments"));
                list.add(temp);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    private Loan.Status getStatus(ResultSet rs)
    {
        try
        {
            switch (rs.getString("status"))
            {
                case "Approved":
                    return Loan.Status.Approved;
                case "Pending":
                    return Loan.Status.Pending;
                case "Denied":
                    return Loan.Status.Denied;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return Loan.Status.Pending;
    }

    public void payMontlhyInstallment(int id)
    {
        try
        {
            Statement stmt = con.createStatement();
            String sql = "UPDATE BankATM.Loans SET number_of_payments = (number_of_payments + " + 1 + ") WHERE id = '" + id + "'";
            stmt.executeUpdate(sql);
        }catch (Exception ex)
        {
            System.out.println("Could not store Loan payment");
        }
    }


    public static ArrayList<CheckingAccount> getCheckingAccounts(){
        ArrayList<CheckingAccount> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.checkings_accounts WHERE is_active = \"TRUE\"";
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

    public static ArrayList<SavingsAccount> getSavingsAccounts(){
        ArrayList<SavingsAccount> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.savings_accounts WHERE is_active = \"TRUE\"";
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

    public static ArrayList<SecurityAccount> getSecurityAccounts(){
        ArrayList<SecurityAccount> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.security_accounts WHERE is_active = \"TRUE\"";
            System.out.println(sql + " : here");
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


    public static ArrayList<CheckingAccount> getCheckingAccounts(User user){
        ArrayList<CheckingAccount> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.checkings_accounts WHERE is_active = \"TRUE\" AND user_id = \""+ user.getId() + "\"";
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
            String sql = "SELECT * FROM BankATM.savings_accounts WHERE is_active = \"TRUE\" AND user_id = \""+ user.getId() + "\"";
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
            String sql = "SELECT * FROM BankATM.security_accounts WHERE is_active = \"TRUE\" AND user_id = \""+ user.getId() + "\"";
            System.out.println(sql + " : here");
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

    public  ArrayList<Loan> getAllLoans(){
        ArrayList<Loan> list = new ArrayList<>();

        try {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.Loans";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                Loan loan = new Loan(getCustomerWithID(rs.getString("user_id")), rs.getDouble("initial_principal"), rs.getString("collateral"), rs.getInt("termInMonths"), rs.getInt("number_of_payments"), rs.getString("status"));
                list.add(loan);
            }
        }
        catch(Exception e){ System.out.println(e);}
        return list;
    }

    public static ArrayList<Account> getAllAccounts(){
        ArrayList<Account> list = new ArrayList<>();
        list.addAll(getCheckingAccounts());
        list.addAll(getSavingsAccounts());
        list.addAll(getSecurityAccounts());
        return list;
    }

    public static ArrayList<Account> getAllAccounts(User user){
        ArrayList<Account> list = new ArrayList<>();
        list.addAll(getCheckingAccounts(user));
        list.addAll(getSavingsAccounts(user));
        list.addAll(getSecurityAccounts(user));
        return list;
    }

    public int getAvailableShares(String StockName)
    {
        return 0; //todo
    }

    public int modifyStockPrice(double amount)
    {
        return 0; //todo
    }

    public void increaseBalanceBy(float amount, String IBAN)
    {
        try
        {
            //1 will work, other will throw excpetion
            Statement stmt = con.createStatement();
            String sql1 = "UPDATE BankATM.savings_accounts SET balance_in_local_currency = (balance_in_local_currency + " + amount + ") WHERE iban = '" + IBAN + "'";
            stmt.executeUpdate(sql1);
        }catch (Exception ex)
        {
            System.out.println("The account is not savings");
        }

        try
        {
            Statement stmt = con.createStatement();
            String sql2 = "UPDATE BankATM.checkings_accounts SET balance_in_local_currency = (balance_in_local_currency + " + amount + ") WHERE iban = '" + IBAN + "'";
            stmt.executeUpdate(sql2);
        }catch (Exception e)
        {
            System.out.println("The account is not checkings");
        }

        try
        {
            Statement stmt = con.createStatement();
            String sql3 = "UPDATE BankATM.security_accounts SET balance_in_local_currency = (balance_in_local_currency + " + amount + ") WHERE iban = '" + IBAN + "'";
            System.out.println(sql3);
            stmt.executeUpdate(sql3);
        }catch (Exception e)
        {
            System.out.println("The account is not security");
        }


    }



    public ArrayList<Stock> getStocks(User user){
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

    public ArrayList<Transaction> getTransactions(Account account) {
        ArrayList<Transaction> list = new ArrayList<>();

        if(account instanceof CheckingAccount)
        {
            try
            {
                Statement stmt=con.createStatement();
                String sql = "SELECT * FROM BankATM.single_checking_account_transactions WHERE account_id = \"" + account.getIBAN()+ "\"";
                System.out.println(sql);
                ResultSet rs=stmt.executeQuery(sql);
                SecurityAccount temp = null;
                while(rs.next())
                {
                    String typeOfTransaction = rs.getString("type");
                    if(typeOfTransaction.equals("Deposit"))
                    {
                        Deposit deposit = new Deposit(rs.getFloat("amount"));
                        list.add(deposit);
                    }else if(typeOfTransaction.equals("Withdrawal"))
                    {
                        Withdrawal withdrawal= new Withdrawal(rs.getFloat("amount"));
                        list.add(withdrawal);
                    }else if(typeOfTransaction.equals("Transfer"))
                    {
                        Transfer transfer = new Transfer(rs.getFloat("amount"), "Transaction to other account");
                        list.add(transfer);
                    }
                }

            }catch (Exception e)
            {

            }
        }else if(account instanceof SavingsAccount)
        {
            try
            {
                Statement stmt=con.createStatement();
                String sql = "SELECT * FROM BankATM.single_saving_account_transactions WHERE account_id = \"" + account.getIBAN()+ "\"";
                System.out.println(sql);
                ResultSet rs=stmt.executeQuery(sql);
                while(rs.next())
                {
                    String typeOfTransaction = rs.getString("type");
                    if(typeOfTransaction.equals("Deposit"))
                    {
                        Deposit deposit = new Deposit(rs.getFloat("amount"));
                        list.add(deposit);
                    }else if(typeOfTransaction.equals("Withdrawal"))
                    {
                        Withdrawal withdrawal= new Withdrawal(rs.getFloat("amount"));
                        list.add(withdrawal);
                    }else if(typeOfTransaction.equals("Transfer"))
                    {
                        Transfer transfer = new Transfer(rs.getFloat("amount"), "Transaction to other account");
                        list.add(transfer);
                    }
                }
            }catch (Exception e)
            {

            }

        }else if(account instanceof SecurityAccount)
        {
            try
            {
                Statement stmt=con.createStatement();
                String sql = "SELECT * FROM BankATM.single_security_transactions WHERE account_id = \"" + account.getIBAN()+ "\"";
                System.out.println(sql);
                ResultSet rs=stmt.executeQuery(sql);
                while(rs.next())
                {
                    String typeOfTransaction = rs.getString("type");
                    if(typeOfTransaction.equals("Deposit"))
                    {
                        Deposit deposit = new Deposit(rs.getFloat("amount"));
                        list.add(deposit);
                    }else if(typeOfTransaction.equals("Withdrawal"))
                    {
                        Withdrawal withdrawal= new Withdrawal(rs.getFloat("amount"));
                        list.add(withdrawal);
                    }else if(typeOfTransaction.equals("Transfer"))
                    {
                        Transfer transfer = new Transfer(rs.getFloat("amount"), "Transaction to other account");
                        list.add(transfer);
                    }
                }
            }catch (Exception e)
            {

            }
        }
        return list;
    }

    public ArrayList<Transaction> getTransactions(User user) {
        ArrayList<Transaction> list = new ArrayList<>();
        ArrayList<Account> userAccounts = getAllAccounts(user);
        for(Account userAccount : userAccounts)
        {
            list.addAll(getTransactions(userAccount));
        }

        return list;
    }

    public void closeAccount(Account account)
    {
        try
        {
            //1 will work, other will throw excpetion
            Statement stmt = con.createStatement();
            String sql1 = "UPDATE BankATM.savings_accounts SET is_active = \"FALSE\" WHERE iban = \"" + account.getIBAN() + "\"";
            stmt.executeUpdate(sql1);
        }catch (Exception ex)
        {
            System.out.println("The account is not savings");
        }

        try
        {
            Statement stmt = con.createStatement();
            String sql2 = "UPDATE BankATM.checkings_accounts SET is_active = \"FALSE\" WHERE iban = \"" + account.getIBAN() + "\"";
            stmt.executeUpdate(sql2);
        }catch (Exception e)
        {
            System.out.println("The account is not checkings");
        }

        try
        {
            Statement stmt = con.createStatement();
            String sql3 = "UPDATE BankATM.security_accounts SET is_active = \"FALSE\" WHERE iban = \"" + account.getIBAN() + "\"";
            System.out.println(sql3);
            stmt.executeUpdate(sql3);
        }catch (Exception e)
        {
            System.out.println("The account is not security");
        }

    }

    public void addDeposit(Account account, Deposit deposit)
    {
        if(account instanceof CheckingAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_checking_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + deposit.getAmount() + ", \"Deposit\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else if(account instanceof SavingsAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_saving_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + deposit.getAmount() + ", \"Deposit\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }else if(account instanceof SecurityAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_security_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + deposit.getAmount() + ", \"Deposit\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void addWithdrawal(Account account, Withdrawal withdrawal)
    {
        if(account instanceof CheckingAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_checking_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + withdrawal.getAmount() + ", \"Withdrawal\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else if(account instanceof SavingsAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_saving_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + withdrawal.getAmount() + ", \"Withdrawal\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }else if(account instanceof SecurityAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_security_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + withdrawal.getAmount() + ", \"Withdrawal\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void addTransfer(Account account, Transfer transfer)
    {
        if(account instanceof CheckingAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_checking_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + transfer.getAmount() + ", \"Transfer\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else if(account instanceof SavingsAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_saving_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + transfer.getAmount() + ", \"Transfer\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }else if(account instanceof SecurityAccount)
        {
            try
            {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO single_security_account_transactions (account_id, amount, type) VALUES (\"" + account.getIBAN()+"\", " + transfer.getAmount() + ", \"Transfer\")";
                System.out.println(sql);
                sqlExecute(sql);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Transaction> getDailyTransactionsFromCheckings()
    {
        ArrayList<Transaction> list = new ArrayList<>();
        try
        {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.single_checking_account_transactions";
            System.out.println(sql);
            ResultSet rs=stmt.executeQuery(sql);
            SecurityAccount temp = null;
            while(rs.next())
            {
                String typeOfTransaction = rs.getString("type");
                if(typeOfTransaction.equals("Deposit"))
                {
                    Deposit deposit = new Deposit(rs.getFloat("amount"));
                    list.add(deposit);
                }else if(typeOfTransaction.equals("Withdrawal"))
                {
                    Withdrawal withdrawal= new Withdrawal(rs.getFloat("amount"));
                    list.add(withdrawal);
                }else if(typeOfTransaction.equals("Transfer"))
                {
                    Transfer transfer = new Transfer(rs.getFloat("amount"), "Transaction to other account");
                    list.add(transfer);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Transaction> getDailyTransactionsFromSavings()
    {
        ArrayList<Transaction> list = new ArrayList<>();
        try
        {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.single_saving_account_transactions";
            System.out.println(sql);
            ResultSet rs=stmt.executeQuery(sql);
            SecurityAccount temp = null;
            while(rs.next())
            {
                String typeOfTransaction = rs.getString("type");
                if(typeOfTransaction.equals("Deposit"))
                {
                    Deposit deposit = new Deposit(rs.getFloat("amount"));
                    list.add(deposit);
                }else if(typeOfTransaction.equals("Withdrawal"))
                {
                    Withdrawal withdrawal= new Withdrawal(rs.getFloat("amount"));
                    list.add(withdrawal);
                }else if(typeOfTransaction.equals("Transfer"))
                {
                    Transfer transfer = new Transfer(rs.getFloat("amount"), "Transaction to other account");
                    list.add(transfer);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Transaction> getDailyTransactionsFromSecurity()
    {
        ArrayList<Transaction> list = new ArrayList<>();
        try
        {
            Statement stmt=con.createStatement();
            String sql = "SELECT * FROM BankATM.single_security_account_transactions";
            System.out.println(sql);
            ResultSet rs=stmt.executeQuery(sql);
            SecurityAccount temp = null;
            while(rs.next())
            {
                String typeOfTransaction = rs.getString("type");
                if(typeOfTransaction.equals("Deposit"))
                {
                    Deposit deposit = new Deposit(rs.getFloat("amount"));
                    list.add(deposit);
                }else if(typeOfTransaction.equals("Withdrawal"))
                {
                    Withdrawal withdrawal= new Withdrawal(rs.getFloat("amount"));
                    list.add(withdrawal);
                }else if(typeOfTransaction.equals("Transfer"))
                {
                    Transfer transfer = new Transfer(rs.getFloat("amount"), "Transaction to other account");
                    list.add(transfer);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }


    /* manager - daily report on transactions */
    public ArrayList<Transaction> getDailyTransactions() {

        ArrayList<Transaction> list = new ArrayList<>();
        list = getDailyTransactionsFromCheckings();
        list.addAll(getDailyTransactionsFromSavings());
        list.addAll(getDailyTransactionsFromSecurity());
        return list;
    }

    // UPDATES PENDING

    /* manager - update stock price */
    public Stock updateStockPrice(Stock stock, float price) {
        /* change the given stock's price */
        return null;
    }

    /* manager - add (or remove) more of an existing stock */
    public Stock updateStockQuantity(Stock stock, int quantity) {
        /*
        stock.totalShares += quantity
        stock.currentlyAvailableShares += quantity

        quantity could be negative too, to represent taking away shares
        in which case check if quantity > totalAvailableShares
        */
        return null;
    }


    // DELETES PENDING


    private void sqlExecute(String sql){
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void approveLoan(Loan loan)
    {
        try
        {
            Statement stmt = con.createStatement();
            String sql3 = "UPDATE BankATM.Loans SET status = \"Approved\" WHERE initial_principal = \"" + loan.getInitialPrincipal() + "\"";
            System.out.println(sql3);
            stmt.executeUpdate(sql3);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

