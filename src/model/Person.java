/**
 * 
 */
package model;

/**
 * @author 
 *
 */
public class Person {
    private int personId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String zipcode;
    private String email;
    private String address;

    // Constructor
    //TODO Decide personID implementation (int value found from controller to DB call)
    public Person(int personId, String firstName, String lastName, String phoneNumber,
                  String zipcode, String email, String address) {
        this.personId = personId; 
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.zipcode = zipcode;
        this.email = email;
        this.address = address;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Additional methods or validation checks can be added as needed
}
