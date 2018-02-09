package Model;

/**
 * Creates an instance of Procedure
 */
public class ProcedureFactory {

    /**
     * Default Procedure
     *
     * @return procedure
     */
    public static Procedure getInstance() {
        return new ProcedureImpl();
    }

    /**
     * Overloaded Procedure
     *
     * @param patient  Patient object
     * @param provider Provider Object
     * @param code     Procedure code
     * @param desc     Description of Procedure
     * @param amount   Cost of Procedure
     * @return Procedure
     */
    public static Procedure getInstance(Patient patient, Provider provider, String code, String desc, double amount) {
        return new ProcedureImpl(patient, provider, code, desc, amount);
    }
}
