package Model;

import java.io.Serializable;

/**
 * Models a user
 */
public class UserImpl implements User, Serializable{
    private String username;
    private String password;
    private boolean admin;

    /**
     * default constructor
     */
    public UserImpl() {
    }

    /**
     * overloaded constructor
     *
     * @param uName user name
     * @param pWord password
     * @param admin is admin
     */
    public UserImpl(String uName, String pWord, boolean admin) {
        this.setUsername(uName);
        this.setPassword(pWord);
        this.setAdmin(admin);
    }

    /**
     * gets username
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * sets this username
     *
     * @param username
     */
    public void setUsername(String username) {
        if (username.equals("") || username == null) {
            throw new IllegalArgumentException("username must not be null or empty");
        }
        this.username = username;
    }

    /**
     * gets password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * sets this password
     *
     * @param password
     */
    public void setPassword(String password) {
        if (password.equals("") || password == null) {
            throw new IllegalArgumentException("Password must not be null or empty");
        }
        this.password = password;
    }

    /**
     * gets Admin
     *
     * @return admin
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * sets admin
     *
     * @param admin
     */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

}
