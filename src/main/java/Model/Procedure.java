package Model;

public interface Procedure {
    public Patient getPatient();
    public void setPatient(Patient p);
    public Provider getProvider();
    public void setProvider(Provider p);
    public String getCode();
    public void setCode(String s);
    public String getDescription();
    public void setDescription(String d);
    public double getAmount();
    public void setAmount(double a);
    
}
