package Model;

public class UserFactory {

    /**
     * default constructor
     * Creates a User
     * @return UserImpl
     */
    public static User getInstance() {
        return new UserImpl();
    }

    /**
     * Overloaded constructor
     * Creates a user
     * @param uName username
     * @param pWord password
     * @param admin admin
     * @return UserImpl
     */
    public static User getInstance(String uName, String pWord, boolean admin) {
        return new UserImpl(uName, pWord, admin);
    }
}
