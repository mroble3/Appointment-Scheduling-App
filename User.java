package model;

/**
 * User class.
 */
public class User {

    private int id;
    private String username;
    private String password;

    /**
     * Constructor for User object.
     *
     * @param id user ID
     * @param username username
     * @param password password
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * setter for User Id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for User Id.
     */
    public int getId() {
        return id;
    }

    /**
     * setter for User Username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter for User Username.
     **/
    public String getUsername() {
        return username;
    }

    /**
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     @param password the user's password     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Object.toString() is overridden to return readable format of Contact object.
     * (Maybe used more than once in ComboBox field).
     */
    @Override
    public String toString() {
        return id + " - " + username;
    }
}
