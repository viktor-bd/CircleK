/**
 * 
 */
package model;

/**
 * @author Rasmus Larsen, Viktor Dorph, Johannes Jensen, Malik Agerbæk, Shemon
 *         Chowdhury
 *
 */
public class Employee extends Person {
	private int employeeId;
	private boolean isManager; // true for manager, false for salesassistent

	// Constructor
	public Employee(int personId, String firstName, String lastName, String phoneNumber, String zipcode, String email,
			String address, boolean isManager) {
		super(personId, firstName, lastName, phoneNumber, zipcode, email, address);
		this.employeeId = personId;
		this.isManager = isManager;
	}

	/**
	 * TODO DELETE HARDCODE CONSTRUCTOR
	 * 
	 * @param i
	 */
	public Employee(int i) {
		super(i, "x", "x", "x", "x", "x", "x");
		this.employeeId = i;
		this.isManager = true;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * 
	 * @return true if employee is a manager, false if not.
	 */
	public boolean isManager() {
		return isManager;
	}

	// Changes the boolean state of isManager
	public void setManager(boolean manager) {
		isManager = manager;
	}

}
