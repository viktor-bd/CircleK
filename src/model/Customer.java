/**
 * 
 */
package model;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon Chowdhury 
 *
 */
public class Customer extends Person {
    private int customerId; //TODO Same as employeeID, check whether its needed to duplicate to differentiate: Fix constructor if wanted
    private Boolean isBusiness; // Allow null, 1 for business, 0 for private

    // Constructor
    public Customer(int personId, String firstName, String lastName, String phoneNumber,
                    String zipcode, String email, String address, Boolean isBusiness) {
        super(personId, firstName, lastName, phoneNumber, zipcode, email, address);
        this.customerId = personId;
        this.isBusiness = isBusiness;
    }

    // Getter and Setter for customerId
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    // Getter and Setter for isBusiness
    public Boolean getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(Boolean isBusiness) {
        this.isBusiness = isBusiness;
    }

    /**
     * Returns whether or not the customer is a business.
     *
     * @return true if the customer is a business, false if private. This can be null (!= null && isBusiness)
     */
    public boolean isBusiness() {
        return isBusiness;
    }
}