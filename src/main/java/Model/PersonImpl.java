package Model;

/**
 * represents a person
 */
public class PersonImpl implements Person {

    private String firstName;
    private String lastName;
    private int ID;

    /**
     * default constructor
     */
    public PersonImpl() {
    }

    /**
     * overloaded constructor
     *
     * @param fname first name
     * @param lName last name
     * @param id    ID number
     */
    public PersonImpl(String fname, String lName, int id) {
        this.setFirstName(fname);
        this.setLastName(lName);
        this.setID(id);

    }

    /**
     * gets the first name
     *
     * @return first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the first name
     *
     * @param firstName first name
     */
    public void setFirstName(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("You need to enter a first name");
        }
        this.firstName = firstName;
    }

    /**
     * gets the last name
     *
     * @return last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the last name
     *
     * @param lastName last name
     */
    public void setLastName(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("You need to enter a last name");
        }
        this.lastName = lastName;
    }

    /**
     * gets an ID
     *
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * sets an ID number
     *
     * @param ID ID number
     */
    public void setID(int ID) {
        if (ID == 0) {
            throw new IllegalArgumentException("You need an ID number greater than 0");
        }
        this.ID = ID;
    }
}
