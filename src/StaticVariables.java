package src;

public class StaticVariables
{
    private static User loggedInUser = null;
    private static DatabaseManager databaseManager = null;
    private static Account selectedAccount = null;

    public static User getLoggedInUser()
    {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggedInUser)
    {
        StaticVariables.loggedInUser = loggedInUser;
    }

    public static DatabaseManager getDatabaseManager()
    {
        return databaseManager;
    }

    public static void setDatabaseManager(DatabaseManager databaseManager)
    {
        StaticVariables.databaseManager = databaseManager;
    }

    public static Account getSelectedAccount()
    {
        return selectedAccount;
    }

    public static void setSelectedAccount(Account selectedAccount)
    {
        StaticVariables.selectedAccount = selectedAccount;
    }
}
