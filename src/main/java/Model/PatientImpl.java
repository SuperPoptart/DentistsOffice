package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * represents a patient
 */
public class PatientImpl extends PersonImpl implements Patient, Serializable {

    private String firstName;
    private String lastName;
    private int ID;
    private long phoneNumber;
    private InsuranceImpl insuranceImpl;
    private String paymentCard;

    /**
     * default constructor
     */
    public PatientImpl() {
    }

    /**
     * overloaded constructor
     *
     * @param fName         first name
     * @param lName         last name
     * @param ID            ID
     * @param pNum          phone number
     * @param insuranceImpl insuranceImpl info
     * @param pCard         payment card
     */
    public PatientImpl(String fName, String lName, int ID, long pNum, InsuranceImpl insuranceImpl, String pCard) {
        super(fName, lName, ID);
        this.setPhoneNumber(pNum);
        this.setInsuranceImpl(insuranceImpl);
        this.setPaymentCard(pCard);
    }

    /**
     * gets phone number
     *
     * @return phone number
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * sets phone number
     *
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(long phoneNumber) {
        if (phoneNumber < 1000000000) {
            throw new IllegalArgumentException("Every phone number is 14 digits long and must start with one or greater!");
        }
        this.phoneNumber = phoneNumber;
    }

    /**
     * gets insuranceImpl
     *
     * @return insuranceImpl
     */
    public InsuranceImpl getInsuranceImpl() {
        return insuranceImpl;
    }

    /**
     * sets insuranceImpl
     *
     * @param insuranceImpl insuranceImpl
     */
    public void setInsuranceImpl(InsuranceImpl insuranceImpl) {
        if (insuranceImpl == null) {
            throw new IllegalArgumentException("You must enter an insuranceImpl");
        }
        this.insuranceImpl = insuranceImpl;
    }

    /**
     * gets payment card
     *
     * @return payment card
     */
    public String getPaymentCard() {
        return paymentCard;
    }

    /**
     * sets payment card
     *
     * @param paymentCard payment card
     */
    public void setPaymentCard(String paymentCard) {
        if (paymentCard.length() < 16 || paymentCard.length() > 16) {
            throw new IllegalArgumentException("You must have a card length that is 16 digits long!");
        }
        this.paymentCard = paymentCard;
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
     * sets id
     *
     * @param id id
     */
    @Override
    public void setId(int id) {
        super.setID(id);
    }

    @Override
    public String toString() {
        return super.toString() + ", " + this.getPaymentCard() + ", " + this.getPhoneNumber() + ", " + this.getInsuranceImpl().toString();
    }

    public void setName(String fName, String lName) {
        super.setFirstName(fName);
        super.setLastName(lName);
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

}
