package Model;

import java.io.Serializable;

/**
 * represents a provider
 */
public class ProviderImpl extends PersonImpl implements Provider, Serializable {

    private String firstName;
    private String lastName;
    private String title;
    private int id;

    /**
     * default constructor
     */
    public ProviderImpl() {
    }

    /**
     * overloaded constructor
     *
     * @param fName first name
     * @param lName last name
     * @param title title
     * @param Id    ID
     */
    public ProviderImpl(String fName, String lName, int Id, String title) {
        super(fName, lName, Id);
        this.setTitle(title);
    }

    /**
     * gets title
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets title
     *
     * @param title title
     */
    public void setTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("You need a title!");
        }
        this.title = title;
    }

    /**
     * gets id
     *
     * @return id
     */
    @Override
    public int getId() {
        return super.getID();
    }

    /**
     * sets ID
     *
     * @param id id
     */
    @Override
    public void setId(int id) {
        super.setID(id);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + this.getTitle();
    }
}
