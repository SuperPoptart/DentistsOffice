package Model;

/**
 * creates an instance of Patient
 */
public class PatientFactory {
    /**
     * default patient
     *
     * @return default patient
     */
    public static Patient getInstance() {
        return new PatientImpl();
    }

    /**
     * overloaded patient
     *
     * @param fName     first name
     * @param lName     last name
     * @param Id        ID
     * @param pNum      phone number
     * @param insurance insurance info
     * @param pCard     payment card
     * @return patient with info
     */
    public static Patient getInstance(String fName, String lName, int Id, long pNum, Insurance insurance, String pCard) {
        return new PatientImpl(fName, lName, Id, pNum, insurance, pCard);
    }


}
