package Model;

public class PatientImpl extends PersonImpl {

    private String firstName;
    private String lastName;
    private int ID;
    private long phoneNumber;
    private Insurance insurance;
    private String paymentCard;

    public PatientImpl() {
    }

    public PatientImpl(String fName, String lName, int ID, long pNum, Insurance insurance, String pCard) {
        super(fName, lName, ID);
        this.setPhoneNumber(pNum);
        this.setInsurance(insurance);
        this.setPaymentCard(pCard);
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        if (phoneNumber < 1000000000) {
            throw new IllegalArgumentException("Every phone number is 14 digits long and must start with one or greater!");
        }
        this.phoneNumber = phoneNumber;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        if (insurance == null) {
            throw new IllegalArgumentException("You must enter an insurance");
        }
        this.insurance = insurance;
    }

    public String getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(String paymentCard) {
        if (paymentCard.length() < 24 || paymentCard.length() > 24) {
            throw new IllegalArgumentException("You must have a card length that is 24 digits long!");
        }
        this.paymentCard = paymentCard;
    }
}
