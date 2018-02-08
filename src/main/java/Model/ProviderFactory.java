package Model;

/**
 * creates an instance of provider
 */
public class ProviderFactory {
    /**
     * default provider
     *
     * @return provider
     */
    public static Provider getInstance() {
        return new ProviderImpl();
    }

    /**
     * overloaded provider
     *
     * @param fName first name
     * @param lName last name
     * @param id    ID
     * @param title title
     * @return overloaded provider
     */
    public static Provider getInstance(String fName, String lName, int id, String title) {
        return new ProviderImpl(fName, lName, id, title);
    }
}
