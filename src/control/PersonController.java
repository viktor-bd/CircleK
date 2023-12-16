/**
 * 
 */
package control;

import java.sql.SQLException;

import dataaccesslayer.DataAccessException;
import dataaccesslayer.PersonDB;
import model.Customer;
import model.Employee;

/**
 * @author 
 *
 */
public class PersonController {
	private PersonDB personDB;

	/**
	 * @throws DataAccessException 
	 * 
	 */
	public PersonController() throws DataAccessException {
		// TODO Auto-generated constructor stub
		this.personDB = new PersonDB();
	}

	public Customer lookUpCustomerInDB(String validPhoneNumber) throws DataAccessException {
		return personDB.lookUpCustomerInDBWithPhoneNumber(validPhoneNumber);

	}
	public Customer findCustomerByCustomerId(int customerId) {
		return personDB.findCustomerWithCustomerId(customerId);
	}

	public Customer getCustomerFromOrderId(int orderId) throws SQLException {
		return personDB.getCustomerWithOrderId(orderId);
	}

	public Employee getEmployeeFromOrderId(int orderId) throws SQLException {
		return personDB.getEmployeeWithOrderId(orderId);
	}
	

}
