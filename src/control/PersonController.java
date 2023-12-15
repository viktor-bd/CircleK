/**
 * 
 */
package control;

import dataaccesslayer.DataAccessException;
import dataaccesslayer.PersonDB;
import model.Customer;

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
		// TODO Auto-generated method stub
		Customer customerFound = personDB.lookUpCustomerInDB(validPhoneNumber);
		return customerFound;
	}
	public Customer findCustomerByCustomerId(int customerId) {
		Customer customer = personDB.findCustomerById(customerId);
		
	}
	

}
