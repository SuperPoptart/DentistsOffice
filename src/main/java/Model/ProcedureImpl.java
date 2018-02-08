package Model;

/**
 * 
 */
public class ProcedureImpl implements Procedure{

    private Patient patient;
    private Provider provider;
    private String code;
    private String description;
    private double amount;

    public ProcedureImpl(){}

    public ProcedureImpl(Patient patient, Provider provider, String code, String desc, double amount){
        this.setPatient(patient);
        this.setProvider(provider);
        this.setCode(code);
        this.setDescription(desc);
        this.setAmount(amount);
    }

    @Override
    public Patient getPatient() {
        return patient;
    }

    @Override
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public Provider getProvider() {
        return provider;
    }

    @Override
    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
