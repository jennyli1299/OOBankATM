package src;
import src.DatabaseManager;

public abstract class User {

    /* member variables */
    private String firstName;
    private String lastName;
    private String id;
    private String username;
    private String password;

    /* constructor */
    public User(String firstName, String lastName, String id, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.username = username;
        this.password = password;
    }
    
    /* getters and setters */
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getfirstName() {
        return this.firstName;
    }

    public String getlastName() {
        return this.lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* event handler for clicking the login button */
    public void onPressLogin() {

        /* pull the username and password from frontend */

        /* boolean userExists = query the database to see if such a user exists */

        /* if userExists, then navigate into application */

        /* if !userExists, show an error */
        
    }

}