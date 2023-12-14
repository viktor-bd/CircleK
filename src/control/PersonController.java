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
	 * 
	 */
	public PersonController(PersonDB personDB) {
		// TODO Auto-generated constructor stub
		this.personDB = personDB;
	}

	public Customer lookUpCustomerInDB(String validPhoneNumber) throws DataAccessException {
		// TODO Auto-generated method stub
		Customer customerFound = personDB.lookUpCustomerInDB(validPhoneNumber);
		return customerFound;
	}

	

}
