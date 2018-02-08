package Model;

/**
 * creates an instance of insurance
 */
public class InsuranceFactory {
    /**
     * default insurance
     *
     * @return insurance
     */
    public static Insurance getInstance() {
        return new InsuranceImpl();
    }

    /**
     * overloaded insurance
     *
     * @param cName company name
     * @param gId   group ID
     * @param mId   member ID
     * @return insurance
     */
    public static Insurance getInstance(String cName, long gId, String mId) {
        return new InsuranceImpl(cName, gId, mId);
    }
}