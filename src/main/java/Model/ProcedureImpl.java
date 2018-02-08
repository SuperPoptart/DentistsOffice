package Model;

/**
 *represents a procedure
 */
public class ProcedureImpl implements Procedure{

    private Patient patient;
    private Provider provider;
    private String code;
    private String description;
    private double amount;

    /**
     * default constructor
     */
    public ProcedureImpl(){}

    /**
     * overloaded constructor
     * @param patient patient
     * @param provider provider
     * @param code procedure code
     * @param desc description
     * @param amount amount
     */
    public ProcedureImpl(Patient patient, Provider provider, String code, String desc, double amount){
        this.setPatient(patient);
        this.setProvider(provider);
        this.setCode(code);
        this.setDescription(desc);
        this.setAmount(amount);
    }

    /**
     * gets patient
     * @return patient
     */
    @Override
    public Patient getPatient() {
        return patient;
    }

    /**
     * sets patient
     * @param patient patient
     */
    @Override
    public void setPatient(Patient patient) {
        if(patient == null){
            throw new IllegalArgumentException("You need a patient");
        }
        this.patient = patient;
    }

    /**
     * gets provider
     * @return provider
     */
    @Override
    public Provider getProvider() {
        return provider;
    }

    /**
     * sets provider
     * @param provider provider
     */
    @Override
    public void setProvider(Provider provider) {
        if(provider == null){
            throw new IllegalArgumentException("You need a provider");
        }
        this.provider = provider;
    }

    /**
     * gets code
     * @return code
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * sets code
     * @param code code
     */
    @Override
    public void setCode(String code) {
        if(code == null || !code.contains("D") || code.length()!=6){
            throw new IllegalArgumentException("Code must be 6 characters long and start with D");
        }
        this.code = code;
    }

    /**
     * gets description
     * @return description
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * sets description
     * @param description description
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets amount
     * @return amount
     */
    @Override
    public double getAmount() {
        return amount;
    }

    /**
     * sets amount
     * @param amount amount
     */
    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
