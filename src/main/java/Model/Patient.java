package Model;

public interface Patient {

    public long getPhoneNumber();

    public void setPhoneNumber(long phoneNumber);

    public InsuranceImpl getInsuranceImpl();

    public void setInsuranceImpl(InsuranceImpl insuranceImpl);

    public String getPaymentCard();

    public void setPaymentCard(String paymentCard);
}
