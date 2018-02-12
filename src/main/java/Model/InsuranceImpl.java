package Model;

import java.io.Serializable;

/**
 * represents insurance information
 */
public class InsuranceImpl implements Insurance, Serializable{

    private String companyName;
    private long groupID;
    private String memberID;

    /**
     * default constructor
     */
    public InsuranceImpl() {
    }

    /**
     * overloaded constructor
     *
     * @param cName company name
     * @param gId   group ID
     * @param mId   member ID
     */
    public InsuranceImpl(String cName, long gId, String mId) {
        this.setCompanyName(cName);
        this.setGroupID(gId);
        this.setMemberID(mId);
    }

    /**
     * gets the company name
     *
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * sets the company name
     *
     * @param companyName company name
     */
    public void setCompanyName(String companyName) {
        if (companyName == null || companyName.isEmpty()) {
            throw new IllegalArgumentException("You need to enter a company name");
        }
        this.companyName = companyName;
    }

    /**
     * gets the group ID
     *
     * @return group ID
     */
    public long getGroupID() {
        return groupID;
    }

    /**
     * sets the group ID
     *
     * @param groupID group ID
     */
    public void setGroupID(long groupID) {
        if (groupID == 0) {
            throw new IllegalArgumentException("You need a group ID");
        }
        this.groupID = groupID;
    }

    /**
     * gets the member ID
     *
     * @return member ID
     */
    public String getMemberID() {
        return memberID;
    }

    /**
     * sets the member ID
     *
     * @param memberID member ID
     */
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    @Override
    public String toString() {
        return this.getCompanyName() + ", " + this.getGroupID() + ", " + this.getMemberID();
    }
}
